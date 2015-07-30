package tbs.spinjump;

import android.graphics.Canvas;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 11/06/2015.
 */
public class CoinPickup {
    public int worth;
    public float x, y;
    public float scale;
    public boolean active;
    public float angle;

    public CoinPickup() {
        scale = GameValues.COIN_SCALE;
    }

    public void setup(float x, float y) {
        this.x = x;
        this.y = y;
        worth = 1;
    }

    public void draw(SpriteBatch canvas) {
        if (active) {
            Game.paint.setColor(GameValues.COIN_COLOR);
            canvas.drawCircle(x, y, scale, Game.paint);
            Game.paint.setColor(GameValues.COIN_COLOR_2);
            canvas.drawCircle(x, y, scale * 0.5f, Game.paint);
        }
    }
}
