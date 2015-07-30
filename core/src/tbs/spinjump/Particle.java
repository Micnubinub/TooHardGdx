package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Particle {
    public static final int STATE_ALIVE = 0; // particle is alive
    public static final int STATE_DEAD = 1; // particle is dead

    public static final int DEFAULT_LIFETIME = 200; // play with this
    private static Sprite circle;
    private static boolean isCircleInit = false;
    private int state; // particle is alive or dead
    private float width; // width of the particle
    private float height; // height of the particle
    private float x, y; // horizontal and vertical position
    private double xv, yv; // vertical and horizontal velocity
    private int age; // current age of the particle
    private int lifetime; // particle dies when it reaches this value
    private int color; // the color of the particle

    public Particle() {
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
        pixmap.setColor(new Color(1, 1, 1, 1));
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        circle = new Sprite(new Texture(pixmap));

        pixmap.dispose();
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

    public void draw(SpriteBatch batch) {
        if (this.state == STATE_ALIVE) {
            circle.setPosition(x, y);
            circle.setSize(width, width);
            circle.draw(batch);
        }
    }
}