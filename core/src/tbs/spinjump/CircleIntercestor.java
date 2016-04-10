package tbs.spinjump;

/**
 * Created by linde on 06-Oct-15.
 */
public class CircleIntercestor {
    private static final double minAngle = 270;
    private static final Point[] points = new Point[]{
            new Point(),
            new Point(),
            new Point(),
            new Point()
    };
    private static float[] lineStop = new float[3];
    private static double check;

    public static void reset() {
        lineStop[2] = 0;
    }

    public static float[] intersect(GearPlatform platform, float outerX, float outerY) {
        check = (360 + Math.toDegrees(Game.player.platformOnAngle)) % 360;

        if (check < 90 || check > minAngle) {
            lineStop[0] = outerX;
            lineStop[1] = outerY;
            lineStop[2] = 1;
            return lineStop;
        }


        points[0].x = Game.player.x;
        points[0].y = Game.player.y;

        points[1].x = outerX;
        points[1].y = outerY;

        points[2].x = platform.x;
        points[2].y = platform.y;

        final Point intercept = getCircleLineIntersectionPoint(platform.width + GameValues.PLAYER_SCALE);
        if (intercept != null) {
            lineStop[0] = intercept.x;
            lineStop[1] = intercept.y;
            lineStop[2] = 1;
        } else {
            lineStop[0] = outerX;
            lineStop[1] = outerY;
            lineStop[2] = 0;
        }
        return lineStop;
    }

    private static Point getCircleLineIntersectionPoint(float radius) {
        float baX = points[1].x - points[0].x;
        float baY = points[1].y - points[0].y;
        float caX = points[2].x - points[0].x;
        float caY = points[2].y - points[0].y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return null;
        }

        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;

        points[3].x = points[0].x - baX * abScalingFactor1;
        points[3].y = points[0].y - baY * abScalingFactor1;

        return points[3];
    }

    static class Point {
        float x, y;

        public Point() {
            super();
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }
    }
}
