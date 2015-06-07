package com.sleep.maket;

import com.sleep.maket.comands.*;
import com.sleep.spamfilter.*;
import com.sleep.spamfilter.SURBL;

import java.util.*;


public class Program {

	public static void main(String[] args) {

		SpamFilter spamFilter = new SpamFilter();
		Scanner input = new Scanner(System.in);
		SURBL surbl = new SURBL();
		TBL tbl = new TBL();

		Map<String, ICommandHandler> handlers = initCommands(spamFilter, input, surbl, tbl);

		handlers.get("help").execute(null);

		System.out.println("Bayes Message Spam-Filter");
		while (true) {
			System.out.println("Do:");
			String command = input.nextLine();
			String[] strings = command.split(" ");
			List<String> commandParts = Arrays.asList(strings);

			if (commandParts.size() > 0) {
				ICommandHandler handler = handlers.get(commandParts.get(0));
				if (handler != null) {
					Boolean result = handler.execute(commandParts);
					if (!result) {
						break;
					}
				} else {
					System.out.println("Cant do this: " + commandParts.get(0) + ". Try to use help.");
				}
			}
			System.out.println();
		}

		input.close();
	}

	private static Map<String, ICommandHandler> initCommands(SpamFilter spamFilter, Scanner input, SURBL surbl, TBL tbl) {
		HashMap<String, ICommandHandler> handlers = new HashMap<>();

		ICommandHandler helpHandler = new CommandHelp();
		handlers.put("helpme", helpHandler);
		handlers.put("help", helpHandler);
		handlers.put("man", helpHandler);
		handlers.put("?", helpHandler);

		ICommandHandler learnHandler = new CommandLearn(spamFilter, input);
		handlers.put("learn", learnHandler);

		ICommandHandler validateHandler = new CommandValidate(spamFilter, input);
		handlers.put("validate", validateHandler);

		ICommandHandler statHandler = new CommandStat(spamFilter);
		handlers.put("stat", statHandler);

		ICommandHandler saveHandler = new CommandSave(spamFilter);
		handlers.put("save", saveHandler);

		ICommandHandler urlHandler = new CommandSURBL(surbl, input);
		handlers.put("url", urlHandler);

		ICommandHandler tblHandler = new CommandTBL(tbl, input);
		handlers.put("tbl", tblHandler);
		return handlers;
	}
}
