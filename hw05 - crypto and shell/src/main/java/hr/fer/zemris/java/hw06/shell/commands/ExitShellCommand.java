package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class ExitShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln("GOODBYE");
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Exits from shell.");
		list.add("No arguments are needed.");
		return list;
	}

}
