package ufms.hangman.game.object;

import java.util.ArrayList;
import java.util.List;

public class WordManager {
    private Word word;
    private Game.Difficulty difficulty;
    private List<String> lettersUsed;

    public WordManager(Game.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.word = getNextWord();
        this.lettersUsed = new ArrayList<String>();
    }

    public Word getWord() {
        return word;
    }

    public List<String> getLettersUsed() {
        return lettersUsed;
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
        if (lettersUsed.contains(letter)) {
            return false;
        }
        lettersUsed.add(letter);
        return word.getWord().toLowerCase().contains(letter.toLowerCase());
    }
}
