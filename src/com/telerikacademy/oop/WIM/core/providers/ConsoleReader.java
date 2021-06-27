package com.telerikacademy.oop.WIM.core.providers;


import com.telerikacademy.oop.WIM.core.contracts.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.util.Scanner;


public class ConsoleReader implements Reader {
    private final BufferedReader br;
    //private final Scanner scanner;

    public ConsoleReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
        // scanner = new Scanner(System.in);
    }

    public String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Input");
        }
        //return scanner.nextLine();
    }

}
