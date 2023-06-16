package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;

import ufms.hangman.game.GameActivity;
import ufms.hangman.game.R;
import ufms.hangman.game.object.Avatars;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Player;
import ufms.hangman.game.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private FlexboxLayout flexboxLayout;

    private Button playButton;
    private Spinner difficultySpinner;
    private EditText playerName;
    private Button addWordButtom;
    private Avatars selectedAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playButton = findViewById(R.id.start_game);
        this.difficultySpinner = findViewById(R.id.difficulty_spinner);
        this.playerName = findViewById(R.id.player_name);
        this.addWordButtom = findViewById(R.id.add_word);
        this.flexboxLayout = findViewById(R.id.iconFlexbox);

        createPlayButtonHandle();
        createButtonAddWordHandle();
        DatabaseHelper.init(this);
        loadAvatars();
    }

    private void createButtonAddWordHandle() {
        addWordButtom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivity(intent);
        });
    }

    private void createPlayButtonHandle() {
        playButton.setOnClickListener(v -> {
            startGame();
        });
    }

    private void loadAvatars() {
        for(Avatars avatar : Avatars.values()) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(avatar.getResource());
            imageView.setOnClickListener(v -> {
                selectedAvatar = avatar;
                for(int i = 0; i < flexboxLayout.getChildCount(); i++) {
                    ImageView child = (ImageView) flexboxLayout.getChildAt(i);
                    child.setBackgroundResource(0);
                }
                imageView.setBackgroundResource(R.drawable.selected_avatar_border);
            });
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    100,
                    100
            );
            if(selectedAvatar != null && selectedAvatar.equals(avatar)) {
                imageView.setBackgroundResource(R.drawable.selected_avatar_border);
            }
            layoutParams.setMargins(8, 8, 8, 8);

            flexboxLayout.addView(imageView, layoutParams);
        }
    }

    private void startGame() {
        Game.Difficulty difficulty = Arrays.stream(Game.Difficulty.values())
                .filter(d -> d.name().equals(difficultySpinner.getSelectedItem().toString()))
                .findFirst()
                .orElse(Game.Difficulty.EASY);
        Player player = new Player(playerName.getText().toString(), selectedAvatar);

        Game game = new Game(player, difficulty);

        game.getWordManager().loadWords();
        game.getWordManager().setWord(game.getWordManager().getNextWord());

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}