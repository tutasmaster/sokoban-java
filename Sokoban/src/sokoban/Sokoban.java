package sokoban;

public class Sokoban {
    public static void main(String[] args) {
        Application app = new Application();
        
        if(args.length > 0 && args[0].equals("-console")){
            app.runConsole();
        }else{
            app.runSwing();
        }
    }
}
