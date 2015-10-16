package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Sidney on 11/06/2015.
 */
public class CoinPickup {
    private static final Color color = new Color();
    //Todo renderer private static Sprite circle;
    public int worth;
    public float x, y;
    public float scale;
    public boolean active;
    public float angle;

    public CoinPickup() {
        scale = GameValues.COIN_SCALE;
    }


    public static void dispose() {

    }


    public void setup(float x, float y) {
        this.x = x;
        this.y = y;
        worth = 1;
    }

    public void draw(ShapeRenderer renderer) {
        if (active) {

            color.set(GameValues.COIN_COLOR);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, scale);

            color.set(GameValues.COIN_COLOR_2);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, scale / 2);
        }
    }
}
