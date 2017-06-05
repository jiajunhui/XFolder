package com.kk.taurus.xfolder.engine;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import java.util.Queue;

/**
 * Created by Taurus on 2017/6/5.
 */

public class AnimationEngine {

    public static Animation getMediaTypeAnimation(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY){
        Animation animation = new ScaleAnimation(fromX,toX,fromY,toY,pivotX,pivotY);
        animation.setDuration(300);
        return animation;
    }

    public static Animation getMediaTypeAnimation(int width, int height){
        AnimationSet animationSet = new AnimationSet(true);
        Animation scaleAnimation1 = new ScaleAnimation(0f,1.1f,0f,1.1f,width/2,height/2);
        scaleAnimation1.setDuration(300);

        animationSet.addAnimation(scaleAnimation1);

        Animation scaleAnimation2 = new ScaleAnimation(1.1f,1f,1.1f,1f,width/2,height/2);
        scaleAnimation2.setDuration(500);

        animationSet.addAnimation(scaleAnimation2);



        return animationSet;
    }

    public static void QueueAnimator(Queue<AnimatorSet> animatorSets){
        while(!animatorSets.isEmpty()){
            AnimatorSet animatorSet = animatorSets.poll();
            animatorSet.start();
            
        }
    }

    public static void animator(View view, float pivotX, float pivotY){

        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0.1f,1.0f).setDuration(400),
                ObjectAnimator.ofFloat(view,"scaleX",0.2f,1.1f,0.8f,1.0f,0.9f,1.0f).setDuration(1000),
                ObjectAnimator.ofFloat(view,"scaleY",0.2f,1.1f,0.8f,1.0f,0.9f,1.0f).setDuration(1000)
        );

        animatorSet.setInterpolator(new AccelerateInterpolator(2.5f));

        animatorSet.start();

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
