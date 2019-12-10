package com.shayanr.dangerdungeon.gameplay.mapping;

import android.graphics.Canvas;

import com.shayanr.dangerdungeon.gameplay.AnimationGroup;
import com.shayanr.dangerdungeon.gameplay.Sprite;

public abstract class Tile {
    public int tileX;
    public int tileY;

    public static final int SIZE = 16 * Sprite.SCALE;
    private static int FPS = 6;

    private boolean animated;

    private Sprite tileSpr;
    private AnimationGroup tileAnim;

    public Tile(String name, int x, int y, int f) {
        tileX = x;
        tileY = y;

        animated = f > 0;

        if(animated) {
            tileAnim = new AnimationGroup(new String[]{name}, new int[]{f}, FPS);
        } else {
            tileSpr = new Sprite(name);
        }
    }

    public void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
    }

    public void draw(Canvas canvas) {
        if(animated) {
            tileAnim.animate(canvas, tileX, tileY);
        } else {
            tileSpr.draw(canvas, tileX, tileY);
        }
    }
}
