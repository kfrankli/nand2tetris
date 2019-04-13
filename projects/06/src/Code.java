import java.util.HashMap;

/*
 * Specified by section 6.3.2 The Code Module, page 114
 */
public class Code {
  public static HashMap<String,String> compInstructionMap = new HashMap<String, String>();

  public static HashMap<String,String> destMap = new HashMap<String, String>();
  public static HashMap<String,String> jumpMap = new HashMap<String, String>();

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
    compInstructionMap.put("0","101010");
    compInstructionMap.put("1","111111");
    compInstructionMap.put("-1","111010");
    compInstructionMap.put("D","001100");
    compInstructionMap.put("A","110000");
    compInstructionMap.put("!D","001101");
    compInstructionMap.put("!A","110001");
    compInstructionMap.put("-D","001111");
    compInstructionMap.put("-A","110011");
    compInstructionMap.put("D+1","011111");
    compInstructionMap.put("A+1","110111");
    compInstructionMap.put("D-1","001110");
    compInstructionMap.put("A-1","110010");
    compInstructionMap.put("D+A","000010");
    compInstructionMap.put("D-A","010011");
    compInstructionMap.put("A-D","000111");
    compInstructionMap.put("D&A","000000");
    compInstructionMap.put("D|A","010101");

    // Compute Instruction table
    //put all comp posibilities with M into a HashMap,a=1
    compInstructionMap.put("M","110000");
    compInstructionMap.put("!M","110001");
    compInstructionMap.put("-M","110011");
    compInstructionMap.put("M+1","110111");
    compInstructionMap.put("M-1","110010");
    compInstructionMap.put("D+M","000010");
    compInstructionMap.put("D-M","010011");
    compInstructionMap.put("M-D","000111");
    compInstructionMap.put("D&M","000000");
    compInstructionMap.put("D|M","010101");

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
  }
  // 3 bits Returns the binary code of the dest mnemonic
  public String getDest(String mnemonic) {
    return destMap.get(mnemonic);
  }
  // 7 bits Returns the binary code of the comp mnemonic.
  public String getComp(String mnemonic) {
    return compInstructionMap.get(mnemonic);
  }
  // 3 bits Returns the binary code of the jump mnemonic
  public String getJump(String mnemonic) {
    return jumpMap.get(mnemonic);
  }
}
