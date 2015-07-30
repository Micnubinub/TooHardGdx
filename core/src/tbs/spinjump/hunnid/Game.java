package tbs.spinjump.hunnid.blackwhite.bw2.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import logic.AnimCircle;
import logic.JumpTrail;
import logic.Level;
import logic.Player;
import logic.ScoreAnimCircle;
import logic.SpeedParticle;
import logic.SplashParticle;
import logic.Tile;
import logic.TrailParticle;
import logic.UnlockText;
import logic.Utility;
import menu.MainMenu;
import menu.MenuItem;
import menu.Shape;
import menu.ValueAnimator;

/**
 * Created by root on 30/10/14.
 */
public class Game implements Screen, InputProcessor {
    public static final int width = Gdx.graphics.getWidth();
    public static final int height = Gdx.graphics.getHeight();
    private static final ArrayList<SpeedParticle> speedParticles = new ArrayList<SpeedParticle>();
    private static final int center = (Gdx.graphics.getWidth() / 2);
    private static final int greyTextStart = (int) ((height - (height / 8f)) - (center / 5f));
    private static final Color grey = new Color(0.8627f, 0.8627f, 0.8627f, 1), dark_grey = new Color(0x808080ff), tapToRestart = new Color(0xa0a0a0ff);
    public static int cHoleNum;
    public static int score, lastScore, highScore, tileWidth, tileHeight;
    public static Level level;
    public static int color;
    public static Player player;
    public static Sprite spikeLeft;
    private static Sprite deathScreenButtonBackground;
    private static Sprite speedParticle;
    private static Sprite wall;
    private static Sprite emptySpace;
    private static Sprite spikeRight;
    private static Sprite scoreBackground;
    private static BitmapFont font;
    private static int time;
    private static int spikeNum;
    private static float speedFactor;
    private static SpriteBatch batch;
    private static MenuItem home, share;
    private static GameContainer gameContainer;
    private final OrthographicCamera camera;
    private final ValueAnimator animator = new ValueAnimator();
    private final GameMode gameMode;
    private final Color whiteFadeOut = new Color(0xffffffff);
    private boolean deathNumUpdated;
    private ScoreAnimCircle animCircleAnim;
    private boolean inMenu, isEncourageTextEnabled;
    private boolean scoreSaved = true;
    private int totalJumps, deathNum, scoreBackgroundLen = Utility.width / 4;

    private Texture coin;
    private double animated_value = 1;
    private int iteration = 1;
    private final ValueAnimator.UpdateListener updateListener = new ValueAnimator.UpdateListener() {
        @Override
        public void update(double animatedValue) {
            animated_value = animatedValue;
        }

        @Override
        public void onAnimationStart() {
            iteration = 1;
        }

        @Override
        public void onAnimationFinish() {

        }
    };

    private int deathScreenTop;
    private boolean isEffectEnabled = true;
    private int coins = 0;

