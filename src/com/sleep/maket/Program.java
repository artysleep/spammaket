import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
package com.sleep.maket;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub



	  // Scanner s.readLine()
	  // BufferedReader b.readLine
		List<String> list = new ArrayList<String>();
		Scanner in = null;
		try {
			in = new Scanner(new File("D:\\spam.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (in.hasNextLine())
			list.add(in.nextLine());
		String[] array = list.toArray(new String[0]);
	}

}
