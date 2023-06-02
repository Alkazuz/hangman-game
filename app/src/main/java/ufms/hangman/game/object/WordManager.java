package ufms.hangman.game.object;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ufms.hangman.game.utils.LocalStorageManager;

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
        this.words = (List<Word>) LocalStorageManager.getInstance().loadObject("words");
        if (this.words == null) {
            this.words = new ArrayList<Word>();
        }
    }

    public boolean verifyLetter(String letter) {
        if (lettersUsed.contains(letter)) {
            return false;
        }
        lettersUsed.add(letter);
        return word.getWord().toLowerCase().contains(letter.toLowerCase());
    }
}
