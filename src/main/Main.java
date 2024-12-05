package main;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {
    // bennnnnnn sheliiiiiiiiiiiiiii

    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            throw new IOException("Not one argument");
        }
        String fileName = args[0].split("\\.")[0] + ".hack";
        FileWriter file = new FileWriter(new File(fileName));
        Parser parser = new Parser(args[0]);
        Code code = new Code();
        while (parser.hasMoreLines()){
            parser.advance();
            StringBuilder line = new StringBuilder();
            String instructionType = parser.instructionType();
            if (Objects.equals(instructionType, "A_Instruction")){
                line.append("000");
            } else {
                line.append("111");
            }
            line.append(code.comp(parser.comp()));
            line.append(code.dest(parser.comp()));
            line.append(code.jump(parser.comp()));
            int binaryValue = Integer.parseInt(line.toString(), 2);
            file.write(binaryValue);
        }
    }
}