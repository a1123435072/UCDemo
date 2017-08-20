package com.example.yangg.uc.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.yangg.uc.R;

import java.util.List;

/**
 * Created by yangg on 2017/7/6.
 */

public class ContentBehavior extends HeaderScrollingViewBehavior {

    private final Context context;

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.fl_head;
    }

    /**
     * 找到要以来的头部视图,,找到时候 用方法layoutDependsOn 绑定依赖
     *
     * @param views
     * @return
     */
    @Override
    protected View findFirstDependency(List<View> views) {
        //找到需要依赖的头部:  找到我们的头部视图
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view.getId() == R.id.fl_head) {
                return view;
            }
        }
        return null;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChild(child, dependency);

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void offsetChild(View child, View dependency) {

        //de 和child 移动的百分比是一样的
        /**
         * 当钱自试图滚东的距离wield:头部视图滚动的百分比滚动的范围
         */
        //能够移动的最大距离
        //int scrollRange = getScrollRange(dependency);
        child.setTranslationY(-dependency.getTranslationY() / getHeaderOffsetRange() * getScrollRange(dependency));
        Log.i("test", "translationY------------>" + child.getTranslationY());
    }


    private float getHeaderOffsetRange() {
        //让他返回-90dp,,,直接返回的话
        float height = context.getResources().getDimension(R.dimen.height);
        return -height;
    }

    /**
     * 滚动范围,,头部视图的高度就是他的滚动范围
     *
     * @param v
     * @return 指的是当前是如的滚动范围, 值得是高度变化范围
     */
    @Override
    protected int getScrollRange(View v) {
        if (v.getId() == R.id.fl_head) {
            //取最大值,
            int resule = Math.max(0, v.getMeasuredHeight() - getFinalHeight());
            return resule;
        }
        return super.getScrollRange(v);
    }

    public int getFinalHeight() {


        // context.getResources().getDimension(R.dimen.height);
        return (int) context.getResources().getDimension(R.dimen.height);//90
        //return 90;//90
    }
}
