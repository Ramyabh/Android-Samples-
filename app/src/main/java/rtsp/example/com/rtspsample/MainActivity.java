package rtsp.example.com.rtspsample;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnClickListener {

    String rtspUrl = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";
    Button playButton;
    VideoView videoView;
    MediaPlayer mp;
    int currentSeeklength =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) this.findViewById(R.id.rtspVideo);
        playButton = (Button) this.findViewById(R.id.playButton);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                RtspStream(rtspUrl);
                break;
        }
    }

    private void RtspStream(String rtspUrl) {
        videoView.setVideoURI(Uri.parse(rtspUrl));
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                    mp= mediaPlayer;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(videoView != null) {
            videoView.seekTo(currentSeeklength);
        }
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentSeeklength = videoView.getCurrentPosition();
        if (videoView.isPlaying())
            videoView.pause();
    }
}