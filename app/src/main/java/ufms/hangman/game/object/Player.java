package ufms.hangman.game.object;

import android.graphics.Bitmap;

import ufms.hangman.game.utils.DatabaseHelper;

public class Player implements EntityImpl {
    private int id;
    private String name;
    private int score;
    private Bitmap avatar;

    public Player(String name, Bitmap avatar) {
        this.name = name;
        this.score = 0;
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

    public void setAvatar(Bitmap avatar) {
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
        String sql = "INSERT INTO players (name, photo) VALUES ('" + this.name + "', '" + this.avatar + "')";
        db.executeSQL(sql);
    }

    @Override
    public void delete() {
        DatabaseHelper db = DatabaseHelper.getInstance();
        String sql = "DELETE FROM players WHERE name = '" + this.name + "'";
        db.executeSQL(sql);
    }
}
