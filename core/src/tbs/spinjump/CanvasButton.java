package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Rectangle;

public class CanvasButton extends GameObject {
    private static final Rectangle rectangle = new Rectangle();
    public static Sprite image;
    public int width, height;

    public CanvasButton(int x, int y, String id) {
        this.x = x;
        this.y = y;
        width = GameValues.MENU_BTN_WIDTH;
        height = GameValues.MENU_BTN_HEIGHT;
        image = new Sprite(new Texture(Gdx.files.internal(id)));
        image.setSize(width, height);
    }


    public boolean isClicked(int x, int y) {
        rectangle.setBounds(x, y, width, height);
        return rectangle.contains(x, y);
    }

    public void draw(SpriteBatch canvas) {
        image.setPosition(x, y);
        image.draw(canvas);
    }
}
