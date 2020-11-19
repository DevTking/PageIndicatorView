package com.rd;

import android.view.MotionEvent;
import android.view.View;

/**
 * 支持指示器接口
 */
public abstract class PageIndicatorSupport {

    PageIndicatorView indicatorView;

    /**
     * @return 是否有适配器
     */
    public abstract boolean hasAdapter();

    /**
     * @return 总数量
     */
    public abstract int getCount();

    /**
     * @return 当前选中位置
     */
    public abstract int getCurrentPosition();

    /**
     * 与指示器绑定
     */
    public abstract void bind();

    /**
     * 与指示器解除绑定
     */
    public abstract void unbind();

    protected final void onPageScroll(int position, float positionOffset) {
        if (null != indicatorView) {
            indicatorView.onPageScroll(position, positionOffset);
        }
    }

    protected final void onPageSelected(int position) {
        if (null != indicatorView) {
            indicatorView.onPageSelect(position);
        }
    }

    protected final void onPageScrollStateIdle() {
        if (null != indicatorView) {
            indicatorView.onPageScrollStateIdle();
        }
    }

    protected final boolean onTouchPagerGroup(View v, MotionEvent event) {
        if (null == indicatorView) return false;
        if (!indicatorView.getManager().indicator().isFadeOnIdle()) return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                indicatorView.stopIdleRunnable();
                break;

            case MotionEvent.ACTION_UP:
                indicatorView.startIdleRunnable();
                break;
        }
        return false;
    }

    protected final void onDataChanged() {
        if (null == indicatorView) return;
        if (indicatorView.getManager().indicator().isDynamicCount()) {
            indicatorView.updateState();
        }
    }

}