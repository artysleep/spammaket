package com.sleep.maket;

import com.sleep.maket.comands.*;
import com.sleep.spamfilter.Bayes;
import com.sleep.spamfilter.Exchanger;
import com.sleep.spamfilter.MessagesInfo;
import com.sleep.spamfilter.WordInfo;

import java.util.*;


public class Program {

	public static void main(String[] args) {

		Exchanger exchanger = new Exchanger();

		MessagesInfo messagesInfo = exchanger.importMessagesInfo();
		Map<String, WordInfo> words = exchanger.importWordInfo();
		exchanger.destroy();

		Bayes bayes = new Bayes(messagesInfo,words);
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

		ICommandHandler saveHandler = new CommandSave(bayes);
		handlers.put("save", saveHandler);

		helpHandler.execute(null);

		System.out.println("Bayes SMS Spam-Filter");
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
}
