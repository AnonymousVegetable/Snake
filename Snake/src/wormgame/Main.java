package wormgame;

import javax.swing.SwingUtilities;
import wormgame.gui.UserInterface;
import wormgame.game.WormGame;
import wormgame.domain.Worm;

public class Main {

    public static void main(String[] args) {
        
        WormGame game = new WormGame(24, 24);
        game.getWorm().setGame(game);
        UserInterface ui = new UserInterface(game, 24);
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("The drawing board hasn't been created yet.");
            }
        }

        game.setUpdatable(ui.getUpdatable());
        game.start();
    }
}
