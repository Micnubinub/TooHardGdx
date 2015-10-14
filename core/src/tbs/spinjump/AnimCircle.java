package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 11/06/2015.
 */
public class AnimCircle {
    //Todo renderer private static Sprite circle;
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
        }
    }

    private static void initCircle() {
        dispose();
        final int s = Game.w / 3;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
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
            circle.setAlpha(alpha / 255f);
            circle.setSize(scale * 2, scale * 2);
            circle.setCenter(x, Game.h - y);
            circle.draw(batch);
        }
    }
}
