import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.io.File;

public class Main {
    private static List<String> acceptedCommands = Arrays.asList("echo", "exit", "type", "grep");

    private static boolean checkCommand(String command){
        String coreCommand = splitInput(command)[0];
        if(acceptedCommands.contains(coreCommand)){
            return true;
        }
        return false;
    }   

    private static String[] splitInput(String command){
        command = command.trim();
        int inputIndex = command.indexOf(" ");
        if (inputIndex == -1) {
            return new String[] { command, "" };
        }
        String coreCommand = command.substring(0, inputIndex);
        String restOfInput = command.substring(inputIndex + 1);

        return new String[] { coreCommand, restOfInput };
    }

    private static void response(String command){
        String coreCommand = command.trim().split(" ")[0];
        if(coreCommand.equals("echo")){
            String response = splitInput(command)[1]; 
            System.out.println(response);
            return;
        }else if(coreCommand.equals("type")){
            if(checkCommand(splitInput((command))[1])){
                System.out.println(splitInput((command))[1] + " is a shell builtin");
            }else if(pathLocation(splitInput((command))[1]) != ""){
                System.out.println(splitInput((command))[1] + " is " + pathLocation(splitInput((command))[1]));
            }else{
                System.out.println(splitInput((command))[1] + ": not found");       
            }
        }
    }

    private static String pathLocation(String command){
        String path = System.getenv("PATH");
        if(path == null){
            return "";
        }
        String[] directories = path.split(File.pathSeparator);
        
        for (String dir : directories) {

            File candidate = new File(dir, command);

            if (candidate.exists() && candidate.canExecute()) {
                return candidate.getAbsolutePath();
            }
        }

        return "";
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("$ ");
            
            String command = scanner.nextLine();
            if(command.equals("exit")){break;}
            if (!checkCommand(command)) {
                System.out.println(command + ": command not found");
            }else{
                response(command);
            }
            
        }
    }
}
