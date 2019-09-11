package com.sict.soft.smarthome.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ImageBarnnerViewGroup extends ViewGroup{

    private int children;//子视图的个数
    private int childwidth;//子视图的宽度
    private int childheight;//子视图的高度

    private int x;//移动前的横坐标
    private int index = 0;//图片的索引

    private Scroller scroller;

    //实现单击事件的获取
    private boolean isClick;

    private ImageBarnnerLister lister;

    public ImageBarnnerLister getLister(){
        return lister;
    }

    public void setLister(ImageBarnnerLister lister){
        this.lister = lister;
    }

    public interface ImageBarnnerLister{
        void clickImageIndex(int pos);
    }

    private ImageBarnnerViewGroupLisnner barnnerViewGroupLisnner;

    public ImageBarnnerViewGroupLisnner getBarnnerViewGroupLisnner(){
        return barnnerViewGroupLisnner;
    }

    public void setBarnnerViewGroupLisnner(ImageBarnnerViewGroupLisnner barnnerViewGroupLisnner){
        this.barnnerViewGroupLisnner = barnnerViewGroupLisnner;
    }

    //实现自动轮播
    private boolean isAuto = true;
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler autoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(++index >= children){
                        index = 0;
                    }
                    scrollTo(index * childwidth,0);
                    barnnerViewGroupLisnner.selectImage(index);
                    break;
            }
        }
    };

    private void startAuto(){
        isAuto = true;
    }

    private void stopAuto(){
        isAuto = false;
    }


    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj(){
        scroller = new Scroller(getContext());
        task = new TimerTask() {
            @Override
            public void run() {
                if(isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task,100,3000);
    }

    public void computeScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

       children = getChildCount();

        if(0 == children){
            setMeasuredDimension(0,0);
        }else{
            measureChildren(widthMeasureSpec,heightMeasureSpec);

            View view = getChildAt(0);
            childwidth = view.getMeasuredWidth();
            childheight = view.getMeasuredHeight();
            int width = view.getMeasuredWidth() * children;

            setMeasuredDimension(width,childheight);
        }
    }
    //事件拦截方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    //实现手动轮播
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                isClick = true;
                x = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int)event.getX();
                int distance = moveX - x;
                scrollBy(-distance,0);
                x = moveX;
                isClick = false;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + childwidth / 2) / childwidth;
                if(index < 0){
                    index = 0;
                }else if(index > children - 1){
                    index = children - 1;
                }

                if(isClick){//单击事件
                    lister.clickImageIndex(index);
                }else{
                    int dx = index * childwidth - scrollX;
                    scroller.startScroll(scrollX,0,dx,0);
                    postInvalidate();
                    barnnerViewGroupLisnner.selectImage(index);
                }
                startAuto();

//                scrollTo(index * childwidth,0);
                break;
            default:

                break;
        }
        return true;
    }

    //布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int LeftMargin = 0;
            for(int i=0;i<children;i++){
                View view = getChildAt(i);

                view.layout(LeftMargin,0,LeftMargin+childwidth,childheight);
                LeftMargin += childwidth;
            }
        }
    }

    public interface ImageBarnnerViewGroupLisnner{
        void selectImage(int index);
    }
}
