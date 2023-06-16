package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import ufms.hangman.game.R;
import ufms.hangman.game.utils.DatabaseHelper;

public class RankingActivity extends AppCompatActivity {

    private LinearLayout linearLayoutRanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.linearLayoutRanks = findViewById(R.id.layoutRank);
        updateRank();
    }

    private void updateRank() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        Cursor selectCursor = databaseHelper.select("SELECT name, score FROM player ORDER BY score DESC LIMIT 5");
        while (selectCursor.moveToNext()) {
            String name = selectCursor.getString(1);
            int score = selectCursor.getInt(2);
            String formattedScoreNumber = NumberFormat.getNumberInstance().format(score);
            String formattedScore = String.format("%s - %s", name, formattedScoreNumber);
            TextView textView = new TextView(this);
            textView.setText(formattedScore);
            this.linearLayoutRanks.addView(textView);
        }

    }

}