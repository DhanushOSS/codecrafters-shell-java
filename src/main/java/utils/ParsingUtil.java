package utils;
public class ParsingUtil {
    public static String[] splitCommand(String command){
        // [0] -> core command, [1] -> rest of command.
        command = command.trim();
        int inputIndex = command.indexOf(" ");
        if (inputIndex == -1) {
            return new String[] { command, "" };
        }
        String coreCommand = command.substring(0, inputIndex);
        String restOfInput = command.substring(inputIndex + 1);

        return new String[] { coreCommand, restOfInput };
    }


}
