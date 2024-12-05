package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            throw new IOException("Not one argument");
        }
        String fileName = args[0].split("\\.")[0] + ".hack";
        FileWriter file = new FileWriter(new File(fileName));

        Parser firstParser = new Parser(args[0]);
        Code code = new Code();
        SymbolTable symbolTable = new SymbolTable();
        while (firstParser.hasMoreLines()){
            firstParser.advance();
            String instructionType1 = firstParser.instructionType();
            if (Objects.equals(instructionType1, "L_Instruction")){
                String toAdd = firstParser.currInstuc.split("\\(")[1].split("\\)")[0];
                symbolTable.addEntry(toAdd, firstParser.counter);
            }
        }
        Parser parser = new Parser(args[0]);
        int counter = -1;
        while (parser.hasMoreLines()){
            parser.advance();
            counter ++;
            StringBuilder line = new StringBuilder();
            String instructionType = parser.instructionType();

            if (Objects.equals(instructionType, "A_Instruction")){
                if (parser.currInstuc.startsWith("@")){
                    String var = parser.currInstuc.split("@")[1];
                    if (!symbolTable.contains(var)){
                        symbolTable.addEntry(var, parser.counter);
                    }
                    line.append("000");
                } else {
                    line.append("000");
                }
            } else if (Objects.equals(instructionType, "C_Instruction")){
                line.append("111");
            }
            if (counter == 2){
                System.out.println(parser.currInstuc);
            }

            line.append(code.comp(parser.comp()));
            if (!parser.currInstuc.startsWith("@")){
                line.append(code.dest(parser.dest()));
                line.append(code.jump(parser.jump()));
            }else {
                String loc = parser.currInstuc.split("@")[1];
                String binaryStr = Integer.toBinaryString(symbolTable.getAddress(loc));
                line.append(binaryStr);
                System.out.println("==========\n");
                System.out.println(parser.currInstuc + "\n");
                System.out.println(loc + "\n");
                System.out.println(binaryStr + "\n");
                System.out.println("==========\n");
            }
            if (parser.hasMoreLines()){
                file.write(line + "\n");
                file.flush();
            }else {
                file.write(line.toString());
                file.flush();
            }
        }
        file.close();
    }
}
