package com.example.yangg.uc.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.yangg.uc.R;

/**
 * Created by yangg on 2017/7/6.
 */

public class NewsPagerBehavior extends CoordinatorLayout.Behavior {

    private MyRunable myRunable;
    private Scroller s;
    private View child;


    public NewsPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        currentOffsetRange = -(int) context.getResources().getDimension(R.dimen.height);
    }

    /**
     * 初始化
     */
    private void init(Context context) {

        s = new Scroller(context);
    }

    //用于判断滚动方向  顺序 1   滚动之前执行的
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        this.child = child;
        //垂直滚动
        return (nestedScrollAxes == ViewGroup.SCROLL_AXIS_VERTICAL) && !isToTop(child);
    }

    private boolean isToTop(View child) {
        /**
         * ???????????
         */
        if (child.getTranslationY() == currentOffsetRange) {
            return true;
        }
        return false;
    }

    /**
     * 滚动的时候  顺序 1
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        float tempDy = (dy / 4f);//除4,,滑动慢一点

        Log.i("test", "tempDy" + tempDy);//系统监控手指
        if (!canScroll(child, tempDy)) {
            //不能滚动,,一会二处理
            if (tempDy > 0) {
                //如果用户向上移动,并已经接近上部的最大高度,则直接将视图移动到做最上部
                child.setTranslationY(currentOffsetRange);
            } else {
                //向下移动
                child.setTranslationY(0);
            }
        } else {
            /**
             * 控制viewpager移动到-90
             */
            //能移动
            child.setTranslationY(child.getTranslationY() - tempDy);
            Log.i("test", "translationY:" + child.getTranslationY());
        }

        consumed[1] = dy;
    }

    /**
     * 该方法主要用于判读newspager是否能狗移动,移动的范围i额是-90  到0
     *
     * @param child
     * @param tempDy
     * @return
     */
    private boolean canScroll(View child, float tempDy) {
        //将要移动的  (将要滑动到的坐标值)
        int pendingTranslationY = (int) (child.getTranslationY() - tempDy);
        if (pendingTranslationY <= 0 && pendingTranslationY >= currentOffsetRange) {
            return true;//可以滚动
        }

        return false;
    }

    //当前的偏移量的范围 移动范围i额  newspager走了多少, != 列表向上移动的距离,下面滚动的快,上面滚动的慢
    private int currentOffsetRange = -90;//定义的是坐标

    /**
     * 按理说 抬起手的时候调用,但是newpaget没有触摸,,
     * @param parent
     * @param child
     * @param ev
     * @return
     */
//    @Override
//    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
//
//        return super.onTouchEvent(parent, child, ev);
//    }

    /**
     * 截断事件
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            handleActionUp(child);
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }
    //在头部没有完全不和的时候,要组织底部列表进入Fling状态
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return !isClosed();//返回true  类似:对事件的拦截
    }

    private void handleActionUp(View child) {
        myRunable = new MyRunable(child);
        if (child.getTranslationY() < currentOffsetRange / 2f) {

            myRunable.scrollToClose();
        } else {
            myRunable.scrollToOpen();

        }
    }



    public void open() {
        myRunable = new MyRunable(child);
        myRunable.scrollToOpen();

    }

    public boolean isClosed() {
        return child.getTranslationY() == currentOffsetRange;
    }

    private class MyRunable implements Runnable {
        private View child;

        public MyRunable(View child) {
            this.child = child;
        }

        public void scrollToClose() {
            int startY = (int) child.getTranslationY();
            int dY = currentOffsetRange - startY;
            s.startScroll(0, startY, 0, dY);
            start();
        }

        public void scrollToOpen() {
            int startY = (int) child.getTranslationY();
            s.startScroll(0, startY, 0, -startY);
            start();
        }

        private void start() {
            if (s.computeScrollOffset()) {
                //判断滚动有没有结束,用于获取新的坐标点
                child.postDelayed(this, 30);
            }

        }

        @Override
        public void run() {
            //computeScrollOffset
            if (s.computeScrollOffset()) {
                child.setTranslationY(s.getCurrY());
                child.postDelayed(this, 30);
            }
        }
    }
}
