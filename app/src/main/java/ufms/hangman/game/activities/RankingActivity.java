package ufms.hangman.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.text.NumberFormat;

import ufms.hangman.game.R;
import ufms.hangman.game.object.Avatars;
import ufms.hangman.game.utils.DatabaseHelper;

public class RankingActivity extends AppCompatActivity {

    private LinearLayout linearLayoutRanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.linearLayoutRanks = findViewById(R.id.layoutRank);
        updateRank();
    }

    private void updateRank() {
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            Cursor selectCursor = databaseHelper.select("SELECT name, score, photo FROM players ORDER BY score DESC LIMIT 5");
            while (selectCursor.moveToNext()) {
                String name = selectCursor.getString(0);
                int score = selectCursor.getInt(1);
                Avatars avatars = Avatars.valueOf(selectCursor.getString(2));
                String formattedScoreNumber = NumberFormat.getNumberInstance().format(score);
                String formattedScore = String.format("%s - %s", name, formattedScoreNumber);

                LinearLayout inlinearLayout = new LinearLayout(this);
                inlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                inlinearLayout.setPadding(0, 0, 0, 30);
                inlinearLayout.setGravity(1);

                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setPadding(0, 0, 0, 10);
                textView.setGravity(1);
                textView.setText(formattedScore);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(avatars.getResource());
                imageView.setPadding(0, 0, 0, 10);
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(0, 0, 10, 0);

                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                        100,
                        100
                );

                inlinearLayout.addView(imageView, layoutParams);
                inlinearLayout.addView(textView);
                this.linearLayoutRanks.addView(inlinearLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
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