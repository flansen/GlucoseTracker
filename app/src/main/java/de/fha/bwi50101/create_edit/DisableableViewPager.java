package de.fha.bwi50101.create_edit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Florian on 17.10.2016.
 */

public class DisableableViewPager extends ViewPager {
    private boolean isViewPagingEnabled;

    public DisableableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isViewPagingEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isViewPagingEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isViewPagingEnabled && super.onInterceptTouchEvent(ev);
    }

    public void setViewPagingEnabled(boolean isViewPagingEnabled) {
        this.isViewPagingEnabled = isViewPagingEnabled;
    }
}
