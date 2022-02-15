package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SettingsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted = arguments.split("\\s+");
		String s = splitted[0];
		if(splitted.length == 1) {
			if(s.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol()+"'");
			}
			else if(s.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol()+"'");
			}
			else if(s.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINES is '" + env.getMultilineSymbol()+"'");
			}
			else {
				env.writeln("Argument is wrong! Wrong symbol is wanted!");
			}
		}
		
		else if(splitted.length == 2) {
			Character a = splitted[1].charAt(0);
			if(s.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed form '" + env.getPromptSymbol()+"' to '" + a + "'");
				env.setPromptSymbol(a);
			}
			else if(s.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed form '" + env.getMorelinesSymbol()+"' to '" + a + "'");
				env.setMorelinesSymbol(a);
			}
			else if(s.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINES changed form '" + env.getMultilineSymbol()+"' to '" + a + "'");
				env.setMultilineSymbol(a);
			}
			else {
				env.writeln("Argument is wrong! Wrong symbol is wanted!");
			}
		}
		
		else {
			env.writeln("Must provide argument!");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list.add("Command takes one or two arguments.");
		list.add("Writes or changes symbols used.");
		return list;
	}

}
