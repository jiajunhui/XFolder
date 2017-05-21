package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.SeekBar;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.ScanImagePagerAdapter;
import com.kk.taurus.xfolder.bean.ScanImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanImageHolder extends ContentHolder<ScanImageData> implements ViewPager.OnPageChangeListener {

    private View mRootView;
    private ViewPager mViewPager;
    private SeekBar mSeekBar;
    private ScanImagePagerAdapter mAdapter;
    private List<PhotoItem> mPhotoItems = new ArrayList<>();

    private OnScanImageListener onScanImageListener;

    public ScanImageHolder(Context context) {
        super(context);
    }

    public void setOnScanImageListener(OnScanImageListener onScanImageListener) {
        this.onScanImageListener = onScanImageListener;
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_scan_images);
        mRootView = getViewById(R.id.rootView);
        mSeekBar = getViewById(R.id.seekBar);
        mViewPager = getViewById(R.id.viewPager);
        // 1.设置幕后item的缓存数目
        mViewPager.setOffscreenPageLimit(3);
        // 2.设置页与页之间的间距
        mViewPager.setPageMargin(0);
    }

    public void setBackgroundColor(int color){
        mRootView.setBackgroundColor(color);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mAdapter = new ScanImagePagerAdapter(mContext,mPhotoItems);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(ScanImageHolder.this);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mPhotoItems.clear();
        mPhotoItems.addAll(mData.getPhotoItems());
        mAdapter.notifyDataSetChanged();
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                int currentPos = mData.getCurrPosition();
                mViewPager.setCurrentItem(currentPos,false);
                onPageSelected(currentPos);
                setSeekBarListener();
            }
        });
    }

    private Runnable mRunnable;

    private void setSeekBarListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                if(fromUser){
                    if(mRunnable!=null){
                        mViewPager.removeCallbacks(mRunnable);
                    }
                    mViewPager.postDelayed(mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(progress);
                        }
                    }, 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int size = mPhotoItems.size();
        if(size<1)
            return;
        mSeekBar.setMax(size - 1);
        mSeekBar.setProgress(position);
        if(onScanImageListener!=null){
            onScanImageListener.onCardSelected(mPhotoItems.get(position), position, size);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mViewPager!=null && mRunnable!=null){
            mViewPager.removeCallbacks(mRunnable);
        }
    }

    public interface OnScanImageListener{
        void onCardSelected(PhotoItem item, int position, int count);
    }
}