    public Game(final SpriteBatch spriteBatch, final GameContainer container, GameMode mode) {
        batch = spriteBatch;
        gameContainer = container;
        gameMode = mode;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player = null;
        init();
        getSprites();
    }

//    private static void saveScreenShot() {
//        try {
//            FileHandle fh;
//            do {
//                fh = new FileHandle(Gdx.files.getLocalStoragePath() + "screenshot.png");
//            } while (fh.exists());
//            final Pixmap pixmap = getScreenShot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
//            PixmapIO.writePNG(fh, pixmap);
//            pixmap.dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private static Sprite getSprite(Shape shape, final int w, final int h, final Color color) {
        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        return getSprite(shape, w, h, pixmap);
    }

    private static Sprite getSprite(Shape shape, final int w, final int h, final int color) {
        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        return getSprite(shape, w, h, pixmap);
    }

    private static Sprite getSprite(Shape shape, final int w, final int h, Pixmap pixmap) {
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        Pixmap.setBlending(Pixmap.Blending.None);
        switch (shape) {
            case CIRCLE:
                final int r = Math.min(w, h) / 2;
                pixmap.fillCircle(w / 2, h / 2, r);
                break;
            case RECT:
                pixmap.fill();
                break;
        }
        final Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new Sprite(texture);
    }

    private static Sprite getSpike(final int w, final int h, final int color, boolean left) {
        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        if (left)
            pixmap.fillTriangle(0, 0, w, h / 2, 0, h);
        else
            pixmap.fillTriangle(w, h, 0, h / 2, w, 0);
        final Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new Sprite(texture);
    }

    public static float getSpeedFactor() {
        return level.speed;
    }

    private Sprite getDeathScreenButtonBackground(final int w, final int h) {
        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xffffffff);
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        final int cy = h / 2;

        pixmap.fillCircle(cy, cy, cy);
        pixmap.fillRectangle(cy, 0, w - cy - cy, pixmap.getHeight());
        pixmap.fillCircle(w - cy, cy, cy);

        final Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new Sprite(texture);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                click(0, 0);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private void click(int x, int y) {
        y = height - y;
        if (inMenu) {
            if (share.checkClick(false, x, y)) {
                //share();
                return;
            }
            if (home.checkClick(false, x, y)) {
                exit();
                return;
            }
        }

        inMenu = false;

        if (player.state == Player.PlayerState.DYING)
            endGame();

        if (player.state == Player.PlayerState.ALIVE) {
            player.jump();
            inMenu = false;
            animCircleAnim.activate();
            lastScore = player.score;
        }


        if (animator.isRunning())
            animator.stop();

        if ((player.state == Player.PlayerState.DYING)) {
            highScore = getHighScore();
            saveHighScore(Math.max(highScore, lastScore));
        }

        if (player.state == Player.PlayerState.IDLE)
            player.jump();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        click(x, y);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void init() {
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = Utility.getFont();

        Gdx.input.setInputProcessor(this);
        tileHeight = (int) (height / 3.5f);
        tileWidth = (int) (width / 10.25f);

        cHoleNum = 2;
        coins = getCoins();
        highScore = getHighScore();
        totalJumps = Utility.getInt(Utility.TOTAL_JUMPS);
        deathNum = Utility.getInt(Utility.TOTAL_DEATHS);
        isEncourageTextEnabled = isEncourageTextEnabled();
        isEffectEnabled = Utility.getPreferences().getBoolean(Utility.EFFECTS_ENABLED_BOOL, true);

        color = 0x738ffeff;

        coin = new Texture(Gdx.files.internal("coin.png"));
        animator.setUpdateListener(updateListener);
        animator.setMode(ValueAnimator.Mode.DECELERATE);
        animator.setDuration(850f);

        switch (gameMode) {
            case ARCADE:
                time = 680;
                break;
            case PRACTICE:
                time = 1200;
                break;
        }

        speedFactor = height / (float) time;
        spikeNum = 6;
        speedParticles.clear();
        for (int i = 0; i < 6; ++i) {
            speedParticles.add(new SpeedParticle());
        }
        initGame();
    }

    private void initGame() {
        if (player == null || !player.isInitGame()) {
            level = new Level(speedFactor);
            player = new Player(batch, Player.Mode.GAME, level, speedFactor * 1.68f);
            player.newGame();
        }
        animated_value = 1;
        deathNumUpdated = false;
        scoreSaved = false;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
    }

    @Override
    public void render(float delta) {
        clearScreen();

        if (animCircleAnim != null && animCircleAnim.active)
            animCircleAnim.update(delta);

        if ((player.state == Player.PlayerState.DYING)) delta /= 1.5;

        batch.begin();
        if (animator.isRunning())
            animator.update();
        update(delta);
        drawGame(delta);
        batch.end();
    }

    private Sprite getScoreBackground(int diameter) {
        final Pixmap pixmap = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0xffffffff));
        final int r = diameter / 2;
        pixmap.fillCircle(r, r, r);

        final Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new Sprite(texture);
    }

    private void getSprites() {
        if (isEffectEnabled)
            speedParticle = getSprite(Shape.RECT, width / 65, height / 3, 0xffffff33);
        emptySpace = getSprite(Shape.RECT, tileWidth, tileHeight, 0xdcdcdcff);
        wall = getSprite(Shape.RECT, tileWidth, tileHeight, 0x808080ff);

        final int tmpHeight = tileHeight / spikeNum;
        spikeLeft = getSpike(Math.round(tmpHeight * 0.8f), tmpHeight, 0x808080ff, true);
        spikeRight = getSpike(Math.round(tmpHeight * 0.8f), tmpHeight, 0x808080ff, false);

        scoreBackground = getScoreBackground(width);
        animCircleAnim = new ScoreAnimCircle();

        font.setScale(Utility.mediumText());
        final int buttonHeight = (int) (font.getLineHeight() * 1.5f);

        deathScreenButtonBackground = getDeathScreenButtonBackground(Utility.BUTTON_WIDTH, buttonHeight);

        share = new MenuItem(deathScreenButtonBackground, "Share");
        home = new MenuItem(deathScreenButtonBackground, "Home");

        share.setPosX(Utility.FIRST_BUTTON_X);
        home.setPosX(Utility.FIRST_BUTTON_X + Utility.FIRST_BUTTON_X / 2 + Utility.BUTTON_WIDTH);

        share.setTextColor(grey);
        MenuItem.setTextSize(Utility.mediumText());
    }

    private void endGame() {
        inMenu = true;
        if (!scoreSaved) {
            coins = getCoins() + (lastScore / 5);
            saveCoins(coins);
            scoreSaved = true;
        }
        initGame();
    }

    private void drawGame(float delta) {
        if (isEncourageTextEnabled)
            for (UnlockText unlockText : player.encourageText) {
                if (unlockText.active) {
                    final int textPos = height - unlockText.yPos;
                    float a = (unlockText.yPos / (float) (player.yPos)) + 0.4f;
                    a = a < 0.4f ? 0.4f : a;
                    a = a > 1 ? 1 : a;
                    font.setColor(whiteFadeOut.set(1, 1, 1, a));
                    drawText(unlockText.text, Utility.mediumText() * 1.6f, unlockText.xPos, textPos);
                }
            }

        font.setColor(whiteFadeOut.set(0xffffffdd));

        camera.setToOrtho(true);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw Good Tiles
        for (int i = 0; i < level.tiles.size(); ++i) {
            batch.draw(wall, level.tiles.get(i).xPos, level.tiles.get(i).yPos, tileWidth, tileHeight);
        }

        // Draw Particles
        // If Particles Enabled:

        if (isEffectEnabled)
            for (TrailParticle trailParticle1 : player.trail) {
                if (trailParticle1.xPos == (tileWidth - TrailParticle.width) || trailParticle1.xPos == (width - tileWidth)) {
                    batch.draw(Player.trailParticle, trailParticle1.xPos, trailParticle1.yPos, TrailParticle.width, TrailParticle.height);
                }
            }


        // Draw Bad Tiles
        for (Tile tile : level.tiles) {
            if (!tile.white) {
                if (tile.spikes) {
                    batch.draw(wall, tile.xPos, tile.yPos, tileWidth, tileHeight);

                    if (tile.left) {
                        for (int x = 0; x < spikeNum; ++x) {
                            batch.draw(spikeLeft, tileWidth, tile.yPos + (spikeRight.getHeight() * x));
                        }
                    } else {
                        for (int x = 0; x < spikeNum; ++x) {
                            batch.draw(spikeRight, tile.xPos - spikeRight.getWidth(), tile.yPos + (spikeRight.getHeight() * x));
                        }
                    }
                } else {
                    batch.draw(emptySpace, tile.xPos, tile.yPos, tileWidth, tileHeight);
                }
            }
        }

        // Land Circles
        if (isEffectEnabled) {
            for (AnimCircle animCircle : player.circles2) {
                if (animCircle.active) {
                    float scale = animCircle.scale;
                    batch.setColor(1, 1, 1, (1f - animCircle.alpha));
                    batch.draw(scoreBackground, animCircle.xPos - (scale / 2), animCircle.yPos - (scale / 2), scale, scale);
                    batch.setColor(1, 1, 1, 1);
                }
            }

            if (level.speed > 0) {
                for (SpeedParticle speedParticle1 : speedParticles)
                    batch.draw(speedParticle, speedParticle1.xPos, speedParticle1.yPos, speedParticle1.width, speedParticle1.height);

            }


        }
        player.update(delta);
        if (isEffectEnabled)
            for (SplashParticle splashParticle : player.splashParticles) {
                if (splashParticle.active) {
                    batch.setColor(1, 1, 1, (1f - splashParticle.alpha));
                    batch.draw(player.splashParticle, splashParticle.x, splashParticle.y, splashParticle.scale, splashParticle.scale);
                    batch.setColor(1, 1, 1, 1);
                }
            }

        // Draw Player
        if (player.state == Player.PlayerState.DEAD) {
            if (!deathNumUpdated) {
                animator.start();
                deathNum += 1;
                totalJumps += player.score;
                deathNumUpdated = true;
            }
            endGame();
        }

        if (inMenu && !(player.state == Player.PlayerState.ALIVE)) {
            drawDeathScreen();
            return;
        }

        if (isEffectEnabled) {
            if (!(Player.PlayerState.DYING == player.state))
                for (JumpTrail jumpTrail : player.jumpTrail) {
                    if (jumpTrail.active) {
                        float size = jumpTrail.scale * 1.4f;
                        batch.setColor(1, 1, 1, 1f - jumpTrail.alpha);
                        batch.draw(scoreBackground, jumpTrail.xPos, jumpTrail.yPos, size, size);
                        batch.setColor(1, 1, 1, 1);
                    }
                }


            if (animCircleAnim.active) {
                float scale = animCircleAnim.scale;
                batch.setColor(1, 1, 1, 1f - animCircleAnim.alpha);
                batch.draw(scoreBackground, (width / 2) - (scale / 2), (height / 8) - (scale / 2), scale, scale);
                batch.setColor(1, 1, 1, 1);
            }
        }

        batch.draw(scoreBackground, (width / 2) - (scoreBackgroundLen / 2), (height / 8) - (scoreBackgroundLen / 2), scoreBackgroundLen, scoreBackgroundLen);
        drawText();
    }


    private void drawText() {
        font.setColor(grey);
        font.setScale(Utility.mediumText());
        final int mediumFontHeight = (int) font.getLineHeight();

        camera.setToOrtho(false);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (!(player.state == Player.PlayerState.IDLE)) {
            String scoreS = String.valueOf(player.score);
            if (player.score < 1) {
                scoreS = "0";
            }

            if (player.score < 100)
                drawText(scoreS, Utility.largeText() * 1.125f, center, height - (height / 8));
            else
                drawText(scoreS, Utility.mediumText() * 1.125f, center, height - (height / 8));
        } else {
            if (lastScore < 100) {
                drawText(String.valueOf(lastScore), Utility.largeText(), center, height - (height / 8));
            } else {
                drawText(String.valueOf(lastScore), Utility.mediumText(), center, height - (height / 8));
            }
        }
        font.setColor(dark_grey);
        if ((player.state == Player.PlayerState.IDLE) && !inMenu) {
            drawText("TAP TO JUMP", Utility.mediumText(), center, height / 2);
            drawText("DEATHS: " + String.valueOf(deathNum), center, greyTextStart - (2.2f * mediumFontHeight));
            drawText("JUMPS: " + String.valueOf(totalJumps), center, greyTextStart - (3.3f * mediumFontHeight));
        }
    }

    private void drawDeathScreen() {

        font.setScale(Utility.mediumText());
        final int mediumFontHeight = (int) font.getLineHeight();

        final float scoreLeft = (width / 2) - (scoreBackgroundLen / 2);
        final float scoreBottom = (height / 8) - (scoreBackgroundLen / 2);


        batch.draw(scoreBackground, scoreLeft, scoreBottom, scoreBackgroundLen, scoreBackgroundLen);

        camera.setToOrtho(false);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        final int pos = Math.round(((1 - (float) animated_value) * (Utility.BUTTON_Y + home.getRectangle().getHeight())) - home.getRectangle().getHeight());
        share.setPosY(pos);
        home.setPosY(pos);
        share.draw(batch);
        home.draw(batch);

        font.setColor(grey);

        String scoreS = String.valueOf(lastScore);
        if (lastScore < 1) {
            scoreS = "0";
        }

        if (player.score < 100) {
            drawText(scoreS, Utility.largeText() * 1.1f, center, height - (height / 8));
        } else {
            drawText(scoreS, Utility.largeText() * 0.85f, center, (height / 8));
        }

        font.setColor(dark_grey);

        if (animator.isRunning())
            deathScreenTop = (int) (greyTextStart - (greyTextStart * animated_value));
        else
            deathScreenTop = greyTextStart;

        if (lastScore >= highScore) {
            drawText("NEW BEST SCORE!", center,
                    deathScreenTop - (1.1f * mediumFontHeight));
        } else {
            drawText("BEST SCORE: " + String.valueOf(highScore),
                    center, deathScreenTop - (1.1f * mediumFontHeight));
        }

        drawText("DEATHS: " + String.valueOf(deathNum),
                center, deathScreenTop - (2.2f * mediumFontHeight));

        drawCoinText(center, deathScreenTop - (3.3f * mediumFontHeight));

        font.setColor(tapToRestart);
        final int dif = greyTextStart - (height / 2);
        drawText("TAP TO RESTART", (float) ((3 * Utility.mediumText() / 4) * (1 - animated_value)), center, deathScreenTop - dif);
    }

    private void update(float delta) {
        if (!(player.state == Player.PlayerState.IDLE))
            level.update(delta);
        for (SpeedParticle speedParticle : speedParticles) {
            speedParticle.update(delta);
        }
    }

    @Override
    public void resize(final int w, final int h) {

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        init();
    }

    @Override
    public void dispose() {
        Utility.saveInt(Utility.TOTAL_DEATHS, deathNum);
        Utility.saveInt(Utility.TOTAL_JUMPS, totalJumps);
        try {
            deathScreenButtonBackground.getTexture().dispose();
            speedParticle.getTexture().dispose();
            wall.getTexture().dispose();
            emptySpace.getTexture().dispose();
            spikeLeft.getTexture().dispose();
            spikeRight.getTexture().dispose();
            scoreBackground.getTexture().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        dispose();
        gameContainer.setScreen(new MainMenu(batch, gameContainer));
    }

    private void drawCoinText(float x, float y) {
        final float scale = Utility.mediumText();
        font.setScale(scale);
        String str;

        str = String.valueOf(coins);

        final float textWidth = font.getBounds(str).width;
        final float left = x - (textWidth / 2);
        final float textHeight = font.getLineHeight();
        font.draw(batch, str, left, y + (textHeight / 3));
        batch.draw(coin, left - textHeight, y - ((3 * textHeight) / 7), textHeight * 0.75f, textHeight * 0.75f);
    }

    private void drawText(String text, float scale, float x, float y) {
        scale = scale < Utility.smallText() ? Utility.smallText() : scale;
        font.setScale(scale);
        final float textWidth = font.getBounds(text).width;
        final float textHeight = font.getLineHeight();
        font.draw(batch, text, x - textWidth / 2, y + (textHeight / 3));
    }

    private void drawText(String text, float x, float y) {
        drawText(text, Utility.mediumText(), x, y);
    }

    private int getCoins() {
        return Utility.getPreferences().getInteger(Utility.COINS_INT);
    }

    private void saveCoins(int coins) {
        Utility.getPreferences().putInteger(Utility.COINS_INT, coins).flush();
    }

    private int getHighScore() {
        return Utility.getPreferences().getInteger(Utility.HIGH_SCORE_INT);
    }

    private boolean isEncourageTextEnabled() {
        return Utility.getPreferences().getBoolean(Utility.HIGH_SCORE_INT, true);
    }

    private void saveHighScore(int score) {
        if (gameMode == GameMode.ARCADE)
            Utility.getPreferences().putInteger(Utility.HIGH_SCORE_INT, score).flush();
    }

    public enum GameMode {
        ARCADE, PRACTICE, MULTI
    }

    public static class Utility {
        private final Texture coin = new Texture(Gdx.files.internal("coin.png"));
        public static final String SOUND_ENABLED_BOOL = "SOUND_ENABLED_BOOL";
        public static final String ENCOURAGE_TEXT_ENABLED_BOOL = "SOUND_ENABLED_BOOL";
        public static final String EFFECTS_ENABLED_BOOL = "EFFECTS_ENABLED_BOOL";
        public static final String COINS_INT = "COINS_INT";
        public static final String HIGH_SCORE_INT = "HIGH_SCORE_INT";
        public static final String CURRENT_COLOR = "CURRENT_COLOR";
        public static final String CURRENT_HAT = "CURRENT_HAT";
        public static final String CURRENT_MOUSTACHE = "CURRENT_MOUSTACHE";
        public static final int COLOR_PRICE = 20;
        public static final int BUTTON_Y = Gdx.graphics.getHeight() / 11;
        public static final int FIRST_BUTTON_X = (int) (Gdx.graphics.getWidth() / 9.318f);
        public static final int BUTTON_WIDTH = (int) ((Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 3.727f)) / 2);
        //  public static final String TRAILS_BOUGHT = "TRAILS_BOUGHT";
        public static final String TOTAL_JUMPS = "TOTAL_JUMPS";
        public static final String TOTAL_DEATHS = "TOTAL_DEATHS";
        public static final String[] colors = new String[]{
                "0.286,0.349,0.98", "0.27,0.368,0.831", "0.36,0.42,0.753",
                "0.67,0.278,0.737", "0.612,0.152,0.69", "0.468,0.121,0.674",
                "0.941,0.384,0.572", "0.913,0.118,0.388", "0.753,0.094,0.357",
                "0.039,0.494,0.027", "0.145,0.607,0.141", "0.259,0.714,0.254",
                "0.815,0.09,0.09", "0.815,0.2,0.21", "0.898,0.11,0.137",
                "0.992,0.847,0.207", "1,0.655,0.149", "0.984,0.549,0",
                "0.25,0.25,0.25", "0.35,0.35,0.35", "0.45,0.45,0.45", "0.8,0.8,0.8", "1,1,1"};
        public static final String[] hatProperties = {
                "50,0,55,200,110,50", "30,0,55,160,120,30",
                "75,0,60,150,110,25", "65,0,80,160,92,20",
                "80,0,80,144,130,38", "350,0,90,104,65,2",
                "250,0,85,130,105,15", "350,0,85,106,50,3",
                "400,0,85,106,60,3", "450,0,88,100,55,0",
                "450,0,55,160,120,30", "500,0,78,108,60,4",
                "25,0,60,150,60,10", "50,0,85,170,64,32",
                "480,0,88,160,75,32", "280,0,70,160,92,30",
                "300,0,80,106,80,3", "32,0,55,115,72,5",
                "40,0,72,106,60,3", "455,0,70,160,120,32",
                "325,0,82,180,125,42", "30,0,85,106,75,3",
                "90,55,70,50,40,0", "45,0,80,155,80,30",
                "350,0,60,172,130,45", "390,0,70,160,90,30",
                "340,0,86,140,98,20", "190,0,90,120,60,10",
                "420,0,58,180,190,39"
        };
        public static final String[] moustacheProperties = {
                "180,0,5,100,50,0", "150,0,5,130,50,18",
                "420,0,20,110,70,5", "50,0,10,130,50,15",
                "320,0,0,120,60,10"};
        public static final ArrayList<String> hats = new ArrayList<String>(Arrays.asList("h0", "h1", "h10", "h11", "h12", "h13", "h14", "h15", "h16", "h17", "h18", "h19", "h2", "h20", "h21", "h22", "h23", "h24", "h25", "h26", "h27", "h28", "h3", "h4", "h5", "h6", "h7", "h8", "h9"));
        public static final ArrayList<String> moustaches = new ArrayList<String>(Arrays.asList("m0", "m1", "m2", "m3", "m4"));
        public static final int height = Gdx.graphics.getHeight();
        public static final int width = Gdx.graphics.getWidth();
        private static final String HATS_BOUGHT = "HATS_BOUGHT";
        private static final String COLORS_BOUGHT = "COLORS_BOUGHT";
        private static final String MOUSTACHES_BOUGHT = "MOUSTACHES_BOUGHT";
        private static final Random rand = new Random();
        private static final int playerYpos = Math.round(Gdx.graphics.getHeight() - ((Gdx.graphics.getWidth() / 10f) * 6));
        private static final float speedFactor = Gdx.graphics.getHeight() / 1210f;
        private static BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
        private static TextureAtlas sprites = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        private static int playerWidth = (int) (Gdx.graphics.getWidth() / 10f), tileWidth = (int) (Gdx.graphics.getWidth() / 10.25f);

        public static int getPlayerYpos() {
            return playerYpos;
        }

        public static TextureAtlas getAtlas() {
            return sprites;
        }

        public static int getPrice(String item) {
            int price;
            try {
                price = Integer.parseInt(getItemProperties(item)[0]);
            } catch (Exception e) {
                e.printStackTrace();
                price = 30;
            }
            return price;
        }

        public static boolean isItemBought(String item) {
            if (item.contains("Hats") || item.contains("Moustache"))
                return true;

            if (item.startsWith("h"))
                return getPreferences().getString(HATS_BOUGHT).contains(item);
            else if (item.startsWith("m"))
                return getPreferences().getString(MOUSTACHES_BOUGHT).contains(item);
            else if (item.equals(colors[0]))
                return true;
            else
                return getPreferences().getString(COLORS_BOUGHT).contains(item);
        }

        public static boolean buyItem(String item) {
            if (isItemBought(item))
                return false;

            final int coins = getInt(COINS_INT);
            final int price = getPrice(item);
            if (price > coins)
                return false;

            saveInt(COINS_INT, coins - price);
            if (item.startsWith("h"))
                addHat(item);
            else if (item.startsWith("m"))
                addMoustache(item);
            else
                addColor(item);
            return true;
        }

        private static void addHat(String hat) {
            String hats = getPreferences().getString(HATS_BOUGHT);
            getPreferences().putString(HATS_BOUGHT, hats + "," + hat).flush();
        }

        private static void addColor(String color) {
            String hats = getPreferences().getString(COLORS_BOUGHT);
            getPreferences().putString(COLORS_BOUGHT, hats + "," + color).flush();
        }

        private static void addMoustache(String moustache) {
            String hats = getPreferences().getString(MOUSTACHES_BOUGHT);
            getPreferences().putString(MOUSTACHES_BOUGHT, hats + "," + moustache).flush();
        }

        public static BitmapFont getFont() {
            return font;
        }

        // Random in Range
        public static int randInt(int min, int max) {
            return rand.nextInt((max - min) + 1) + min;
        }

        public static float smallText() {
            return (Gdx.graphics.getHeight() / 138f) * (5 / 180f);
        }

        public static float mediumText() {
            return (Gdx.graphics.getHeight() / 138f) * (8.2f / 180f);
        }

    //    private Image getImageFromText(String text, int index) {
    //        final int heightSegment = height / 6;
    //        final int actorY = height - (heightSegment * index);
    //
    //        final BitmapFont font = Utility.getFont();
    //        font.setColor(new Color(0x808080ff));
    //        font.setScale(Utility.mediumText());
    //        final int titleHeight = (int) (font.getLineHeight() * 1.8f);
    //
    //        Gdx.gl.glClearColor(1, 1, 1, 1);
    //        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    //
    //        batch.begin();
    //        final float textWidth = font.getBounds(text).width;
    //        final float textHeight = font.getLineHeight();
    //        font.draw(batch, text, (width / 2) - textWidth / 2, (titleHeight / 2) + (textHeight / 3));
    //        batch.end();
    //        final Image image = new Image(ScreenUtils.getFrameBufferTexture((int) (-0.05f * width), 0, (int) (width * 1.1f), titleHeight));
    //        image.setWidth((int) (width * 1.1f));
    //        image.setPosition(0 - (int) (0.05f * width), actorY);
    //
    //        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
    //        image.setUserObject(text);
    //        return image;
    //    }

        public static float largeText() {
            return (Gdx.graphics.getHeight() / 138f) * (16f / 180f);
        }

        public static Preferences getPreferences() {
            return Gdx.app.getPreferences("prefs");
        }

        public static int getInt(String key) {
            return getPreferences().getInteger(key, 0);
        }

        public static void saveInt(String key, int value) {
            getPreferences().putInteger(key, value).flush();
        }

        public static Color colorFromString(String color) {
            try {
                return colorFromString(color, 1);
            } catch (Exception e) {
                return new Color();
            }
        }

        private static Color colorFromString(String color, float alpha) {
            final String[] floats = color.split(",");
            return new Color(Float.parseFloat(floats[0]), Float.parseFloat(floats[1]), Float.parseFloat(floats[2]), alpha);
        }

        public static String[] getHatProperties(String hat) {
            return hatProperties[hats.indexOf(hat)].split(",");
        }

        public static String[] getMoustacheProperties(String moustache) {
            return moustacheProperties[moustaches.indexOf(moustache)].split(",");
        }

        private static String[] getItemProperties(String item) {
            if (item.startsWith("h"))
                return getHatProperties(item);
            else if (item.startsWith("m"))
                return getMoustacheProperties(item);
            else
                return new String[]{"0", "0", "0", "0", "0", "0"};
        }

        public static Color[] getColors() {
            final Color[] colors = new Color[Utility.colors.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = colorFromString(Utility.colors[i]);
            }
            return colors;
        }

        public static int getPlayerWidth() {
            return playerWidth;
        }

        public static int getTileWidth() {
            return tileWidth;
        }

        public static float getSpeedFactor() {
            return speedFactor;
        }

        private Texture getTexture(final int s, int color) {
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            Pixmap.setBlending(Pixmap.Blending.None);

            final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
            pixmap.setColor(new Color(color));
            pixmap.fillCircle(s / 2, s / 2, s / 2);

            return new Texture(pixmap);
        }

        private Sprite getImageFromText(String text, int padding) {
            final BitmapFont font = Utility.getFont();
            font.setColor(new Color(0x808080ff));
            font.setScale(Utility.mediumText() * 1.1f);
            // final int titleHeight = (int) (font.getLineHeight() * 1.8f);

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            final float textHeight = font.getLineHeight();
            font.draw(batch, text, padding, (titleHeight / 2) + (textHeight / 3));
            batch.end();
            final TextureRegion textureRegion = ScreenUtils.getFrameBufferTexture(0, 0, width, titleHeight);
            final Sprite sprite = new Sprite(textureRegion.getTexture());
            sprite.flip(false, true);

            return sprite;
        }

        private static Sprite getSprite(Shape shape, final int w, final int h, final int color) {
            final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
            pixmap.setColor(color);
            return getSprite(shape, w, h, pixmap);
        }

        private static Sprite getSprite(Shape shape, final int w, final int h, Pixmap pixmap) {
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            switch (shape) {
                case CIRCLE:
                    final int r = Math.min(w, h) / 2;
                    pixmap.fillCircle(w / 2, h / 2, r);
                    break;
                case RECT:
                    pixmap.fill();
                    break;
            }
            final Texture texture = new Texture(pixmap);
            pixmap.dispose();
            return new Sprite(texture);
        }

        private static Image getColorSprite(int w, final int id, String color) {
            final Pixmap pixmap = new Pixmap(w, w, Pixmap.Format.RGBA8888);
            Pixmap.setBlending(Pixmap.Blending.None);
            pixmap.setColor(0xffffffff);
            w /= 2;
            pixmap.fillCircle(w, w, w);
            pixmap.setColor(Utility.colorFromString(color));
            int r = (int) (w * 0.98f);
            pixmap.fillCircle(w, w, r);

            final Texture texture = new Texture(pixmap);
            pixmap.dispose();
            boolean bought = Utility.isItemBought(color);

            final BitmapFont font = Utility.getFont();
            font.setColor(textColor);
            font.setScale(Utility.mediumText() * 0.55f);

            final float textWidth = font.getBounds("30").width;
            final float textHeight = font.getLineHeight();
            final int viewWidth = Math.round(textWidth + (textHeight / 2));
            final int titleHeight = Math.round(font.getLineHeight() + (textHeight / 2));
            final int distanceFromEdge = Math.round((viewWidth + titleHeight) * tagRatio);
            int textYpos = 0;

            Sprite text = null;

            if (!bought) {
                clearScreen();
                final int pH = titleHeight;
                final int pW = viewWidth + (2 * titleHeight);
                textYpos = Math.round(pH * tagRatio);
                Pixmap p = new Pixmap(pW, pH, Pixmap.Format.RGBA4444);
                p.setColor(white);
                p.fillTriangle(0, 0, pH, pH, pH, 0);
                p.fillRectangle(pH, 0, pW - pH - pH, pH);
                p.fillTriangle(pW - pH, pH, pW, 0, pW - pH, 0);
                Texture t = new Texture(p);
                batch.begin();
                batch.draw(t, 0, 0);
                font.draw(batch, "30", (pW / 2) - (textWidth / 2), (titleHeight / 2) + (textHeight / 3));
                batch.end();
                text = new Sprite(ScreenUtils.getFrameBufferTexture(0, 0, pW, pH));
                if (pW > sw)
                    text.setScale((float) sw / pW);
                else
                    text.setScale(1);

                text.setOrigin(0, 0);
            }
            clearScreen();


            batch.begin();
            batch.draw(texture, 0, 0, sw, sw);
            if (!bought)
                batch.draw(text, sw - distanceFromEdge, 0 - textYpos, 0, 0, text.getWidth(), text.getHeight(), text.getScaleX(), text.getScaleY(), 45);
            batch.end();

            Image image = new Image(ScreenUtils.getFrameBufferTexture(0, 0, sw, sw));
            image.setScaling(Scaling.fit);
            image.setUserObject("item" + String.valueOf(id));
            return image;
        }

        private static Sprite getSpeedParticle(final int w, final int h, final int color) {
            final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
            pixmap.setColor(new Color(color));
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            pixmap.fill();
            final Texture texture = new Texture(pixmap);
            pixmap.dispose();
            return new Sprite(texture);
        }

        private static void clearScreen() {
            Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        private Image getButton(String text) {
            final BitmapFont font = Utility.getFont();
            font.setColor(textColor);
            font.setScale(Utility.mediumText());
            final int h = (int) (font.getLineHeight() * 1.6f);
            final int w = (int) (width / 2.8f);

            final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
            pixmap.setColor(0xffffffff);
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            final int cy = h / 2;

            pixmap.fillCircle(cy, cy, cy);
            pixmap.fillRectangle(cy, 0, w - cy - cy, pixmap.getHeight());
            pixmap.fillCircle(w - cy, cy, cy);

            final Texture texture = new Texture(pixmap);

            Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            final float textWidth = font.getBounds(text).width;
            final float textHeight = font.getLineHeight();
            int x = w / 2;
            int y = h / 2;
            batch.begin();
            batch.draw(texture, 0, 0);
            font.draw(batch, text, x - textWidth / 2, y + (textHeight / 3));
            batch.end();
            texture.dispose();
            pixmap.dispose();
            final Image image = new Image(ScreenUtils.getFrameBufferTexture(0, 0, w, h));
            image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
            image.setUserObject(text);

            return image;
        }

        private void drawCoinText(float x, float y) {
            final float scale = Utility.mediumText();
            font.setScale(scale);
            final String str = String.valueOf(coins);
            final float textWidth = font.getBounds(str).width;
            final float left = x - (textWidth / 2);
            final float textHeight = font.getLineHeight();
            font.setColor(textColor);
            font.draw(batch, str, left, y + (textHeight / 3));
            batch.draw(coin, left - textHeight, y - ((3 * textHeight) / 7), textHeight * 0.75f, textHeight * 0.75f);
        }


        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.draw(walls, 0, 0, wallWidth, height);
            batch.draw(walls, width - wallWidth, 0, width, height);
            batch.draw(walls, 0, height - colorTitle.getHeight(), width, colorTitle.getHeight());

            camera.setToOrtho(true);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            player.update(delta);
            camera.setToOrtho(false);
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            batch.draw(walls, 0, 0, wallWidth, height);
            batch.draw(walls, width - wallWidth, 0, width, height);
            batch.draw(title, 0, height - height / 7);
            drawCoinText(Utility.width / 2, Utility.BUTTON_Y / 2);
            updateParticles(delta);

            batch.end();
        }
    }
}
