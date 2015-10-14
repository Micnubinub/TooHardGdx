package tbs.spinjump;

/**
 * Created by root on 4/11/14.
 */
public class ValueAnimator {

    private Mode mode = Mode.DECELERATE;
    private boolean running;
    private double animated_value = 1;
    private UpdateListener updateListener;
    private long startTime = System.currentTimeMillis();
    private double duration = 1200;


    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
        if (updateListener != null)
            updateListener.onAnimationStart();
    }


    public void stop() {
        startTime = -1;
        running = false;
        if (updateListener != null)
            updateListener.onAnimationFinish();
    }


    public boolean isRunning() {
        return running;
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }


    public void update() {
        final double x = (System.currentTimeMillis() - startTime) / duration;
        if ((System.currentTimeMillis() - startTime) == duration) {
            running = false;
            return;
        }

        switch (mode) {
            case DECELERATE:
                animated_value = (float) (Math.pow(x, 2) - (2 * x) + 1);
                break;
            case ACCELERATE:
                animated_value = (float) ((-0.643f * Math.pow(x, 3)) + (-0.3357f * Math.pow(x, 2)) + (0.02143f * x) + 1);
                break;
            case OVER_SHOOT:

                break;
            case SPRING:
                animated_value = (float) (
                        (-43.6 * Math.pow(x, 5)) +
                                (102.46 * Math.pow(x, 4)) +
                                (-76.88 * Math.pow(x, 3)) +
                                (18.83 * Math.pow(x, 2)) +
                                (1.806 * x)
                );

                break;
        }

        animated_value = animated_value > 1 ? 1 : animated_value;
        if (animated_value <= 0.001) {
            stop();
            animated_value = 1;

            return;
        }
        if (updateListener != null)
            updateListener.update(animated_value);

    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


    public enum Mode {
        ACCELERATE, DECELERATE, OVER_SHOOT, SPRING
    }

    public interface UpdateListener {
        void update(double animatedValue);

        void onAnimationStart();

        void onAnimationFinish();


    }
}
