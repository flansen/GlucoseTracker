package de.fha.bwi50101.create_edit.slider;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.impl.ColorHelper;

/**
 * Created by Florian on 16.04.2016.
 */
public class LumindSlider extends RelativeLayout {
    @BindView(R.id.slider_circle_view)
    RoundRectSliderView roundRectSliderView;
    private int startTopMargin, startY;
    private float morph;
    private boolean isTouched, isActive = false;


    private LumindSliderHandlerImpl handler;

    public LumindSlider(Context context) {
        super(context);
        init();
    }

    public LumindSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LumindSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LumindSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.lumind_slider, this);
        ButterKnife.bind(this);
        //http://stackoverflow.com/questions/9398057/android-move-a-view-on-touch-move-action-move
        roundRectSliderView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int touchRawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!roundRectSliderView.isTouchInBounds(event.getX(), event.getY())) {
                            return false;
                        }
                        handleTouchDown(view, touchRawY);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isTouched)
                            return false;
                        handleTouchUp();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!isTouched)
                            return false;
                        handleTouchMove(view, touchRawY);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private void handleTouchMove(View view, int touchRawY) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        // if top margin would exceed view height less than the circles heigth then "clamp", else set to computed value
        int newY = touchRawY - startTopMargin;
        int newMarginValue;
        //if top margin would be less than zero, set it to 0
        if (newY < 0) {
            newMarginValue = 0;
            if (handler != null)
                handler.onMaxTop();
        } else {
            //if the newY is greater than the screen height less than the view's height , set it to that value
            if (newY > getHeight() - roundRectSliderView.getHeight()) {
                newMarginValue = getHeight() - roundRectSliderView.getHeight();
                if (handler != null)
                    handler.onMinBottom();
                //if the newY is less than the max screen height and greater than touchRawY, just set it to the delta value
            } else {
                newMarginValue = newY;
                if (handler != null) {
                    float deltaY = (float) (touchRawY + startY);
                    handler.onSlide(deltaY / getHeight() - .5f);
                }
            }
        }
        layoutParams.topMargin = newMarginValue;
        view.setLayoutParams(layoutParams);
    }

    private void handleTouchUp() {
        setViewZIndex(0);
        roundRectSliderView.animateToCircle();
        ObjectAnimator anim = ObjectAnimator.ofFloat(LumindSlider.this, "morph", 1, 0);
        anim.setDuration(300);
        anim.start();
        if (handler != null)
            handler.onSliderRelease();
        isTouched = false;
        isActive = true;
    }

    private void handleTouchDown(View v, int y) {
        isTouched = true;
        LayoutParams lParams = (LayoutParams) v.getLayoutParams();
        startTopMargin = y - lParams.topMargin;
        startY = y;
        setViewZIndex(10000);
        roundRectSliderView.animateToRectangle();
        if (handler != null)
            handler.onSliderStart();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) roundRectSliderView.getLayoutParams();
            params.setMargins(0, (getHeight() - roundRectSliderView.getHeight()) / 2, 0, 0);
            roundRectSliderView.setLayoutParams(params);
        }
    }

    public Activity getHostingActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public void setViewColorAndAlpha(int color, int alpha) {
        int colorInt = ColorHelper.addAlphaToColor(color, alpha);
        getRoundRectSliderView().setBackgroundColor(colorInt);
    }

    public RoundRectSliderView getRoundRectSliderView() {
        return roundRectSliderView;
    }

    public void setHandler(LumindSliderHandlerImpl handler) {
        this.handler = handler;
        handler.onHandlerSet();
    }

    private void setMorph(float morph) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) roundRectSliderView.getLayoutParams();
        float centerY = (getHeight() - roundRectSliderView.getHeight()) / 2;
        params.setMargins(0, (int) (params.topMargin * morph + centerY * (1 - morph)), 0, 0);
        roundRectSliderView.setLayoutParams(params);
    }

    private void setViewZIndex(int zIndex) {
        ViewCompat.setTranslationZ(this, zIndex);
    }

}
