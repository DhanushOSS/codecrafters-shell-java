package builtins;

import utils.*;
import shell.ShellState;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.IOException;

public enum Builtin{
    ECHO("echo"){
        @Override 
        public void run(String a) { 
            System.out.println(String.join(" ", a)); 
        }
    },
    PWD("pwd"){
        @Override 
        public void run(String a) {
            System.out.println(ShellState.cwd);
        }
    },
    EXIT("exit"){
        @Override
        public void run(String a) {
            // 
        }
    },
    TYPE("type") {
        @Override 
        public void run(String a) {
            String name = a.split(" ")[0];
            switch (CommandClassifier.classify(name, builtinNames())) {
                case CommandType.BUILTIN     -> System.out.println(name + " is a shell builtin");
                case CommandType.EXECUTABLE  -> System.out.println(name + " is " + PathResolver.locate(name));
                case CommandType.NONEXISTENT -> System.out.println(name + ": not found");
            }
        }
    },
    CD("cd"){
        @Override
        public void run(String target){
            File dest = target.startsWith("/") ? new File(target) : new File(ShellState.cwd, target);

            try {
                File canonical = dest.getCanonicalFile(); // auto-normalizes . and ..
                if (canonical.isDirectory()) {
                    ShellState.cwd = canonical; // valid dir → move there
                } else {
                    System.out.println("cd: " + target + ": No such file or directory");
                }
            } catch (IOException e) { // getCanonical* is checked
                System.out.println("cd: " + target + ": No such file or directory");
            }
        }
    };

    private final String cmd;

    public static Set<String> builtinNames() {
        Set<String> result = new HashSet<>();
        for (Builtin b : values())
            result.add(b.cmd); // single source of truth
        return result;
    }

    Builtin(String cmd){ 
        this.cmd = cmd;
    } 

    public abstract void run(String cmd);

    public static Builtin fromName(String name) { // lookup + "is it a builtin?"
        for (Builtin b : values())
            if (b.cmd.equals(name))
                return b;
        return null;
    }

}