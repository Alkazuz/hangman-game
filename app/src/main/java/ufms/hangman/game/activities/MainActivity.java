package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import ufms.hangman.game.GameActivity;
import ufms.hangman.game.GameManager;
import ufms.hangman.game.R;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Player;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    CircleImageView playerPhoto;

    private Button playButton;
    private Spinner difficultySpinner;
    private EditText playerName;
    private Bitmap playerPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playButton = findViewById(R.id.start_game);
        this.playerPhoto = findViewById(R.id.player_photo);
        this.difficultySpinner = findViewById(R.id.difficulty_spinner);
        this.playerName = findViewById(R.id.player_name);

        createPlayButtonHandle();
        createButtonPhotoHandle();
    }

    private void createPlayButtonHandle() {
        playButton.setOnClickListener(v -> {
            startGame();
        });
    }

    private void startGame() {
        Game.Difficulty difficulty = Arrays.stream(Game.Difficulty.values())
                .filter(d -> d.name().equals(difficultySpinner.getSelectedItem().toString()))
                .findFirst()
                .orElse(Game.Difficulty.EASY);
        Player player = new Player(playerName.getText().toString(), playerPhotoBitmap);

        Game game = new Game(player, difficulty);

        GameManager.getInstance().setGame(game);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private void createButtonPhotoHandle() {
        Button takePictureButton = findViewById(R.id.select_photo);

        takePictureButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            playerPhoto.setImageBitmap(imageBitmap);
            this.playerPhotoBitmap = imageBitmap;
        }
    }

}