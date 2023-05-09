package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import ufms.hangman.game.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        createAZButtons();
    }

    private void createAZButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setOnClickListener(view -> onLetterClick(((Button)view).getText().toString()));

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
        Toast.makeText(this, "Letra clicada: " + letter, Toast.LENGTH_SHORT).show();
    }
}