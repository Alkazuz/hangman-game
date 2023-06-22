package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
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

public class NewWordActivity extends AppCompatActivity {

    private EditText txtWord, txtHint;
    private Spinner spnCategory;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtWord = findViewById(R.id.new_word);
        txtHint = findViewById(R.id.hint_word);
        spnCategory = findViewById(R.id.difficulty_spinner);

        this.btnRegister = findViewById(R.id.btn_save);
        this.btnRegister.setOnClickListener(v -> {
            registerWord();
        });
    }

    public void registerWord() {
        try {
            verifyFields();
        } catch (RuntimeException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        Game.Difficulty difficulty = Arrays.asList(Game.Difficulty.values())
                .stream().filter(d -> d.getName().equals(spnCategory.getSelectedItem().toString()))
                .findFirst().get();
        Word word = new Word(txtWord.getText().toString(),
                txtHint.getText().toString(), difficulty);

        word.save();
        Toast.makeText(this, "Palavra cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void verifyFields() {
        List<String> fields = new ArrayList<>();
        if (txtWord.getText().toString().isEmpty()) {
            fields.add("Palavra");
        }
        if (txtHint.getText().toString().isEmpty()) {
            fields.add("Dica");
        }
        if (spnCategory.getSelectedItem().toString().isEmpty()) {
            fields.add("Categoria");
        }
        if (!fields.isEmpty()) {
            throw new RuntimeException("Os campos " + fields.toString() + " são obrigatórios");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}