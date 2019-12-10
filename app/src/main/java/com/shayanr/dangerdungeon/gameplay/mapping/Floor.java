package com.shayanr.dangerdungeon.gameplay.mapping;

public class Floor extends Tile {
    public Floor(String name, int x, int y, int f) {
        super(name, x, y, f);
    }

    public boolean checkTouch(int touchX, int touchY) {
        return touchX >= tileX && touchX <= tileX + SIZE && touchY >= tileY && touchY <= tileY + SIZE;
    }
}
