package ufms.hangman.game;

import ufms.hangman.game.object.Game;

public class GameManager {
    private static final GameManager INSTANCE = new GameManager();
    public static GameManager getInstance() { return INSTANCE; }

    private Game game;

    public void setGame(Game game) { this.game = game; }
    public Game getGame() { return this.game; }
}
