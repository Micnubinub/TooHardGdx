package tbs.spinjump;

import android.graphics.Rect;

public class GameObject {
    float x;
    float y;
    int width;
    int height;
    float speed;
    //todo if getting issues with rect, remove the static modifier
    private static final Rect bounds = new Rect();
    int color;
    public boolean hasScore;

    public GameObject() {

    }

    public void setup(int x, int y, int width, int height, int color, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
        bounds.set(x, y, width + x, height + y);
    }

    public float getAngle(float xO, float yO) {
        //Todo check if theres too much conversion to and from deg
        return ((float) Math.toDegrees(Math.atan2(yO - y, xO - x)))%360;
    }

    public Rect getBounds() {
        bounds.set((int) x, (int) y, (int) x + width, (int) y + height);
        return bounds;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
