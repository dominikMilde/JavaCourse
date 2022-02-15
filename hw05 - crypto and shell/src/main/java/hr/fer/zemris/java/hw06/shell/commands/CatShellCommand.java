package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("Arguments are not given");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(arguments);
		try {
			Scanner read = new Scanner(path);
			while(read.hasNextLine()) {
				env.writeln(read.nextLine());
			}
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			env.writeln("Error during IO!");
		}
		env.write("\n" + env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name that should be used to interpret chars from bytes.");
		return list;
	}
}
