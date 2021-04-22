package com.example.enigma;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class DrawCanvas extends SurfaceView implements Runnable{
    private int x;
    public int dx = 0;
    private int y;
    public Canvas canvas;
    private Context activity;
    private SurfaceHolder holder;
    private Paint circlePaint;
    private Paint squarePaint;
    private int squareX = 500;
    private int squareY = 500;
    private boolean tapIsInSquare = false;

    public DrawCanvas(int x, int y, Context context) {
        super(context);
        this.x = x;
        this.y = y;
        this.activity = context;
        holder = getHolder();
        setBackgroundColor(Color.TRANSPARENT);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.RED);

        squarePaint= new Paint();
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setColor(Color.BLUE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawColor(Color.CYAN);
        canvas.drawCircle(x, y, 60, circlePaint);
        canvas.drawCircle(1000, y + 40, 60, circlePaint);
        canvas.drawRect(squareX - 100, squareY - 100, squareX + 100, squareY + 100, squarePaint);

        double distance = Math.sqrt( 1600 + Math.pow((x - 1000), 2));
        if(distance < 120)
            circlePaint.setColor(Color.GREEN);
    }



    public void run() {
        while (x < 1000) {
            try {
                if (holder.getSurface().isValid()) {
                    canvas = holder.lockCanvas();
                    x += 10;
                    postInvalidateOnAnimation(x, y, x + 1, y + 1);
                }
            }
            catch (Exception e) {
                Log.e(TAG, "Exception: ", e);
            }
            finally {
                holder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ignored) { }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case(MotionEvent.ACTION_DOWN):
                Rect square = new Rect(squareX - 100, squareY - 100, squareX + 100, squareY + 100);
                tapIsInSquare = square.contains((int)event.getX(), (int)event.getY());
                break;
            case(MotionEvent.ACTION_UP):
                tapIsInSquare = false;
                break;
            case(MotionEvent.ACTION_MOVE):
                if(tapIsInSquare) {
                    squareX = (int)event.getX();
                    squareY = (int)event.getY();
                    postInvalidateOnAnimation();
                }
                break;
        }
        return true;
    }

    public void raiseToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}