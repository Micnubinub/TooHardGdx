package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TrailParticle {
    private static Sprite circle;
    private static boolean isCircleInit = false;
    public float scale;
    public int x;
    public int y;


    public TrailParticle() {
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
        Game.paint.setColor(0xFFe6e8f1);
        pixmap.setColor(new Color(1, 1, 1, 1));
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        circle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }

    public void reset(int x, int y) {
        scale = GameValues.PARTICLE_SIZE;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        scale -= GameValues.PARTICLE_SHRINK_SPEED * delta;
    }

    public void draw(SpriteBatch batch) {
        circle.setPosition(x, y);
        circle.setSize(scale, scale);
        circle.draw(batch);
    }
}
