package ihs.jumpytrump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class GameManager {

    volatile int status;
    private SurfaceHolder holder;
    private Bitmap trump;
    private Bitmap pipe;
    private ArrayList<Pipe> pipes;
    private Context c;

    private class Pipe {
        public float x; //Refers to how far it is from left side of screen
        public float y; //Refers to how high the lower end is from top
    }

    public GameManager(SurfaceHolder holder, Context c) {
        this.c = c;
        status = 0;
        this.holder = holder;
        trump = BitmapFactory.decodeResource(c.getResources(), R.drawable.trump);
        pipe = BitmapFactory.decodeResource(c.getResources(), R.drawable.pipe);
        pipes = new ArrayList<Pipe>();
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            //Not sure what to do here
        } else {
            for (Pipe p : pipes) {
                canvas.drawBitmap(pipe, p.x, p.y, null);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private class Loop implements Runnable {

        @Override
        public void run() {
            while(status > 0) {
                    render();
                    detectCollisions();
                    update();
            }
            endGame();
        }
    }

    public void endGame(){
        //Can add animations here later
        Intent intent = new Intent(c , LoseActivity.class);
        intent.putExtra("score", -status);
        c.startActivity(intent);
    }

    public void detectCollisions(){
    }

    public void update(){
    }
}
