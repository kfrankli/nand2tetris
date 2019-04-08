////  // This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed.
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// B&W Screen 256 rows x 512 columns of pixels addressed in 16-bit
//    words @SCREEN sets the A register to point to start of the memory
//    region that controls the screen. Total region is:
//        (256 rows * 512 columns) / 16-bit word = 8192 words

  @wordCount  // Variable to keep track of the memory word we are editing for
  M=0         //    the screen
(LOOP)
  @24576      // RAM[24576], sets to ASCII value of key depressed
  D=M
  @BLACK
  D;JGT       // Jump to BLACK label if D>0 (key is pressed)
  @wordCount
  D=M         // Put the value of wordCount into the D register
  @SCREEN     // Calculate what the working address is using the wordCount
  A=A+D       //    plus the screen base
  M=0        // Paint white
  @TAIL
  0;JMP
(BLACK)
  @wordCount
  D=M         // Put the value of wordCount into the D register
  @SCREEN     // Calculate what the working address is using the wordCount
  A=A+D       //    plus the screen base
  M=-1        // Paint black
(TAIL)
  @wordCount
  M=M+1       // Increment word count
  D=M
  @8192       // D=wordCount-8192
  D=D-A
  @LOOP
  D;JLT       // Jump if (wordCount-8192)<0
  @wordCount
  M=0         // wordCount =0
  @LOOP
  0;JMP       // Always jump to the label loop, while (true)
