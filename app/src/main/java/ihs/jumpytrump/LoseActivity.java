package ihs.jumpytrump;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoseActivity extends Activity implements View.OnClickListener{

    private SurfaceView surface;
    private TextView tview;
    private SurfaceHolder holder;
    private Button playAgain;
    private Button gotoStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        //Initalize variables
        surface = (SurfaceView) findViewById(R.id.loseSurface);
        holder = surface.getHolder();
        tview = (TextView) findViewById(R.id.loseText);
        playAgain = (Button) findViewById(R.id.playAgainButton);
        gotoStartButton = (Button) findViewById(R.id.gotoStartButton);

        playAgain.setOnClickListener(this);
        gotoStartButton.setOnClickListener(this);

        tview.append(" WITH ONLY " + getIntent().getIntExtra("score", 0) + " POINTS");
        clear();
    }

    /**
     * Literally just clears the canvas (no other purpose aside from looking nice
     * Maybe it'll be useful later idk
     */
    private void clear() {
        Canvas c = holder.lockCanvas();
        if (c == null) {
        } else {
            c.drawColor(Color.BLUE);
            holder.unlockCanvasAndPost(c);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == playAgain){
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
        if(v == gotoStartButton){
            Intent i = new Intent(this, StartActivity.class);
            startActivity(i);

        }
    }
}
