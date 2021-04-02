package org.acme;

import org.acme.pda.*;
import org.apache.commons.codec.binary.StringUtils;

import javax.sound.midi.SysexMessage;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        String s = "A and (B or C)";
        String ab = "(A and B) iff (C or D)";
        String sb = "(id and id)";
        String[] split = sb.split("(?<=\\()|(?=\\))");

        String format = "";
        for (String a : split) {
            format = format + " " + a;
        }
        System.out.println(format);

        Scanner scanner = new Scanner(format);
        while (scanner.hasNext()){
            System.out.println(scanner.next());
        }

        PushDown pda = new PushDown();
        System.out.println(pda.readInput(format));
        System.out.println(pda.getAutomataStack());

    }
}

