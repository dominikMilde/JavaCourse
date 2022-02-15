package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class OtherFractals {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("D:\\Downloads\\examples\\sierpinskiGasket.txt"));
			String line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] pass = new String[lines.size()];
		String[] data = lines.toArray(pass);
		
		LSystemViewer.showLSystem(createFractal(LSystemBuilderImpl::new, data));
	    
	}
	private static LSystem createFractal(LSystemBuilderProvider provider, String[] data) {
		return provider.createLSystemBuilder().configureFromText(data).build();
		
	}
}
