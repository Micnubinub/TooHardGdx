package tbs.spinjump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    // SAVE STATE:
    private static final Color color = new Color();
    private static final GlyphLayout glyphLayout = new GlyphLayout();
    private static final GameController controller = new GameController();
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
    public static CanvasButton retryButton;
    public static CanvasButton adButton;
    public static CanvasButton likeButton;
    public static CanvasButton buyButton;
    public static CanvasButton reviveButton;
    public static CanvasButton gambleButton;
    public static int w, h;
    // STORE:
    public static ArrayList<StoreItem> storeItems;
    // SOUNDS:
    public static Sound jumpSound, coinSound, buttonSound, moneySound, deathSound, winSound;
    public static Music ambientMusic;
    // ADS:
//    public static InterstitialAd interstitial;
    public static boolean loadedBigAd;
    public static int showAdInt;
    // INTRO SCREEN:
    public static int introAlpha;
    public static float introCountdown;
    // REVIVAL:
    public static int revivalCost;
    // CASINO:
    public static CasinoManager casinoManager;
    //Todo init
    private static Texture menuTint;
    private static SpriteBatch batch;

    public static void setup() {
        // SETUP:
        state = GameState.Menu;
        level.setup();
        player.setup();
        Gdx.input.setInputProcessor(controller);
        score = 0;

        // ANIMATOR:
        scoreTextMult = 1;
        coinTextMult = 1;

        player.loadData();
        casinoManager.updateCost(player.coins);
    }

    public static void showAd(final boolean manual) {
//        if (player.showAds || manual) {
//            interstitial = new InterstitialAd(context);
//            interstitial.setAdUnitId(GameValues.FULL_SCREEN_AD);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            interstitial.loadAd(adRequest);
//            interstitial.setAdListener(new AdListener() {
//                public void onAdLoaded() {
//                    if (manual) {
//                        interstitial.show();
//                        player.coins += 10;
//                        player.saveData();
//                        if (player.score > 0)
//                            reviveButton.active = player.coins >= revivalCost;
//                    }
//                    loadedBigAd = true;
//                }
//            });
//        }
    }


    public static void draw(SpriteBatch batch) {

        // GAME:
        level.draw(batch);
        player.draw(batch);
        textAnimator.draw(batch);

        // SCORE:
        if (state == GameState.Playing) {
            //Todo use glyph here for measuremesnts
      /*      batch.drawText(textToMeasure, w - GameValues.TEXT_PADDING, textRect.height() + GameValues.TEXT_PADDING, paint);
            batch.drawText("SCORE", w - (textRect.width() + (GameValues.TEXT_PADDING * 1.55f)), textRect.height() + (GameValues.TEXT_PADDING * 0.9f), paint);
       */
            color.set(0xFFFFFFFF);
            textToMeasure = "SCORE" + player.score;
            Utility.drawCenteredText(batch, color, textToMeasure, w - GameValues.TEXT_PADDING, GameValues.TEXT_PADDING, Utility.getScale(GameValues.SCORE_TEXT_SIZE * scoreTextMult));
        }

        // DRAW MENU:
        if (state == GameState.Menu) {
            batch.draw(menuTint, 0, 0, w, h);

   /* Todo use glyph and getData
           batch.drawText("TOO HARD?", w/2, h/2 / 4, paint);
            batch.drawText("CAN YOU GET TO 100?", w/2, (h/2 / 4) + (GameValues.TEXT_PADDING * 1.5f), paint);
            batch.drawText("TAP TO BEGIN", w/2, h/2 * 1.075f, paint);
*/

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
            gambleButton.draw(batch);
            if (gambleButton.active) {
                color.set(1, 1, 1, 120 / 255f);
                Utility.drawCenteredText(batch, color, casinoManager.playCost + " COINS", w / 2, (gambleButton.y + GameValues.MENU_BTN_HEIGHT) + (GameValues.TEXT_PADDING * 1.5f), Utility.getScale(GameValues.MENU_TEXT_SIZE_2 / 2));
            }
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
            retryButton.draw(batch);
            adButton.draw(batch);
            likeButton.draw(batch);
            buyButton.draw(batch);
            reviveButton.draw(batch);
            if (reviveButton.active) {
                color.set(1, 1, 1, 120 / 250f);
                Utility.drawCenteredText(batch, color, revivalCost + " COINS", w / 2, (reviveButton.y + GameValues.MENU_BTN_HEIGHT) + (GameValues.TEXT_PADDING * 1.5f), Utility.getScale(GameValues.MENU_TEXT_SIZE / 2.5f));
            }
        }

        // DRAW STORE:
        if (state == GameState.Store) {
            batch.draw(menuTint, 0, 0, w, h);


            // DEATH TEXT:
            color.set(0xe6e8f1FF);
            Utility.drawCenteredText(batch, color, "STORE", w / 2, h / 2 / 4, Utility.getScale(GameValues.MENU_TEXT_SIZE));

            color.set(1, 1, 1, 120 / 250f);
            Utility.drawCenteredText(batch, color, "BUY NEW ITEMS", w / 2, (h / 2 / 4) + (GameValues.TEXT_PADDING * 1.5f), Utility.getScale(GameValues.MENU_TEXT_SIZE / 2));
        }

        // DRAW CASINO
        if (state == GameState.Casino) {
            batch.draw(menuTint, 0, 0, w, h);

            // CASINO HEADER:
            paint.setColor(0xFFe6e8f1);
            paint.setAlpha(255);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE);
            canvas.drawText("REWARDS", w / 2, h / 2 / 4, paint);
            paint.setColor(0xffFFFFFF);
            paint.setAlpha(120);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE / 2);
            canvas.drawText("SELECT ONE", w / 2, (h / 2 / 4) + (GameValues.TEXT_PADDING * 1.5f), paint);

            // DRAW CASINO:
            paint.setAlpha(180);
            casinoManager.draw(canvas);
            homeButton2.draw(canvas);
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

    private static Texture getTextureColor(int color) {
        final Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
        p.setColor(color);
        p.fill();
        return new Texture(p);
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

    private static void init() {
        // ONCE:
        level = new Level();
        player = new Player();
        textAnimator = new TextAnimator();

        // SETUP BUTTONS:
        rateButton = new CanvasButton((w / 2) - (GameValues.MENU_BTN_WIDTH / 2), (h / 2) + GameValues.BUTTON_PADDING * 2, "rate_btn", false);
        leaderButton = new CanvasButton((int) rateButton.x - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (h / 2) + GameValues.BUTTON_PADDING * 2, "leader_btn", false);
        achievementButton = new CanvasButton((int) rateButton.x + (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (h / 2) + GameValues.BUTTON_PADDING * 2, "achiv_btn", false);
        storeButton = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "store_btn", false);
        homeButton2 = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "home_btn", false);
        // DEATH:
        shareButton = new CanvasButton((int) rateButton.x - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (int) ((h / 2) + (GameValues.BUTTON_PADDING * 2.5f)), "share_btn", false);
        homeButton = new CanvasButton((w / 2) - (GameValues.MENU_BTN_WIDTH / 2), (int) ((h / 2) + (GameValues.BUTTON_PADDING * 2.5f)), "home_btn", false);
        retryButton = new CanvasButton((int) rateButton.x + (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), (int) ((h / 2) + (GameValues.BUTTON_PADDING * 2.5f)), "retry_btn", false);
        adButton = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "ad_btn", true);
        likeButton = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "fb_btn", true);
        buyButton = new CanvasButton((int) (w - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), h - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), "money_btn", true);
        reviveButton = new CanvasButton((w / 2) - (GameValues.MENU_BTN_WIDTH / 2), (h / 2) - (int) (GameValues.MENU_BTN_WIDTH * 2.25f), "heart_btn", true);
        gambleButton = new CanvasButton((w / 2) - (GameValues.MENU_BTN_WIDTH / 2), (h / 2) - (int) (GameValues.MENU_BTN_WIDTH * 2.25f), "gamble_btn", true);
        reviveButton.up = false;
        buyButton.playSound = false;
        gambleButton.playSound = false;
        gambleButton.up = false;
        menuTint = getTextureColor(0x000000aa);

        // AD:
        showAdInt = 0;
        loadedBigAd = false;

        // INTRO:
        introCountdown = 100;
        introAlpha = 255;

        initSound();
        // SETUP ETC:
        setup();
        initStoreItems();
    }

    private static void initSound() {
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));
        moneySound = Gdx.audio.newSound(Gdx.files.internal("money.wav"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("death2.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("win.wav"));
        ambientMusic = Gdx.audio.newMusic(Gdx.files.internal("ambient.wav"));
    }

    private static void initStoreItems() {
        storeItems = new ArrayList<StoreItem>();
        // SKINS:
        storeItems.add(new StoreItem(10, 0XFFff8c8c, 0XFFffabab, false, 0, false)); // 0
        storeItems.add(new StoreItem(10, 0xFF948cff, 0xFFc0abff, false, 0, false)); // 1
        storeItems.add(new StoreItem(10, 0xFFc2ff8c, 0xFFd2ffab, false, 0, false)); // 2
        storeItems.add(new StoreItem(10, 0xFFffff8c, 0xFFf9ffab, false, 0, false)); // 3
        storeItems.add(new StoreItem(25, 0xFFf08cff, 0xFFf4abff, false, 0, false)); // 4
        storeItems.add(new StoreItem(25, 0xFFff8ce8, 0xFFffabee, false, 0, false)); // 5
        storeItems.add(new StoreItem(50, 0xFFffab4a, 0xFFffc17a, false, 0, false)); // 6
        storeItems.add(new StoreItem(50, 0xFFff477a, 0xFFff99b5, false, 0, false)); // 7
        storeItems.add(new StoreItem(50, 0xFF6699b5, 0xFFc2d7e3, false, 0, false)); // 8
        storeItems.add(new StoreItem(50, 0xFF69d7e3, 0xFFc7e0e3, false, 0, false)); // 9
        storeItems.add(new StoreItem(75, 0xFFe4ef37, 0xFFeaef9a, false, 0, false)); // 10
        storeItems.add(new StoreItem(75, 0xFF23ef4b, 0xFFe6ffeb, false, 0, false)); // 11
        storeItems.add(new StoreItem(100, 0xFF999999, 0xFFb3b3b3, false, 0, false)); // 12
        storeItems.add(new StoreItem(100, 0xFFcbcbcb, 0xFFffffff, false, 0, false)); // 13
        storeItems.add(new StoreItem(150, 0xFFeb767f, 0xFFeb767f, false, 0, false)); // 14
        storeItems.add(new StoreItem(150, 0xFF81eb66, 0xFF81eb66, false, 0, false)); // 15
        storeItems.add(new StoreItem(200, 0xFF66b6eb, 0xFF66b6eb, false, 0, false)); // 16
        storeItems.add(new StoreItem(200, 0xFF575757, 0xFFf9f9f9, false, 0, false)); // 17
        storeItems.add(new StoreItem(500, 0xFF575757, 0xFFeb6973, false, 0, false)); // 18
        storeItems.add(new StoreItem(500, 0xFF575757, 0xFF81eb66, false, 0, false)); // 19
        storeItems.add(new StoreItem(1000, 0xFFFFFFFF, 0xFFffabab, false, 0, false)); // 20
        storeItems.add(new StoreItem(1000, 0xFF363636, 0xFF6a6a6a, false, 0, false)); // 21
        storeItems.add(new StoreItem(1000, 0xFFFFFFFF, 0xFF4cde5a, false, 0, false)); // 22
        storeItems.add(new StoreItem(1000, 0xFF4c5fde, 0xFF55de4c, false, 0, false)); // 23
        storeItems.add(new StoreItem(1000, 0xFFde4c4c, 0xFFe3ed71, false, 0, false)); // 24
        storeItems.add(new StoreItem(1000, 0xFFe3ed5b, 0xFFe37a5b, false, 0, false)); // 25
        storeItems.add(new StoreItem(10000, 0xFFFFFFFF, 0xFF000000, false, 0, false)); // 26
        storeItems.add(new StoreItem(10000, 0xFF000000, 0xFFFFFFFF, false, 0, false)); // 27
        storeItems.add(new StoreItem(-1, 0xFFed2df9, 0xFF415ef9, false, 5, false)); // 28
        storeItems.add(new StoreItem(-1, 0xFF516aec, 0xFFFFFFFF, false, 10, false));  // 29
        storeItems.add(new StoreItem(-1, 0xFFec5151, 0xFFFFFFFF, false, 100, false));  // 30
        storeItems.add(new StoreItem(-1, 0xFF9f6236, 0xFF9f62be, false, 500, false));  // 31
        storeItems.add(new StoreItem(-1, 0xFFffbb00, 0xFFff2800, false, 1000, false)); // 32
        storeItems.add(new StoreItem(-1, 0xFFffff93, 0xFFffffe8, false, 10000, false)); // 33
        // TRAILS:
        storeItems.add(new StoreItem(10, 0xFFFFFFFF, -1, false, 0, true)); // 34
        storeItems.add(new StoreItem(10, 0xFF363636, -1, false, 0, true)); // 35
        storeItems.add(new StoreItem(100, 0xFFc2ff8c, -1, false, 0, true)); // 36
        storeItems.add(new StoreItem(100, 0xFF4c5fde, -1, false, 0, true)); // 37
        storeItems.add(new StoreItem(1000, 0xFFde4c4c, -1, false, 0, true)); // 38
        storeItems.add(new StoreItem(1000, 0xFFe3ed5b, -1, false, 0, true)); // 39
        storeItems.add(new StoreItem(10000, 0xFF2ded28, -1, false, 0, true)); // 40
        storeItems.add(new StoreItem(10000, 0xFFffab4a, -1, false, 0, true)); // 41
        storeItems.add(new StoreItem(-1, 0xFFffbb00, -1, false, 2, true)); // 42
        storeItems.add(new StoreItem(-1, 0xFFffff93, -1, false, 100, true)); // 43
        storeItems.add(new StoreItem(-1, 0xFFed9c28, -1, false, 1000, true)); // 44
        storeItems.add(new StoreItem(-1, 0xFFed28d8, -1, false, 10000, true)); // 45

        StoreItem removeAds = new StoreItem(0, 0xFF000000, 0xFF000000, false, 0, false);
        removeAds.text = "Remove Ads";
        removeAds.IAPID = GameValues.removeAdsID; // PUT ID HERE
        storeItems.add(removeAds);
        StoreItem buyCoins = new StoreItem(0, 0xFF000000, 0xFF000000, false, 0, false);
        buyCoins.text = "Buy Coins";
        buyCoins.IAPID = GameValues.buyCoinsID; // PUT ID HERE
        storeItems.add(buyCoins);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        init();
    }

    @Override
    public void render() {
        color.set(GameValues.BACKGROUND_COLOR);
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        draw(batch);
        batch.end();
    }


}
