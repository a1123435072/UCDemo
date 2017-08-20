package com.example.yangg.uc.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.example.yangg.uc.R;

import java.util.List;

/**
 * Created by yangg on 2017/7/6.
 */

public class tabBehavior extends HeaderScrollingViewBehavior {
    private  Context context;

    public tabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        return dependency.getId() == R.id.fl_head;
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i= 0;i<views.size();i++){

            View view = views.get(i);
            if (view.getId() == R.id.fl_head){
                return view;
            }
        }
        return null;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChild(child,dependency);

        return super.onDependentViewChanged(parent, child, dependency);
    }


    private void offsetChild(View child, View dependency) {
    //随着滑动,tab便宜点额百分比和newpaget偏移的百分比相同
        float offsetRange = getFinalHeigt()-child.getTop();
        child.setTranslationY(dependency.getTranslationY() / getHeadetOffsetRange() * offsetRange) ;

    }

    private int getFinalHeigt(){
        return (int) (context.getResources().getDimension(R.dimen.height)/2);
    }

    private int getHeadetOffsetRange(){
        return -(int) (context.getResources().getDimension(R.dimen.height));
    }
}
