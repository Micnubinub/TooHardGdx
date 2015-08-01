package tbs.spinjump;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sidney on 16/06/2015.
 */
public class Enemy {
    private static Sprite circle;
    public float scale;
    public float x, y;
    public GearPlatform platform;
    public double platformOnAngle;
    public boolean active;

    public Enemy(GearPlatform platform) {
        this.scale = GameValues.ENEMY_SCALE;
        this.platformOnAngle = 0;
        this.platform = platform;
        initCircle();
        updateAnglePos();
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

        pixmap.setColor(GameValues.COG_COLOR);
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        pixmap.setColor(GameValues.COG_COLOR_2);
        pixmap.fillCircle(s / 2, s / 2, s / 4);

        circle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }

    public void draw(SpriteBatch canvas) {
        if (active) {
            circle.setCenter(x, y);
            circle.setSize(scale, scale);
            circle.draw(canvas);
        }
    }

    public void update(float delta) {
        platformOnAngle -= (platform.rotationSpeed * delta);
        platformOnAngle %= 360;
        updateAnglePos();
    }

    public void updateAnglePos() {
        if (platform == null)
            return;
        final int[] ints = Utility.getAnglePos((float) platformOnAngle, platform.width + (scale), (int) platform.x, (int) platform.y);
        x = ints[0];
        y = ints[1];
    }

}
