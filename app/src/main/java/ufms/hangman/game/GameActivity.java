package ufms.hangman.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import ufms.hangman.game.R;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Word;

public class GameActivity extends AppCompatActivity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.game = GameManager.getInstance().getGame();

        updateForca();
        createAZButtons();
    }

    public void updateForca() {
        Game.GameState gameState = game.getGameState();
        ImageView forca = findViewById(R.id.imageView_forca);
        int drawbleId = gameState.getFileImage();
        forca.setImageResource(drawbleId);
    }

    private void createAZButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setOnClickListener(view -> onLetterClick(((Button)view).getText().toString(), button));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(2, 2, 2, 2);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            gridLayout.addView(button, params);
        }
    }

    private void onLetterClick(String letter, Button button) {
        button.setActivated(false);
        if(game.verifyLetter(letter)) {
            game.addScore(1);
            Toast.makeText(this, "Letra correta: " + letter, Toast.LENGTH_SHORT).show();
            if(game.getWordManager().getWord().isCompleted()) {
                Word nextWord = game.getWordManager().getNextWord();
                if (nextWord == null) {
                    Toast.makeText(this, "Fim de jogo. Você ganhou!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (game.getDifficulty() != Game.Difficulty.HARD)
                    game.setGameState(Game.GameState.VAZIA);
                game.getWordManager().setWord(nextWord);
                updateForca();
                Toast.makeText(this, "Você acertou a palavra", Toast.LENGTH_SHORT).show();
            }
        } else {
            Game.GameState nextGameState = game.getGameState().getNextState();
            if(nextGameState == null) {
                Toast.makeText(this, "Fim de jogo. Você perdeu!", Toast.LENGTH_SHORT).show();
                return;
            }
            game.addScore(-1);
            game.setGameState(nextGameState);
            updateForca();
            Toast.makeText(this, "Letra incorreta: " + letter, Toast.LENGTH_SHORT).show();
        }
    }
}