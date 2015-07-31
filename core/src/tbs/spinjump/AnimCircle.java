package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 11/06/2015.
 */
public class AnimCircle {
    private static Sprite circle;
    public float scale;
    public float alpha;
    public float x, y;

    public AnimCircle() {
        initCircle();
    }

    public static void dispose() {
        try {
            circle.getTexture().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initCircle() {
        dispose();

        final int s = Game.w / 3;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA4444);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        circle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }


    public void setup(float x, float y) {
        this.x = x;
        this.y = y;
        alpha = 255;
        scale = 0;
    }

    public void update(float delta) {
        if (alpha > 0) {
            alpha -= (delta * 0.6f);
            scale += (delta * 0.7f);
            if (alpha < 0)
                alpha = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        if (alpha > 0) {
            final float a = (alpha / 255f);

            batch.setColor(1, 1, 1, a);
            circle.setSize(scale, scale);
            circle.setCenter(x, y);
            circle.draw(batch);
        }
    }
}
