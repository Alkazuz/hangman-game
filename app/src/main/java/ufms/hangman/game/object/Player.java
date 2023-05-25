package ufms.hangman.game.object;

import android.graphics.Bitmap;

public class Player {
    private String name;
    private int score;
    private Bitmap avatar;

    public Player(String name, int score, Bitmap avatar) {
        this.name = name;
        this.score = score;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
