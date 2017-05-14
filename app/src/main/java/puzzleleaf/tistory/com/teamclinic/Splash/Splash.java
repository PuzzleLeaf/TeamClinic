package puzzleleaf.tistory.com.teamclinic.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.title;


public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIME = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0,SPLASH_DISPLAY_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_slide_in_up,R.anim.anim_slide_out_up);


    }
}
