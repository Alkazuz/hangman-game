package ufms.hangman.game.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.function.Consumer;

public class WorkerCron implements Runnable{

    private int time;
    private TextView timeView;
    private volatile boolean running = true;
    private Handler handler = new Handler(Looper.getMainLooper());
    private OnTimeFinishCallback callback;

    public WorkerCron(int time, TextView timeView, OnTimeFinishCallback callback) {
        this.time = time;
        this.timeView = timeView;
        this.callback = callback;

    }

    private String formatMMSS() {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void run() {
        while (time > 0) {
            try {
                Thread.sleep(1000);
                time--;
                handler.post(() -> timeView.setText(formatMMSS()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (callback != null) {
            handler.post(() -> callback.onTimeFinish());
        }
    }

    public void stop() {
        running = false;
    }

    public interface OnTimeFinishCallback {
        void onTimeFinish();
    }
}

