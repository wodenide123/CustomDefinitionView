package com.xiong.test.customdefinitionview.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;

import com.xiong.test.customdefinitionview.R;
import com.xiong.test.customdefinitionview.utilts.SizeUtils;

public class ArcProgress extends View {
    Context context;
    int outColor= Color.BLUE;
    int inColor=Color.RED;
    float radius=15;
    int strokeWidth=10;
    private Paint inPaint;
    private Paint outPaint;
    private Paint textPaint;
    float maxValue=100;
    float currentValue=40;
    private String text="2000";

    public ArcProgress(Context context) {
        this(context,null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.arc_progress);
        outColor = typedArray.getColor(R.styleable.arc_progress_out_color, outColor);
        inColor = typedArray.getColor(R.styleable.arc_progress_out_color, inColor);
        radius=typedArray.getDimension(R.styleable.arc_progress_Radius, SizeUtils.sp2px(context,SizeUtils.sp2px(context,radius)));
        strokeWidth= (int) typedArray.getDimension(R.styleable.arc_progress_strokeWidth, SizeUtils.sp2px(context,strokeWidth));
        typedArray.recycle();
        inPaint = new Paint();
        inPaint.setAntiAlias(true);
        inPaint.setColor(inColor);
        inPaint.setStrokeCap(Paint.Cap.ROUND);
        inPaint.setStrokeWidth(strokeWidth);
        inPaint.setStyle(Paint.Style.STROKE);

        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setColor(outColor);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setStrokeWidth(strokeWidth);
        outPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(outColor);
        textPaint.setTextSize(SizeUtils.sp2px(context,16));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.AT_MOST){
            width= (int) (2*radius);
        }
        if(heightMode==MeasureSpec.AT_MOST){
            height= (int) (2*radius);
        }
        radius=(width<=height?width:height)/2;
        setMeasuredDimension(width<=height?width:height,width<=height?width:height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF=new RectF(strokeWidth/2,strokeWidth/2,getWidth()-strokeWidth/2,getHeight()-strokeWidth/2);
        canvas.drawArc(rectF,135,270,false,inPaint);
        canvas.drawArc(rectF,135, (float)(currentValue/maxValue)*270,false,outPaint);

        //计算的宽高 与 字体的长度有关 与字体的大小有关  用画笔测量
        Rect bounds=new Rect();
        //测量获取文本的Rect
        textPaint.getTextBounds(text,0,text.length(),bounds);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int baseline = (int) ((getHeight() - fontMetrics.bottom - fontMetrics.top) / 2);
        Log.e("123",radius-(float)(bounds.width()/2)+"");
        canvas.drawText(text,radius,baseline,textPaint);
    }
}
