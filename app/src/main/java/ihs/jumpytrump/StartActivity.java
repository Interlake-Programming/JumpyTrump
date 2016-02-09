package ihs.jumpytrump;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class StartActivity extends Activity implements View.OnClickListener{

    private SurfaceHolder holder;
    private SurfaceView surfaceRef;
    private Bitmap trump;
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        trump = BitmapFactory.decodeResource(getResources(), R.drawable.trump);
        trump = Bitmap.createScaledBitmap(trump, 200, 200, true);
        surfaceRef = (SurfaceView) findViewById(R.id.startCanvas);
        holder = surfaceRef.getHolder();
        startGame = (Button) findViewById(R.id.startGameButton);

        startGame.setOnClickListener(this);

        Intent music = new Intent(this, SoundService.class);
        startService(music);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == startGame){
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
    }
}
