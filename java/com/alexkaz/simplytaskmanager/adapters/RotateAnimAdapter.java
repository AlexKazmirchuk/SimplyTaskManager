package com.alexkaz.simplytaskmanager.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class RotateAnimAdapter extends AnimationAdapter {

    private static final int DURATION_MILLIS = 300;
    private static final float M_SCALE_FROM = 25.0f;
    private static final float M_SCALE_X_TO = 0.0f;
    private static final float M_SCALE_Y_TO = 0.0f;
    private final float mScaleFrom;

    public RotateAnimAdapter(@NonNull BaseAdapter baseAdapter) {
        this(baseAdapter, M_SCALE_FROM);
    }

    private RotateAnimAdapter(@NonNull BaseAdapter baseAdapter, float mScaleFrom) {
        super(baseAdapter);
        this.mScaleFrom = mScaleFrom;
    }

    @Override
    public void setAbsListView(@NonNull AbsListView absListView) {
        super.setAbsListView(absListView);
        if (getViewAnimator() != null){
            getViewAnimator().setAnimationDurationMillis(DURATION_MILLIS);
        }
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view) {
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(view,"rotationX", this.mScaleFrom, M_SCALE_X_TO);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(view,"rotationY", this.mScaleFrom, M_SCALE_Y_TO);
        return new Animator[]{rotationX,rotationY};
    }
}
