package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Button rankingButton;
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
        this.rankingButton = findViewById(R.id.btn_rank);

        createPlayButtonHandle();
        createButtonAddWordHandle();
        DatabaseHelper.init(this);
        createRankingButtonHandle();
        loadAvatars();
    }

    private void createRankingButtonHandle() {
        rankingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RankingActivity.class);
            startActivity(intent);
        });
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
                    child.setAlpha(0.2f);
                }
                imageView.setAlpha(1f);
                Log.d("Avatar", "Avatar selected: " + avatar.name());
            });
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    250,
                    250
            );

            layoutParams.setMargins(8, 8, 8, 8);

            flexboxLayout.addView(imageView, layoutParams);
        }
    }

    private void startGame() {
        try {
            validateFields();
        } catch (RuntimeException e) {
            Log.e("MainActivity", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        Game.Difficulty difficulty = Arrays.stream(Game.Difficulty.values())
                .filter(d -> d.getName().equals(difficultySpinner.getSelectedItem().toString()))
                .findFirst()
                .orElse(Game.Difficulty.EASY);
        Player player = new Player(playerName.getText().toString(), selectedAvatar);

        Game game = new Game(player, difficulty);

        game.getWordManager().loadWords();

        if(game.getWordManager().getWords().size() == 0) {
            Toast.makeText(this, "Não há palavras cadastradas", Toast.LENGTH_LONG).show();
            return;
        }

        game.getWordManager().setWord(game.getWordManager().getNextWord());

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private void validateFields() throws RuntimeException{
        if(playerName.getText().toString().isEmpty()){
            throw new RuntimeException("É preciso escolher um nome para o jogador");
        }
        if(selectedAvatar == null){
            throw new RuntimeException("É preciso escolher um avatar para o jogador");
        }
    }

}