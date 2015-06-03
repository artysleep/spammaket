package com.sleep.maket;

import com.sleep.maket.comands.*;
import com.sleep.spamfilter.Bayes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Program {

	public static void main(String[] args) {
		Bayes bayes = new Bayes();
		Scanner input = new Scanner(System.in);

		HashMap<String, ICommandHandler> handlers = new HashMap<>();

		ICommandHandler helpHandler = new CommandHelp();
		handlers.put("helpme", helpHandler);
		handlers.put("help", helpHandler);
		handlers.put("man", helpHandler);
		handlers.put("?", helpHandler);

		ICommandHandler learnHandler = new CommandLearn(bayes, input);
		handlers.put("learn", learnHandler);

		ICommandHandler validateHandler = new CommandValidate(bayes, input);
		handlers.put("validate", validateHandler);

		ICommandHandler statHandler = new CommandStat(bayes);
		handlers.put("stat", statHandler);

		helpHandler.execute(null);

		System.out.println("Hello let's fuck some spam =)");
		while (true) {
			System.out.println("What should i do?");
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
					System.out.println("Do you think that i could " + commandParts.get(0) + " you are crazy. Try to use help");
				}
			}
			System.out.println();
		}

		input.close();
	}
}
