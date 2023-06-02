package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ufms.hangman.game.R;
import ufms.hangman.game.object.Game;
import ufms.hangman.game.object.Word;
import ufms.hangman.game.utils.LocalStorageManager;

public class NewWordActivity extends AppCompatActivity {

    private EditText txtWord, txtHint;
    private Spinner spnCategory;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        txtWord = findViewById(R.id.new_word);
        txtHint = findViewById(R.id.hint_word);
        spnCategory = findViewById(R.id.difficulty_spinner);

        this.btnRegister = findViewById(R.id.btn_save);
        this.btnRegister.setOnClickListener(v -> {
            registerWord();
            finish();
        });
    }

    public void registerWord() {
        Game.Difficulty difficulty = Arrays.asList(Game.Difficulty.values())
                .stream().filter(d -> d.getName().equals(spnCategory.getSelectedItem().toString()))
                .findFirst().get();
        Word word = new Word(txtWord.getText().toString(),
                txtHint.getText().toString(), difficulty);

        List<Word> words = (List<Word>) LocalStorageManager.getInstance().loadObject("words");
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
        LocalStorageManager.getInstance().save("words", words);
        Toast.makeText(this, "Palavra cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
    }

}