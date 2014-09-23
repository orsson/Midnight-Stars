package com.example.midnight;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.SystemClock;

public class Scene {

    private Paint backgroundPaint;
    private Paint outerCirclePaint;
    private Paint circlePaint;

    // animation specific variables
    private float outerCircleRadius;

   
    public int upadateCount;
    int r;
    
    public Scene() {

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xff8aa8a0);

        outerCirclePaint = new Paint();
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setColor(0xff5e736d);
        outerCirclePaint.setStyle(Style.STROKE);
        outerCirclePaint.setStrokeWidth(3.0f);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(0xffa2bd3a);
        circlePaint.setStyle(Style.FILL);
        upadateCount=0;
        r=0;
    }

    public synchronized void updateSize(int width, int height) 
    {
        update();  
    }

    public synchronized void update() {

        if(  upadateCount>=20)
        upadateCount++;
	
        for(int i=0;i<30;i++)
        {
        	
        if(Wallpaper.fall[i]>90)
        {
        	
        if(i%2==0)
        {
        Wallpaper.touchX[i]+=18;
        Wallpaper.touchY[i]+=11;
        }
        else
        {
        Wallpaper.touchX[i]+=9;
        Wallpaper.touchY[i]+=18;	
        }
        }
        	
        if(Wallpaper.fall[i]>190)
        {
        Wallpaper.touchX[i]=-200;
        Wallpaper.touchY[i]=-200;
        }
        	
        Wallpaper.fall[i]++;
        }
   
     
    }

    public synchronized static void draw(Canvas canvas) 
    {
    	
     canvas.drawBitmap(Wallpaper.backgroundImage2, Wallpaper.XPIXELS, 0, null);

     for(int i=0;i<30;i++)
      {
      canvas.drawBitmap(Wallpaper.image1, Wallpaper.touchX[i]  , Wallpaper.touchY[i] , null);
      }
                 
    }
    

}
