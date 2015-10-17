package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particle {
    public static final int STATE_ALIVE = 0; // particle is alive
    public static final int STATE_DEAD = 1; // particle is dead

    public static final int DEFAULT_LIFETIME = 200; // play with this
    private static final Color color = new Color(1, 1, 1, 1); // the color of the particle
    //Todo renderer private static Sprite circle;
    private int state; // particle is alive or dead
    private float width; // width of the particle
    private float height; // height of the particle
    private float x, y; // horizontal and vertical position
    private double xv, yv; // vertical and horizontal velocity
    private int age; // current age of the particle
    private int lifetime; // particle dies when it reaches this value


    public Particle() {
    }

    public void setup(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = Particle.STATE_ALIVE;
        this.width = Utility.getRandom((int) (GameValues.SPLASH_SIZE * 0.5f), (int) (GameValues.SPLASH_SIZE * 1.5f));
        this.height = this.width;
        this.lifetime = DEFAULT_LIFETIME;
        this.age = 0;
        this.xv = (double) (Utility.randFloat((int) -GameValues.SPLASH_SPEED, (int) GameValues.SPLASH_SPEED));
        this.yv = (double) (Utility.randFloat((int) -GameValues.SPLASH_SPEED, (int) GameValues.SPLASH_SPEED));
        // smoothing out the diagonal speed
        xv *= 0.85;
        yv *= 0.85;

        // DEBUG VALUES:::::
    }

    public void update(float delta) {
        if (this.state != STATE_DEAD) {
            this.x += this.xv * delta;
            this.y += this.yv * delta;
            this.yv += delta * 0.0025f;
            this.age++; // increase the age of the particle
            if (this.age >= this.lifetime) { // reached the end if its life
                this.state = STATE_DEAD;
            }
        }
    }

    public void draw(ShapeRenderer renderer) {
        if (this.state == STATE_ALIVE) {
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, width / 2);
        }
    }
}