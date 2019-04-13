import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Assembler {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Not enough arguments supplied");
      System.out.println("  Usage: java Assembler *.asm");
      return;
    } else if (args.length > 1) {
      System.out.println("Too many arguments supplied");
      System.out.println("  Usage: java Assembler *.asm");
      return;
    } else {
      Code test = new Code();
      String testString = test.getJump("JGT");
      System.out.println(testString);
    }
  }
}
