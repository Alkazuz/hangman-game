package ufms.hangman.game.object;

public class WordManager {
    private Word word;
    private Game.Difficulty difficulty;

    public WordManager(Game.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.word = getNextWord();
    }

    public Word getWord() {
        return word;
    }

    public Game.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setDifficulty(Game.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Word getNextWord() {
        return null;
    }

    public boolean verifyLetter(String letter) {
        return word.getWord().toLowerCase().contains(letter.toLowerCase());
    }
}
