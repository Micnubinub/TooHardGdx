package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player extends GameObject {

    private static final Color color = new Color();
    private static int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
    // Collision & Platforms:
    public GearPlatform platform; // Which Platform the Player is on
    public double platformOnAngle;
    public int score;
    public int highScore;
    public int coins;
    // Particles:
    public int particleTime;
    public int particleIndex;
    public int particleUseIndex;
    public int particleGroups = 3;
    public ArrayList<TrailParticle> trail;
    public ArrayList<Particle> splash;
    public ArrayList<AnimCircle> circles;
    public int circleIndex;
    // ANIMATIONS:
    public float scaleMultiplier;
    public float overAlpha;
    // DEATH:
    public boolean dead;

    public Player() {
        setWidth((int) GameValues.PLAYER_SCALE);
        setHeight((int) GameValues.PLAYER_SCALE);
        trail = new ArrayList<TrailParticle>();
        for (int i = 0; i < 6; ++i) {
            trail.add(new TrailParticle());
        }
        splash = new ArrayList<Particle>();
        for (int i = 0; i < 30; ++i) {
            splash.add(new Particle());
        }
        circles = new ArrayList<AnimCircle>();
        for (int i = 0; i < 6; ++i) {
            circles.add(new AnimCircle());
        }
    }

    public void setup() {
        // Starting Position on First Cog
        setupParticles();
        dead = false;
        score = 0;
        speed = GameValues.PLAYER_JUMP_SPEED;
        setX(w / 2);
        platform = Level.gears.get(0);
        setY((int) ((platform.getY() - platform.width) - (width)));
        platformOnAngle = getAngle(platform.x, platform.y);
        particleIndex = 0;
        circleIndex = 0;
        updateAnglePos();
    }

    public void update(float delta) { // Use Delta
        // Update Level Platforms as well:
        if (platform != null) {
            if (!dead) {
                // On a Platform:
                platformOnAngle -= (platform.rotationSpeed * delta);
                platformOnAngle %= 360;

                // CHECK COIN COLLISION:
                for (int i = 0; i < platform.coins.size(); ++i) {
                    if (platform.coins.get(i).active
                            && circleCollision(platform.coins.get(i).x, platform.coins.get(i).y, platform.coins.get(i).scale)) {
                        // GET COIN:
                        scaleMultiplier = 1.5f;
                        coins += platform.coins.get(i).worth;
                        Game.textAnimator.startText("+" + platform.coins.get(i).worth, (int) x, (int) y);
                        platform.coins.get(i).active = false;
                        splashAnim();
                        circleAnim();
                        Game.coinTextMult = 1.5f;
                        // SOUND:

                    }
                }

                // CHECK ENEMY COLLISION:
                for (int i = 0; i < platform.enemies.size(); ++i) {
                    if (platform.enemies.get(i).active
                            && circleCollision(platform.enemies.get(i).x, platform.enemies.get(i).y, platform.enemies.get(i).scale)) {
                        // DIE:
                        death();
                    }
                }
            }
        } else {
            // Jumping:
            x += (speed * delta) * Math.sin(Math.toRadians(platformOnAngle + 90));
            y += (speed * delta) * Math.cos(Math.toRadians(platformOnAngle + 90));

            // Check Landing:
            for (int i = 0; i < Level.gears.size(); ++i) {
                if (Level.gears.get(i).circleCollision(x, y, width)) {
                    land(Level.gears.get(i));
                }
            }
        }

        updateAnglePos();

        // Update Particles:
        particleTime -= delta;
        if (particleTime <= 0) {
            particleTime = GameValues.PARTICLE_LIFETIME;
            particleIndex += 1;
            if (particleIndex >= trail.size()) {
                particleIndex = 0;
            }
            trail.get(particleIndex).reset((int) x, (int) y);
        }
        for (int i = 0; i < trail.size(); ++i) {
            trail.get(i).update(delta);
        }
        for (int i = 0; i < splash.size(); ++i) {
            splash.get(i).update(delta);
        }
        for (int i = 0; i < circles.size(); ++i) {
            circles.get(i).update(delta);
        }

        // Check Death:
        if (!dead && (x < 0 || y < 0 || x >= w || y >= h)) {
            death();
        }

        // ANIMATION:
        scaleMultiplier -= delta * 0.0025;
        if (scaleMultiplier < 1) {
            scaleMultiplier = 1;
        }
        width = (int) (GameValues.PLAYER_SCALE * scaleMultiplier);
        height = width;
        if (overAlpha > 0)
            overAlpha -= delta * 0.5f;
        if (overAlpha < 0)
            overAlpha = 0;
    }

    public boolean circleCollision(float x2, float y2, float radius2) {
        final double xDif = x - x2;
        final double yDif = y - y2;
        final double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (width + radius2) * (width + radius2);
    }

    public void draw(SpriteBatch canvas) {
        if (!dead)
            for (int i = 0; i < trail.size(); ++i) {
                trail.get(i).draw(canvas);
            }
        for (int i = 0; i < splash.size(); ++i) {
            splash.get(i).draw(canvas);
        }
        //Todo Game.paint.setStrokeWidth(GameValues.RING_WIDTH / 1.25f);
        for (int i = 0; i < circles.size(); ++i) {
            circles.get(i).draw(canvas);
        }
        if (!dead) {
//            Game.paint.setColor(GameValues.PLAYER_COLOR);
//            canvas.drawCircle(x, y, width, Game.paint);
//            Game.paint.setColor(GameValues.PLAYER_COLOR_2);
//            canvas.drawCircle(x, y, width * 0.5f, Game.paint);
//            if (overAlpha > 0) {
//                Game.paint.setColor(0xFFFFFFFF);
//                Game.paint.setAlpha((int) overAlpha);
//                canvas.drawCircle(x, y, width, Game.paint);
//            }
        }
    }

    public void land(GearPlatform platformTmp) {
        score += 1;
        Game.scoreTextMult = 1.5f;
        platform = platformTmp;
        if (Level.moverIndex != Level.gears.indexOf(this)) {
            Level.moverIndex = Level.gears.indexOf(platform);
            Level.moving = true;
        }
        platformOnAngle = (180 - getAngle(platform.x, platform.y)) % 360;
        updateAnglePos();
    }

    public void setupParticles() {
        for (int i = 0; i < trail.size(); ++i) {
            trail.get(i).reset((int) x, (int) y);
        }
        particleUseIndex = 0;
    }

    public void updateAnglePos() {
        if (platform == null)
            return;
        final int[] ints = Utility.getAnglePos((float) platformOnAngle, platform.width + (width), (int) platform.x, (int) platform.y);
        x = ints[0];
        y = ints[1];
    }

    public void jump() {
        // Jump if on a Platform:
        if (isOnPlatform()) {
            Level.updateLevelMover(Level.gears.indexOf(platform));
            platform = null;
        }
    }

    public void splashAnim() {
        overAlpha = 200;
        int start = (particleUseIndex * (splash.size() / particleGroups));
        int end = ((particleUseIndex + 1) * (splash.size() / particleGroups));
//        .e("DEBUG", "START: " + start + " END: " + end);
        for (int i = start; i < end; ++i) {
            splash.get(i).setup((int) x, (int) y);
        }
        particleUseIndex += 1;
        if (particleUseIndex == 3) // 3 GROUPS
            particleUseIndex = 0;
    }

    public void circleAnim() {
        circles.get(circleIndex).setup(x, y);
        circleIndex += 1;
        if (circleIndex >= circles.size())
            circleIndex = 0;
    }

    public void death() {
        dead = true;
        splashAnim();
        splashAnim();
        circleAnim();
        Game.state = GameState.Death;
        saveData(); // SAVE COINS AND SCORE
    }

    public void loadData() {
        // LOAD DATA:
        highScore = Utility.getInt("hScore");
        coins = Utility.getInt("pCoins");

    }

    public void saveData() {
        if (score > highScore) {
            Utility.saveInt("hScore", score);
            highScore = score;
        }
        Utility.saveInt("pCoins", coins);
    }

    public boolean isOnPlatform() {
        return (platform != null);
    }
}
