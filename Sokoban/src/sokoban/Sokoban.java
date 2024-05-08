/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sokoban;

import sokoban.game.renderer.swing.SwingGameFrame;
import sokoban.game.renderer.swing.SwingRenderer;

/**
 *
 * @author Tutas
 */
public class Sokoban {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = new Application();
        try{
            app.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
