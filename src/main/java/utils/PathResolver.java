package utils;
import java.io.File;

public class PathResolver {
    public static String locate(String command){

        String cmd = command.split(" ")[0];
        String path = System.getenv("PATH");
        String[] directories = path.split(File.pathSeparator);
        for(String dir : directories){
            File candidate = new File(dir, cmd);

            if(candidate.exists() && candidate.canExecute()){
                return candidate.getAbsolutePath();
            }
        }
        return "";
    }
    
}
