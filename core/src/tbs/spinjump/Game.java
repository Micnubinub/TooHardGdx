package tbs.spinjump;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Game {
    public static final Paint paint = new Paint();
    public static Level level;
    public static GameState state;
    public static int score;
    public static Player player;
    public static Rect textRect;
    public static Typeface mainFont;

    // TEXT ANIMATOR:
    public static TextAnimator textAnimator;
    public static float scoreTextMult;
    public static float coinTextMult;

    // TMPS:
    public static String textToMeasure;
    public static Context context;

    // MENU BUTTONS:
    public static CanvasButton rateButton;
    public static CanvasButton leaderButton;
    public static CanvasButton achievementButton;
    public static CanvasButton storeButton;
    public static CanvasButton homeButton;
    public static CanvasButton shareButton;
    public static CanvasButton homeButton2;

    // SAVE STATE:
    public static SecurePreferences saver;

    public Game(Context context) {
        this.context = context;
        saver = new SecurePreferences(context, "prefs_tbs_th",
                "X5TBSSDVSH6GH", true);

        // LOAD FONT:
        mainFont = Typeface.createFromAsset(context.getAssets(), "fonts/Cantarell-Regular.ttf");
        paint.setTypeface(mainFont);
        paint.setAntiAlias(false);

        // ONCE:
        level = new Level();
        player = new Player();
        textAnimator = new TextAnimator();

        // SETUP BUTTONS:
        rateButton = new CanvasButton(ScreenObject.getCenterX() - (GameValues.MENU_BTN_WIDTH / 2), ScreenObject.getCenterY() + GameValues.BUTTON_PADDING * 2, R.drawable.rate_btn);
        leaderButton = new CanvasButton((int) rateButton.x - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), ScreenObject.getCenterY() + GameValues.BUTTON_PADDING * 2, R.drawable.leader_btn);
        achievementButton = new CanvasButton((int) rateButton.x + (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING), ScreenObject.getCenterY() + GameValues.BUTTON_PADDING * 2, R.drawable.achiv_btn);
        storeButton = new CanvasButton((int) (ScreenObject.width - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), ScreenObject.height - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), R.drawable.store_btn);
        homeButton = new CanvasButton((int) (ScreenObject.getCenterX() + (GameValues.BUTTON_PADDING * 0.5f)), (int) (ScreenObject.getCenterY() + (GameValues.BUTTON_PADDING * 2.5f)), R.drawable.home_btn);
        shareButton = new CanvasButton((int) (ScreenObject.getCenterX() - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING * 0.5f)), (int) (ScreenObject.getCenterY() + (GameValues.BUTTON_PADDING * 2.5f)), R.drawable.share_btn);
        homeButton2 = new CanvasButton((int) (ScreenObject.width - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 1.5f)), ScreenObject.height - (GameValues.MENU_BTN_WIDTH + GameValues.BUTTON_PADDING / 2), R.drawable.home_btn);

        // SETUP ETC:
        setup();
    }

    public static void setup() {
        // SETUP:
        textRect = new Rect();
        state = GameState.Menu;
        level.setup();
        player.setup();
        score = 0;

        // ANIMATOR:
        scoreTextMult = 1;
        coinTextMult = 1;

        player.loadData();
    }

    public static void draw(Canvas canvas) {
        paint.setColor(GameValues.BACKGROUND_COLOR);
        canvas.drawPaint(paint);

        // GAME:
        level.draw(canvas);
        player.draw(canvas);
        textAnimator.draw(canvas);

        // SCORE:
        if (state == GameState.Playing) {
            Game.paint.setColor(0xFFFFFFFF);
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(GameValues.SCORE_TEXT_SIZE * scoreTextMult);
            textToMeasure = player.score + "";
            paint.getTextBounds(textToMeasure, 0, textToMeasure.length(), textRect);
            canvas.drawText(textToMeasure, ScreenObject.width - GameValues.TEXT_PADDING, textRect.height() + GameValues.TEXT_PADDING, paint);
            paint.setColor(GameValues.RING_COLOR);
            paint.setTextSize(GameValues.SCORE_TEXT_SIZE / 2.5f);
            canvas.drawText("SCORE", ScreenObject.width - (textRect.width() + (GameValues.TEXT_PADDING * 1.5f)), textRect.height() + (GameValues.TEXT_PADDING * 0.9f), paint);
        }

        // DRAW MENU:
        if (state == GameState.Menu) {
            paint.setColor(0xFF000000);
            paint.setAlpha(180);
            canvas.drawRect(0, 0, ScreenObject.width, ScreenObject.height, paint);

            // MENU TEXT:
            paint.setColor(0xFFe6e8f1);
            paint.setAlpha(255);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE);
            canvas.drawText("TOO HARD?", ScreenObject.getCenterX(), ScreenObject.getCenterY() / 4, paint);
            paint.setColor(0xffFFFFFF);
            paint.setAlpha(120);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE / 2);
            canvas.drawText("CAN YOU GET TO 100?", ScreenObject.getCenterX(), (ScreenObject.getCenterY() / 4) + (GameValues.TEXT_PADDING * 1.5f), paint);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE_2);
            canvas.drawText("TAP TO BEGIN", ScreenObject.getCenterX(), ScreenObject.getCenterY() * 1.075f, paint);

            // BUTTONS:
            paint.setAlpha(180);
            rateButton.draw(canvas);
            leaderButton.draw(canvas);
            achievementButton.draw(canvas);
            storeButton.draw(canvas);
        }

        // DRAW DEATH:
        if (state == GameState.Death) {
            paint.setColor(0xFF000000);
            paint.setAlpha(180);
            canvas.drawRect(0, 0, ScreenObject.width, ScreenObject.height, paint);

            // DEATH TEXT:
            paint.setColor(0xFFe6e8f1);
            paint.setAlpha(255);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE);
            canvas.drawText("YOU DIED", ScreenObject.getCenterX(), ScreenObject.getCenterY() / 4, paint);
            paint.setColor(0xffFFFFFF);
            paint.setAlpha(120);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE / 2);
            if (player.score < 100)
                canvas.drawText("GET TO 100", ScreenObject.getCenterX(), (ScreenObject.getCenterY() / 4) + (GameValues.TEXT_PADDING * 1.5f), paint);
            else
                canvas.drawText("310819958", ScreenObject.getCenterX(), (ScreenObject.getCenterY() / 4) + (GameValues.TEXT_PADDING * 1.5f), paint);

            // AFTER GAME INFO:
            paint.setTextSize(GameValues.MENU_TEXT_SIZE_2);
            canvas.drawText("SCORE: " + player.score, ScreenObject.getCenterX(), ScreenObject.getCenterY() * 1.185f, paint);
            paint.setTextSize(GameValues.MENU_TEXT_SIZE_2 * 1.25f);
            paint.setColor(0xFFe6e8f1);
            paint.setAlpha(255);
            canvas.drawText("BEST: " + player.highScore, ScreenObject.getCenterX(), ScreenObject.getCenterY() * 1.075f, paint);


            // BUTTONS:
            paint.setAlpha(180);
            homeButton.draw(canvas);
            shareButton.draw(canvas);
        }

        // COINS:
        paint.setColor(0xFFFFFFFF);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(GameValues.COIN_TEXT_SIZE * coinTextMult);
        textToMeasure = player.coins + "";
        paint.getTextBounds(textToMeasure, 0, textToMeasure.length(), textRect);
        canvas.drawText(textToMeasure, GameValues.TEXT_PADDING, ScreenObject.height - GameValues.TEXT_PADDING, paint);
        if (state == GameState.Playing)
            paint.setColor(GameValues.RING_COLOR);
        else {
            paint.setColor(0xFFFFFFFF);
            paint.setAlpha(120);
        }
        paint.setTextSize(GameValues.COIN_TEXT_SIZE / 2.5f);
        canvas.drawText("COINS", textRect.width() + (GameValues.TEXT_PADDING * 1.4f), ScreenObject.height - (GameValues.TEXT_PADDING * 1.1f), paint);
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

    // SHARE:
    public static Bitmap takeScreenShot() {
        final View rootView = ((Activity) context).findViewById(
                android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void Share(Bitmap img) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String time_date = sdf.format(new Date());
        final File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + "tap_screen_" + time_date + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileOutputStream ostream = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 90, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        String filel = "file://" + Environment.getExternalStorageDirectory()
                + File.separator + "tap_screen_" + time_date + ".jpg";
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(filel));
        context.startActivity(Intent.createChooser(share,
                "Share Image"));
    }

    // STORE DIALOG:
    public static void showStore() {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.store_gridview);
        dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final ArrayList<StoreItem> storeItems = new ArrayList<StoreItem>();
        storeItems.add(new StoreItem("item1", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item2", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item1", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item3", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item1", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item21", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item13", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item11", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item12", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item1232", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item12", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item132", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item21", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item13", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item11", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item12", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item1232", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item12", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item132", R.drawable.rate_btn));
        storeItems.add(new StoreItem("item91", R.drawable.rate_btn));

        final GridView gridView = (GridView) dialog.findViewById(R.id.grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //toast(storeItems.get(position).text);
            }
        });
        gridView.setAdapter(new StoreAdapter(storeItems));
        dialog.show();
    }


}
