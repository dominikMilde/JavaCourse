package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> charsets = Charset.availableCharsets(); 
		for(Entry<String, Charset> entry : charsets.entrySet()) {
			env.writeln(entry.getKey());
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "charset";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("takes no arguments and lists names of supported charsets");
		return list;
	}

}
