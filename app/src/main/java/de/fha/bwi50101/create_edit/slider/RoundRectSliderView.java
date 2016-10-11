package de.fha.bwi50101.create_edit.slider;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import de.fha.bwi50101.R;


//https://github.com/pavlospt/CircleView/blob/master/app/src/main/java/com/github/pavlospt/CircleView.java
public class RoundRectSliderView extends View {
    private static final int DEFAULT_VIEW_SIZE = 96;
    private static int DEFAULT_TITLE_COLOR = Color.CYAN;
    private static int DEFAULT_SUBTITLE_COLOR = Color.WHITE;
    private static String DEFAULT_TITLE = "Title";
    private static String DEFAULT_SUBTITLE = "Subtitle";
    private static boolean DEFAULT_SHOW_TITLE = true;
    private static boolean DEFAULT_SHOW_SUBTITLE = true;
    private static float DEFAULT_TITLE_SIZE = 30f;
    private static float DEFAULT_SUBTITLE_SIZE = 20f;
    private static int DEFAULT_STROKE_COLOR = Color.CYAN;
    private static int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static int DEFAULT_FILL_COLOR = Color.DKGRAY;
    private static float DEFAULT_STROKE_WIDTH = 5f;
    private static float DEFAULT_FILL_RADIUS = 0.9f;
    private int mTitleColor = DEFAULT_TITLE_COLOR;
    private int mSubtitleColor = DEFAULT_SUBTITLE_COLOR;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int mFillColor = DEFAULT_FILL_COLOR;

    private String mTitleText = DEFAULT_TITLE;
    private String mSubtitleText = DEFAULT_SUBTITLE;

    private float mTitleSize = DEFAULT_TITLE_SIZE;
    private float mSubtitleSize = DEFAULT_SUBTITLE_SIZE;
    private float mStrokeWidth = DEFAULT_STROKE_WIDTH;
    private float mFillRadius = DEFAULT_FILL_RADIUS;

    private boolean mShowTitle = DEFAULT_SHOW_TITLE;
    private boolean mShowSubtitle = DEFAULT_SHOW_SUBTITLE;

    private TextPaint mTitleTextPaint;
    private TextPaint mSubTextPaint;

    private Paint mStrokePaint;
    private Paint mBackgroundPaint;
    private Paint mFillPaint;

    private RectF rectF;


    private float morphValue = 0f;
    private boolean isTouched;

    public RoundRectSliderView(Context context) {
        super(context);
        init(null, 0);
    }


