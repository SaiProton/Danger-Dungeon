package com.shayanr.dangerdungeon.gameplay.entities;

import com.shayanr.dangerdungeon.gameplay.Sprite;

public class Knight extends Entity {
    private static int width = 16 * Sprite.SCALE;
    private static int height = 28 * Sprite.SCALE;

    public Knight(int sw, int sh) {
        xPos = ((int)(sw / 2.0)) - width;
        yPos = ((int)(sh * 3.0/4)) - height;

        animNames = new String[]{"knight_f_idle_anim_f", "knight_f_run_anim_f", "knight_f_hit_anim_f"};

        generateAnim(new int[]{4, 4, 1});
    }

    @Override
    public int[] getHurtBox() {
        return new int[]{xPos, (int)(yPos + height / 2.0)};
    }

    @Override
    public void update() {

    }
}
