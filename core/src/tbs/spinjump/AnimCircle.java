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
    private static boolean isCircleInit = false;
    public float scale;
    public float alpha;
    public float x, y;

    public AnimCircle() {
        initCircle();
    }

    public static void dispose() {
        //Todo call thi in dispose, and make sure all the methods call getX(){ if (!isXInit...){initX();)
        circle.getTexture().dispose();
        isCircleInit = false;
    }

    private static void initCircle() {
        if (isCircleInit)
            return;
        isCircleInit = true;

        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        Pixmap.setBlending(Pixmap.Blending.None);

        //Todo get maxHeight/width of the circle
        final int s = GameValues.MENU_BTN_WIDTH;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
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
            batch.setColor(1, 1, 1, (alpha / 225f));
            circle.setPosition(x, y);
            circle.setSize(scale, scale);
            circle.draw(batch);
        }
    }
}
