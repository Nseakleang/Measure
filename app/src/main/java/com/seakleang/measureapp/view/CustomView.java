package com.seakleang.measureapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.seakleang.measureapp.MainActivity;
import com.seakleang.measureapp.R;

public class CustomView extends View {

    private Paint mStartCircle, mEndCircle;
    private Paint mStartPoint, mEndPoint;

    private float mStartCircleX, mStartCircleY, mEndCircleX, mEndCircleY;

    private float mStartCircleRadius = 50f;
    private float mEndCircleRadius = 50f;

    private float mStartPointRadius = 15f;
    private float mEndPointRadius = 15f;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @androidx.annotation.Nullable @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @androidx.annotation.Nullable @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @androidx.annotation.Nullable @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet){
        mStartCircle = new Paint();
        mEndCircle = new Paint();

        mStartCircle.setColor(getResources().getColor(R.color.colorAccent));
        mStartCircle.setStrokeWidth(1f);
        mStartCircle.setStyle(Paint.Style.STROKE);

        mEndCircle.setColor(getResources().getColor(R.color.colorPrimary));
        mEndCircle.setStrokeWidth(1f);
        mEndCircle.setStyle(Paint.Style.STROKE);

        mStartPoint = new Paint();
        mStartPoint.setColor(getResources().getColor(R.color.colorAccent));
        mEndPoint = new Paint();
        mEndPoint.setColor(getResources().getColor(R.color.colorPrimary));

        if (attributeSet == null) return;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mStartCircleX == 0 || mStartCircleY == 0) {
            mStartCircleX = getWidth() / 2 - 100;
            mStartCircleY = getHeight() / 2;
        }if (mEndCircleX == 0 || mEndCircleY == 0) {
            mEndCircleX = getWidth() / 2 + 100;
            mEndCircleY = getHeight() / 2;
        }

        Paint pathPain = new Paint();
        pathPain.setColor(Color.parseColor("#00cca0"));
        pathPain.setAntiAlias(true);
        pathPain.setStrokeWidth(5f);
        pathPain.setStyle(Paint.Style.STROKE);

        canvas.drawLine(mStartCircleX, mStartCircleY, mEndCircleX, mEndCircleY, pathPain);

        canvas.drawCircle(mStartCircleX, mStartCircleY, mStartCircleRadius, mStartCircle);
        canvas.drawCircle(mStartCircleX, mStartCircleY, mStartPointRadius, mStartPoint);

        canvas.drawCircle(mEndCircleX, mEndCircleY, mEndCircleRadius, mEndCircle);
        canvas.drawCircle(mEndCircleX, mEndCircleY, mEndPointRadius, mEndPoint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                double distanceStart = Math.sqrt((Math.pow(mStartCircleX - event.getX(),2)
                        +Math.pow(mStartCircleY - event.getY(),2)));
                double distanceEnd = Math.sqrt((Math.pow(mEndCircleX - event.getX(),2)
                        +Math.pow(mEndCircleY - event.getY(),2)));
                if (distanceStart <= mStartCircleRadius) {
                    mStartCircleX = event.getX();
                    mStartCircleY = event.getY();
                }
                else if (distanceEnd <= mEndCircleRadius) {
                    mEndCircleX = event.getX();
                    mEndCircleY = event.getY();
                }
                postInvalidate();
            case MotionEvent.ACTION_CANCEL:
                return true;
        }

        return super.onTouchEvent(event);
    }

    public double getDefaultDistance() {
        Double distance = Math.sqrt((Math.pow(mStartCircleX - mEndCircleX,2)
                +Math.pow(mStartCircleY - mEndCircleY,2)));
        return distance;
    }

    public double getDistance() {
        Double distance = Math.sqrt((Math.pow(mStartCircleX - mEndCircleX,2)
                +Math.pow(mStartCircleY - mEndCircleY,2)));
        return distance;
    }
}
