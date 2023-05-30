package ufms.hangman.game.object;

import java.util.List;

public class Word {
    private String word;
    private String hint;

    public Word(String word, String hint) {
        this.word = word;
        this.hint = hint;
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


    public boolean isCompleted() {
        return word.chars().allMatch(c -> Character.isLetter(c) && c != ' ' && c != '_');
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
