/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
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
