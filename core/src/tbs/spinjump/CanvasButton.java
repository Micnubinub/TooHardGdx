package tbs.spinjump;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;


public class CanvasButton extends GameObject {
    private static final Rectangle rectangle = new Rectangle();
    public TextureAtlas.AtlasRegion image;
    public int width, height;
    public boolean active;
    public boolean animated;
    public boolean up;
    public float yOrigin;
    public boolean playSound;
    public float moveRange;

    // WIGGLE:
    public boolean rotate;
    public boolean rotateLeft;
    public float rotation;

    public CanvasButton(TextureAtlas.AtlasRegion region, int x, int y, boolean anim) {
        this.x = x;
        this.y = y;
        width = GameValues.MENU_BTN_WIDTH;
        height = GameValues.MENU_BTN_HEIGHT;
        yOrigin = y;
        active = true;
        animated = anim;
        image = region;
        up = true;
        playSound = true;

        // MOVE RANGE (RANDOMIZE):
        if (anim) {
            moveRange = GameValues.CAS_BUTTON_MOVE_RANGE;
        }

        // ROTATION:
        rotate = false;
    }

    public void wiggle() {
        if (!rotate) {
            rotate = true;
            rotateLeft = false;
        }
    }

    public boolean isClicked(int x, int y) {
        if (!active)
            return false;

        rectangle.set(this.x, this.y, width, height);
        Game.log(rectangle.toString() + " x,y : " + x + ", " + y);

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

    public void draw(SpriteBatch batch) {
        if (!active)
            return;
//        image.setCenter(image.getWidth() / 2, image.getHeight() / 2);

        if (active) {
            if (rotate) {
                batch.draw(image, x, y, x, y, width, width, 1, 1, rotation);

            } else {
                batch.draw(image, x, y, width, width);
            }
        }
    }

}
