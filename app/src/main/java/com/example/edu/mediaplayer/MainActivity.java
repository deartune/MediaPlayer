package com.example.edu.mediaplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button play1;
    Button stop1;
    MediaPlayer mediaPlayer;
    Button play, stop, record;
    MediaRecorder myAudioRecorder;
    String outputFile = null;
    final int REQUEST_CODE=201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this,

                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);

            play1 = (Button) findViewById(R.id.buttonPlay);
            stop1 = (Button) findViewById(R.id.buttonStop);
            play1.setOnClickListener(this);
            stop1.setOnClickListener(this);

            mediaPlayer = MediaPlayer.create(this, R.raw.meetingyou);
            play = (Button) findViewById(R.id.play);
            stop = (Button) findViewById(R.id.stop);
            record = (Button) findViewById(R.id.record);
            play.setOnClickListener(this);
            stop.setOnClickListener(this);
            record.setOnClickListener(this);


            stop.setEnabled(false);
            play.setEnabled(false);
            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.reset();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 ||

                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i("", "Permission has been granted by user");
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlay:
                mediaPlayer.start();
                play1.setEnabled(true);
                play1.setEnabled(false);
                break;
            case R.id.buttonStop:
                mediaPlayer.pause();
                stop1.setEnabled(false);
                play1.setEnabled(true);
                break;
            case R.id.record:
                try {
                    myAudioRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAudioRecorder.start();
                record.setEnabled(false);
                stop.setEnabled(true);
                break;
            case R.id.stop:
                stop.setEnabled(false);
                play.setEnabled(true);
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                break;
            case R.id.play:
                MediaPlayer m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                m.start();
                break;
        }
    }
}
