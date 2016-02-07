package ihs.jumpytrump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class GameManager {

    volatile int status;
    private SurfaceHolder holder;
    private Bitmap trump;
    private Bitmap pipe;
    private Bitmap uPipe;
    private ArrayList<Pipe> pipes;
    private Context c;

    private int tHeight = 200;
    private int tX = 10;

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

        //Rotate pipe image so that another pipe image is stored in memory
        Matrix m = new Matrix();
        m.postRotate(180);
        uPipe = Bitmap.createBitmap(pipe, 0, 0, pipe.getWidth(), pipe.getHeight(), m, true);
    }

    public void startGame(){
        status = 1;
        Thread g = new Thread(new Loop());
        g.start();
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            //Not sure what to do here
        } else {
            //Draw trump face
            canvas.drawBitmap(trump, tX, tHeight, null);

            //Debugging thing to see if face moves
            tX++;
            //Draw pipes
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
