package ufms.hangman.game.object;

import java.util.List;

public class Word {
    private String word;
    private String hint;
    private Game.Difficulty difficulty;

    public Word(String word, String hint) {
        this.word = word;
        this.hint = hint;
    }

    public Word(String word, String hint, Game.Difficulty difficulty) {
        this.word = word;
        this.hint = hint;
        this.difficulty = difficulty;
    }

    public String getWord() {
        return word;
    }

    public String getHint() {
        return hint;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Game.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Game.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isCompleted(List<String> lettersUsed) {
        for (char letter : word.toCharArray()) {
            if (Character.isLetter(letter) && letter != ' ') {
                if (!lettersUsed.contains(String.valueOf(letter))) {
                    return false;
                }
            }
        }
        return true;
    }

    public String incompletedPreview(List<String> lettersUsed) {
        StringBuilder preview = new StringBuilder();
        for (char letter : word.toCharArray()) {
            if (Character.isLetter(letter) && letter != ' ') {
                if (lettersUsed.contains(String.valueOf(letter))) {
                    preview.append(letter);
                } else {
                    preview.append('_');
                }
            } else {
                preview.append(letter);
            }
        }
        return preview.toString();
    }
}
