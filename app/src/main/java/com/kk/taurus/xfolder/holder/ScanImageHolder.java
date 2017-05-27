package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.ScanImagePagerAdapter;
import com.kk.taurus.xfolder.bean.ScanImageData;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanImageHolder extends ContentHolder<ScanImageData> implements ViewPager.OnPageChangeListener {

    private View mRootView;
    private ViewPager mViewPager;
    private SeekBar mSeekBar;
    private View mPopContainer;
    private ImageView mPreviewImage;
    private ScanImagePagerAdapter mAdapter;
    private List<PhotoItem> mPhotoItems = new ArrayList<>();

    private OnScanImageListener onScanImageListener;

    private int mPopW,mSeekW,mPaddingLeft,mPaddingRight,mSeekLocationX;
    private LinearLayout.LayoutParams mPopParams;

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
        mPopContainer = getViewById(R.id.ll_pop);
        mPreviewImage = getViewById(R.id.iv_preview_image);
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
        mPopParams = (LinearLayout.LayoutParams) mPopContainer.getLayoutParams();
        mPopContainer.post(new Runnable() {
            @Override
            public void run() {
                mPopW = mPopContainer.getMeasuredWidth();
                mSeekW = mSeekBar.getMeasuredWidth();
                int[] location = new int[2];
                mSeekBar.getLocationInWindow(location);
                mSeekLocationX = location[0];
                mPaddingLeft = mSeekBar.getPaddingLeft();
                mPaddingRight = mSeekBar.getPaddingRight();
            }
        });
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
//                    ImageDisplayEngine.display(mContext,mPreviewImage,mPhotoItems.get(progress).getPath(),R.mipmap.icon_image);
//                    calcTipLocation(progress);
//                    if(mRunnable!=null){
//                        mViewPager.removeCallbacks(mRunnable);
//                    }
//                    mViewPager.postDelayed(mRunnable = new Runnable() {
//                        @Override
//                        public void run() {
//                        }
//                    }, 50);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                calcTipLocation(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mViewPager.setCurrentItem(seekBar.getProgress());
                mPopContainer.setVisibility(View.GONE);
            }
        });
    }

    private void calcTipLocation(int progress) {
        int progressW = (mSeekW - mPaddingLeft - mPaddingRight)*progress/mSeekBar.getMax();
        int left = progressW + mPaddingLeft - (mPopW/2);
        mPopParams.leftMargin = left;
        mPopContainer.setLayoutParams(mPopParams);
        mPopContainer.setVisibility(View.VISIBLE);
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
