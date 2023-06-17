package ufms.hangman.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ufms.hangman.game.R;
import ufms.hangman.game.activities.LoseActivity;
import ufms.hangman.game.activities.WinActivity;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Word;

public class GameActivity extends AppCompatActivity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        this.game = (Game) getIntent().getSerializableExtra("game");
        this.game.getPlayer().save();
        updateForca();
        createAZButtons();
        updateHint();
        updateScore();
        updateWord();
    }

    public void updateForca() {
        Game.GameState gameState = game.getGameState();
        ImageView forca = findViewById(R.id.imageView_forca);
        int drawbleId = gameState.getFileImage();
        forca.setImageResource(drawbleId);
    }

    public void updateScore() {
        int score = game.getScore();
        TextView scoreView = findViewById(R.id.textView_score);
        scoreView.setText(String.format("Score: %d", score));
        game.getPlayer().setScore(score);
    }

    public void updateWord() {
        Word word = game.getWordManager().getWord();
        TextView wordView = findViewById(R.id.textView_word);
        Log.d("word", word.getWord() + " - " + word.incompletedPreview(game.getWordManager().getLettersUsed()));
        wordView.setText(word.incompletedPreview(game.getWordManager().getLettersUsed()));
    }

    public void updateHint() {
        Word word = game.getWordManager().getWord();
        TextView hintView = findViewById(R.id.textView_hint);
        hintView.setText(word.getHint());
    }

    private void createAZButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.removeAllViews();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setOnClickListener(view -> {
                button.setEnabled(false);
                onLetterClick(((Button)view).getText().toString());
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(2, 2, 2, 2);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            gridLayout.addView(button, params);
        }
    }

    private void onLetterClick(String letter) {
        if(game.verifyLetter(letter)) {
            game.addScore(1);
            updateScore();
            updateWord();
            if(game.getWordManager().getWord().isCompleted(game.getWordManager().getLettersUsed())) {
                Log.d("nextWord", "is Completed");
                Word nextWord = game.getWordManager().getNextWord();
                if (nextWord == null) {
                    Log.d("nextWord", "null");
                    handleGameFinish();
                    game.getPlayer().update();
                    Toast.makeText(this, "Fim de jogo. Você ganhou!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("nextWord", nextWord.getWord());
                if (game.getDifficulty() != Game.Difficulty.HARD)
                    game.setGameState(Game.GameState.VAZIA);
                game.getWordManager().setWord(nextWord);
                game.getWordManager().resetLettersUsed();
                createAZButtons();
                updateForca();
                updateWord();
                updateScore();
                Toast.makeText(this, "Você acertou a palavra", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Letra correta: " + letter, Toast.LENGTH_SHORT).show();
            }
        } else {
            Game.GameState nextGameState = game.getGameState().getNextState();
            if(nextGameState == null) {
                handleGameLose();
                game.getPlayer().update();
                Toast.makeText(this, "Fim de jogo. Você perdeu!", Toast.LENGTH_SHORT).show();
                return;
            }
            game.addScore(-1);
            game.setGameState(nextGameState);
            updateForca();
            updateScore();
            Toast.makeText(this, "Letra incorreta: " + letter, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleGameLose() {
        Intent intent = new Intent(this, LoseActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private void handleGameFinish() {
        game.getPlayer().update();
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

}