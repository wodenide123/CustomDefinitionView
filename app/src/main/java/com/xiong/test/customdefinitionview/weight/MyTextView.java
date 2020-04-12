package com.xiong.test.customdefinitionview.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xiong.test.customdefinitionview.R;
import com.xiong.test.customdefinitionview.utilts.SizeUtils;

/**
  * @type  View
  * @explain 学习自定义view的第一个demo，手写TextView
  * @author xiongchang
  * @creat 2020/4/4
  */

public class MyTextView extends View{
    private Context context;
    private int myTextSize=15; //这个15是像素
    private String myText;
    private int myTextColor= Color.BLACK;
    private Paint mPaint;
    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        //获取自定义属性
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.my_textView);
        myText=typedArray.getString(R.styleable.my_textView_my_text);
        myTextColor=typedArray.getColor(R.styleable.my_textView_my_textColor,myTextColor);
        myTextSize=typedArray.getDimensionPixelSize(R.styleable.my_textView_my_textSize,sp2px(myTextSize));
        //回收
        typedArray.recycle();
        mPaint=new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(myTextSize);
        mPaint.setColor(myTextColor);
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                getResources().getDisplayMetrics());
    }
    /**
     * 自定义View的测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        //1.确定的值，这个时候不需要计算，给的多少就是多少
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        //2.给的是wrap_content，需要计算
        //EXACTLY定值
        if(widthMode==MeasureSpec.AT_MOST){
//            计算的宽高 与 字体的长度有关 与字体的大小有关  用画笔测量
            Rect bounds = new Rect();
//            测量获取文本的Rect
//            mPaint.getTextBounds(myText,0,myText.length(),bounds);
//            mPaint.measureText(myText,0,myText.length()
//            两种测出来的宽度不一样
            int textWidth = (int) mPaint.measureText(myText, 0, myText.length());
            width = textWidth + getPaddingLeft() + getPaddingRight();

        }
        if(heightMode==MeasureSpec.AT_MOST){
            //计算的宽高 与 字体的长度有关 与字体的大小有关  用画笔测量
            Rect bounds=new Rect();
            //测量获取文本的Rect
            mPaint.getTextBounds(myText,0,myText.length(),bounds);
            height=bounds.height()+getPaddingBottom()+getPaddingTop();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        画文字  x开始位置  y基线（baseLine） paint
//        canvas.drawText();
//        画弧
//        canvas.drawArc();
//        画圆
//        canvas.drawCircle();
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();//获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
        int baseline = (int) ((getHeight() - fontMetrics.bottom - fontMetrics.top) / 2);//计算出在每格index区域，竖直居中的baseLine值
        canvas.drawText(myText,getPaddingLeft(),baseline,mPaint);
    }
}
