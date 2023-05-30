package ufms.hangman.game.object;

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

    public String incompletedPreview() {
        StringBuilder preview = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                preview.append("_");
            } else {
                preview.append(c);
            }
        }
        return preview.toString();
    }
}
