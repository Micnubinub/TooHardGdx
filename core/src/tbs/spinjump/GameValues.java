package tbs.spinjump;

public class GameValues {

    // Texts:
    public static final float MENU_TEXT_SIZE = ScreenObject.width / 6.5f;
    public static final float MENU_TEXT_SIZE_2 = ScreenObject.width / 12.0f;
    public static final float SCORE_TEXT_SIZE = ScreenObject.width / 6.5f;
    public static final float COIN_TEXT_SIZE = SCORE_TEXT_SIZE * 0.7f;
    public static final float TEXT_PADDING = ScreenObject.width / 17.5f;

    // Animated Texts:
    public static final float ANIM_TEXT_SPEED = (float) ScreenObject.width / 2700.0f;
    public static final float ANIM_TEXT_SIZE = ScreenObject.width / 14;

    // Scaler:
    public static final float SCALE_MULT = 1.1f;

    // Player:
    public static final int PLAYER_COLOR = 0XFFff8c8c;
    public static final int PLAYER_COLOR_2 = 0XFFffabab;
    public static final float PLAYER_SCALE = (ScreenObject.width / 28) * SCALE_MULT;
    public static final float PLAYER_JUMP_SPEED = (float) (ScreenObject.height / 800) * SCALE_MULT;

    // Background:
    public static final int BACKGROUND_COLOR = 0xFF4d5787;

    // Particles:
    public static final float PARTICLE_SIZE = (PLAYER_SCALE * 0.75f);
    public static final float PARTICLE_SHRINK_SPEED = PLAYER_JUMP_SPEED / 34;
    public static final int PARTICLE_LIFETIME = 80;
    public static final float SPLASH_SIZE = (PLAYER_SCALE * 0.17f);
    public static final float SPLASH_SPEED = ((float) ScreenObject.height / 850.0f);

    // Gears:
    public static final int RING_WIDTH = ScreenObject.width / 100;
    public static final int RING_COLOR = 0xFF98a3d9;
    public static final int COG_COLOR = 0XFF999999;
    public static final int COG_COLOR_2 = 0xFFb3b3b3;
    public static final float COG_GAP = ScreenObject.height / 2.5f;
    public static final float COG_START_Y = ScreenObject.height * 0.775f;
    public static final float COG_MIN_SIZE = (ScreenObject.width / 19.5f) * SCALE_MULT;
    public static final float COG_MAX_SIZE = (ScreenObject.width / 10.0f) * SCALE_MULT;
    public static final float COG_MOVE_SPEED = ((float) ScreenObject.height / 6500.0f) * SCALE_MULT;
    public static final float COG_STARTING_SPEED = (float) ScreenObject.width / 4000.0f;
    public static final float SPIKE_SIZE = ScreenObject.width / 12;

    // Coins:
    public static final float COIN_SCALE = PLAYER_SCALE * 0.6f;
    public static final int COIN_COLOR = 0xFFffff93;
    public static final int COIN_COLOR_2 = 0xFFffffe8;

    // Menu Buttons:
    public static final int MENU_BTN_WIDTH = (int) (ScreenObject.width / 5.75f);
    public static final int MENU_BTN_HEIGHT = MENU_BTN_WIDTH;
    public static final int BUTTON_PADDING = ScreenObject.width / 12;

    // Enemies:
    public static final float ENEMY_SCALE = PLAYER_SCALE;
}
