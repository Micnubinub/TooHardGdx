package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Riley on 6/05/15.
 */
public class Utility {

    public static final Random rand = new Random();
    private static final int[] ints = new int[2];
    //Todo init
    public static BitmapFont font = new BitmapFont(Gdx.files.internal("/fonts/font.fnt"));

    public static int getRandom(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randFloat(int minX, int maxX) {
        return rand.nextFloat() * (maxX - minX) + minX;
    }

    public static int[] getAnglePos(float angle, float distFromCenter, int x, int y) {
        //Todo check if theres too much conversion too and from deg
        angle = (float) Math.toRadians(angle + 270);

        ints[0] = (int) ((distFromCenter) * Math.sin(angle + Math.PI) + x);
        ints[1] = (int) ((distFromCenter) * Math.cos(angle + Math.PI) + y);
        return ints;
    }

    public static Sprite getResizedBitmap(Sprite sprite, int newHeight, int newWidth) {
        sprite.setSize(newWidth, newHeight);
        return sprite;
    }

    public static void drawCenteredText(SpriteBatch batch, Color color, String text, int x, int y, float scale) {
        font.setScale(scale);

        final float textWidth = font.getBounds(text).width;
        final float left = x - (textWidth / 2);
        final float textHeight = font.getLineHeight();
        font.setColor(color);
        font.draw(batch, text, left, y + (textHeight / 3));
    }
}
