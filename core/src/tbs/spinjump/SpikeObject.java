package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Sidney on 14/05/2015.
 */
public class SpikeObject extends GameObject {
    private static final Color color = new Color();
    static int height, width, halfT, tCX, tCY;
    //Todo renderer    private static Sprite triangle;
    public float angle;


    public SpikeObject() {
    }

    public static void dispose() {

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
