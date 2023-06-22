package ufms.hangman.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ufms.hangman.game.R;
import ufms.hangman.game.activities.LoseActivity;
import ufms.hangman.game.activities.WinActivity;
import ufms.hangman.game.dialog.PauseDialogFragment;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Word;
import ufms.hangman.game.utils.WorkerCron;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private MediaPlayer mediaPlayer;
    private ImageButton soundButton;
    private ImageButton menuButton;
    private WorkerCron workerCron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        this.game = (Game) getIntent().getSerializableExtra("game");
        this.game.getPlayer().save();
        this.mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        this.mediaPlayer.setLooping(true);

        this.soundButton = findViewById(R.id.button_sound);
        this.menuButton = findViewById(R.id.button_pause);

        startMusic();
        updateForca();
        createAZButtons();
        updateHint();
        updateScore();
        updateWord();
        createSoundButtonHandle();
        createMenuButtonHandle();
        startCron();
    }

    private void startMusic() {
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            this.soundButton.setBackgroundResource(R.drawable.soundon);
        }
    }

    private void stopMusic() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            this.soundButton.setBackgroundResource(R.drawable.soundoff);
        }
    }

    private void createMenuButtonHandle() {
        menuButton.setOnClickListener(v -> {
            PauseDialogFragment pauseDialogFragment = new PauseDialogFragment(this);
            pauseDialogFragment.show(getSupportFragmentManager(), "pause_dialog_fragment");
        });
    }

    private void createSoundButtonHandle() {
        soundButton.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying()) {
                stopMusic();
            } else {
                startMusic();
            }
        });
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

    private void restartCron() {
        if(threadCron != null) {
            threadCron.interrupt();
            workerCron.stop();
        }
        startCron();
    }

    private Thread threadCron;
    private void startCron() {
        TextView cronView = findViewById(R.id.textView_time);
        this.workerCron = new WorkerCron(120, cronView, new WorkerCron.OnTimeFinishCallback() {
            @Override
            public void onTimeFinish() {
                Toast.makeText(GameActivity.this, "Tempo Esgotado!", Toast.LENGTH_SHORT).show();
                handleGameLose();
            }
        });
        threadCron = new Thread(workerCron);
        threadCron.start();
    }

    public void restartGame() {
        game = new Game(game.getPlayer(), game.getDifficulty());
        game.getWordManager().loadWords();
        game.getWordManager().setWord(game.getWordManager().getNextWord());
        restartCron();
        updateForca();
        updateWord();
        updateHint();
        updateScore();
        createAZButtons();
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
                restartCron();
                Toast.makeText(this, "Você acertou a palavra", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Letra correta: " + letter, Toast.LENGTH_SHORT).show();
            }
        } else {
            Game.GameState nextGameState = game.getGameState().getNextState();
            Log.d("nextGameState", nextGameState == null ? "null" : nextGameState.toString());
            if(nextGameState == null || nextGameState == Game.GameState.PERNA_DIREITA) {
                handleGameLose();
                game.getPlayer().update();
                Log.d("nextGameState", "lose game");
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
        Log.d("handleGameLose", "handleGameLose");
        Intent intent = new Intent(this, LoseActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private void handleGameFinish() {
        Log.d("handleGameFinish", "handleGameFinish");
        game.getPlayer().update();
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}