package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mike on 4/10/16.
 */
public class BitmapLoader {
    public static TextureRegion ad, leaderB, store, share, item, like, trophy, star, money, replay, heart, home, dice;
    private static TextureAtlas atlas;

    public static void init() {
        atlas = new TextureAtlas(Gdx.files.internal("sprites"));
        ad = atlas.findRegion("ad");
        leaderB = atlas.findRegion("leaderB");
        store = atlas.findRegion("store");
        share = atlas.findRegion("share");
        item = atlas.findRegion("item");
        like = atlas.findRegion("like");
        trophy = atlas.findRegion("trophy");
        star = atlas.findRegion("star");
        money = atlas.findRegion("money");
        replay = atlas.findRegion("replay");
        heart = atlas.findRegion("heart");
        home = atlas.findRegion("home");
        dice = atlas.findRegion("dice");
    }

    public static void dispose() {

    }

}
