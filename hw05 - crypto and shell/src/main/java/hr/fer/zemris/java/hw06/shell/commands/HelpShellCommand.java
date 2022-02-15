package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> map = env.commands();
		
		if(arguments.length() == 0) {
			env.writeln("List of all commands:");
			
			for(Entry<String, ShellCommand> e : map.entrySet()) {
				env.writeln(e.getKey());
			}
		}
		else if(map.containsKey(arguments)) {
			env.writeln("Use of command:");
			List<String> list = map.get(arguments).getCommandDescription();
			for(String l : list) {
				env.writeln(l);
			}
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list.add("Command takes zero or one argument.");
		list.add("Writes how to use commands.");
		return list;
	}

}
