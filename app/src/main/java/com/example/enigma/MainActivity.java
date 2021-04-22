package com.example.enigma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout appLayout;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appLayout = findViewById(R.id.frmMove); //Height: 1848 Width: 1080

        Bitmap airplane = BitmapFactory.decodeResource(getResources(), R.drawable.aeroplane);
        airplane = Bitmap.createScaledBitmap(airplane, 200, 200, false);
        final DrawCanvas myCanvasView = new DrawCanvas(60, 60, this);

        final Button button = new Button(this);
        ViewTreeObserver vto = button.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button.setX((screenWidth - button.getWidth()) / 2);
                button.setY(screenHeight - button.getHeight());
            }
        });
        button.setText("Button");

        final Thread thread = new Thread(myCanvasView);


        ;
        View.OnClickListener buttonMethod = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
            }
        };
        button.setOnClickListener(buttonMethod);


        appLayout.addView(button);
        appLayout.addView(myCanvasView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
    }
}