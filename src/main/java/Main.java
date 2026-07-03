import java.util.Scanner;

import utils.ParsingUtil;
import builtins.Builtin;
import utils.CommandClassifier;
import utils.CommandType;

public class Main {   

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("$ ");
            String command = scanner.nextLine();
            String coreCommand = ParsingUtil.splitCommand(command)[0];
            String argCommand = ParsingUtil.splitCommand(command)[1];
            if(command.equals("exit")){break;}

            switch (CommandClassifier.classify(coreCommand, Builtin.builtinNames())) {
                case CommandType.BUILTIN -> {
                    Builtin b = Builtin.fromName(coreCommand);
                    b.run(argCommand);
                }
                case CommandType.EXECUTABLE -> ExternalCommand.run(command);
                case CommandType.NONEXISTENT -> System.out.println(coreCommand + ": command not found");
            }
        }
    }
}
