import java.io.IOException;

import shell.ShellState;

public class ExternalCommand {
    public static void run(String command) throws IOException, InterruptedException{
        String[] args = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.directory(ShellState.cwd);
        pb.inheritIO();            // child uses my terminal's stdin/stdout/stderr
        Process process = pb.start();   // launch the child, returns immediately
        int exitCode = process.waitFor();  // block until it finishes
    }
}       
