package ufms.hangman.game.object;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ufms.hangman.game.utils.DatabaseHelper;

public class WordManager implements java.io.Serializable{
    private Word word;
    private Game.Difficulty difficulty;
    private List<String> lettersUsed;
    private List<Word> words;
    private List<Word> usedWords;

    public WordManager(Game.Difficulty difficulty) {
        loadWords();
        this.difficulty = difficulty;
        this.word = getNextWord();
        this.lettersUsed = new ArrayList<String>();
    }

    public List<Word> getWords() {
        return words;
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
        Log.d("[WordManager]", "Remaining words: " + remainingWords.size());
        if (remainingWords.size() == 0) {
            return null;
        }
        Word word = remainingWords.get((int) (Math.random() * remainingWords.size()));
        this.usedWords.add(word);
        return word;
    }

    public void loadWords() {
        try {
            this.usedWords = new ArrayList<>();
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            List<Word> words = new ArrayList<>();
            Cursor cursor = databaseHelper.select("SELECT * FROM words");
            while (cursor.moveToNext()) {
                Log.d("[WordManager]", "Loaded word: " + cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getString(3));
                words.add(new Word(cursor.getString(1), cursor.getString(2), Game.Difficulty.valueOf(cursor.getString(3))));
            }
            Log.d("[WordManager]", "Loaded " + words.size() + " words");
            this.words = words;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("[WordManager]", "Failed to load words");
        }

    }

    public boolean verifyLetter(String letter) {
        if (lettersUsed.contains(letter)) {
            return false;
        }
        lettersUsed.add(letter);
        return word.getWord().toLowerCase().contains(letter.toLowerCase());
    }

    public void resetLettersUsed() {
        lettersUsed.clear();
    }
}
