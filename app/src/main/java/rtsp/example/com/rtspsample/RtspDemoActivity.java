package rtsp.example.com.rtspsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by ramya on 20/6/18.
 */

public class RtspDemoActivity extends Activity {
    VideoView simpleVideoView;
    MediaController mediaControls;
    private static ProgressDialog progressDialog;
    String rtsplURL ="rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtsp_video_layout);
        simpleVideoView = (VideoView) findViewById(R.id.rtspVideoView);
        progressDialog = ProgressDialog.show(RtspDemoActivity.this, "", "Buffering video...", true);
        progressDialog.setCancelable(true);
        playRtspVideo();

    }
    private void playRtspVideo() {
        if (mediaControls == null) {
            mediaControls = new MediaController(RtspDemoActivity.this);
            mediaControls.setAnchorView(simpleVideoView);
            mediaControls.isShowing();
        }
        simpleVideoView.setMediaController(mediaControls);
        simpleVideoView.setVideoURI(Uri.parse(rtsplURL));
        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                simpleVideoView.start();
            }
        });

        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Please check your internet Connection..Try Again", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
            }
        });
        simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

}
