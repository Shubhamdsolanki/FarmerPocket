package com.company.farmerpocket.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by GHY on 2016/3/15.
 */
public class MyLoadingView extends View {

    /**
     * 画笔
     */
    private Paint mPaintRed;
    private Paint mPaintGreen;
    private Paint mPaintBlue;
    private Paint mPaintOrange;
    private Paint mPaintPurple;
    private Paint mPaintGray;

    /**
     * 颜色色值
     */
    private String colorRed = "#d71345";
    private String colorGreen = "#7fb80e";
    private String colorBlue = "#2a5caa";
    private String colorOrange = "#f47920";
    private String colorPurple = "#8552a1";
    private String colorGray = "#999d9c";

    /**
     * 圆点半径
     */
    private static final float RADIUS_SMALL = 12f;//圆的半径
    private static final float RADIUS_BIG = 14f;//圆的半径
    /**
     * 两个圆之间的间隔
     */
    private static final float SPACE = 34f;

    /**
     * 动画执行时间
     */
    private long redDuration = 200;
    private long greenDuration = 400;
    private long blueDuration = 600;
    private long orangeDuration = 800;
    private long purpleDuration = 1000;
    private long grayDuration = 1200;

    /**
     * 圆点
     */
    private Point pointRed, pointGreen, pointBlue, pointOrange, pointPurple, pointGray;

    /**
     * 起点，中间点，结束点
     */
    float startLocationX, startLocationY;
    float centerLocationX, centerLocationY;
    float endLocationX, endLocationY;

