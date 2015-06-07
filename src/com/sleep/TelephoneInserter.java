package com.sleep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TelephoneInserter {

    public static void main(String[] args) {
        try {
            List<String> messages = new ArrayList<String>();
            PrintWriter writer = new PrintWriter("tns", "UTF-8");
            Scanner fileInput = new Scanner(new File("ns"), "UTF-8");
            while (fileInput.hasNextLine()) {
                writer.println(generatePhoneNumber() + " : " + fileInput.nextLine());
            }

            fileInput.close();
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static String generatePhoneNumber() {
        return "+7" + (long)(9000000000l * new Random().nextDouble() + 1000000000l);
    }
}
