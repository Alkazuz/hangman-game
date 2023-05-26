package ufms.hangman.game.object;

import ufms.hangman.game.R;

public class Game {
    private Player player;
    private Difficulty difficulty;
    private WordManager wordManager;
    private GameState gameState;
    private int score;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        this.score = 0;
        this.wordManager = new WordManager(difficulty);
        this.gameState = GameState.VAZIA;
    }

    public Player getPlayer() {
        return player;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public WordManager getWordManager() {
        return wordManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean verifyLetter(String letter) {
        if (wordManager.verifyLetter(letter)) {
            addScore(10);
            return true;
        }

        return false;
    }

    public enum GameState {
        VAZIA(R.drawable.vazia),
        CABECA(R.drawable.cabeca),
        CORPO(R.drawable.corpo),
        BRACO_ESQUERDO(R.drawable.braco1),
        BRACO_DIREITO(R.drawable.braco2),
        PERNA_ESQUERDA(R.drawable.perna1),
        PERNA_DIREITA(R.drawable.perna2);
        private int fileImage;
        GameState(int fileImage) {
            this.fileImage = fileImage;
        }
        public int getFileImage() {
            return fileImage;
        }

        public GameState getNextState() {
            int index = this.ordinal();
            if (index < GameState.values().length - 1) {
                return GameState.values()[index + 1];
            }
            return null;
        }

    }
    public enum Difficulty {
        EASY("Fácil"),
        MEDIUM("Médio"),
        HARD("Difícil");

        private String name;

        private Difficulty(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}


