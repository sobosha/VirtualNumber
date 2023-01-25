package com.diaco.majazi.Setting;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerScroll extends RecyclerView.OnScrollListener {
    static float MINIMUM = 100;
    int scrollDist = 0;
    boolean isVisible = true,running=false;
    Handler handler;
    Runnable runnable;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isVisible && scrollDist > MINIMUM) {
            handler = new Handler(Looper.getMainLooper());
            if (!running)
            {
                hide();
                scrollDist = 0;
                isVisible = false;
                running = true;
                runnable = () -> {
                    running = false;
                    handler.removeCallbacks(runnable);
                };
            }
            handler.postDelayed(runnable,500);

        } else if (!isVisible && scrollDist < -MINIMUM) {
            handler = new Handler(Looper.getMainLooper());
            if (!running)
            {
                show();
                scrollDist = 0;
                isVisible = true;
                running = true;
                runnable = () -> {
                    running = false;
                    handler.removeCallbacks(runnable);
                };
            }
            handler.postDelayed(runnable,500);
        }

        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }

    }

    public void show() {

    }

    public void hide() {

    }


}
