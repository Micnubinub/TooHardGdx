package tbs.spinjump;

import com.badlogic.gdx.Gdx;

public class GameValues {

    // Texts:
    public static final float MENU_TEXT_SIZE = Gdx.graphics.getWidth() / 6.5f;
    public static final float MENU_TEXT_SIZE_2 = Gdx.graphics.getWidth() / 12.0f;
    public static final float SCORE_TEXT_SIZE = Gdx.graphics.getWidth() / 6.5f;
    public static final float COIN_TEXT_SIZE = SCORE_TEXT_SIZE * 0.7f;
    public static final float TEXT_PADDING = Gdx.graphics.getWidth() / 17.5f;

    // Animated Texts:
    public static final float ANIM_TEXT_SPEED = (float) Gdx.graphics.getWidth() / 2700.0f;
    public static final float ANIM_TEXT_SIZE = Gdx.graphics.getWidth() / 14;

    // Scaler:
    public static final float SCALE_MULT = 1.1f;

    // Player:
    public static final int PLAYER_COLOR = 0Xff8c8cFF;
    public static final int PLAYER_COLOR_2 = 0XffababFF;
    public static final float PLAYER_SCALE = (Gdx.graphics.getWidth() / 28) * SCALE_MULT;
    public static final float PLAYER_JUMP_SPEED = (float) (Gdx.graphics.getHeight() / 800) * SCALE_MULT;

    // Background:
    public static final int BACKGROUND_COLOR = 0x4d5787FF;

    // Particles:
    public static final float PARTICLE_SIZE = (PLAYER_SCALE * 0.75f);
    public static final float PARTICLE_SHRINK_SPEED = PLAYER_JUMP_SPEED / 34;
    public static final int PARTICLE_LIFETIME = 80;
    public static final float SPLASH_SIZE = (PLAYER_SCALE * 0.17f);
    public static final float SPLASH_SPEED = ((float) Gdx.graphics.getHeight() / 850.0f);

    // Gears:
    public static final int RING_WIDTH = Gdx.graphics.getWidth() / 100;
    public static final int RING_COLOR = 0x98a3d9FF;
    public static final int COG_COLOR = 0X999999FF;
    public static final int COG_COLOR_2 = 0xb3b3b3FF;
    public static final float COG_GAP = Gdx.graphics.getHeight() / 4f;
    public static final float COG_START_Y = Gdx.graphics.getHeight() * 0.775f;
    public static final float COG_MIN_SIZE = (Gdx.graphics.getWidth() / 17.5f) * SCALE_MULT;
    public static final float COG_MAX_SIZE = (Gdx.graphics.getWidth() / 10.0f) * SCALE_MULT;
    public static final float COG_MOVE_SPEED = ((float) Gdx.graphics.getHeight() / 6500.0f) * SCALE_MULT;
    public static final float COG_STARTING_SPEED = (float) Gdx.graphics.getWidth() / 4600.0f;
    public static final float SPIKE_SIZE = Gdx.graphics.getWidth() / 12;


    // Coins:
    public static final float COIN_SCALE = PLAYER_SCALE * 0.6f;
    public static final int COIN_COLOR = 0xffff93FF;
    public static final int COIN_COLOR_2 = 0xffffe8FF;

    // Menu Buttons:
    public static final int MENU_BTN_WIDTH = (int) (Gdx.graphics.getWidth() / 5.75f);
    public static final int MENU_BTN_HEIGHT = MENU_BTN_WIDTH;
    public static final int BUTTON_PADDING = Gdx.graphics.getWidth() / 12;
    public static final float BUTTON_SPEED = Gdx.graphics.getWidth() / 6000f;

    // Enemies:
    public static final float ENEMY_SCALE = PLAYER_SCALE;


    // CASINO STUFF:
    public static final int CASINO_ITEM_PADDING = Gdx.graphics.getWidth() / 11;
    public static final int CASINO_ITEM_SCALE = MENU_BTN_WIDTH;
    public static final int CAS_BUTTON_MOVE_RANGE = CASINO_ITEM_SCALE / 7;
    public static final int CASINO_TEXT_SCALE = Gdx.graphics.getWidth() / 7;

    // IAP:
    public static final String removeAdsID = "remove_ads";
    public static final String buyCoinsID = "buy_coins";

}
