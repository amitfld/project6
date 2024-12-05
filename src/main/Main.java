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
        Parser parser = new Parser(args[0]);
        Parser firstParser = new Parser(args[0]);
        Code code = new Code();
        SymbolTable symbolTable = new SymbolTable();
        while (firstParser.hasMoreLines()){
            firstParser.advance();
            String instructionType1 = parser.instructionType();
            if (Objects.equals(instructionType1, "L_Instruction")){
                String toAdd = firstParser.currInstuc.split("\\(")[1].split("\\)")[0];
                symbolTable.addEntry(toAdd, parser.counter);
            }
        }

        while (parser.hasMoreLines()){
            parser.advance();
            StringBuilder line = new StringBuilder();
            String instructionType = parser.instructionType();
            if (Objects.equals(instructionType, "A_Instruction")){
                if (parser.currInstuc.startsWith("@")){
                    String var = parser.currInstuc.split("@")[1];
                    if (!symbolTable.contains(var)){
                        symbolTable.addEntry(var, parser.counter);
                    }
                } else {
                    line.append("000");
                }
            } else if (Objects.equals(instructionType, "C_Instruction")){
                line.append("111");
            }
            line.append(code.comp(parser.comp()));
            line.append(code.dest(parser.comp()));
            line.append(code.jump(parser.comp()));
            if (parser.hasMoreLines()){
                file.write(line + "\n");
            }else {
                file.write(line.toString());
            }
        }
    }
}