    public RoundRectSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RoundRectSliderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RoundRectSliderView, defStyle, 0);

        if (a.hasValue(R.styleable.RoundRectSliderView_titleText)) {
            mTitleText = a.getString(R.styleable.RoundRectSliderView_titleText);
        }

        if (a.hasValue(R.styleable.RoundRectSliderView_subtitleText)) {
            mSubtitleText = a.getString(R.styleable.RoundRectSliderView_subtitleText);
        }

        mTitleColor = a.getColor(R.styleable.RoundRectSliderView_titleColor, DEFAULT_TITLE_COLOR);
        mSubtitleColor = a.getColor(R.styleable.RoundRectSliderView_subtitleColor, DEFAULT_SUBTITLE_COLOR);
        mBackgroundColor = a.getColor(R.styleable.RoundRectSliderView_backgroundColorValue, DEFAULT_BACKGROUND_COLOR);
        mStrokeColor = a.getColor(R.styleable.RoundRectSliderView_strokeColorValue, DEFAULT_STROKE_COLOR);
        mFillColor = a.getColor(R.styleable.RoundRectSliderView_fillColor, DEFAULT_FILL_COLOR);

        mTitleSize = a.getDimension(R.styleable.RoundRectSliderView_titleSize, DEFAULT_TITLE_SIZE);
        mSubtitleSize = a.getDimension(R.styleable.RoundRectSliderView_subtitleSize, DEFAULT_SUBTITLE_SIZE);

        mStrokeWidth = a.getFloat(R.styleable.RoundRectSliderView_strokeWidthSize, DEFAULT_STROKE_WIDTH);
        mFillRadius = a.getFloat(R.styleable.RoundRectSliderView_fillRadius, DEFAULT_FILL_RADIUS);

        a.recycle();

        //Title TextPaint
        mTitleTextPaint = new TextPaint();
        mTitleTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        mTitleTextPaint.setLinearText(true);
        mTitleTextPaint.setColor(mTitleColor);
        mTitleTextPaint.setTextSize(mTitleSize);

        //Subtitle TextPaint
        mSubTextPaint = new TextPaint();
        mSubTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mSubTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mSubTextPaint.setTextAlign(Paint.Align.CENTER);
        mSubTextPaint.setLinearText(true);
        mSubTextPaint.setColor(mSubtitleColor);
        mSubTextPaint.setTextSize(mSubtitleSize);

        //Stroke Paint
        mStrokePaint = new Paint();
        mStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        //Background Paint
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        //Fill Paint
        mFillPaint = new Paint();
        mFillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(mFillColor);

        rectF = new RectF();

    }

    private void invalidateTextPaints() {
        mTitleTextPaint.setColor(mTitleColor);
        mSubTextPaint.setColor(mSubtitleColor);
        mTitleTextPaint.setTextSize(mTitleSize);
        mSubTextPaint.setTextSize(mSubtitleSize);
        invalidate();
    }

    private void invalidatePaints() {
        mBackgroundPaint.setColor(mBackgroundColor);
        mStrokePaint.setColor(mStrokeColor);
        mFillPaint.setColor(mFillColor);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*canvas.drawOval(mInnerRectF, mStrokePaint);

        if (mShowTitle) {
            canvas.drawText(mTitleText,
                    xPos,
                    yPos,
                    mTitleTextPaint);
        }

        */
        float mRad = getHeight() / 2;
        float centerY = getHeight() / 2;

        float left = (1 - morphValue) * (getWidth() / 2 - mRad) + morphValue * 0;
        float top = (1 - morphValue) * (centerY - mRad) + morphValue * (centerY - mRad);
        float right = (1 - morphValue) * (getWidth() / 2 + mRad) + morphValue * getWidth();
        float bottom = (1 - morphValue) * (centerY + mRad) + morphValue * (centerY + mRad);
        float radVal = (1 - morphValue) * mRad;
        rectF.set(left, top, right, bottom);
        int textOffsetY = (int) (mTitleTextPaint.descent() + mTitleTextPaint.ascent() / 2);
        int yPos = (int) rectF.centerY() - textOffsetY;
        int xPos = (int) (((1 - morphValue) * rectF.centerX()) + morphValue * mTitleSize);
        canvas.drawRoundRect(rectF, radVal, radVal, mBackgroundPaint);
        canvas.drawText(mTitleText,
                xPos,
                yPos,
                mTitleTextPaint);
        if (mShowSubtitle) {
            canvas.drawText(mSubtitleText,
                    xPos,
                    yPos + 30,
                    mSubTextPaint);
        }
    }

    public void animateToRectangle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "morphValue", 0, 1);
        animator.setDuration(150);
        animator.start();
    }

    public void animateToCircle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "morphValue", 1, 0);
        animator.setDuration(150);
        animator.start();
    }

    public float getMorphValue() {
        return morphValue;
    }

    public void setMorphValue(float morphValue) {
        this.morphValue = morphValue;
        invalidate();
    }

    /**
     * Sets whether the view's title string will be shown.
     *
     * @param flag The boolean value.
     */
    public void setShowTitle(boolean flag) {
        this.mShowTitle = flag;
        invalidate();
    }

    /**
     * Sets whether the view's subtitle string will be shown.
     *
     * @param flag The boolean value.
     */
    public void setShowSubtitle(boolean flag) {
        this.mShowSubtitle = flag;
        invalidate();
    }

    /**
     * Gets the title string attribute value.
     *
     * @return The title string attribute value.
     */
    public String getTitleText() {
        return mTitleText;
    }

    /**
     * Sets the view's title string attribute value.
     *
     * @param title The example string attribute value to use.
     */
    public void setTitleText(String title) {
        mTitleText = title;
        invalidate();
    }

    /**
     * Gets the subtitle string attribute value.
     *
     * @return The subtitle string attribute value.
     */
    public String getSubtitleText() {
        return mSubtitleText;
    }

    /**
     * Sets the view's subtitle string attribute value.
     *
     * @param subtitle The example string attribute value to use.
     */
    public void setSubtitleText(String subtitle) {
        mSubtitleText = subtitle;
        invalidate();
    }

    /**
     * Gets the stroke color attribute value.
     *
     * @return The stroke color attribute value.
     */
    public int getStrokeColor() {
        return mStrokeColor;
    }

    /**
     * Sets the view's stroke color attribute value.
     *
     * @param strokeColor The stroke color attribute value to use.
     */
    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        invalidatePaints();
    }

    /**
     * Gets the background color attribute value.
     *
     * @return The background color attribute value.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Sets the view's background color attribute value.
     *
     * @param backgroundColor The background color attribute value to use.
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidatePaints();
    }

    /**
     * Sets the view's stroke width attribute value.
     *
     * @param strokeWidth The stroke width attribute value to use.
     */
    public void setBackgroundColor(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidate();
    }

    /**
     * Gets the fill color attribute value.
     *
     * @return The fill color attribute value.
     */
    public int getFillColor() {
        return mStrokeColor;
    }

    /**
     * Sets the view's fill color attribute value.
     *
     * @param fillColor The fill color attribute value to use.
     */
    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
        invalidatePaints();
    }

    /**
     * Gets the stroke width dimension attribute value.
     *
     * @return The stroke width dimension attribute value.
     */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * Gets the fill radius dimension attribute value.
     *
     * @return The fill radius dimension attribute value.
     */
    public float getFillRadius() {
        return mFillRadius;
    }

    /**
     * Sets the view's fill radius attribute value.
     *
     * @param fillRadius The fill radius attribute value to use.
     */
    public void setFillRadius(float fillRadius) {
        mFillRadius = fillRadius;
        invalidate();
    }

    /**
     * Gets the title size dimension attribute value.
     *
     * @return The title size dimension attribute value.
     */
    public float getTitleSize() {
        return mTitleSize;
    }

    /**
     * Sets the view's title size dimension attribute value.
     *
     * @param titleSize The title size dimension attribute value to use.
     */
    public void setTitleSize(float titleSize) {
        mTitleSize = titleSize;
        invalidateTextPaints();
    }

    /**
     * Gets the subtitle size dimension attribute value.
     *
     * @return The subtitle size dimension attribute value.
     */
    public float getSubtitleSize() {
        return mSubtitleSize;
    }

    /**
     * Sets the view's subtitle size dimension attribute value.
     *
     * @param subtitleSize The subtitle size dimension attribute value to use.
     */
    public void setSubtitleSize(float subtitleSize) {
        mSubtitleSize = subtitleSize;
        invalidateTextPaints();
    }

    /**
     * Gets the title text color attribute value.
     *
     * @return The text color attribute value.
     */
    public int getTitleColor() {
        return mTitleColor;
    }

    /**
     * Sets the view's title text color attribute value.
     *
     * @param titleColor The title text color attribute value to use.
     */
    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
        invalidateTextPaints();
    }

    /**
     * Gets the subtitle text color attribute value.
     *
     * @return The text color attribute value.
     */
    public int getSubtitleColor() {
        return mSubtitleColor;
    }

    /**
     * Sets the view's subtitle text color attribute value.
     *
     * @param subtitleColor The subtitle text color attribute value to use.
     */
    public void setSubtitleColor(int subtitleColor) {
        mSubtitleColor = subtitleColor;
        invalidateTextPaints();
    }


    public boolean isTouchInBounds(float x, float y) {
        return rectF.contains(x, y);
    }

    public void setIsTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }
}