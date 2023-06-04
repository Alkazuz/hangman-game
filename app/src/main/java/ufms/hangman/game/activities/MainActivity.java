package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import ufms.hangman.game.GameActivity;
import ufms.hangman.game.R;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Player;
import ufms.hangman.game.object.Word;
import ufms.hangman.game.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    CircleImageView playerPhoto;

    private Button playButton;
    private Spinner difficultySpinner;
    private EditText playerName;
    private Button addWordButtom;
    private Bitmap playerPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playButton = findViewById(R.id.start_game);
        this.playerPhoto = findViewById(R.id.player_photo);
        this.difficultySpinner = findViewById(R.id.difficulty_spinner);
        this.playerName = findViewById(R.id.player_name);
        this.addWordButtom = findViewById(R.id.add_word);

        createPlayButtonHandle();
        createButtonPhotoHandle();
        createButtonAddWordHandle();
        DatabaseHelper.init(this);
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

    private void startGame() {
        Game.Difficulty difficulty = Arrays.stream(Game.Difficulty.values())
                .filter(d -> d.name().equals(difficultySpinner.getSelectedItem().toString()))
                .findFirst()
                .orElse(Game.Difficulty.EASY);
        Player player = new Player(playerName.getText().toString(), playerPhotoBitmap);

        Game game = new Game(player, difficulty);

        game.getWordManager().setWord(new Word("TESTE", "Dica: teste"));

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private void createButtonPhotoHandle() {
        CircleImageView takePictureButton = findViewById(R.id.player_photo);

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