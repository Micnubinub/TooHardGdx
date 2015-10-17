package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Sidney on 11/06/2015.
 */
public class AnimCircle {
    private static final Color color = new Color();
    //Todo renderer private static Sprite circle;
    public float scale;
    public float alpha;
    public float x, y;

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

    public void draw(ShapeRenderer renderer) {
        if (alpha > 0) {
            color.set(1, 1, 1, alpha / 255f);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, scale / 2);
        }
    }
}
