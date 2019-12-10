package com.shayanr.dangerdungeon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private double avgFps;
    private boolean running = false;

    private SurfaceHolder holder;
    private GamePanel panel;
    private static Canvas canvas;

    public void setRunning(boolean run) {running = run;}

    public MainThread(SurfaceHolder holder, GamePanel panel) {
        super();

        this.holder = holder;
        this.panel = panel;
    }

    @Override
    public void run() {
        long startTime;
        long waitTime;
        long timeMillis;
        long frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while(running) {
            startTime = System.nanoTime();

            canvas = null;

            try {
                canvas = holder.lockCanvas();

                synchronized(holder) {
                    panel.update();
                    panel.draw(canvas);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS) {
                avgFps = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(avgFps);
            }
        }
    }
}
