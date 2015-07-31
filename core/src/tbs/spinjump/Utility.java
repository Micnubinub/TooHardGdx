package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Riley on 6/05/15.
 */
public class Utility {

    public static final Random rand = new Random();
    private static final int[] ints = new int[2];
    private static final GlyphLayout layout = new GlyphLayout();
    private static boolean isFontInit;
    //Todo make the font private and have a method called getFont(){
    private static BitmapFont font;

    public static int getRandom(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randFloat(int minX, int maxX) {
        return rand.nextFloat() * (maxX - minX) + minX;
    }

    public static BitmapFont getFont() {
        if (!isFontInit) {
            font = new BitmapFont(Gdx.files.internal("font.fnt"));
            isFontInit = true;
        }
        return font;
    }

    public static float getScale(float textHeight) {
        //Todo write this
        float scale = 0.25f;
        return scale;
    }

    public static void disposeFont() {
        //Todo call thi in dispose, and make sure all the methods call getX(){ if (!isXInit...){initX();)
        isFontInit = false;
        font.dispose();
    }

    public static int[] getAnglePos(float angle, float distFromCenter, int x, int y) {
        //Todo check if there's too much conversion too and from deg
        angle = (float) Math.toRadians(angle + 270);

        ints[0] = (int) ((distFromCenter) * Math.sin(angle + Math.PI) + x);
        ints[1] = (int) ((distFromCenter) * Math.cos(angle + Math.PI) + y);
        return ints;
    }

    public static Sprite getResizedBitmap(Sprite sprite, int newHeight, int newWidth) {
        sprite.setSize(newWidth, newHeight);
        return sprite;
    }

    public static Preferences getPreferences() {
        return Gdx.app.getPreferences("prefs");
    }

    public static int getInt(String key) {
        return getPreferences().getInteger(key, 0);
    }

    public static void saveInt(String key, int value) {
        getPreferences().putInteger(key, value).flush();
    }

    public static void drawCenteredText(SpriteBatch batch, Color color, String text, float x, float y, float scale) {
        final BitmapFont font = getFont();
        font.getData().setScale(scale);

        layout.setText(font, text);
        final float textWidth = layout.width;
        final float left = x - (textWidth / 2);
        final float textHeight = font.getLineHeight();
        font.setColor(color);
        font.draw(batch, text, left, y + (textHeight / 3));
    }
}
