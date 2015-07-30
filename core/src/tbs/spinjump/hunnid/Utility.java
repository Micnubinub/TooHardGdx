package tbs.spinjump.hunnid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Utility {
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
    private final Texture coin = new Texture(Gdx.files.internal("coin.png"));

    public static int getPlayerYpos() {
        return playerYpos;
    }

    public static TextureAtlas getAtlas() {
        return sprites;
    }



    private static Color colorFromString(String color, float alpha) {
        final String[] floats = color.split(",");
        return new Color(Float.parseFloat(floats[0]), Float.parseFloat(floats[1]), Float.parseFloat(floats[2]), alpha);
    }

    private static Image getColorSprite(int w, final int id, String color) {
//        final Pixmap pixmap = new Pixmap(w, w, Pixmap.Format.RGBA8888);
//        Pixmap.setBlending(Pixmap.Blending.None);
//        pixmap.setColor(0xffffffff);
//        w /= 2;
//        pixmap.fillCircle(w, w, w);
//        pixmap.setColor(Utility.colorFromString(color));
//        int r = (int) (w * 0.98f);
//        pixmap.fillCircle(w, w, r);
//
//        final Texture texture = new Texture(pixmap);
//        pixmap.dispose();
//        boolean bought = Utility.isItemBought(color);
//
//        final BitmapFont font = Utility.getFont();
//        font.setColor(textColor);
//        font.setScale(0.25f);
//
//        final float textWidth = font.getBounds("30").width;
//        final float textHeight = font.getLineHeight();
//        final int viewWidth = Math.round(textWidth + (textHeight / 2));
//        final int titleHeight = Math.round(font.getLineHeight() + (textHeight / 2));
//        final int distanceFromEdge = Math.round((viewWidth + titleHeight) * tagRatio);
//        int textYpos = 0;
//
//        Sprite text = null;
//
//        if (!bought) {
//            clearScreen();
//            final int pH = titleHeight;
//            final int pW = viewWidth + (2 * titleHeight);
//            textYpos = Math.round(pH * tagRatio);
//            Pixmap p = new Pixmap(pW, pH, Pixmap.Format.RGBA4444);
//            p.setColor(white);
//            p.fillTriangle(0, 0, pH, pH, pH, 0);
//            p.fillRectangle(pH, 0, pW - pH - pH, pH);
//            p.fillTriangle(pW - pH, pH, pW, 0, pW - pH, 0);
//            Texture t = new Texture(p);
//            batch.begin();
//            batch.draw(t, 0, 0);
//            font.draw(batch, "30", (pW / 2) - (textWidth / 2), (titleHeight / 2) + (textHeight / 3));
//            batch.end();
//            text = new Sprite(ScreenUtils.getFrameBufferTexture(0, 0, pW, pH));
//            if (pW > sw)
//                text.setScale((float) sw / pW);
//            else
//                text.setScale(1);
//
//            text.setOrigin(0, 0);
//        }
//        clearScreen();
//
//
//        batch.begin();
//        batch.draw(texture, 0, 0, sw, sw);
//        if (!bought)
//            batch.draw(text, sw - distanceFromEdge, 0 - textYpos, 0, 0, text.getWidth(), text.getHeight(), text.getScaleX(), text.getScaleY(), 45);
//        batch.end();

        Image image = new Image(ScreenUtils.getFrameBufferTexture(0, 0, 50, 50));
        image.setScaling(Scaling.fit);
        image.setUserObject("item" + String.valueOf(id));
        return image;
    }

/*    private Sprite getImageFromText(String text, int padding) {
        final BitmapFont font = Utility.getFont();
        font.setColor(new Color(0x808080ff));
        font.setScale(0.25f);
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
    }*/

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

    private Texture getTexture(final int s, int color) {
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        Pixmap.setBlending(Pixmap.Blending.None);

        final Pixmap pixmap = new Pixmap(s, s, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(color));
        pixmap.fillCircle(s / 2, s / 2, s / 2);

        return new Texture(pixmap);
    }

    private Image getButton(SpriteBatch batch, String text, int textColor) {
//        final BitmapFont font = Utility.getFont();
//        font.setColor(textColor);
//        font.setScale(0.25f);
//        final int h = (int) (font.getLineHeight() * 1.6f);
//        final int w = (int) (width / 2.8f);
//
//        final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
//        pixmap.setColor(0xffffffff);
//        Pixmap.setFilter(Pixmap.Filter.BiLinear);
//        final int cy = h / 2;
//
//        pixmap.fillCircle(cy, cy, cy);
//        pixmap.fillRectangle(cy, 0, w - cy - cy, pixmap.getHeight());
//        pixmap.fillCircle(w - cy, cy, cy);
//
//        final Texture texture = new Texture(pixmap);
//
//        Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        final float textWidth = font.getBounds(text).width;
//        final float textHeight = font.getLineHeight();
//        int x = w / 2;
//        int y = h / 2;
//        batch.begin();
//        batch.draw(texture, 0, 0);
//        font.draw(batch, text, x - textWidth / 2, y + (textHeight / 3));
//        batch.end();
//        texture.dispose();
//        pixmap.dispose();
        final Image image = new Image(ScreenUtils.getFrameBufferTexture(0, 0, 90, 90));
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        image.setUserObject(text);

        return image;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.8627f, 0.8627f, 0.8627f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

/*        batch.begin();
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

        batch.end();*/
    }
}
