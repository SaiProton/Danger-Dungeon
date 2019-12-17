package com.shayanr.dangerdungeon.gameplay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Sprite {
    public static final int SCALE = 10;

    public static Context context;
    public static Resources resources;

    private Bitmap sprite;
    private static BitmapFactory.Options options = new BitmapFactory.Options() {{ inScaled = false; }};
    private Paint paint;

    private int getSprId(String name) {
        return resources.getIdentifier(name, "drawable", context.getPackageName());
    }

    public Sprite(String name) {
        int sprId = getSprId(name);

        sprite = BitmapFactory.decodeResource(resources, sprId, options);
        sprite = Bitmap.createScaledBitmap(sprite, sprite.getWidth() * SCALE, sprite.getHeight() * SCALE, false);

        paint = new Paint();
    }

    public void flip() {
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, sprite.getWidth()/2f, sprite.getHeight()/2f);
        sprite = Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), matrix, true);
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(sprite, x, y, paint);
    }
}
