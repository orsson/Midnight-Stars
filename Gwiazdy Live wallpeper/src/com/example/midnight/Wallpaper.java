package com.example.midnight;

import com.example.livewallpaper.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Wallpaper extends WallpaperService {

    private static final String TAG = "Wallpaper";
	public static Bitmap backgroundImage;
	public static Bitmap image1;
    public static int XPIXELS;
    public static Bitmap backgroundImage2;
    
    public static int[] touchX;
    public static int[] touchY;
    public static int[] fall;
    public static int count;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }

    class WallpaperEngine extends Engine {

        private static final String TAG = "WallpaperEngine";

        private AnimationThread animationThread;
        private Scene scene;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            Log.d(TAG, "onCreate");

            // create the scene
            scene = new Scene();
            
            // start animation thread; thread starts paused
            // will run onVisibilityChanged
            animationThread = new AnimationThread(surfaceHolder, scene);
            animationThread.start();

            image1 = BitmapFactory.decodeResource(getResources(),R.drawable.star);
            backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.backgroundstars);
            XPIXELS=100;
              
            {
  
            
            Context mContext = getBaseContext();
            DisplayMetrics displayMetrics = new DisplayMetrics(); 
            displayMetrics = mContext.getResources().getDisplayMetrics();
            int mScreenWidth = displayMetrics.widthPixels;
            int mSreenHeight = displayMetrics.heightPixels;
            
            float xScale = (float) mScreenWidth/ backgroundImage.getWidth();
            float yScale = (float) mSreenHeight/ backgroundImage.getHeight();
            float scale = Math.max(xScale, yScale); //selects the larger size to grow the images by

        //    scale = (float) (scale*1.1); //this allows for ensuring the image covers the whole screen.

      //   float   scaledWidth = scale * backgroundImage.getWidth();
            
         float   scaledWidth = (float) (backgroundImage.getWidth() * (0.82));
         float   scaledHeight = scale * backgroundImage.getHeight();

         backgroundImage2 = Bitmap.createScaledBitmap(backgroundImage, (int)scaledWidth, (int)scaledHeight, true);     
            }
            
          
            touchX=new int[30];
            touchY=new int[30];
            fall=new int[30];
            
            for(int i=0;i<30;i++)
            {
            touchX[i]=-200;
            touchY[i]=-200;
            fall[i]=0;
            }
  
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");

            animationThread.stopThread();
            joinThread(animationThread);
            animationThread = null;

            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "onVisibilityChanged: " + (visible ? "visible" : "invisible"));
            if (visible) {
                animationThread.resumeThread();
            } else {
                animationThread.pauseThread();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d(TAG, "onSurfaceChanged: width: " + width + ", height: " + height);

            scene.updateSize(width, height);

        }
        
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) 
        {
        	XPIXELS=xPixels;

        }

        @Override
        public void onTouchEvent(MotionEvent event) {
          
        	if(count==30)
        		count=0;
        	
			if (event.getAction() == MotionEvent.ACTION_UP) {
	            touchX[count]=(int) event.getX();
	            touchY[count]=(int) event.getY();
	            fall[count]=1;
            }
            
            super.onTouchEvent(event);
            count++;
        }

        
        private void joinThread(Thread thread) {
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

    }

}
