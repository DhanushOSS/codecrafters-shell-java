import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    private static List<String> acceptedCommands = Arrays.asList("echo", "exit", "type");
    
    enum TYPES {
        BUILTIN,
        EXECUTABLE,
        NONEXISTENT
    }

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

    private static void runPrograms(String command) throws IOException, InterruptedException{
        String[] args = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.inheritIO();            // child uses my terminal's stdin/stdout/stderr
        Process process = pb.start();   // launch the child, returns immediately
        int exitCode = process.waitFor();  // block until it finishes
    }

    private static TYPES type(String command){
        if (checkCommand(splitInput((command))[1])) {
            System.out.println(splitInput((command))[1] + " is a shell builtin");
            return TYPES.BUILTIN;
        } else if (!pathLocation(splitInput((command))[1]).isEmpty()) {
            System.out.println(splitInput((command))[1] + " is " + pathLocation(splitInput((command))[1]));
            return TYPES.EXECUTABLE;
        } else {
            System.out.println(splitInput((command))[1] + ": not found");
            return TYPES.NONEXISTENT;
        }
    }
    private static void response(String command){
        String coreCommand = command.trim().split(" ")[0];
        if(coreCommand.equals("echo")){
            String response = splitInput(command)[1]; 
            System.out.println(response);
            return;
        }else if(coreCommand.equals("type")){
            type(command);
        }else if(type(coreCommand) == TYPES.EXECUTABLE){

        }
    }

    private static String pathLocation(String command){
        String coreCommand = command.split(" ")[0];
        String path = System.getenv("PATH");
        if(path == null){
            return "";
        }
        String[] directories = path.split(File.pathSeparator);
        
        for (String dir : directories) {

            File candidate = new File(dir, coreCommand);

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
            if(checkCommand(command)){
                response(command);
            }
            else if(!pathLocation(command).isEmpty()) {
                runPrograms(command);
            }else{
                System.out.println(command + ": command not found");
            }
            
        }
    }
}
