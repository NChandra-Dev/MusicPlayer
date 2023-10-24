package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Size;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicMethod extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener {




    static MediaPlayer mediaPlayer;
    private Context context;

    ImageButton play_pauseButton, replay_button;
    SeekBar seekBar;
    Bitmap thumbnail;

    ImageButton albumArt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_screen);


        play_pauseButton = (ImageButton) findViewById(R.id.play_pause_button);
        replay_button = (ImageButton) findViewById(R.id.replay_button);
        albumArt = (ImageButton) findViewById(R.id.albumArt);
        TextView textView = findViewById(R.id.title_textview);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        // Register the onClick listener with the implementation above
        play_pauseButton.setOnClickListener(this);
        replay_button.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);


        new Timer().scheduleAtFixedRate(new TimerTask() {
                                            @Override
                                            public void run() {
                                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                            }
                                        }
                , 0, 900);



        // taking the values of the song information by using intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String songName = intent.getStringExtra("SongName");
        textView.setText(songName);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setSelected(true);


        Uri contentUri = intent.getParcelableExtra("AlbumArtUri");
        // Loading thumbnail of the specific uri
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            try {
                thumbnail = getApplicationContext().getContentResolver().loadThumbnail(
                        contentUri, new Size(640, 480), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        albumArt.setImageBitmap(thumbnail);

    }


    public MusicMethod(Context context) {
        this.context = context;
    }

    public MusicMethod() {}

    public void playMusic(Uri uri) {
        //Music music = new Music();
        //textView = (TextView) findViewById(R.id.title_textview);
        //textView.setText(music.getName());


        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(MainActivity.getContext(), uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.prepareAsync();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }
    @Override
    public void onClick (View view){
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know
        // So we will make


        if (view.getId()== R.id.play_pause_button) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                play_pauseButton.setImageResource(R.drawable.baseline_play_circle_32);
            }

            else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                play_pauseButton.setImageResource(R.drawable.baseline_pause_circle_32);
            }
        }

        if (view.getId()== R.id.replay_button){
            mediaPlayer.reset();
            mediaPlayer.start();

        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){mediaPlayer.seekTo(progress);}
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}

