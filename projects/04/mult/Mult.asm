// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// int i = 1;
// int R2 = 0;
// while (i <= R0) {
//    R2=R2+R1;
// }
// return R2;

  @i    // int i
  M=1   // i = 1
  @2
  M=0
(LOOP)
  @i    // M = i
  D=M   // 16-bit D Register = M = i
  @0    // RAM[0]
  D=D-M // D=D-RO == i=i-r0
  @END
  D;JGT // if D > 0 goto END
  @i
  M=M+1 // i=i++
  @1
  D=M   // 16-bit D Register = M = RAM[1]
  @2
  M=M+D // 16-bit D Register = M+D
  @LOOP
  0;JMP
(END)
  @END  // Reference label "(END)"
  0;JMP // Infinite loop, apparently the "standard way" to terminate hack programs per page 65
