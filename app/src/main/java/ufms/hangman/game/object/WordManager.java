package ufms.hangman.game.object;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ufms.hangman.game.utils.DatabaseHelper;

public class WordManager {
    private Word word;
    private Game.Difficulty difficulty;
    private List<String> lettersUsed;
    private List<Word> words;
    private List<Word> usedWords;

    public WordManager(Game.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.word = getNextWord();
        this.lettersUsed = new ArrayList<String>();
        loadWords();
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
        List<Word> remainingWords = this.words.stream().filter(
                word -> !this.usedWords.contains(word) && word.getDifficulty() == this.difficulty)
                .collect(Collectors.toList());
        if (remainingWords.size() == 0) {
            return null;
        }
        return remainingWords.get((int) (Math.random() * remainingWords.size()));
    }

    public void loadWords() {
        this.usedWords = new ArrayList<>();
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        List<Word> words = new ArrayList<>();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT * FROM words", null);
        while (cursor.moveToNext()) {
            words.add(new Word(cursor.getString(1), cursor.getString(2), Game.Difficulty.valueOf(cursor.getString(3))));
        }
        this.words = words;
    }

    public boolean verifyLetter(String letter) {
        if (lettersUsed.contains(letter)) {
            return false;
        }
        lettersUsed.add(letter);
        return word.getWord().toLowerCase().contains(letter.toLowerCase());
    }
}
