import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;

public class Assembler {
  public static HashMap<String,String> compAddressInstructionMap = new HashMap<String, String>();
  public static HashMap<String,String> compComputeInstructionMap = new HashMap<String, String>();

  public static HashMap<String,String> destMap = new HashMap<String, String>();
  public static HashMap<String,String> jumpMap = new HashMap<String, String>();

  public static HashMap<String,Integer> predefinedSymbolsMap = new HashMap<String, Integer>();

  static {
    /*
     * Specified by section 6.2.2 Instructions, page 109
     * a=0 == Address (A) Instruction
     * a=1 == Compute (C) Instruction
     *
     * comp |                   | comp
     * a=0  | c1 c2 c3 c4 c5 c6 | a=1
     * -------------------------------
     *  0   | 1  0  1  0  1  0  |
     *  1   | 1  1  1  1  1  1  |
     * -1   | 1  1  1  0  1  0  |
     *  D   | 0  0  1  1  0  0  |
     *  A   | 1  1  0  0  0  0  |   M
     * !D   | 0  0  1  1  0  1  |
     * !A   | 1  1  0  0  0  1  |  !M
     * -D   | 0  0  1  1  1  1  |
     * -A   | 1  1  0  0  1  1  |  -M
     * D+1  | 0  1  1  1  1  1  |
     * A+1  | 1  1  0  1  1  1  |  M+1
     * D-1  | 0  0  1  1  1  0  |
     * A-1  | 1  1  0  0  1  0  |  M-1
     * D+A  | 0  0  0  0  1  0  |  D+M
     * D-A  | 0  1  0  0  1  1  |  D-M
     * A-D  | 0  0  0  1  1  1  |  M-D
     * D&A  | 0  0  0  0  0  0  |  D&M
     * D|A  | 0  1  0  1  0  1  |  D|M
     */

    // Address Instruction table
    compAddressInstructionMap.put("0","101010");
    compAddressInstructionMap.put("1","111111");
    compAddressInstructionMap.put("-1","111010");
    compAddressInstructionMap.put("D","001100");
    compAddressInstructionMap.put("A","110000");
    compAddressInstructionMap.put("!D","001101");
    compAddressInstructionMap.put("!A","110001");
    compAddressInstructionMap.put("-D","001111");
    compAddressInstructionMap.put("-A","110011");
    compAddressInstructionMap.put("D+1","011111");
    compAddressInstructionMap.put("A+1","110111");
    compAddressInstructionMap.put("D-1","001110");
    compAddressInstructionMap.put("A-1","110010");
    compAddressInstructionMap.put("D+A","000010");
    compAddressInstructionMap.put("D-A","010011");
    compAddressInstructionMap.put("A-D","000111");
    compAddressInstructionMap.put("D&A","000000");
    compAddressInstructionMap.put("D|A","010101");

    // Compute Instruction table
    //put all comp posibilities with M into a HashMap,a=1
    compComputeInstructionMap.put("M","110000");
    compComputeInstructionMap.put("!M","110001");
    compComputeInstructionMap.put("-M","110011");
    compComputeInstructionMap.put("M+1","110111");
    compComputeInstructionMap.put("M-1","110010");
    compComputeInstructionMap.put("D+M","000010");
    compComputeInstructionMap.put("D-M","010011");
    compComputeInstructionMap.put("M-D","000111");
    compComputeInstructionMap.put("D&M","000000");
    compComputeInstructionMap.put("D|M","010101");

    /*
     * Specified by 6.2.2 Instructions, page 110
     *
     *  dest |  d1 d2 d3
     *  ----------------
     *  null |  0  0  0
     *     M |  0  0  1
     *     D |  0  1  0
     *    MD |  0  1  1
     *     A |  1  0  0
     *    AM |  1  0  1
     *    AD |  1  1  0
     *   AMD |  1  1  1
     */
    destMap.put("","000");
    destMap.put("M","001");
    destMap.put("D","010");
    destMap.put("MD","011");
    destMap.put("A","100");
    destMap.put("AM","101");
    destMap.put("AD","110");
    destMap.put("AMD","111");

    /*
     * Specified by 6.2.2 Instructions, page 110
     *  jump |  j1 j2 j3
     *  ----------------
     *  null |  0  0  0
     *   JGT |  0  0  1
     *   JEQ |  0  1  0
     *   JGE |  0  1  1
     *   JLT |  1  0  0
     *   JNE |  1  0  1
     *   JLE |  1  1  0
     *   JMP |  1  1  1
     */
    jumpMap.put("","000");
    jumpMap.put("JGT","001");
    jumpMap.put("JEQ","010");
    jumpMap.put("JGE","011");
    jumpMap.put("JLT","100");
    jumpMap.put("JNE","101");
    jumpMap.put("JLE","110");
    jumpMap.put("JMP","111");

    /*
     * Specified by section 6.2.3 Symbols, page 110
     *
     * Label  | RAM | address (hexa)
     * -----------------------------
     *  SP    | 0   | 0x0000
     *  LCL   | 1   | 0x0001
     *  ARG   | 2   | 0x0002
     *  THIS  | 3   | 0x0003
     *  THAT  | 4   | 0x0004
     *  R0-R15| 0-15| 0x0000-f
     *  SCREEN|16384| 0x4000
     *  KBD   |24576| 0x6000
     */
    predefinedSymbolsMap.put("SP",0);
    predefinedSymbolsMap.put("LCL",1);
    predefinedSymbolsMap.put("ARG",2);
    predefinedSymbolsMap.put("THIS",3);
    predefinedSymbolsMap.put("THAT",4);
    predefinedSymbolsMap.put("R0",0);
    predefinedSymbolsMap.put("R1",1);
    predefinedSymbolsMap.put("R2",2);
    predefinedSymbolsMap.put("R3",3);
    predefinedSymbolsMap.put("R4",4);
    predefinedSymbolsMap.put("R5",5);
    predefinedSymbolsMap.put("R6",6);
    predefinedSymbolsMap.put("R7",7);
    predefinedSymbolsMap.put("R8",8);
    predefinedSymbolsMap.put("R9",9);
    predefinedSymbolsMap.put("R10",10);
    predefinedSymbolsMap.put("R11",11);
    predefinedSymbolsMap.put("R12",12);
    predefinedSymbolsMap.put("R13",13);
    predefinedSymbolsMap.put("R14",14);
    predefinedSymbolsMap.put("R15",15);
    predefinedSymbolsMap.put("SCREEN",16384);
    predefinedSymbolsMap.put("KBD",24576);

  }
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Not enough arguments supplied");
      System.out.println("  Usage: java Assembler *.asm");
      return;
    } else if (args.length > 1) {
      System.out.println("Too many arguments supplied");
      System.out.println("  Usage: java Assembler *.asm");
      return;
    }
  }
}
