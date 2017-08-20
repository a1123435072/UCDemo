package com.example.yangg.uc.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.example.yangg.uc.R;

/**
 * Created by yangg on 2017/7/6.
 */

public class TitleBehavior extends CoordinatorLayout.Behavior {
    private  Context context;

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        return dependency.getId() == R.id.fl_head;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (child.getLayoutParams());
//        params.topMargin = -45;
        ((CoordinatorLayout.LayoutParams)child.getLayoutParams()).topMargin = (int) - (context.getResources().getDimension(R.dimen.height)/2);
        //当修改完子试图的布局参数后,要让付布局

        parent.onLayoutChild(child,layoutDirection);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChikd(child,dependency);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void offsetChikd(View child, View dependency) {

        int titleOffsetRange = (int) (context.getResources().getDimension(R.dimen.height)/2);

        child.setTranslationY(dependency.getTranslationY()/getHeaderOffsetRange()*titleOffsetRange);

    }
    private int getHeaderOffsetRange(){
        return -(int) (context.getResources().getDimension(R.dimen.height));
    }
}
