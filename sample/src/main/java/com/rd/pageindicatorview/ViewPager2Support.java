package com.rd.pageindicatorview;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.rd.PageIndicatorSupport;

public class ViewPager2Support extends PageIndicatorSupport implements View.OnTouchListener {

    private final ViewPager2 pager2;
    private final ViewPager2.OnPageChangeCallback callback;
    private final RecyclerView.AdapterDataObserver dataObserver;

    public ViewPager2Support( @NonNull ViewPager2 pager2) {
        this.pager2 = pager2;
        this.callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ViewPager2Support.this.onPageScroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                ViewPager2Support.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager2.SCROLL_STATE_IDLE == state) {
                    ViewPager2Support.this.onPageScrollStateIdle();
                }
            }
        };
        this.dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                onDataChanged();
            }
        };
    }

    @Override
    public boolean hasAdapter() {
        return null != pager2.getAdapter();
    }

    @Override
    public int getCount() {
        return hasAdapter() ? pager2.getAdapter().getItemCount() : 0;
    }

    @Override
    public int getCurrentPosition() {
        return pager2.getCurrentItem();
    }

    @Override
    public void bind() {
        this.pager2.setOnTouchListener(this);
        this.pager2.registerOnPageChangeCallback(callback);
        this.pager2.getAdapter().registerAdapterDataObserver(dataObserver);
    }

    @Override
    public void unbind() {
        this.pager2.setOnTouchListener(null);
        this.pager2.unregisterOnPageChangeCallback(callback);
        this.pager2.getAdapter().unregisterAdapterDataObserver(dataObserver);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return onTouchPagerGroup(v, event);
    }


}
