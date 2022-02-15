package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {

	public static void main(String[] args) {
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
		FractalViewer.show(new MojProducer(crp));
	}
	public static class MojProducer implements IFractalProducer {
		private ComplexRootedPolynomial crp;
		private ComplexPolynomial polynomial;
		public MojProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			this.polynomial = crp.toComplexPolynom();
			System.out.println(polynomial);
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
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
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	}

}
