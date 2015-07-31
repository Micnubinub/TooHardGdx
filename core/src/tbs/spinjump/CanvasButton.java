package tbs.spinjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Rectangle;

public class CanvasButton extends GameObject {
    private static final Rectangle rectangle = new Rectangle();
    public Sprite image;
    public int width, height;
    public boolean active;
    public boolean animated;
    public boolean up;
    public float yOrigin;
    public boolean playSound;
    public int id;
    public float moveRange;

    // WIGGLE:
    public boolean rotate;
    public boolean rotateLeft;
    public float rotation;

    public CanvasButton(int x, int y, String id, boolean anim) {
        this.x = x;
        this.y = y;
        width = GameValues.MENU_BTN_WIDTH;
        height = GameValues.MENU_BTN_HEIGHT;
        yOrigin = y;
        image = new Sprite(new Texture(Gdx.files.internal(id + ".png")));
        image.setSize(width, height);
        active = true;
        animated = anim;
        up = true;
        playSound = true;

        // MOVE RANGE (RANDOMIZE):
        if (anim) {
            moveRange = GameValues.CAS_BUTTON_MOVE_RANGE;
        }

        // ROTATION:
        rotate = false;
    }

    public void wiggle(int amount) {
        if (!rotate) {
            rotate = true;
            rotateLeft = false;
        }
    }

    public boolean isClicked(int x, int y) {
        if (!active)
            return false;
        rectangle.setBounds(x, y, width, height);
        if (rectangle.contains(x, y)) {
            if (playSound)
                Game.buttonSound.play();
            return true;
        }

        return false;
    }

    public void update(float delta) {
        if (animated) {
            if (up) {
                y -= GameValues.BUTTON_SPEED * delta;
                if (y <= (yOrigin - moveRange)) {
                    y = (yOrigin - moveRange);
                    up = false;
                }
            } else {
                y += (GameValues.BUTTON_SPEED / 1.5f) * delta;
                if (y >= (yOrigin + moveRange)) {
                    y = (yOrigin + moveRange);
                    up = true;
                }
            }
        }

        if (rotate) {
            if (rotateLeft) {
                rotation -= (((float) GameValues.CASINO_TEXT_SCALE / 2500) * delta);
                if (rotation < -12) {
                    rotateLeft = false;
                }
            } else {
                rotation += (((float) GameValues.CASINO_TEXT_SCALE / 2500) * delta);
                if (rotation > 12) {
                    rotateLeft = true;
                }
            }
        }
    }

    public void draw(SpriteBatch canvas) {
        image.setPosition(x, y);
        image.draw(canvas);

        if (active) {
            if (rotate) {
                image.setRotation(rotation);
                image.draw(canvas);
            } else {
                image.draw(canvas);
            }
        }
    }

    public void dispose() {
        try {
            image.getTexture().dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
