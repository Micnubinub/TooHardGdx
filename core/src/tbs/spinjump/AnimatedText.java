package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 10/06/2015.
 */
public class AnimatedText {
    public int x, y;
    public int alpha;
    public String text;
    public float yVel; // SPEED ON Y AXIS
    private static final Color color = new Color();

    public void setup(int x, int y, String text) {
        this.x = x;
        this.y = y;
        alpha = 255;
        this.text = text;
        yVel = GameValues.ANIM_TEXT_SPEED;
    }

    public void update(float delta) {
        if (alpha > 0) { // (SAME AS LIFETIME)
            // UPDATE ALPHA:
            alpha -= delta * 0.5f;
            if (alpha < 0) {
                alpha = 0;
            }

            // MOVE UP (AN GET SLOWER):
            this.y -= yVel * delta;
        }
    }

    public void draw(SpriteBatch canvas) {
        if (alpha > 0) {
            color.set(1, 1, 1, (alpha / 255f));
            //Todo use this to get scale : GameValues.ANIM_TEXT_SIZE
            Utility.drawCenteredText(canvas, color, text, x, y, 0.25f);
        }
    }

}
