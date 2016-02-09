package ihs.jumpytrump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class GameManager {

    volatile int status;

    private SurfaceView surface;
    private SurfaceHolder holder;
    private Bitmap trump;
    private Bitmap pipe;
    private Bitmap uPipe;
    private ArrayList<Pipe> pipes;
    private Context c;

    private int tHeight = 200;
    private int tX = 10;

    private long time;

    private class Pipe {
        public float x; //Refers to how far it is from left side of screen
        public float y; //Refers to how high the lower end is from top
    }

    public GameManager(SurfaceHolder holder, SurfaceView view, Context c) {
        this.c = c;
        this.surface = view;
        status = 0;
        this.holder = holder;
        trump = BitmapFactory.decodeResource(c.getResources(), R.drawable.trump);
        //Rescale trump picture down
        trump = Bitmap.createScaledBitmap(trump, 100, 100, false);
        pipe = BitmapFactory.decodeResource(c.getResources(), R.drawable.pipe);
        pipes = new ArrayList<Pipe>();

        //Rotate pipe image so that another pipe image is stored in memory
        Matrix m = new Matrix();
        m.postRotate(180);
        uPipe = Bitmap.createBitmap(pipe, 0, 0, pipe.getWidth(), pipe.getHeight(), m, true);

        surface.setOnTouchListener(new TouchListener());
    }

    private class TouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(v == surface){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    jump();
                }
            }
            return false;
        }
    }

    public void startGame(){
        status = 1;
        Thread g = new Thread(new Loop());
        time = System.currentTimeMillis();
        g.start();
    }

    private int pipeWidth = 500;
    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            //Not sure what to do here
        } else {
            //Clear canvas
            canvas.drawColor(Color.BLACK);
            //Ideally instead of clearing the canvas, the background will be drawn here too

            //Draw trump face
            canvas.drawBitmap(trump, tX, tHeight, null);

            //Draw pipes
            for (Pipe p : pipes) {

                //Paint is for the future (currently unnecessary)
                Paint paint = new Paint();

                //Draw bottom pipe
                canvas.drawBitmap(pipe, p.x, p.y, paint);

                //Draw top pipe
                canvas.drawBitmap(uPipe, p.x, (p.y - pipeWidth - uPipe.getHeight()), paint);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void startAnimation(){
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i = 3; i > 0; i--){
            Canvas canvas = holder.lockCanvas();
            if(canvas == null){
                //IDK WHAT TO DO HERE
            }
            else{
                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                canvas.drawText(i + "", canvas.getWidth() / 2, canvas.getHeight()/2 , paint);
                holder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1500);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                    Log.d("d", "WHO INTERRUPTED MY ETERNAL SLUMBER (LINE: " + 130 + "ish");
                }
            }
        }
    }

    private class Loop implements Runnable {

        @Override
        public void run() {
            startAnimation();
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


    private long lastSpawnedTime = 0;
    private long interval = 1000;
    private int fallAmount = 0;
    private int fallChange = 2;
    private int pipeMoveSpeed = 20;
    private int jumpSpeed = 40;
    private void jump(){
        if(tHeight > surface.getHeight()){
            //Ded
        }
        if(tHeight < 0){
            return;
        }
        fallAmount = -jumpSpeed;
    }
    public void update(){
        if(System.currentTimeMillis() - lastSpawnedTime > interval){
            //Spawn pipe
            Pipe buffer = new Pipe();
            buffer.x = surface.getWidth();
            buffer.y = (int) (Math.random() * surface.getHeight() * 3.0 / 4.0 + surface.getHeight() / 8.0);
            pipes.add(buffer);
            lastSpawnedTime = System.currentTimeMillis();
        }
        for(int i = 0; i < pipes.size(); i++){
            pipes.get(i).x -= pipeMoveSpeed;
            if(pipes.get(i).x < -100){
                pipes.remove(i);
                i--;
            }
        }
        tHeight += fallAmount;
        fallAmount += fallChange;
        if(tHeight > surface.getHeight() + 10){
            status = -1;
        }
    }
}
