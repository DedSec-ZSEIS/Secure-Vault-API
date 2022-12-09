package net.ckmk.api.handlers;

public class CommandHandler {
    public void handleCommand(String s){
        if (s.equalsIgnoreCase("stop")){
            System.exit(0);
        }
    }
}
