package com.reference.maczak.referenceapplication.main;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.reference.maczak.referenceapplication.R;


/**
 * Created by Bandi on 2015.09.01..
 */
public class LoadingDialog extends Dialog {

    private Context context;
    ObjectAnimator outerAnimation;
    ObjectAnimator innerAnimation;

    public LoadingDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setCancelable(false);
        this.context = context;
        init();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        init();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        init();
    }

    public void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.loading, null);

        setContentView(layout);

        ImageView outer = (ImageView) layout.findViewById(R.id.loading_outer);
        ImageView inner = (ImageView) layout.findViewById(R.id.loading_inner);
        PropertyValuesHolder outerRotation = PropertyValuesHolder.ofFloat(View.ROTATION, 360f);
        outerAnimation = ObjectAnimator.ofPropertyValuesHolder(outer, outerRotation);
        outerAnimation.setRepeatCount(-1);
        outerAnimation.setStartDelay(0);
        outerAnimation.setInterpolator(null);
        outerAnimation.setRepeatMode(ValueAnimator.RESTART);
        outerAnimation.setDuration(1250);
        outerAnimation.start();

        PropertyValuesHolder innerRotation = PropertyValuesHolder.ofFloat(View.ROTATION, -360f);
        innerAnimation = ObjectAnimator.ofPropertyValuesHolder(inner, innerRotation);
        innerAnimation.setRepeatCount(-1);
        innerAnimation.setStartDelay(0);
        innerAnimation.setInterpolator(null);
        innerAnimation.setRepeatMode(ValueAnimator.RESTART);
        innerAnimation.setDuration(1250);
        innerAnimation.start();

    }

    @Override
    public void dismiss() {
        if(outerAnimation!=null && innerAnimation!=null){
            innerAnimation.end();
            //innerAnimation.cancel();
            outerAnimation.end();
            //outerAnimation.cancel();
        }
        super.dismiss();
    }

    @Override
    public void show() {
        if(outerAnimation!=null && innerAnimation!=null && !innerAnimation.isRunning() && !outerAnimation.isRunning()){
            innerAnimation.start();
            outerAnimation.start();
        }
        super.show();
    }
}
