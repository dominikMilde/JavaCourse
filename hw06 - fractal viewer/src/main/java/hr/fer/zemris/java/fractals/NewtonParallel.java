package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	public static void main(String[] args) {
		int N = Runtime.getRuntime().availableProcessors();
		int K = 10;
		
		if(args.length==2) {
			N = Integer.parseInt(args[0]);
			K = Integer.parseInt(args[1]);
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer. Please enter at least two roots, one root per line. Enter 'done' when done.");
		int counter = 0;
		ArrayList<Complex> listOfRoots = new ArrayList<>();
		while(true) {
			System.out.println("Root" + (counter+1) + "> ");
			String rootText = scanner.nextLine();
			if(rootText.equals("done")) {
				break;
			}
			try {
				Complex root = Complex.parse(rootText);
				listOfRoots.add(root);
				counter++;
			}catch (Exception e) {
				System.out.println("wrong input!");
			}
		}
		if(counter<2) {
			System.out.println("You must enter at least 2 roots to draw fractal.");
			System.exit(0);
		}
		
		Complex[] rootArray = new Complex[counter];
		listOfRoots.toArray(rootArray);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootArray);
		System.out.println(crp);
		FractalViewer.show(new MojProducer(crp, N, K));	
	}
	
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public final static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		ComplexRootedPolynomial crp;
		ComplexPolynomial polynomial;
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial crp, ComplexPolynomial polynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.crp = crp;
			this.polynomial = polynomial;
		}
		
		@Override
		public void run() {
			int offset = yMin * width;
			for(int y = yMin; y < yMax; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					int iters = 0;
					double module = 0;
					ComplexPolynomial derived = polynomial.derive();
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < m && module > 0.002);
					int index = crp.indexOfClosestRootFor(zn, 0.001);
					data[offset++] = (short) (index+1);
				}
			}
			
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		private ComplexRootedPolynomial crp;
		private ComplexPolynomial polynomial;
		private int K;
		private int N;
		public MojProducer(ComplexRootedPolynomial crp, int N, int K) {
			this.crp = crp;
			this.polynomial = crp.toComplexPolynom();
			this.K = K;
			this.N = N;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.format("Zapocinjem izracun, koristim %s dretvi i %s poslova\n", N, K);
			int m = 16*16*16;

			short[] data = new short[width * height];
			final int brojTraka = K;
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
			
			Thread[] radnici = new Thread[N];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci;
				if(i==brojTraka-1) {
					yMax = height;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, crp, polynomial);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	}
}

