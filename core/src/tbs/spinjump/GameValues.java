package tbs.spinjump;

import com.badlogic.gdx.Gdx;

public class GameValues {
    // Texts:
    public static float MENU_TEXT_SIZE;
    public static float MENU_TEXT_SIZE_2;
    public static float SCORE_TEXT_SIZE;
    public static float COIN_TEXT_SIZE;
    public static float TEXT_PADDING;

    // Animated Texts:
    public static float ANIM_TEXT_SPEED;
    public static float ANIM_TEXT_SIZE;

    // Scaler:
    public static float SCALE_MULT;

    // Player:
    public static float PLAYER_SCALE;
    public static float PLAYER_JUMP_SPEED;

    // Background:
    public static int BACKGROUND_COLOR;

    // Particles:
    public static float PARTICLE_SIZE;
    public static float PARTICLE_SHRINK_SPEED;
    public static int PARTICLE_LIFETIME;
    public static float SPLASH_SIZE;
    public static float SPLASH_SPEED;

    // Gears:
    public static int RING_WIDTH;
    public static int RING_COLOR;
    public static int COG_COLOR;
    public static int COG_COLOR_2;
    public static float COG_GAP;
    public static float COG_START_Y;
    public static float COG_MIN_SIZE;
    public static float COG_MAX_SIZE;
    public static float COG_MOVE_SPEED;
    public static float COG_STARTING_SPEED;
    public static float SPIKE_SIZE;

    // Coins:
    public static float COIN_SCALE;
    public static int COIN_COLOR;
    public static int COIN_COLOR_2;

    // Menu Buttons:
    public static int MENU_BTN_WIDTH;
    public static int MENU_BTN_HEIGHT;
    public static int BUTTON_PADDING;
    public static float BUTTON_SPEED;

    // Enemies:
    public static float ENEMY_SCALE;

    // CASINO STUFF:
    public static int CASINO_ITEM_PADDING;
    public static int CASINO_ITEM_SCALE;
    public static int CAS_BUTTON_MOVE_RANGE;
    public static int CASINO_TEXT_SCALE;

    // IAP:
    public static String removeAdsID = "remove_ads";
    public static String buyCoinsID = "buy_coins";

    // INTERFACE:
    public static int BUTTON_SCALE;
    //DIALOG
    public static int DIALOG_PADDING;
    public static int DIALOG_CLOSE_BUTTON_SCALE;
    public static int TITLE_HEIGHT;
    public static int CORNER_SCALE;
    public static int SHAPE_WIDTH;

    public static void init() {
        // Texts:
        MENU_TEXT_SIZE = Gdx.graphics.getWidth() / 6.5f;
        MENU_TEXT_SIZE_2 = Gdx.graphics.getWidth() / 12.0f;
        SCORE_TEXT_SIZE = Gdx.graphics.getWidth() / 6.5f;
        COIN_TEXT_SIZE = SCORE_TEXT_SIZE * 0.7f;
        TEXT_PADDING = Gdx.graphics.getWidth() / 17.5f;

        // Animated Texts:
        ANIM_TEXT_SPEED = (float) Gdx.graphics.getWidth() / 2700.0f;
        ANIM_TEXT_SIZE = Gdx.graphics.getWidth() / 14;

        // Scaler:
        SCALE_MULT = 1.1f;

        // Player:
        PLAYER_SCALE = (Gdx.graphics.getWidth() / 28) * SCALE_MULT;
        PLAYER_JUMP_SPEED = (float) (Gdx.graphics.getHeight() / 800) * SCALE_MULT;

        // Background:
        BACKGROUND_COLOR = 0x00796BFF;

        // Particles:
        PARTICLE_SIZE = (PLAYER_SCALE * 0.75f);
        PARTICLE_SHRINK_SPEED = PLAYER_JUMP_SPEED / 34;
        PARTICLE_LIFETIME = 80;
        SPLASH_SIZE = (PLAYER_SCALE * 0.17f);
        SPLASH_SPEED = ((float) Gdx.graphics.getHeight() / 850.0f);

        // Gears:
        RING_WIDTH = Gdx.graphics.getWidth() / 100;
        RING_COLOR = 0xB2DFDBFF;
        COG_COLOR = 0x929292FF;
        COG_COLOR_2 = 0xb2b2b2FF;
        COG_GAP = Gdx.graphics.getHeight() / 4f;
        COG_START_Y = Gdx.graphics.getHeight() * 0.775f;
        COG_MIN_SIZE = (Gdx.graphics.getWidth() / 17.5f) * SCALE_MULT;
        COG_MAX_SIZE = (Gdx.graphics.getWidth() / 10.0f) * SCALE_MULT;
        COG_MOVE_SPEED = ((float) Gdx.graphics.getHeight() / 6500.0f) * SCALE_MULT;
        COG_STARTING_SPEED = (float) Gdx.graphics.getWidth() / 4600.0f;
        SPIKE_SIZE = Gdx.graphics.getWidth() / 12;

        // Coins:
        COIN_SCALE = PLAYER_SCALE * 0.6f;
        COIN_COLOR = 0xffff93FF;
        COIN_COLOR_2 = 0xffffe8FF;

        // Menu Buttons:
        MENU_BTN_WIDTH = (int) (Gdx.graphics.getWidth() / 5.75f);
        MENU_BTN_HEIGHT = MENU_BTN_WIDTH;
        BUTTON_PADDING = Gdx.graphics.getWidth() / 12;
        BUTTON_SPEED = Gdx.graphics.getWidth() / 6000f;

        // Enemies:
        ENEMY_SCALE = PLAYER_SCALE;

        // CASINO STUFF:
        CASINO_ITEM_PADDING = Gdx.graphics.getWidth() / 11;
        CASINO_ITEM_SCALE = MENU_BTN_WIDTH;
        CAS_BUTTON_MOVE_RANGE = CASINO_ITEM_SCALE / 7;
        CASINO_TEXT_SCALE = Gdx.graphics.getWidth() / 7;

        // INTERFACE:
        BUTTON_SCALE = (int) (Game.w / 6.0f);

        DIALOG_PADDING = Game.w / 20;
        DIALOG_CLOSE_BUTTON_SCALE = Game.w / 12;
        TITLE_HEIGHT = Game.w / 13;
        SHAPE_WIDTH = Game.h / 8;
        CORNER_SCALE = Game.w / 42;
    }

}
