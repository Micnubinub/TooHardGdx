package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player extends GameObject {

    private static final Color color = new Color();
    private static int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
    private static int[] ints;
    private static float[] lineStop;
    private static int lineWidth, lineLength, lineSep;
    //  Todo renderer
    // Collision & Platforms:
    public GearPlatform platform; // Which Platform the Player is on
    public double platformOnAngle;
    public int score;
    public int highScore;
    public int coins;
    public int lastPlatformIndex;
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
    // PURCHASES:
    public ArrayList<Integer> purchases;
    // COLORS:
    public int color1;
    public int color2;
    public int skinIndex;
    public int trailColor;
    public int trailIndex;
    // SHOW ADS:
    public boolean showAds;
    // POINTS SINCE DEATH:
    public int revivalCount;


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

        lineLength = Math.round(GameValues.PLAYER_SCALE * 0.8f);
        lineWidth = Math.round(lineLength * 0.22f);
        lineSep = Math.round(lineLength * 0.33f);
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
        platformOnAngle = Math.PI;
        particleIndex = 0;
        circleIndex = 0;
        updateAnglePos();
        revivalCount = 0;
    }


    public void revive() {
        platform = Level.gears.get(Level.moverIndex);
        // REMOVE ENEMIES: (SO YOU DONT DIE AGAIN)
        for (int i = 0; i < platform.enemies.size(); ++i) {
            platform.enemies.get(i).active = false;
        }
        platformOnAngle = getAngle(platform.x, platform.y);
        updateAnglePos();
        dead = false;
        revivalCount += 1;
    }

    public void update(float delta) { // Use Delta
        // Update Level Platforms as well:
        if (platform != null) {
            if (!dead) {
                // On a Platform:
                platformOnAngle -= (platform.rotationSpeed * delta * 0.01745330555f);
                platformOnAngle = platformOnAngle % 6.28319;

                // CHECK COIN COLLISION:
                for (int i = 0; i < platform.coins.size(); ++i) {
                    if (platform.coins.get(i).active
                            && circleCollision(platform.coins.get(i).x, platform.coins.get(i).y, platform.coins.get(i).scale)) {
                        // GET COIN:
                        scaleMultiplier = 1.5f;
                        coins += platform.coins.get(i).worth;
                        Game.textAnimator.startText("+" + platform.coins.get(i).worth, (int) x, (int) y);
                        Game.coinSound.play();
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
            // Jumping: (IF NO ONE LIKES IT CHANGE THIS BACK)
            x += (speed * delta) * Math.sin(platformOnAngle % 6.28319);
            y += (speed * delta) * Math.cos(platformOnAngle % 6.28319);

            // Check Landing:
            for (int i = 0; i < Level.gears.size(); ++i) {
                if (i != lastPlatformIndex)
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

    public void draw(ShapeRenderer renderer) {
        drawLine(renderer);
        if (!dead)
            for (int i = 0; i < trail.size(); ++i) {
                trail.get(i).draw(renderer);
            }
        for (int i = 0; i < splash.size(); ++i) {
            splash.get(i).draw(renderer);
        }

        for (int i = 0; i < circles.size(); ++i) {
            circles.get(i).draw(renderer);
        }

        if (!dead) {
            color.set(color1);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, width);

            color.set(color2);
            renderer.setColor(color);
            renderer.circle(x, Game.h - y, width / 2);
            if (overAlpha > 0) {
                color.set(1, 1, 1, overAlpha / 255f);
                renderer.circle(x, Game.h - y, width);
            }
        }
    }

    private void drawLine(ShapeRenderer renderer) {
        if (!(Game.state == GameState.Playing) || !isOnPlatform() || dead)
            return;

        CircleIntercestor.reset();

        for (GearPlatform gear : Level.gears) {
            if (gear == platform)
                continue;

            Utility.getAnglePos(platformOnAngle, Game.h, platform.x, platform.y);
            lineStop = CircleIntercestor.intersect(gear, ints[0], ints[1]);
            if (lineStop[2] > 0) break;
        }

        float dirX = lineStop[0] - x;
        float dirY = (Game.h - lineStop[1]) - (Game.h - y);

        float length = Vector2.len(dirX, dirY);
        dirX /= length;
        dirY /= length;

        float curLen = 0;
        float curX;
        float curY;

        color.set(0xffffffaa);
        renderer.setColor(color);

        while (curLen < length) {
            curX = (x + dirX * curLen);
            curY = ((Game.h - y) + dirY * curLen);
            if (curLen + lineLength > length) {
                final float len = length - curLen;
                renderer.rectLine(curX, curY, curX + dirX * len, (curY) + dirY * len, lineWidth);
            } else {
                renderer.rectLine(curX, curY, curX + dirX * lineLength, (curY) + dirY * lineLength, lineWidth);
            }
            curLen += (lineLength + lineSep);
        }

    }

    public void land(GearPlatform platformTmp) {
        if (!dead) {
            score += 1;
            int expectedIndex = lastPlatformIndex + 1;
            if (expectedIndex >= Level.gears.size())
                expectedIndex = 0;
            if (Level.gears.indexOf(platformTmp) != expectedIndex)
                score += 1;
            Game.scoreTextMult = 1.5f;
            platform = platformTmp;
            if (Level.moverIndex != Level.gears.indexOf(platform)) {
                Level.moverIndex = Level.gears.indexOf(platform);
                Level.moving = true;
            }
            platformOnAngle = Math.atan2(x - platform.x, y - platform.y);
        }
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

        ints = Utility.getAnglePos((platformOnAngle % 6.28319), platform.width + width, (int) platform.x, (int) platform.y);
        x = ints[0];
        y = ints[1];
    }

    public void jump() {
        // Jump if on a Platform:
        if (isOnPlatform()) {
            Game.jumpSound.play();
            Level.updateLevelMover(Level.gears.indexOf(platform));
            updateAnglePos();
            lastPlatformIndex = Level.gears.indexOf(platform);
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

    public void earnCoinAnim(int xT, int yT, int amount) {
        yT = Game.h - yT;
        circles.get(circleIndex).setup(xT, yT);
        for (int i = 0; i < splash.size(); ++i) {
            splash.get(i).setup(xT, yT);
        }
        Game.scoreTextMult = 1.5f;
        if (amount > 0) {
            Game.textAnimator.startText("+" + amount, xT, yT);
            coins += amount;
        }
        saveData();
    }

    public void circleAnim() {
        circles.get(circleIndex).setup(x, y);
        circleIndex += 1;
        if (circleIndex >= circles.size())
            circleIndex = 0;
    }

    public void death() {
        Game.showAdInt += 1;
        if (score > 5)
            Game.showAdInt += 1;
        if (score > 10)
            Game.showAdInt += 2;
        if (score > 30)
            Game.showAdInt += 2;
        if (score > 50)
            Game.showAdInt += 2;
        Game.deathSound.play();
        dead = true;
        splashAnim();
        circleAnim();

        // REVIVAL BUTTON:
        Game.reviveButton.active = false;
        if (score < 10) {
            Game.revivalCost = 10;
        } else if (score < 20) {
            Game.revivalCost = 25;
        } else if (score < 30) {
            Game.revivalCost = 100;
        } else if (score < 50) {
            Game.revivalCost = 250;
        } else if (score < 80) {
            Game.revivalCost = 500;
        } else if (score < 90) {
            Game.revivalCost = 1000;
        } else if (score <= 100) {
            Game.revivalCost = 5000;
        } else if (score > 100) {
            Game.revivalCost = 0;
        }
        Game.reviveButton.active = coins >= Game.revivalCost;
        if (Game.revivalCost == 0 || score < 1 || revivalCount > 3)
            Game.reviveButton.active = false;

        int buttonNum = Utility.getRandom(0, 100);
        Game.adButton.active = false;
        Game.likeButton.active = false;
        Game.buyButton.active = false;
        if (buttonNum < 10) { // 10 %
            Game.likeButton.active = true;
        } else if (buttonNum < 40) { // 30 %
            Game.adButton.active = true;
        } else if (buttonNum < 80) { // 40%
            Game.buyButton.active = true;
        } else { // 20 %

        }

        // DEATH ACHIEVEMNTS:
// Todo       if (score >= 5) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQBQ");
//        }
//        if (score >= 25) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQBg");
//        }
//        if (score >= 100) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQBw");
//        }
//        if (score < 1) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQDQ");
//        }
//        if (score == 69) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQDg");
//        }

        Game.state = GameState.Death;
        saveData(); // SAVE COINS AND SCORE

        // SAVE TO LEADERBOARD:
// Todo       if (BaseGameActivity.getApiClient().isConnected())
//            Games.Leaderboards.submitScore(MainActivity.getApiClient(),
//                    GameValues.leaderboardID, highScore);

        // LOAD ADS:
// Todo       if (Game.loadedBigAd) {
//            if (Game.interstitial.isLoaded()) {
//                Game.interstitial.show();
//                Game.showAdInt = 0;
//                Game.loadedBigAd = false;
//            }
//
//        }
        // LOAD BIG AD SOMEITMES:
        if (Game.showAdInt >= 14) {
            Game.showAd(false);
        }
    }

    public void loadData() {
        // LOAD DATA:
        highScore = Utility.getInt("hScore");
        coins = Utility.getInt("pCoins");

        if (Utility.getString("sAds") != null) {
            showAds = Utility.getString("sAds").equals("true");
        } else {
            showAds = true;
        }

        // PURCHASES:
        purchases = new ArrayList<Integer>();
        if (Utility.getString("pPurchases") != null) {
            String[] animalsArray = Utility.getString("pPurchases").split(",");
            for (int i = 0; i < Game.storeItems.size(); ++i) {
                if (i == 0 || i == 34) { // UNLUCK FIRST SKIN AND TRAIL:
                    purchases.add(1);
                } else {
                    try {
                        purchases.add(Integer.valueOf(animalsArray[i]));
                    } catch (Exception e) {
                        purchases.add(0); // ADD NEW ITEMS IF STORE HAS NEW ONES
                    }
                }
            }
        } else {
            for (int i = 0; i < Game.storeItems.size(); ++i) {
                if (i == 0)
                    purchases.add(1);
                else
                    purchases.add(0);
            }
        }

        // UPDATE STORE STUFF:
        for (int i = 0; i < Game.storeItems.size(); ++i) {
            Game.storeItems.get(i).bought = purchases.get(i) == 1; // x == y;
        }

        // EQUIP:
        //SKIN:
        skinIndex = Utility.getInt("psIndex");

        equipSkin(Game.storeItems.get(skinIndex));
        // TRAIL:
        trailIndex = Utility.getInt("trIndex");

        equipTrail(Game.storeItems.get(trailIndex));
    }

    public void saveData() {
        if (score > highScore) {
            Utility.saveInt("hScore", score);
            highScore = score;
        }
        Utility.saveInt("pCoins", coins);
        if (showAds) {
            Utility.saveString("sAds", "true");
        } else {
            Utility.saveString("sAds", "false");
        }

        // SAVE PURCHASES:
        String sToSave = "";
        for (int i = 0; i < purchases.size(); ++i) {
            if (i < purchases.size() - 1)
                sToSave += purchases.get(i).toString() + ",";
            else
                sToSave += purchases.get(i).toString();
        }
        Utility.saveString("pPurchases", sToSave);

        // MONEY ACHIEVEMENTS:
// Todo       if (coins > 100) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQCQ");
//        }
//        if (coins > 100000) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQCg");
//        }
//        if (coins > 1000000) {
//            MainActivity.unlockAchievement("CgkIxIfix40fEAIQCw");
//        }
//           UPDATE COST OF CASINO EVERYTIME COINS CHANGE:
        Game.casinoManager.updateCost(coins);
    }

    public void equipSkin(StoreItem item) {
        color1 = item.color1;
        color2 = item.color2;
        skinIndex = Game.storeItems.indexOf(item);
        Utility.saveInt("psIndex", skinIndex);
    }

    public void equipTrail(StoreItem item) {
        trailColor = item.color1;
        trailIndex = Game.storeItems.indexOf(item);
        Utility.saveInt("trIndex", trailIndex);
    }

    public boolean isOnPlatform() {
        return (platform != null);
    }
}
