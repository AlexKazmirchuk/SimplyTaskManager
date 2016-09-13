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

    private final float mScaleFrom;

    public RotateAnimAdapter(@NonNull BaseAdapter baseAdapter) {
        this(baseAdapter,25.0f);
    }

    private RotateAnimAdapter(@NonNull BaseAdapter baseAdapter, float mScaleFrom) {
        super(baseAdapter);
        this.mScaleFrom = mScaleFrom;
    }

    @Override
    public void setAbsListView(@NonNull AbsListView absListView) {
        super.setAbsListView(absListView);
        if (getViewAnimator() != null){
            getViewAnimator().setAnimationDurationMillis(300);
        }
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view) {
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(view,"rotationX", this.mScaleFrom,0.0f);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(view,"rotationY", this.mScaleFrom,0.0f);
        return new Animator[]{rotationX,rotationY};
    }
}
