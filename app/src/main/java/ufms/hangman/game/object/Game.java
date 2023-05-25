package ufms.hangman.game.object;

public class Game {

    private Player player;
    private Difficulty difficulty;
    private int score;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        this.score = 0;
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

    public static enum Difficulty {
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


