package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class GearPlatform extends GameObject {

    private static final Color c = new Color();
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
    }

    public static void dispose() {

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

    public void draw(ShapeRenderer renderer) {
        final float ring1 = (width + (GameValues.PLAYER_SCALE));
        final float ring2 = (width + (GameValues.PLAYER_SCALE * 2));

        c.set(GameValues.RING_COLOR);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, ring2);

        c.set(GameValues.BACKGROUND_COLOR);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, ring2 - (GameValues.RING_WIDTH / 2));

        c.set(GameValues.RING_COLOR);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, ring1);

        c.set(GameValues.BACKGROUND_COLOR);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, ring1 - (GameValues.RING_WIDTH / 2));

        c.set(GameValues.COG_COLOR);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, width);

        c.set(GameValues.COG_COLOR_2);
        renderer.setColor(c);
        renderer.circle(x, Game.h - y, width * 0.7f);

        // RESET:

        // DRAW COINS:
        for (int i = 0; i < coins.size(); ++i) {
            coins.get(i).draw(renderer);
        }

        // DRAW ENEMIES:
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).draw(renderer);
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
        rotationSpeed *= (speedMult * 0.9);
        setWidth(tmpWidth);
        startingWidth = tmpWidth;
        if (!first)
            setY(Level.getNextCogPos(this));

        // Change Direction Sometimes:
        if (Utility.rand.nextBoolean())
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
            enemies.get(i).active = Utility.getRandom(0, 3) == 0; // CHANGE MAYBE
            if (enemies.get(i).active)
                hasEnemy = true;
            if (first || Level.stageNum < 20) {
                enemies.get(i).active = false;
                hasEnemy = false;
            }
            enemies.get(i).platformOnAngle = angle;
            angle += (6.28319 / enemies.size());
            angle %= 6.28319;
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
            angle += (6.28319 / coins.size());
            angle %= 6.28319;
        }


    }
}
