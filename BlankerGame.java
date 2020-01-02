/*
this is the class file needed to support BlankerMain.java
*/

import java.util.*;
import java.io.*;

public class BlankerGame {
   private Scanner console;
   private int level;
   
   public BlankerGame(Scanner console) {
      this.console = console;
      level = 7;
   }
   
   public boolean yesTo(String prompt) {
      System.out.println(prompt + " (y/n)? ");
      String ans = console.nextLine().trim().toLowerCase();
      while (!ans.equals("y") && !ans.equals("n")) {
         System.out.println("Please answer y or n.");
         ans = console.nextLine().trim().toLowerCase();
      }
      return ans.equals("y");
   }
   
   public void blank(Scanner input, PrintStream output) {
      Random r = new Random();
      while (input.hasNextLine()) {
         Scanner line = new Scanner(input.nextLine());
         int blank = r.nextInt(level);
         int its = 0;
         while (line.hasNext()) {
            output.print(" ");
            String token = line.next();
            // check for punctuation
            boolean punctuation = false;
            String punc = "";
            if (!Character.isLetter(token.charAt(token.length()-1))) {
               punctuation = true;
               punc = token.substring(token.length()-1);
               token = token.substring(0,token.length()-1);
            }
            
            if (its == blank) {
               // replace this token with a blank
               String blanks = "";
               for (int i = 0; i < token.length(); i++) {
                  blanks += "_";
               }
               output.print(blanks);
            } else {
               // add this word to output
               output.print(token);
            }
            // add punctuation if present
            if (punctuation) {
               output.print(punc);
            }
            output.print(" ");
            its++;
            if (its >= level) {
               its = 0;
            }
         }
         output.println();
      }
   }
   
   public void fillIn(Scanner key, Scanner blanked) {
      ArrayList<String> errors = new ArrayList<>();
      int correct = 0;
      int total = 0;
      while (key.hasNextLine() && blanked.hasNextLine()) {
         String keyL = key.nextLine();
         Scanner keyLine = new Scanner(keyL);
         Scanner blankedLine = new Scanner(blanked.nextLine());
         while (keyLine.hasNext() && blankedLine.hasNext()) {
            String keyToken = keyLine.next();
            String blankedToken = blankedLine.next();
            System.out.print(blankedToken + " ");
            if (!keyToken.equals(blankedToken)) {
               // prompt for guess
               total++;
               String guess = console.next();
               String guessStripped = guess.replaceAll("[\\W]", ""); // strip non-word chars
               if (guessStripped.equalsIgnoreCase(keyToken.replaceAll("[\\W]",""))) {
                  correct++;
               } else {
                  errors.add(keyL);
                  errors.add(guess);
                  errors.add(keyToken);
               }
            }
         }
         System.out.println();
      }
      // end session
      System.out.println();
      System.out.println("Session done. You got " + correct + " out of " + total + " words correct!");
      System.out.println();
      boolean viewError = yesTo("Would you like to see your errors");
      if (viewError) {
         System.out.println();
         int i = 0;
         while (i < errors.size()) {
            System.out.println("For \"" + errors.get(i++) + "\",");
            System.out.println("you wrote \"" + errors.get(i++) + "\" instead of \"" + errors.get(i++) + "\".");
            System.out.println();
         }
      }
   }

}