import java.util.Scanner;

public class Main {
    private static boolean checkCommand(String command){
        return false;
    }   

    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if(!checkCommand(command)){
            System.out.print(command + ": command not found");
        }
        scanner.close();
    }
}
