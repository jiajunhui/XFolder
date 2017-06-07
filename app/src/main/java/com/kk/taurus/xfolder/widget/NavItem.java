package com.kk.taurus.xfolder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/6/7.
 */

public class NavItem extends LinearLayout {

    private ImageView mHeaderIcon;
    private TextView mTitle;

    private int[] _attrs = {android.R.attr.src,android.R.attr.textSize,android.R.attr.textColor,android.R.attr.text};
    private ImageView mArrow;

    public NavItem(Context context) {
        this(context, null);
    }

    public NavItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, _attrs);

        int iconId = array.getResourceId(0, R.mipmap.ic_launcher);
        float textSize = array.getFloat(1,20f);
        int textColor = array.getColor(2, Color.BLACK);
        CharSequence text = array.getText(3);

        array.recycle();

        setOrientation(LinearLayout.HORIZONTAL);

        mHeaderIcon = new ImageView(context);
        mHeaderIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mHeaderIcon.setImageResource(iconId);

        mTitle = new TextView(context);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        mTitle.setTextColor(textColor);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setText(text);

        addView(mHeaderIcon,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mTitle,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mArrow = new ImageView(context);
        mArrow.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mArrow.setImageResource(R.mipmap.icon_arrow);
        addView(mArrow,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w > 0){
            ViewGroup.LayoutParams headerParams = mHeaderIcon.getLayoutParams();
            headerParams.width = h;
            mHeaderIcon.setLayoutParams(headerParams);

            ViewGroup.LayoutParams titleParams = mTitle.getLayoutParams();
            titleParams.width = w - (2*h);
            mTitle.setLayoutParams(titleParams);

            ViewGroup.LayoutParams arrowParams = mArrow.getLayoutParams();
            arrowParams.width = h;
            mArrow.setLayoutParams(arrowParams);

            requestLayout();
        }
    }
}
