package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TrailParticle {
    private static Sprite circle;
    public float scale;
    public int x;
    public int y;


    public TrailParticle() {
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

        final int s = Game.w / 4;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        pixmap.setColor(0xe6e8f1FF);
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
        circle.setCenter(x, Game.h - y);
        circle.setSize(scale * 2, scale * 2);
        circle.draw(batch);
    }
}
