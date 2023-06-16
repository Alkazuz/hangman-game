package ufms.hangman.game.object;

import android.graphics.Bitmap;

import java.io.Serializable;

import ufms.hangman.game.utils.DatabaseHelper;

public class Player implements EntityImpl, Serializable {
    private int id;
    private String name;
    private int score;
    private Avatars avatar;

    public Player(String name, Avatars avatar) {
        this.name = name;
        this.score = 0;
        this.avatar = avatar == null ? Avatars.BLITZ : avatar;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Avatars getAvatar() {
        return avatar;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAvatar(Avatars avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void removeScore(int score) {
        this.score -= score;
    }

    public void resetScore() {
        this.score = 0;
    }

    @Override
    public void update() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "UPDATE players SET name = '" + this.name + "', photo = '" + this.avatar + "' WHERE id = " + this.id;
        db.executeSQL(sql);
    }

    @Override
    public void save() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "INSERT INTO players (name, photo) VALUES ('" + this.name + "', '" + this.avatar.toString() + "')";
        db.executeSQL(sql);
    }

    @Override
    public void delete() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "DELETE FROM players WHERE name = '" + this.name + "'";
        db.executeSQL(sql);
    }
}
