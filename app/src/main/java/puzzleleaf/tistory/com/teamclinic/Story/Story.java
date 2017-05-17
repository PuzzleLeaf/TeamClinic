package puzzleleaf.tistory.com.teamclinic.Story;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Story.first.chapter_first;
import puzzleleaf.tistory.com.teamclinic.processing.ReviewTree;


public class Story extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_index);
        overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnInit();


    }

    void btnInit()
    {
        ImageView zero = (ImageView)findViewById(R.id.chapter_zero);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_first.class);
                startActivity(intent);
            }
        });

        ImageView first = (ImageView)findViewById(R.id.chapter_first);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_first.class);
                startActivity(intent);
            }
        });
        ImageView second = (ImageView)findViewById(R.id.chapter_sec);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_first.class);
                startActivity(intent);
            }
        });
        ImageView third = (ImageView)findViewById(R.id.chapter_thr);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_first.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.anim_slide_out_left);
    }
}
