package com.sleep.maket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub



	  // Scanner s.readLine()
	  // BufferedReader b.readLine
		List<String> list = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File("spam.txt"));
			while (in.hasNextLine()) {
				list.add(in.nextLine());
			}

			for (String line : list) {
				System.out.println(line);
				System.out.println("*******");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
