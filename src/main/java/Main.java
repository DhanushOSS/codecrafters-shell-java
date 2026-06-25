import java.util.Scanner;

public class Main {
    private static boolean checkCommand(String command){
        return false;
    }   

    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("$ ");
            
            String command = scanner.nextLine();
            if(command.equals("exit")){break;}
            if (!checkCommand(command)) {
                System.out.println(command + ": command not found");
            }
            
        }
    }
}
