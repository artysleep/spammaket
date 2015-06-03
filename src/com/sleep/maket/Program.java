package com.sleep.maket;

import com.sleep.spamfilter.Bayes;

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


		Bayes bayes = new Bayes();
		List<String> list = new ArrayList<String>();
		Scanner in = null;
		try {
			in = new Scanner(new File("spam.txt"), "UTF-8");
			while (in.hasNextLine()) {
				list.add(in.nextLine());
			}

			for (String line : list) {
				bayes.learn(line, true);
				//System.out.println(line);
			}

			System.out.println(bayes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}





	}

}
