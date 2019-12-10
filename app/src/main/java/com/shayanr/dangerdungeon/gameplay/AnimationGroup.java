package com.shayanr.dangerdungeon.gameplay;

import android.graphics.Canvas;

import com.shayanr.dangerdungeon.MainThread;

import java.util.HashMap;

public class AnimationGroup {
    private HashMap<String, Sprite[]> animations = new HashMap<>();
    private Sprite[] currentAnim;
    private double frameRate;
    private double frame = 0;
    private int slice = 0;

    public void setAnim(String name) { currentAnim = animations.get(name); }

    public void setFPS(double fps) {
        frameRate = fps / MainThread.MAX_FPS;
    }

    public AnimationGroup(String[] groupNames, int[] groupFrames, int fps) {
        for(int i = 0; i < groupNames.length; i++) {
            Sprite[] frames = new Sprite[groupFrames[i]];

            for(int f = 0; f < groupFrames[i]; f++) {
                String spriteName = groupNames[i] + f;

                frames[f] = new Sprite(spriteName);
            }

            animations.put(groupNames[i], frames);
        }

        setAnim(groupNames[0]);
        setFPS(fps);
    }

    public void animate(Canvas canvas, int x, int y) {
        currentAnim[slice].draw(canvas, x, y);
        frame += frameRate;
        slice = ((int)Math.floor(frame)) % currentAnim.length;
    }
}
