package tbs.spinjump;

public class StoreImageView {
    //TODO
//    private static final Paint paint = new Paint();
//    public float innerCircleScale = .5f;
//    public int innerCircleColor = 0xffffbb00;
//    public int outerCircleColor = 0xffbbff00;
//    public float radiusMult;
//    public boolean trail;
//    public boolean bought;
//    private int cx, cy, r;
//
//    public StoreImageView(Context context) {
//        super(context);
//    }
//
//    public StoreImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public StoreImageView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public void setInnerCircleColor(int innerCircleColor) {
//        this.innerCircleColor = innerCircleColor;
//    }
//
//    public void setInnerCircleScale(float innerCircleScale) {
//        this.innerCircleScale = innerCircleScale;
//    }
//
//    public void setOuterCircleColor(int outerCircleColor) {
//        this.outerCircleColor = outerCircleColor;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (!trail || !bought) {
//            paint.setColor(outerCircleColor);
//            canvas.drawCircle(cx, cy, r, paint);
//            paint.setColor(innerCircleColor);
//            canvas.drawCircle(cx, cy, r * innerCircleScale, paint);
//        } else {
//            paint.setColor(outerCircleColor);
//            canvas.drawCircle(cx - r, cy, r, paint);
//            canvas.drawCircle(cx + (r), cy, r * 0.75f, paint);
//        }
//        super.onDraw(canvas);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        cx = w / 2;
//        cy = h / 2;
//        r = (int) (Math.min(cy, cx) * radiusMult);
//    }
}
