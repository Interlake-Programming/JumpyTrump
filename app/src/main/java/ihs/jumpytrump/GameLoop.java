package ihs.jumpytrump;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Chen on 2/4/2016.
 */
public class GameLoop {
    SurfaceView view;
    SurfaceHolder holder;

    private class Pipe{
        int x; //Refers to how far it is from left side of screen
        int yTop; //Refers to how high the lower end is from top

    }
    public GameLoop(SurfaceView view, SurfaceHolder holder){
        this.view = view;
        this.holder = holder;
    }

    public void render(){

    }
}
