package dicoding.veri.scrollview;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int REQUEST_NUMBER = 999;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayerView youTubePlayerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY,this);

        youTubePlayerView2 = (YouTubePlayerView) findViewById(R.id.youtube_player2);
        youTubePlayerView2.initialize(Config.YOUTUBE_API_KEY2,this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            youTubePlayer.cueVideo(Config.YOUTUBE_ID);
            youTubePlayer.cueVideo(Config.YOUTUBE_ID2);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_NUMBER).show();
        }else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)", youTubeInitializationResult.toString()
            );
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_NUMBER){
            youTubePlayerView.initialize(Config.YOUTUBE_API_KEY,this);
            youTubePlayerView2.initialize(Config.YOUTUBE_API_KEY2,this);
        }
    }
}
