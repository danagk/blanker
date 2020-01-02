import java.util.*;
import java.io.*;

public class BlankerMain {
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      BlankerGame blanker = new BlankerGame(console);
      System.out.println("Welcome to blanker.");
      System.out.println();
      
      // prompt for instructions
      System.out.println("Would you like instructions to use this application?");
      boolean instructions = blanker.yesTo("Please answer yes if it is your first time");
      if (instructions) {
         Scanner instr = new Scanner(new File("instructions.txt"));
         while (instr.hasNextLine()) {
            System.out.println(instr.nextLine());
         }
         // let user try an example
         boolean trial = blanker.yesTo("Would you like to try an example");
         if (trial) {
            System.out.print("\nI'm not superstitious, but I am a little ________. ");
            String guess = console.nextLine();
            String guessStripped = guess.replaceAll("[\\W]","");
            if (guessStripped.equalsIgnoreCase("stitious")) {
               System.out.println("Great! You got it right. You're ready to go.");
            } else {
               System.out.println("Sorry, the answer was \"stitious\", but you typed \"" + guess + "\".");
               System.out.println("Oh well! You'll get the real thing right. Let's start.");
            }
         }
      }
      
      boolean again = true;
      while (again) {
      
         // ask for input file
         System.out.println("\nWhat is the name of the input file? ");
         String origFile = console.nextLine();
         while (!origFile.endsWith(".txt")) {
            System.out.println("Invalid file name. This application only works with .txt files!");
            System.out.println("Please enter a valid file name.");
            origFile = console.nextLine();
         }
         Scanner input = new Scanner(new File(origFile));
         
         // save blanked version of file
         String blankedFile = "blanked-" + origFile;
         PrintStream output = new PrintStream(new File(blankedFile));
         blanker.blank(input, output);
         System.out.println("File has been successfully blanked. Saved as \"" + blankedFile + "\".");
         System.out.println();
         
         // prompt for interactivity
         boolean interact = blanker.yesTo("Would you like to fill in the blanks interactively");
         System.out.println();
         if (interact) {
            blanker.fillIn(new Scanner(new File(origFile)), new Scanner(new File(blankedFile)));
         }
         
         // prompt to play again
         again = blanker.yesTo("Would you like to blank another file");
      
      }
      
      System.out.println("\nOkay, bye.");
   }

}