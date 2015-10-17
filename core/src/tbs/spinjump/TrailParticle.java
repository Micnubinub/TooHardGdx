package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TrailParticle {
    private static final Color color = new Color();
    //Todo renderer private static Sprite circle;
    public float scale;
    public int x;
    public int y;

    public TrailParticle() {
    }

    public void reset(int x, int y) {
        scale = GameValues.PARTICLE_SIZE;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        scale -= GameValues.PARTICLE_SHRINK_SPEED * delta;
    }

    public void draw(ShapeRenderer renderer) {
        color.set(0xe6e8f1FF);
        renderer.setColor(color);
        renderer.circle(x, Game.h - y, scale);
    }
}
