package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Sidney on 16/06/2015.
 */
public class Enemy {
    private static final Color color = new Color();
    //Todo renderer private static Sprite circle;
    public float scale;
    public float x, y;
    public GearPlatform platform;
    public double platformOnAngle;
    public boolean active;

    public Enemy(GearPlatform platform) {
        this.scale = GameValues.ENEMY_SCALE;
        this.platformOnAngle = 0;
        this.platform = platform;
        updateAnglePos();
    }

    public static void dispose() {

    }


    public void draw(ShapeRenderer renderer) {
        if (active) {
            color.set(GameValues.COG_COLOR);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, scale);

            color.set(GameValues.COG_COLOR_2);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, scale / 2);
        }
    }

    public void update(float delta) {
        platformOnAngle -= (platform.rotationSpeed * delta * 0.01745330555f);
        platformOnAngle %= 6.28319;
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
