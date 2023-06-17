package ufms.hangman.game.object;

import android.util.Log;

import java.util.List;

import ufms.hangman.game.utils.DatabaseHelper;

public class Word implements EntityImpl, java.io.Serializable {
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
        for (char letter : word.toUpperCase().toCharArray()) {
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
        for (char letter : word.toUpperCase().toCharArray()) {
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

    @Override
    public void save() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "INSERT INTO words (word, hint, difficulty) VALUES ('" + word + "', '" + hint + "', '" + difficulty.toString() + "')";
        db.executeSQL(sql);
    }

    @Override
    public void delete() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "DELETE FROM words WHERE word = '" + word + "'";
        db.executeSQL(sql);
    }

    @Override
    public void update() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "UPDATE words SET hint = '" + hint + "', difficulty = '" + difficulty.toString() + "' WHERE word = '" + word + "'";
        db.executeSQL(sql);
    }
}
