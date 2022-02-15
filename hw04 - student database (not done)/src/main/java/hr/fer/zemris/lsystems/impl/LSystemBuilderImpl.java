package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Spliterator;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * @author Dominik
 * Implementation class of LSystemBuilder, implements methods and sets some default values
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	/**
	 * default unit length
	 */
	private double unitLength = 0.1;
	/**
	 * default unit length degree scaler
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * default origin of fractal
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * default angle
	 */
	private double angle = 0;
	/**
	 * default axiom
	 */
	private String axiom = "";
	
	/**
	 * stores pairs (Symbol, Production)
	 */
	Dictionary<Character, String> productionsDictionary = new Dictionary<>();
	/**
	 * stores pairs (Symbol, Command)
	 */
	Dictionary<Character, Command> commandDictionary = new Dictionary<>();

	/**
	 * creates LSystemNested()
	 */
	public LSystem build() {
		
		return new LSystemNested();
	}
	
	/**
	 * @author Dominik
	 * Nested class we return
	 */
	private class LSystemNested implements LSystem{

		/**
		 * Creates new context, creates new TurtleState, color BLACK, pushes it to context, 
		 * calls generate(level), calls commands which fit generated string
		 * @param level Depth to paint
		 * @param painter Reference to painter
		 * @throws NullPointerException if painter is null
		 */
		@Override
		public void draw(int level, Painter painter) {
			if(painter == null) throw new NullPointerException("Painter must not be null!");
			Context ctx = new Context();
			char[] generated = generate(level).toCharArray();
			
			double length = Math.pow(unitLengthDegreeScaler, level) * unitLength;
			Vector2D initRotation = new Vector2D(1, 0).rotated(angle*Math.PI/180.0);
			
			TurtleState ts = new TurtleState(origin, initRotation, Color.BLACK, length);
			ctx.pushState(ts);
			Command comToCommit = null;
			for(char sym : generated) {
				comToCommit = commandDictionary.get(sym);
				if(comToCommit != null) comToCommit.execute(ctx, painter);
			}
		}

		/**
		 * Generates axiom of certain depth
		 * @param level Depth of axiom to generate
		 * @return Axiom string
		 * @throws IllegalArgumentException if level is not positive
		 */
		@Override
		public String generate(int level) {
			if(level < 0) throw new IllegalArgumentException("level must be positive value!");
			
			String returned = axiom;
			for(int i=0; i<level; i++) {
				String temp = "";
				for (char sym : returned.toCharArray()) {
					String prod = productionsDictionary.get(sym);
					if (prod == null) {
						temp += sym; //leave as is
					} else {
						temp+=prod;
					}
					returned = temp;
				}
			}
			return returned;
		}
	}
	/**
	 * Sets configuration from string array
	 * @param configs Array to read from
	 * @throws NullPointerException if configs is null
	 */
	public LSystemBuilder configureFromText(String[] configs) {
		if(configs == null) throw new NullPointerException("Cannot pass null!");
		for (String config : configs) {
			if(config.strip().isBlank()) continue;
			
			String[] splitted = config.split("\\s+", 2);
			
			String command = splitted[0].toLowerCase();
			String arguments = splitted[1];
			
			switch(command) {
			case "angle":
				setAngle(Double.parseDouble(arguments));
				break;
				
			case "axiom":
				setAxiom(arguments);
				break;
				
			case "command":
				String[] splittedArgs = arguments.split("\\s+", 2);
				if(splittedArgs[0].length() > 1) throw new IllegalArgumentException("Must be one symbol per command!");
				if(splittedArgs.length != 2) throw new IllegalArgumentException("After symbol must come command to do!"); 
				
				registerCommand(splittedArgs[0].charAt(0), splittedArgs[1]);
				break;
				
			case "origin":
				String[] splittedArgsOrigin = arguments.split("\\s+", 2);
				double x = Double.parseDouble(splittedArgsOrigin[0]);
				double y = Double.parseDouble(splittedArgsOrigin[1]);
				
				setOrigin(x, y);
				break;
				
			case "production":
				String[] splittedProduction = arguments.split("\\s+", 2);
				
				registerProduction(splittedProduction[0].charAt(0), splittedProduction[1]);
				break;
				
			case "unitlength":
				setUnitLength(Double.parseDouble(arguments));
				break;
				
			case "unitlengthdegreescaler":
				String[] splittedDoubles = arguments.split("/", 2);
				if(splittedDoubles.length == 2) {
					setUnitLengthDegreeScaler(Double.parseDouble(splittedDoubles[0]) / Double.parseDouble(splittedDoubles[1]));
				}
				else if (splittedDoubles.length == 1) {
					setUnitLengthDegreeScaler(Double.parseDouble(splittedDoubles[0]));
				}
				break;
			default:
				throw new IllegalArgumentException("Illegal configuration was given!" + command);
			}
		}
		return this;
	}

	/**
	 * Puts in command dictionary (sym, command for sym)
	 */
	public LSystemBuilder registerCommand(char sym, String arg1) {
		if(arg1 == null) throw new IllegalArgumentException("Command can't be null");
		commandDictionary.put(sym, toCommand(arg1));
		return this;
	}

	/**
	 * Parses String to Command
	 * @param arg1 String to parse
	 * @return Command
	 * @throws IllegalArgumentException if parsing failed
	 */
	private Command toCommand(String arg1) {
		String [] splitted = arg1.strip().split("\\s+");
		if(splitted.length == 0) {
			throw new IllegalArgumentException("Cannot split! Command invalid");
		}
		else if(splitted.length == 1) {
			if (arg1.toLowerCase().equals("pop")) return new PopCommand();
			else if (arg1.toLowerCase().equals("push")) return new PushCommand();
			throw new IllegalArgumentException("Wrong command typed! " + arg1);
		}
		else if (splitted.length == 2) {
			String com = splitted[0];
			String argToSend = splitted[1];
			
			switch (com.toLowerCase()) {
			case "color":
				return new ColorCommand(new Color(Integer.parseInt(argToSend, 16)));
			case "draw":
				return new DrawCommand(Double.parseDouble(argToSend));
			case "rotate":
				return new RotateCommand(Double.parseDouble(argToSend));
			case "scale":
				return new ScaleCommand(Double.parseDouble(argToSend));
			case "skip":
				return new SkipCommand(Double.parseDouble(argToSend));
			default:
				throw new IllegalArgumentException("Cannot parse command, invalid command name " + com);
			}
		}
		throw new IllegalArgumentException("Cannot parse, more than 2 arguments were passed.");
		
	}

	/**
	 * Puts in command dictionary (sym, production for sym)
	 */
	public LSystemBuilder registerProduction(char sym, String prod) {
		if(prod==null) throw new NullPointerException("Production cannot be null");
		productionsDictionary.put(sym, prod);
		return this;
	}

	/**
	 *Setter method
	 */
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	/**
	 *Setter method
	 */
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	/**
	 *Setter method
	 */
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}
	/**
	 *Setter method
	 */
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	/**
	 *Setter method
	 */
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}