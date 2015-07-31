package tbs.spinjump;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StoreItem {
    private static final Color color = new Color();
    private static float w, h;
    public String text;
    public int cost;
    public boolean bought;
    public int color1, color2;
    public Sprite sprite;
    public String IAPID;
    public boolean buyable;
    public int unlockRarity;
    public boolean trail;

    public StoreItem(int cost, int c1, int c2, boolean bought, int rarity, boolean trail) {
        this.text = cost + "";
        this.cost = cost;
        this.bought = bought;
        setColors(c1, c2);
        IAPID = "NONE";
        buyable = cost != -1;
        if (!buyable)
            text = "?";
        unlockRarity = rarity;
        this.trail = trail;
    }

    public static void setSize(float w, float h) {
        StoreItem.w = w;
        StoreItem.h = h;

    }

    private void draw(SpriteBatch batch, float x, float y) {
        sprite.setOriginCenter();
        sprite.setCenter(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public void dispose() {
        try {
            sprite.getTexture().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSprite() {
        dispose();
        final Pixmap p = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        color.set(color1);
        p.setColor(color);

        color.set(color2);
        p.setColor(color);
        sprite = new Sprite(new Texture(p));
        p.dispose();
    }

    public void setColors(int c1, int c2) {
        color1 = c1;
        color2 = c2;
        getSprite();
    }
}