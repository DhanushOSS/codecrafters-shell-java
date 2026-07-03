package builtins;

import utils.*;
import shell.ShellState;
import java.util.Set;
import java.util.HashSet;
import java.io.File;


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
        public void run(String a){
            File candidate = new File(a);
            if(candidate.isDirectory() && candidate.isAbsolute()){
                ShellState.cwd = candidate;
            }else{
                System.out.println("cd: " + a +  ": No such file or directory");
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