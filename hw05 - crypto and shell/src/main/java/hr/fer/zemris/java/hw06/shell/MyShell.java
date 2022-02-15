package hr.fer.zemris.java.hw06.shell;


public class MyShell {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to MyShell v 1.0");
		System.out.println("> ");
		ShellStatus currentShellStatus = ShellStatus.CONTINUE;
		Environment currentEnvironment = new MyEnvironment();
		
		while(true) {
			if(currentShellStatus != ShellStatus.CONTINUE) break;
			
			String reading = currentEnvironment.readLine();
			String commandName = Parser.exctractCommandName(reading);
			String arguments = Parser.exctractArguments(reading);
			//System.out.println(arguments);
			ShellCommand command = currentEnvironment.commands().get(commandName);
			
			try {
				currentShellStatus = command.executeCommand(currentEnvironment, arguments);
			}
			catch (ShellIOException e) {
				currentShellStatus = ShellStatus.TERMINATE;
			}
			
// 			l = readLineOrLines
//			String commandName = extract from l
//			String arguments = extract from l
//			command = commands.get(commandName)
//			status = command.executeCommand(environment, arguments)
		}
	}

}
