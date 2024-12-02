package test;
import main.*;

import java.io.IOException;


public class Tester {
    public static void main(String[] args) {
        // Test each class
        ParserTester();
        testCode();
        testSymbolTable();
    }

        private static void ParserTester(){
            try {
                // Initialize the parser with the sample file
                Parser parser = new Parser("src/test/test.asm");

                // Test hasMoreLines and advance
                System.out.println("Testing hasMoreLines and advance:");
                while (parser.hasMoreLines()) {
                    parser.advance();
                    System.out.println("Current Instruction: " + Parser.currInstuc);

                    // Test instructionType
                    String type = parser.instructionType();
                    System.out.println("Instruction Type: " + type);

                    // Test symbol
                    if (type.equals("A_INSTRUCTION") || type.equals("L_INSTRUCTION")) {
                        System.out.println("Symbol: " + parser.symbol());
                    }

                    // Test dest, comp, and jump for C-instructions
                    if (type.equals("C_INSTRUCTION")) {
                        System.out.println("Dest: " + parser.dest());
                        System.out.println("Comp: " + parser.comp());
                        System.out.println("Jump: " + parser.jump());
                    }
                    System.out.println();
                }

                System.out.println("Parser tests completed successfully.");
            } catch (IOException e) {
                System.out.println("Error: Unable to initialize the parser. " + e.getMessage());
            }
        }

    private static void testCode() {
        System.out.println("Testing Code...");

        try {
            // Create a Code object
            Code code = new Code();

            // Test dest
            System.out.println("Dest (M): " + code.dest("M"));
            System.out.println("Dest (null): " + code.dest("null"));

            // Test comp
            System.out.println("Comp (D+1): " + code.comp("D+1"));
            System.out.println("Comp (0): " + code.comp("0"));

            // Test jump
            System.out.println("Jump (JMP): " + code.jump("JMP"));
            System.out.println("Jump (null): " + code.jump("null"));
        } catch (Exception e) {
            System.out.println("Error testing Code: " + e.getMessage());
        }

        System.out.println("Code test completed.\n");
    }

    private static void testSymbolTable() {
        System.out.println("Testing SymbolTable...");

        try {
            // Create a SymbolTable object
            SymbolTable symbolTable = new SymbolTable();

            // Test predefined symbols
            System.out.println("Address of R0: " + symbolTable.getAddress("R0"));
            System.out.println("Address of SCREEN: " + symbolTable.getAddress("SCREEN"));

            // Add and retrieve a custom symbol
            symbolTable.addEntry("LOOP", 16);
            System.out.println("Address of LOOP: " + symbolTable.getAddress("LOOP"));

            // Test contains
            System.out.println("Contains R0: " + symbolTable.contains("R0"));
            System.out.println("Contains MY_VAR: " + symbolTable.contains("MY_VAR"));
        } catch (Exception e) {
            System.out.println("Error testing SymbolTable: " + e.getMessage());
        }

        System.out.println("SymbolTable test completed.\n");
    }
}

