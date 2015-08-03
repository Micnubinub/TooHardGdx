package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GearPlatform extends GameObject {

    private static final Color c = new Color();
    private static Sprite cog1, cog2, backgroundCircle, ring;
    // Gear Specific:
    public float rotationSpeed;
    public float rotation;
    public int startingWidth;
    public boolean animating;
    public boolean moving;
    public float xSpeed;
    public ArrayList<CoinPickup> coins;
    public ArrayList<Enemy> enemies;

    public GearPlatform() {
        SpikeObject.setWidthAndHeight();
        coins = new ArrayList<CoinPickup>();
        for (int i = 0; i < 5; ++i) {
            coins.add(new CoinPickup());
        }
        enemies = new ArrayList<Enemy>();
        for (int i = 0; i < 2; ++i) {
            enemies.add(new Enemy(this));
        }
        initCircle();
    }

    public static void dispose() {
        try {
            cog1.getTexture().dispose();
        } catch (Exception e) {
        }

        try {
            ring.getTexture().dispose();
        } catch (Exception e) {
        }

        try {
            backgroundCircle.getTexture().dispose();
        } catch (Exception e) {
        }
    }

    private static void initCircle() {
        dispose();

        final int s = Game.w / 3;

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
        Pixmap.setFilter(Pixmap.Filter.BiLinear);

        pixmap.setColor(GameValues.COG_COLOR);
        pixmap.fillCircle(s / 2, s / 2, s / 2);
        cog1 = new Sprite(new Texture(pixmap));

        pixmap.setColor(GameValues.COG_COLOR_2);
        pixmap.fillCircle(s / 2, s / 2, s / 2);
        cog2 = new Sprite(new Texture(pixmap));

        pixmap.setColor(GameValues.BACKGROUND_COLOR);
        pixmap.fillCircle(s / 2, s / 2, s / 2);
        backgroundCircle = new Sprite(new Texture(pixmap));

        pixmap.setColor(GameValues.RING_COLOR);
        pixmap.fillCircle(s / 2, s / 2, s / 2);
        ring = new Sprite(new Texture(pixmap));

        pixmap.dispose();
    }

    public void update(float delta) {
        rotation += (rotationSpeed * delta);

        // SCALING ANIMATION:
        if (animating) {
            if (width < startingWidth) {
                width = startingWidth;
                animating = false;
            }
        }

        // MOVING:
        if (moving) {
            x += delta * xSpeed;
            for (int i = 0; i < coins.size(); ++i) {
                coins.get(i).x += delta * xSpeed;
            }
            if (x > (Game.w * 0.75f)) {
                float adjuster = x - (Game.w * 0.75f);
                x -= adjuster;
                for (int i = 0; i < coins.size(); ++i) {
                    coins.get(i).x -= adjuster;
                }
                xSpeed *= -1;
            } else if (x < (Game.w * 0.25f)) {
                float adjuster = (Game.w * 0.25f) - x;
                x += adjuster;
                for (int i = 0; i < coins.size(); ++i) {
                    coins.get(i).x += adjuster;
                }
                xSpeed *= -1;
            }
        }

        // ENEMIES:
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).update(delta);
        }
    }

    public boolean circleCollision(float x2, float y2, float radius2) {
        final double xDif = x - x2;
        final double yDif = y - y2;
        final double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (width + radius2) * (width + radius2);
    }

    public void draw(SpriteBatch batch) {
        // PLATFORM:
        final int s = width * 2;
        final float ring1 = (width + (GameValues.PLAYER_SCALE)) * 2;
        final float ring2 = (width + (GameValues.PLAYER_SCALE * 2)) * 2;

        ring.setSize(ring2, ring2);
        ring.setCenter(x, Game.h - y);
        ring.draw(batch);

        backgroundCircle.setSize(ring2 - GameValues.RING_WIDTH, ring2 - (GameValues.RING_WIDTH * 2));
        backgroundCircle.setCenter(x, Game.h - y);
        backgroundCircle.draw(batch);

        ring.setSize(ring1, ring1);
        ring.setCenter(x, Game.h - y);
        ring.draw(batch);

        backgroundCircle.setSize(ring1 - (GameValues.RING_WIDTH / 2), ring1 - ((GameValues.RING_WIDTH / 2) * 2));
        backgroundCircle.setCenter(x, Game.h - y);
        backgroundCircle.draw(batch);


        cog1.setSize(s, s);
        cog1.setCenter(x, Game.h - y);
        cog1.draw(batch);

        cog2.setSize(s * 0.7f, s * 0.7f);
        cog2.setCenter(x, Game.h - y);
        cog2.draw(batch);


        // RESET:

        // DRAW COINS:
        for (int i = 0; i < coins.size(); ++i) {
            coins.get(i).draw(batch);
        }

        // DRAW ENEMIES:
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).draw(batch);
        }
    }

    public void moveDown(float speed) { // Already Mult by Delta
        y += speed;
        for (int x = 0; x < coins.size(); ++x) {
            coins.get(x).y += speed;
        }
        // Left the Screen:
        if (y - getWidth() > Game.h) {
            generate(false, false); // Reset
        }
    }

    public void generate(boolean first, boolean second) {
        Level.stageNum += 1;
        moving = false;
        xSpeed = (GameValues.COG_MOVE_SPEED * Level.cogSpeedMult);
        rotationSpeed = GameValues.COG_STARTING_SPEED * Level.cogSpeedMult;
        int tmpWidth = (int) GameValues.COG_MAX_SIZE;
        if (!first) {
            tmpWidth = Utility.getRandom((int) GameValues.COG_MIN_SIZE, (int) GameValues.COG_MAX_SIZE);
            int tmpNum = (int) (GameValues.PLAYER_SCALE + (tmpWidth * 2));
            setX(Utility.getRandom(tmpNum, Game.w - tmpNum));
        } else {
            setX(Game.w / 2);
            rotationSpeed = 0;
        }
        if (second) {
            setX(Game.w / 2);
        }
        float tmpAverage = (GameValues.COG_MIN_SIZE + GameValues.COG_MAX_SIZE) / 2;
        float speedMult = tmpAverage / tmpWidth;
        rotationSpeed *= speedMult;
        setWidth(tmpWidth);
        startingWidth = tmpWidth;
        if (!first)
            setY(Level.getNextCogPos(this));

        // Change Direction Sometimes:
        if (Utility.getRandom(0, 1) == 0)
            rotationSpeed *= -1;

        // STAGE DIFFICULTY GEN STUFF:
        if (Level.stageNum > 5 && Level.stageNum <= 10) { // WHEN LAND ON 5th PLATFORM
            moving = true;
        }

        if (Level.stageNum > 10) {
            moving = Utility.getRandom(0, 3) == 0;
        }

        // ENEMIES:
        int angle = 0;
        boolean hasEnemy = false;
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).active = Utility.getRandom(0, 3) == 0; // CHANGE
            if (enemies.get(i).active)
                hasEnemy = true;
            if (first || Level.stageNum < 20) {
                enemies.get(i).active = false;
                hasEnemy = false;
            }
            enemies.get(i).platformOnAngle = angle;
            angle += (360 / enemies.size());
            angle %= 360;
        }

        // COINS:
        angle = 90 + Utility.getRandom(-30, 30);
        for (int i = 0; i < coins.size(); ++i) {
            coins.get(i).active = Utility.getRandom(0, 2) == 0;
            if (first || hasEnemy)
                coins.get(i).active = false;
            coins.get(i).x = getX();
            coins.get(i).y = (int) ((getY() - width) - (coins.get(i).scale));
            final int[] ints = Utility.getAnglePos(angle, (GameValues.PLAYER_SCALE + width), (int) x, (int) y);
            coins.get(i).angle = angle;
            coins.get(i).setup(ints[0], ints[1]);
            angle += (360 / coins.size());
            angle %= 360;
        }


    }
}
