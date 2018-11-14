package com.example.edu.mediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button play;
    Button stop;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.buttonPlay);

        stop = (Button) findViewById(R.id.buttonStop);
        mediaPlayer = MediaPlayer.create(this, R.raw.meetingyou);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlay:
                mediaPlayer.start();
                play.setEnabled(true);
                play.setEnabled(false);
                break;
            case R.id.buttonStop:
                mediaPlayer.pause();
                stop.setEnabled(false);
                play.setEnabled(true);
                break;
        }
    }
}