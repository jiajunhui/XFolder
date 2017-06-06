package com.kk.taurus.xfolder.engine;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by Taurus on 2017/6/5.
 */

public class AnimationEngine {

    public static void mainMediaAnimator(View view, float pivotX, float pivotY){
        view.clearAnimation();

        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"scaleX",0.0f,1.1f,0.7f,1.0f,0.8f,1.0f,0.9f,1.0f).setDuration(1200),
                ObjectAnimator.ofFloat(view,"scaleY",0.0f,1.1f,0.7f,1.0f,0.8f,1.0f,0.9f,1.0f).setDuration(1200)
        );

        animatorSet.setInterpolator(new AccelerateInterpolator(1.5f));

        animatorSet.start();

    }

    public static AnimationSet getExplorerItemAnimationSet(){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(-1f,0f,1f,1f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400);
        translateAnimation.start();
        animationSet.addAnimation(translateAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f,1.0f);
        alphaAnimation.setDuration(400);
        alphaAnimation.start();
        animationSet.addAnimation(alphaAnimation);
        animationSet.start();
        return animationSet;
    }

    public static abstract class AnimatorCallBack implements Animator.AnimatorListener{
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
