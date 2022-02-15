package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

public class MyEnvironment implements Environment{
	
	private Character PROMPTSYMBOL = '>';
	private Character MORELINESSYMBOL = '\\';
	private Character MULTILINESYMBOL = '|';
	
	SortedMap<String, ShellCommand> commandMap;
	
	public MyEnvironment() {
		commandMap = new TreeMap<>();
		
		ShellCommand cat = new CatShellCommand();
		commandMap.put(cat.getCommandName(), cat);
		ShellCommand charset = new CharsetShellCommand();
		commandMap.put(charset.getCommandName(), charset);
		ShellCommand copy = new CopyShellCommand();
		commandMap.put(copy.getCommandName(), copy);
		ShellCommand exit = new ExitShellCommand();
		commandMap.put(exit.getCommandName(), exit);
		ShellCommand hexdump = new HexdumpShellCommand();
		commandMap.put(hexdump.getCommandName(), hexdump);
		ShellCommand ls = new LsShellCommand();
		commandMap.put(ls.getCommandName(), ls);
		ShellCommand mkdir = new MkdirShellCommand();
		commandMap.put(mkdir.getCommandName(), mkdir);
		ShellCommand tree = new TreeShellCommand();
		commandMap.put(tree.getCommandName(), tree);
		ShellCommand settings = new SettingsShellCommand();
		commandMap.put(settings.getCommandName(), settings);
		ShellCommand help = new HelpShellCommand();
		commandMap.put(help.getCommandName(), help);
	}
	
	@Override
	public String readLine() throws ShellIOException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String l = scanner.nextLine();
		String out = "";
		while(l.endsWith(MORELINESSYMBOL.toString())) { //promjena char u Character
			out += l;
			out = out.substring(0, l.length()-1);
			System.out.print(MULTILINESYMBOL + " ");
			l=scanner.nextLine();
		}
		out += l.strip();
		return out;
		
		//TODO zašto zadnji argument ne prenese dobro
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		// TODO Auto-generated method stub
		return commandMap;
	}

	@Override
	public Character getMultilineSymbol() {
		// TODO Auto-generated method stub
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		// TODO Auto-generated method stub
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		// TODO Auto-generated method stub
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		// TODO Auto-generated method stub
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		// TODO Auto-generated method stub
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		// TODO Auto-generated method stub
		MORELINESSYMBOL = symbol;
	}
}
