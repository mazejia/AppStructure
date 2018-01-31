package test.mzj.com.appstructureproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.mzj.com.appstructureproject.R;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class MiddleTextView extends View {
    /**
     * 基本属性
     */
    private String mText = "Loading";
    private int mTextColor;
    private int mTextSize;

    /**
     * 画笔,文本绘制范围
     */
    private Rect mBound;
    private Paint mPaint;


    public MiddleTextView(Context context) {
        this(context,null);
    }

    public MiddleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /*
         * 获取基本属性
         */
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MiddleTextView);
        mText = a.getString(R.styleable.MiddleTextView_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.MiddleTextView_textSize, 20);
        mTextColor = a.getColor(R.styleable.MiddleTextView_textColor, Color.BLACK);
        a.recycle();

        /*
         * 初始化画笔
         */
        mBound = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mTextColor);


        /*
         * 控件宽度/2 - 文字宽度/2
         */
        float startX = getWidth() / 2 - mBound.width() / 2;


        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();

        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top)/ 2;


        // 绘制文字
        canvas.drawText(mText, startX, startY, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);

        // 中线,做对比
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
    }

    /**
     * 计算控件宽高
     *
     * @param attr 属性 [0宽,1高]
     *
     * @param oldMeasure
     * @author Ruffian
     */
    public int onMeasureR(int attr, int oldMeasure) {

        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                float value;
                if (attr == 0) {
                    value = mPaint.measureText(mText);
                    // 控件的宽度  + getPaddingLeft() +  getPaddingRight()
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());
                } else if (attr == 1) {
                     Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                     value = Math.abs((fontMetrics.bottom - fontMetrics.top));
                    // 控件的高度  + getPaddingTop() +  getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());
                }
                break;
        }
        return newSize;
    }
}
