package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 11/06/2015.
 */
public class CoinPickup {
    //Todo renderer private static Sprite circle;
    public int worth;
    public float x, y;
    public float scale;
    public boolean active;
    public float angle;

    public CoinPickup() {
        scale = GameValues.COIN_SCALE;
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
        pixmap.setColor(GameValues.COIN_COLOR);
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        pixmap.setColor(GameValues.COIN_COLOR_2);
        pixmap.fillCircle(s / 2, s / 2, s / 4);

        circle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }

    public void setup(float x, float y) {
        this.x = x;
        this.y = y;
        worth = 1;
    }

    public void draw(SpriteBatch canvas) {
        if (active) {
            circle.setCenter(x, Game.h - y);
            circle.setSize(scale * 2, scale * 2);
            circle.draw(canvas);
        }
    }
}
