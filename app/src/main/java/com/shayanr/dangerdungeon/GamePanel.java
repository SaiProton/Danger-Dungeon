package com.shayanr.dangerdungeon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.shayanr.dangerdungeon.gameplay.Sprite;
import com.shayanr.dangerdungeon.gameplay.entities.*;
import com.shayanr.dangerdungeon.gameplay.mapping.Map;
import com.shayanr.dangerdungeon.gameplay.mapping.Tile;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static int scrWidth;
    public static int scrHeight;
    public MainThread thread;

    public int level = 0;

    private Knight knight;
    private Map map;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        scrWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        scrHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Sprite.context = context;
        Sprite.resources = getResources();

        knight = new Knight(scrWidth, scrHeight);
        map = new Map(level);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int[] tileTouch = map.tileTouchPos((int)event.getX(), (int)event.getY());

        if(tileTouch != null) {
            map.calcMapSpeed(tileTouch, knight.xPos, knight.yPos, 1);
        }

        return true;
    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.drawMap(canvas);
        knight.draw(canvas);

    }
}
