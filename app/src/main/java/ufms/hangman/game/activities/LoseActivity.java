package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ufms.hangman.game.R;
import ufms.hangman.game.object.Game;

public class LoseActivity extends AppCompatActivity {

    private TextView scoreView;
    private TextView playerNameView;
    private Button backButton;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        if (getIntent().getSerializableExtra("game") != null) {
            this.game = (Game) getIntent().getSerializableExtra("game");
        }
        this.playerNameView = findViewById(R.id.playerName);
        this.scoreView = findViewById(R.id.playerScore);
        this.backButton = findViewById(R.id.backButton);
        this.setPlayerName();
        this.setPlayerScore();
        this.setupBackButton();
    }

    private void setupBackButton() {
        this.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setPlayerName() {
        this.playerNameView.setText(this.game.getPlayer().getName());
    }

    private void setPlayerScore() {
        this.scoreView.setText(String.format("Pontuação: %d", this.game.getScore()));
    }
}