    public MyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pointRed == null || pointGreen == null || pointBlue == null
                || pointOrange == null || pointPurple == null || pointGray == null) {
            initPoint();
            drawCircle(canvas);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initAnim(1);
                }
            },500);
        } else {
            drawCircle(canvas);
        }

    }

    /**
     * 根据number执行不同的动画
     *
     * @param number 动画执行到哪一过程的标识
     */
    private void initAnim(int number) {
        Point redStartPoint = null;
        Point redEndPoint = null;
        Point greenStartPoint = null;
        Point greenEndPoint = null;
        Point blueStartPoint = null;
        Point blueEndPoint = null;
        Point orangeStartPoint = null;
        Point orangeEndPoint = null;
        Point purpleStartPoint = null;
        Point purpleEndPoint = null;
        Point grayStartPoint = null;
        Point grayEndPoint = null;
        /**
         * 不同过程动画执行的起始点
         */
        switch (number) {
            case 1:
                redStartPoint = new Point(startLocationX, startLocationY);
                redEndPoint = new Point(centerLocationX, centerLocationY);
                greenStartPoint = new Point(startLocationX, startLocationY);
                greenEndPoint = new Point(centerLocationX, centerLocationY);
                blueStartPoint = new Point(startLocationX, startLocationY);
                blueEndPoint = new Point(centerLocationX, centerLocationY);
                orangeStartPoint = new Point(startLocationX, startLocationY);
                orangeEndPoint = new Point(centerLocationX, centerLocationY);
                purpleStartPoint = new Point(startLocationX, startLocationY);
                purpleEndPoint = new Point(centerLocationX, centerLocationY);
                grayStartPoint = new Point(startLocationX, startLocationY);
                grayEndPoint = new Point(centerLocationX, centerLocationY);
                break;
            case 2:
                redStartPoint = new Point(centerLocationX, centerLocationY);
                redEndPoint = new Point(endLocationX, endLocationY);
                greenStartPoint = new Point(centerLocationX, centerLocationY);
                greenEndPoint = new Point(endLocationX, endLocationY);
                blueStartPoint = new Point(centerLocationX, centerLocationY);
                blueEndPoint = new Point(endLocationX, endLocationY);
                orangeStartPoint = new Point(centerLocationX, centerLocationY);
                orangeEndPoint = new Point(endLocationX, endLocationY);
                purpleStartPoint = new Point(centerLocationX, centerLocationY);
                purpleEndPoint = new Point(endLocationX, endLocationY);
                grayStartPoint = new Point(centerLocationX, centerLocationY);
                grayEndPoint = new Point(endLocationX, endLocationY);
                break;
            case 3:
                redStartPoint = new Point(endLocationX, endLocationY);
                redEndPoint = new Point(centerLocationX, centerLocationY);
                greenStartPoint = new Point(endLocationX, endLocationY);
                greenEndPoint = new Point(centerLocationX, centerLocationY);
                blueStartPoint = new Point(endLocationX, endLocationY);
                blueEndPoint = new Point(centerLocationX, centerLocationY);
                orangeStartPoint = new Point(endLocationX, endLocationY);
                orangeEndPoint = new Point(centerLocationX, centerLocationY);
                purpleStartPoint = new Point(endLocationX, endLocationY);
                purpleEndPoint = new Point(centerLocationX, centerLocationY);
                grayStartPoint = new Point(endLocationX, endLocationY);
                grayEndPoint = new Point(centerLocationX, centerLocationY);
                break;
            case 4:
                redStartPoint = new Point(centerLocationX, centerLocationY);
                redEndPoint = new Point(startLocationX, startLocationY);
                greenStartPoint = new Point(centerLocationX, centerLocationY);
                greenEndPoint = new Point(startLocationX, startLocationY);
                blueStartPoint = new Point(centerLocationX, centerLocationY);
                blueEndPoint = new Point(startLocationX, startLocationY);
                orangeStartPoint = new Point(centerLocationX, centerLocationY);
                orangeEndPoint = new Point(startLocationX, startLocationY);
                purpleStartPoint = new Point(centerLocationX, centerLocationY);
                purpleEndPoint = new Point(startLocationX, startLocationY);
                grayStartPoint = new Point(centerLocationX, centerLocationY);
                grayEndPoint = new Point(startLocationX, startLocationY);
                break;
            default:
                break;
        }

        if (redStartPoint == null)
            return;
        //开启动画
        startAnim(number, redStartPoint, redEndPoint, greenStartPoint, greenEndPoint, blueStartPoint, blueEndPoint, orangeStartPoint, orangeEndPoint, purpleStartPoint, purpleEndPoint, grayStartPoint, grayEndPoint);

    }

    /**
     * 开启动画
     *
     * @param number 动画执行到哪一过程的标识
     */
    private void startAnim(final int number, Point redStartPoint, Point redEndPoint, Point greenStartPoint, Point greenEndPoint, Point blueStartPoint, Point blueEndPoint, Point orangeStartPoint, Point orangeEndPoint, Point purpleStartPoint, Point purpleEndPoint, Point grayStartPoint, Point grayEndPoint) {
        ValueAnimator animRed = ValueAnimator.ofObject(new PointEvaluator(), redStartPoint, redEndPoint);
        animRed.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointRed = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animRed.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animRed.setDuration(redDuration);
        } else {
            animRed.setDuration(grayDuration);
        }
        animRed.start();

        ValueAnimator animGreen = ValueAnimator.ofObject(new PointEvaluator(), greenStartPoint, greenEndPoint);
        animGreen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointGreen = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animGreen.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animGreen.setDuration(greenDuration);
        } else {
            animGreen.setDuration(purpleDuration);
        }
        animGreen.start();

        ValueAnimator animBlue = ValueAnimator.ofObject(new PointEvaluator(), blueStartPoint, blueEndPoint);
        animBlue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointBlue = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animBlue.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animBlue.setDuration(blueDuration);
        } else {
            animBlue.setDuration(orangeDuration);
        }
        animBlue.start();

        ValueAnimator animOrange = ValueAnimator.ofObject(new PointEvaluator(), orangeStartPoint, orangeEndPoint);
        animOrange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointOrange = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animOrange.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animOrange.setDuration(orangeDuration);
        } else {
            animOrange.setDuration(blueDuration);
        }
        animOrange.start();

        ValueAnimator animPurple = ValueAnimator.ofObject(new PointEvaluator(), purpleStartPoint, purpleEndPoint);
        animPurple.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointPurple = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animPurple.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animPurple.setDuration(purpleDuration);
        } else {
            animPurple.setDuration(greenDuration);
        }
        animPurple.start();

        ValueAnimator animGray = ValueAnimator.ofObject(new PointEvaluator(), grayStartPoint, grayEndPoint);
        animGray.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointGray = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animGray.setInterpolator(new AccelerateDecelerateInterpolator());
        if (number == 1 || number == 2) {
            animGray.setDuration(grayDuration);
        } else {
            animGray.setDuration(redDuration);
        }
        animGray.start();

        /**
         * 动画执行监听
         */
        if (number == 1 || number == 2) {
            animGray.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    switch (number) {
                        case 1:
                            initAnim(2);
                            break;
                        case 2:
                            initAnim(3);
                            break;
                        default:
                            break;
                    }

                }
            });
        } else {
            animRed.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    switch (number) {
                        case 3:
                            initAnim(4);
                            break;
                        case 4:
                            initAnim(1);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }

    /**
     * 初始化圆点
     */
    private void initPoint() {
        //参考点，X轴屏幕的三分之一和三分之二，及屏幕正中。Y轴屏幕的一半
        startLocationX = getWidth() / 3;
        startLocationY = getHeight() / 2;
        centerLocationX = getWidth() / 2 + SPACE * 5 / 2;
        centerLocationY = getHeight() / 2;
        endLocationX = getWidth() / 3 * 2 + SPACE * 5;
        endLocationY = getHeight() / 2;
        pointRed = new Point(startLocationX, startLocationY);
        pointGreen = new Point(startLocationX, startLocationY);
        pointBlue = new Point(startLocationX, startLocationY);
        pointOrange = new Point(startLocationX, startLocationY);
        pointPurple = new Point(startLocationX, startLocationY);
        pointGray = new Point(startLocationX, startLocationY);
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        float x1 = pointRed.getX();
        float y1 = pointRed.getY();
        canvas.drawCircle(x1, y1, RADIUS_SMALL, mPaintRed);
        float x2 = pointGreen.getX() - SPACE;
        float y2 = pointGreen.getY();
        canvas.drawCircle(x2, y2, RADIUS_SMALL, mPaintGreen);
        float x3 = pointBlue.getX() - SPACE * 2;
        float y3 = pointBlue.getY();
        canvas.drawCircle(x3, y3, RADIUS_SMALL, mPaintBlue);
        float x4 = pointOrange.getX() - SPACE * 3;
        float y4 = pointOrange.getY();
        canvas.drawCircle(x4, y4, RADIUS_SMALL, mPaintOrange);
        float x5 = pointPurple.getX() - SPACE * 4;
        float y5 = pointPurple.getY();
        canvas.drawCircle(x5, y5, RADIUS_SMALL, mPaintPurple);
        float x6 = pointGray.getX() - SPACE * 5;
        float y6 = pointGray.getY();
        canvas.drawCircle(x6, y6, RADIUS_SMALL, mPaintGray);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRed.setColor(Color.parseColor(colorRed));

        mPaintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintGreen.setColor(Color.parseColor(colorGreen));

        mPaintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBlue.setColor(Color.parseColor(colorBlue));

        mPaintOrange = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOrange.setColor(Color.parseColor(colorOrange));

        mPaintPurple = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPurple.setColor(Color.parseColor(colorPurple));

        mPaintGray = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintGray.setColor(Color.parseColor(colorGray));
    }
}

/**
 * Point类，只有x和y两个变量用于记录坐标的位置，并提供了构造方法来设置坐标，以及get方法来获取坐标
 */
class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}

/**
 * 将startValue和endValue强转成Point对象，
 * 然后根据fraction来计算当前动画的x和y的值，最后组装到一个新的Point对象当中并返回
 */
class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
