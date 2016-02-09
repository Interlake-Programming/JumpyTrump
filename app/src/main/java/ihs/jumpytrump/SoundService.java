package ihs.jumpytrump;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class SoundService extends Service {
    private MediaPlayer player;

    @Override
    public void onCreate(){
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.undertalemusic);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
    }
    public SoundService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
