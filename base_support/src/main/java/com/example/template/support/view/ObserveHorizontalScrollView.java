package com.example.template.support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Horrarndoo on 2019/7/30.
 * <p>
 */
public class ObserveHorizontalScrollView extends HorizontalScrollView {
    public ObserveHorizontalScrollView(Context context) {
        super(context);
    }

    public ObserveHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null)
            onScrollChangedListener.onScroll(l, t, oldl, oldt);
    }

    private OnScrollChangedListener onScrollChangedListener;

    public void setOnScrollChangedListener(OnScrollChangedListener l) {
        onScrollChangedListener = l;
    }

    public interface OnScrollChangedListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }
}
