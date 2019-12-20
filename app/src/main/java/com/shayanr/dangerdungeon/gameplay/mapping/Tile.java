package com.shayanr.dangerdungeon.gameplay.mapping;

import android.graphics.Canvas;

import com.shayanr.dangerdungeon.GamePanel;
import com.shayanr.dangerdungeon.gameplay.AnimationGroup;
import com.shayanr.dangerdungeon.gameplay.Sprite;

abstract class Tile {
    int tileX;
    int tileY;

    static final int SIZE = 16 * Sprite.SCALE;
    private static int FPS = 6;

    private boolean animated;

    private Sprite tileSpr;
    private AnimationGroup tileAnim;

    Tile(String name, int x, int y, int f) {
        tileX = x;
        tileY = y;

        animated = f > 0;

        if(animated) {
            tileAnim = new AnimationGroup(new String[]{name}, new int[]{f}, FPS);
        } else {
            tileSpr = new Sprite(name);
        }
    }

    static Tile parse(String name, String symbol, int x, int y) {
        int frames = (symbol.charAt(1) == 's' || symbol.charAt(1) == 'f') ? 3 : 0;
        return (symbol.charAt(0) == 'f') ? new Floor(name, x, y, frames) : new Wall(name, x, y, frames);
    }

    boolean onScreen() {
        return (tileX + SIZE > 0 && tileX < GamePanel.scrWidth && tileY + SIZE > 0 && tileY < GamePanel.scrHeight);
    }

    void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
    }

    void draw(Canvas canvas) {
        if(animated) {
            tileAnim.animate(canvas, tileX, tileY);
        } else {
            tileSpr.draw(canvas, tileX, tileY);
        }
    }
}
