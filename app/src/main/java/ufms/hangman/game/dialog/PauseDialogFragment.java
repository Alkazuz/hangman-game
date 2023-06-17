package ufms.hangman.game.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ufms.hangman.game.GameActivity;
import ufms.hangman.game.R;
import ufms.hangman.game.activities.MainActivity;

public class PauseDialogFragment extends DialogFragment {
    private GameActivity gameActivity;

    public PauseDialogFragment(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTransparent);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pause_dialog_layout, null);

        builder.setView(view);

        return builder.create();
    }

    public void onResumeButtonClick(View view) {
       dismiss();
    }

    public void onExitButtonClick(View view) {
        gameActivity.finish();
    }

    public void onRestartButtonClick(View view) {

    }

}
