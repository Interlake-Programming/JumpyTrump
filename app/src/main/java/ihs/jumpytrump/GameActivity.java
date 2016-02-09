package ihs.jumpytrump;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends Activity {

    private SurfaceView surface;
    private SurfaceHolder holder;
    private GameManager gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        surface = (SurfaceView) findViewById(R.id.gameCanvas);
        holder = surface.getHolder();
        gm = new GameManager(holder, surface, this);
        gm.startGame();
    }
}
