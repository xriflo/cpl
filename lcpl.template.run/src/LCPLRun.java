import java.io.*;

import ro.pub.cs.lcpl.*;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Constructor;

public class LCPLRun {
	
	private enum Action { RUN, TRANSLATE, ERROR };
	
	static private String inputFile, outputFile;
	
	static private Action parseCommandLine(String[] args)
	{
		switch (args.length)
		{
		case 2:
			if (args[0].equals("--run"))
			{
				inputFile = args[1];
				return Action.RUN;
			}
			break;
		case 3:
			if (args[0].equals("--translate"))
			{
				inputFile = args[1];
				outputFile = args[2];
				return Action.TRANSLATE;
			}
			break;
		}
		
		return Action.ERROR;
	}

	public static void main(String[] args) {
		
		Action action = parseCommandLine(args);
		if (action == Action.ERROR)
		{
			System.err.println("Usage: LCPLRun --run <program.run>\n");
			System.err.println("Usage: LCPLRun --translate <program.run> <program.c>\n");
			System.exit(1);
		}
		
		try {
			Yaml yaml = new Yaml(new Constructor(Program.class));
			FileInputStream fis = new FileInputStream(inputFile);
			Program p = (Program) yaml.load(fis);
			fis.close();
			switch (parseCommandLine(args))
			{
			case RUN:
				new LCPLInterpreter().run(p);
				break;
			case TRANSLATE:
				PrintStream fos = new PrintStream(new FileOutputStream(outputFile));
				fos.println(new LCPLTranslatorC().translate(p));				
				fos.close();
				break;
			default:
			}			
		} catch (IOException ex) {
			System.err.println("File error: " + ex.getMessage());
			System.err.println("===================================================");
		} 		
	}

}
