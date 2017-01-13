package test.mzj.com.appstructureproject.valueanimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by mazejia on 2017/1/13.
 */

public class ValueAnimatorUtil {

    public static void setAlphaAnimation(View view){
        if(view == null){
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
        animator.setDuration(1000);
        animator.start();
    }

    public static void setRotateAnimation(View view){
        if(view == null){
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0f,360f);
        animator.setDuration(1000);
        animator.start();
    }

    public static void setTranslationXAnimation(View view){
        if(view == null){
            return;
        }
        float currX = view.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationX",currX,-500f,currX);
        animator.setDuration(1000);
        animator.start();
    }

    public static void setScaleYAnimation(View view){
        if(view == null){
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"scaleY",1f,3f,1f);
        animator.setDuration(1000);
        animator.start();
    }

    public static void setSetAnimation(View view){
        if(view == null){
            return;
        }
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }
}
