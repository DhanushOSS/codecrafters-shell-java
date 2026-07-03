package utils;

import java.util.Set;

public class CommandClassifier {
    public static CommandType classify(String name, Set<String> builtinNames) {
        if (builtinNames.contains(name))
            return CommandType.BUILTIN;
        if (!PathResolver.locate(name).isEmpty())
            return CommandType.EXECUTABLE;
        return CommandType.NONEXISTENT;
    }
}