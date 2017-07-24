//package com.lazy2b.app;
//
//import android.app.Activity;
//import android.content.Context;
//import android.media.AudioAttributes;
//import android.media.AudioManager;
//import android.media.SoundPool;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static android.os.Build.VERSION.SDK_INT;
//
//
//public class MainActivity extends Activity {
//
//    @Bind(R.id.dd)
//    TextView dd;
//
//    float volume = 0;
//
//    private synchronized static int calCount(int count) {
//        return count == 0 ? count : count - 1;
//    }
//
//    @OnClick(R.id.dd)
//    void Clickkkk(View view) {
////        startSoundPool();
//        play();
//    }
//
//    private void play() {
//        boolean playSuccess = soundPool.play(closeSoundId, volume, volume, 1, calCount(count), 1) != 0;
//        Toast.makeText(MainActivity.this, "playSuccess=" + playSuccess, Toast.LENGTH_SHORT).show();
////        soundPool.resume(closeSoundId);
////        soundPool.release();
////        soundPool = null;
////        startSoundPool();
//    }
//
//    Handler handler = new Handler();
//
//    int count = 1;
//
//    protected SoundPool soundPool = null;
//    protected AudioManager audioMgr = null;
//    protected int closeSoundId = -1;
//
//    void startSoundPool() {
//        if (soundPool != null) {
//            soundPool.release();
//            soundPool = null;
//        }
//        if (SDK_INT >= 21) {
//            soundPool = (new SoundPool.Builder()).setMaxStreams(10)
//                    .setAudioAttributes(
//                            new AudioAttributes.Builder()
//                                    .setLegacyStreamType(AudioManager.STREAM_VOICE_CALL)
//                                    .build())
//                    .build();//(10, , 0);
//        } else {
//            soundPool = new SoundPool(10, AudioManager.STREAM_VOICE_CALL, 0);
//        }
//        closeSoundId = soundPool.load(this, R.raw.du, 1);
//        Toast.makeText(MainActivity.this, "closeSoundId=" + closeSoundId, Toast.LENGTH_SHORT).show();
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        play();
//                    }
//                }, 5000);
//            }
//        });
//    }
//
//    void initVolume() {
//        if (audioMgr == null) {
//            audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        }
//        volume = 1.0f;//audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        dd.setText("sdfasdfsad");
//        // 初始化音效
//        initVolume();
//        startSoundPool();
//    }
//}
