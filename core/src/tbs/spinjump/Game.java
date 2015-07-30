package tbs.spinjump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
    public static Level level;
    public static GameState state;
    public static int score;
    public static Player player;

    // TEXT ANIMATOR:
    public static TextAnimator textAnimator;
    public static float scoreTextMult;
    public static float coinTextMult;

    // TMPS:
    public static String textToMeasure;

    // MENU BUTTONS:
    public static CanvasButton rateButton;
    public static CanvasButton leaderButton;
    public static CanvasButton achievementButton;
    public static CanvasButton storeButton;
    public static CanvasButton homeButton;
    public static CanvasButton shareButton;
    public static CanvasButton homeButton2;

    // SAVE STATE:
    private static Color color;
    private static int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
    //Todo init
    private static Texture menuTint;
    private static SpriteBatch batch;

    public Game() {


        // ONCE:
        level = new Level();
        player = new Player();
        textAnimator = new TextAnimator();

        // SETUP BUTTONS:
        rateButton = new CanvasButton((w / 2) - (GameValues.MENU_BTN_WIDTH / 2), (h / 2) + GameValues.BUTTON_PADDING * 2, "rate_btn");
        leaderButton = new CanvasButton((int) rateButton.x - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (h / 2) + GameValues.BUTTON_PADDING * 2, "leader_btn");
        achievementButton = new CanvasButton((int) rateButton.x + (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (h / 2) + GameValues.BUTTON_PADDING * 2, "achiv_btn");
        storeButton = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "store_btn");
        homeButton = new CanvasButton((int) ((w / 2) + (GameValues.BUTTON_PADDING * 0.5f)), (int) ((h / 2) + (GameValues.BUTTON_PADDING * 2.5f)), "home_btn");
        shareButton = new CanvasButton((int) ((w / 2) - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING * 0.5f)), (int) ((h / 2) + (GameValues.BUTTON_PADDING * 2.5f)), "share_btn");
        homeButton2 = new CanvasButton((int) (2 - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "home_btn");

        // SETUP ETC:
        setup();
    }

    public static void setup() {
        // SETUP:
        state = GameState.Menu;
        level.setup();
        player.setup();
        score = 0;

        // ANIMATOR:
        scoreTextMult = 1;
        coinTextMult = 1;

        player.loadData();
    }

    public static void draw(SpriteBatch batch) {
        color.set(GameValues.BACKGROUND_COLOR);
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // GAME:
        level.draw(batch);
        player.draw(batch);
        textAnimator.draw(batch);

        // SCORE:
        if (state == GameState.Playing) {
            color.set(0xFFFFFFFF);
            textToMeasure = "SCORE" + player.score;
            Utility.drawCenteredText(batch, color, textToMeasure, w - GameValues.TEXT_PADDING, GameValues.TEXT_PADDING, Utility.getScale(GameValues.SCORE_TEXT_SIZE * scoreTextMult));
        }

        // DRAW MENU:
        if (state == GameState.Menu) {
            batch.draw(menuTint, 0, 0, w, h);

            // MENU TEXT:
            color.set(0xe6e8f1FF);
            Utility.drawCenteredText(batch, color, "TOO HARD?", w / 2, h / 4, Utility.getScale(GameValues.MENU_TEXT_SIZE));
            color.set(1, 1, 1, 120 / 255f);
            Utility.drawCenteredText(batch, color, "CAN YOU GET TO 100?", w / 2, (h / 4) + (GameValues.TEXT_PADDING * 1.5f), Utility.getScale(GameValues.MENU_TEXT_SIZE / 2));
            Utility.drawCenteredText(batch, color, "TAP TO BEGIN", w / 2, (h / 2) * 1.075f, Utility.getScale(GameValues.MENU_TEXT_SIZE_2 / 2));

            // BUTTONS:
            rateButton.draw(batch);
            leaderButton.draw(batch);
            achievementButton.draw(batch);
            storeButton.draw(batch);
        }

        // DRAW DEATH:
        if (state == GameState.Death) {
            batch.draw(menuTint, 0, 0, w, h);

            // DEATH TEXT:
            color.set(0xe6e8f1FF);
            Utility.drawCenteredText(batch, color, "YOU DIED", w / 2, (h / 8), Utility.getScale(GameValues.MENU_TEXT_SIZE));
            color.set(1, 1, 1, 120 / 250f);
            Utility.drawCenteredText(batch, color, "GET TO 100", w / 2, (h / 4) + (GameValues.TEXT_PADDING * 1.5f), Utility.getScale(GameValues.MENU_TEXT_SIZE / 2));
            // AFTER GAME INFO:
            Utility.drawCenteredText(batch, color, "SCORE: " + player.score, w / 2, (h / 2) * 1.185f, Utility.getScale(GameValues.MENU_TEXT_SIZE_2));
            Utility.drawCenteredText(batch, color, "BEST: " + player.highScore, w / 2, (h / 2) * 1.075f, Utility.getScale(GameValues.MENU_TEXT_SIZE_2));


            // BUTTONS:
            homeButton.draw(batch);
            shareButton.draw(batch);
        }

        // COINS:
        color.set(0xFFFFFFFF);

        textToMeasure = player.coins + "";
        Utility.drawCenteredText(batch, color, textToMeasure, GameValues.TEXT_PADDING, h - GameValues.TEXT_PADDING, Utility.getScale(GameValues.COIN_TEXT_SIZE * coinTextMult));
        if (state == GameState.Playing)
            color.set(GameValues.RING_COLOR);
        else {
            color.set(1, 1, 1, 120 / 255f);
        }
        Utility.drawCenteredText(batch, color, "COINS", w / 2, h - (GameValues.TEXT_PADDING * 1.1f), Utility.getScale(GameValues.COIN_TEXT_SIZE / 2.5f));
    }

    public static void update(float delta) {
        level.update(delta);
        player.update(delta);

        // TEXT ANIMATION:
        textAnimator.update(delta);
        if (scoreTextMult > 1) {
            scoreTextMult -= delta * 0.0025;
            if (scoreTextMult <= 1)
                scoreTextMult = 1;
        }
        if (coinTextMult > 1) {
            coinTextMult -= delta * 0.0025;
            if (coinTextMult <= 1)
                coinTextMult = 1;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        draw(batch);
        batch.end();
    }

}
