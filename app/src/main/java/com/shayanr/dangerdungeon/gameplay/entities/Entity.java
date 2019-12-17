package com.shayanr.dangerdungeon.gameplay.entities;

import android.graphics.Canvas;

import com.shayanr.dangerdungeon.gameplay.AnimationGroup;

public abstract class Entity {
    public int xPos;
    public int yPos;

    public int direction = 1;
    public int xSpeed = 0;
    public int ySpeed = 0;

    public int maxHP;
    public int health;

    private static int FPS = 10;

    public String[] animNames;
    public AnimationGroup anim;

    public void generateAnim(int[] animFrames) {
        anim = new AnimationGroup(animNames, animFrames, FPS);
    }

    public void changeAnim(String name) {
        anim.setAnim(name);
    }

    public void flipAnim() {
        anim.flipAnim();
    }

    public abstract void update();
    public abstract int[] getHurtBox();

    public void draw(Canvas canvas) {
        anim.animate(canvas, xPos, yPos);
    }
}
