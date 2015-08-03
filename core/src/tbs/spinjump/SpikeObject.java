package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 14/05/2015.
 */
public class SpikeObject extends GameObject {
    static int height, width, halfT, tCX, tCY;
    private static Sprite triangle;
    public float angle;


    public SpikeObject() {
        initTriangle();
    }

    public static void dispose() {
        try {
            triangle.getTexture().dispose();
        } catch (Exception e) {
        }
    }

    private static void initTriangle() {
        dispose();

        final int s = Game.w / 4;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1, 1, 1, 1));
//Todo        pixmap.fillTriangle(s / 2, s / 2, s / 2);

        triangle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }

    private static Sprite getSpike(final int w, final int h, final int color, boolean left) {
        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        if (left)
            pixmap.fillTriangle(0, 0, w, h / 2, 0, h);
        else
            pixmap.fillTriangle(w, h, 0, h / 2, w, 0);
        final Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new Sprite(texture);
    }

    public static void setWidthAndHeight() {
        SpikeObject.width = (int) GameValues.SPIKE_SIZE;
        SpikeObject.height = (int) GameValues.SPIKE_SIZE;
        halfT = SpikeObject.width / 2;
    }

    public static float cos(float angleDeg) {
        return (float) Math.cos(Math.toRadians(angleDeg));
    }

    public static float sin(float angleDeg) {
        return (float) Math.sin(Math.toRadians(angleDeg));
    }

    public void drawTriangle(SpriteBatch batch, float triangleX, float triangleY, float triangleRadius) {
        triangleRadius *= 0.95f;
        angle %= 360;

        triangle.setSize(triangleRadius, triangleRadius);
        triangle.setRotation(angle);
        triangle.setPosition(triangleX, Game.h - triangleY);
        triangle.draw(batch);
    }

    public boolean checkCollision(int triangleCX, int triangleCY, int triangleR, int playerCX, int playerCY, int playerR) {
        angle %= 360;
        final float cosA = cos(angle);
        final float sinA = sin(angle);
        halfT = width / 2;
        final int tx = triangleCX + Math.round(cosA * triangleR);
        final int ty = triangleCY + Math.round(sinA * triangleR);
        tCX = tx + Math.round(cosA * halfT);
        tCY = ty + Math.round(sinA * halfT);
        return (Math.pow(tCX - playerCX, 2) + Math.pow(tCY - playerCY, 2)) < ((playerR + (halfT / 3.0f)) * (playerR + (halfT / 3.0f)));
    }
}
