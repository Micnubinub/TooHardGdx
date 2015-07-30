package tbs.spinjump;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Level {

    // COGS:
    public static final ArrayList<GearPlatform> gears = new ArrayList<>();
    private final int cogsToRecycle = 3;
    public static float cogSpeedMult = 1;
    public static int moverIndex;
    public static boolean moving;
    public static int stageNum;

    public Level() {
        if (gears.size() > 0) {
            gears.clear();
        }
        for (int i = 0; i < cogsToRecycle; ++i) {
            //GearPlatform tmpGear = new GearPlatform();
            gears.add(new GearPlatform());
        }
        setup();
    }

    public void setup() {
        // COGS:
        stageNum = -1; // ACCOUNT FOR THE FIRST COG YOU ARE ALREADY ON
        cogSpeedMult = 1;
        moverIndex = 0;
        moving = false;
        for (int i = 0; i < gears.size(); ++i) {
            final GearPlatform tmpGear = gears.get(i);
            if (i == 0)
                tmpGear.setY((int) GameValues.COG_START_Y);
            tmpGear.generate(i == 0);
        }
    }

    public void update(float delta) {
        if (moving) {
            moveLevel((GameValues.PLAYER_JUMP_SPEED * 0.85f) * delta);
        }
        for (int i = 0; i < gears.size(); ++i) {
            gears.get(i).update(delta);
        }
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < gears.size(); ++i) {
            gears.get(i).draw(canvas);
        }
    }

    public static void updateLevelMover(int index) {
        int tmpIndex = index + 1;
        if (tmpIndex == gears.size())
            tmpIndex = 0;
        moverIndex = tmpIndex;
        moving = true;
    }

    // MOVE EVERYTHING IN LEVEL HERE: (SIMULATES A CAMERA)
    public static void moveLevel(float speed) {
        Game.player.y += speed;
        for (int i = 0; i < Game.player.trail.size(); ++i) {
            Game.player.trail.get(i).y += speed;
        }
        for (int i = 0; i < Game.player.circles.size(); ++i) {
            Game.player.circles.get(i).y += speed;
        }
        for (int i = 0; i < Game.textAnimator.texts.size(); ++i) {
            Game.textAnimator.texts.get(i).y += speed;
        }
        for (int i = 0; i < gears.size(); ++i) {
            gears.get(i).moveDown(speed);
        }
        if (gears.get(moverIndex).y > GameValues.COG_START_Y) {
            float moveFixer = gears.get(moverIndex).y - GameValues.COG_START_Y;
            for (int i = 0; i < gears.size(); ++i) {
                gears.get(i).moveDown(-moveFixer);
            }
            moving = false;
        }
    }

    public static int getNextCogPos(GearPlatform cog) {
        int tmpIndex = gears.indexOf(cog);
        tmpIndex -= 1;
        if (tmpIndex < 0)
            tmpIndex = gears.size() - 1;
        return (int) (gears.get(tmpIndex).getY() - (gears.get(tmpIndex).getWidth() + GameValues.COG_GAP));
    }
}
