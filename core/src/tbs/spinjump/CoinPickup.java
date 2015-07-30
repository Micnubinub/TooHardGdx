package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 11/06/2015.
 */
public class CoinPickup {
    private static Sprite circle;
    private static boolean isCircleInit;
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

        //Todo move cod color into pix,
        pixmap.fillCircle(s / 2, s / 2, s / 2);
        pixmap.setColor(GameValues.COIN_COLOR);

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
            circle.setPosition(x, y);
            circle.setSize(scale, scale);
            circle.draw(canvas);
        }
    }
}
