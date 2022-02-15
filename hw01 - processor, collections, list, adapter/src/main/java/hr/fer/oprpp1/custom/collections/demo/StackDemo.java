package hr.fer.oprpp1.custom.collections.demo;


import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length != 1) {
			throw new IllegalArgumentException("Must have EXCATLY one argument.");
		}
		
		String [] characters = args[0].split("\\s+"); 
		
		ObjectStack stack = new ObjectStack();
		
		for(String character:characters) {
			try {
				switch (character) {
					case "+": {
						int b = (int) stack.pop();
						int a = (int) stack.pop();
						
						stack.push(a+b);
						break;
					}
					case "-": {
						int b = (int) stack.pop();
						int a = (int) stack.pop();
						
						stack.push(a-b);
						break;
					}
					case "*": {
						int b = (int) stack.pop();
						int a = (int) stack.pop();
						
						stack.push(a*b);
						break;
					}
					case "/": {
						int b = (int) stack.pop();
						int a = (int) stack.pop();
						
						if(b == 0) {
							System.out.println("Can't divide by zero!");
							System.exit(1);
						}
						
						stack.push(a/b);
						break;
					}
					case "%":{
						int b = (int) stack.pop();
						int a = (int) stack.pop();
						
						stack.push(a%b);
						break;
					}
	
					default:{
						try {
							int result = Integer.parseInt(character);
							stack.push(result);
						}
						catch (NumberFormatException e) {
							System.out.println("Invalid expression, can't parse something to int");
							System.exit(1);
						}
					}
				}
			}
			catch (EmptyStackException exc) {
               expressionNotGood(args[0]);
               System.exit(1);
			}
		}
		
		if (stack.size() != 1) {
			expressionNotGood(args[0]);
		}
		else {
			System.out.println(stack.pop());
		}
	
	}
	static void expressionNotGood(String expression) {
		System.out.println("Expression is not good, expression was: " + expression);	
	}	
}

	
