package puzzleleaf.tistory.com.teamclinic.Story;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Story.first.chapter_first;
import puzzleleaf.tistory.com.teamclinic.Story.contents.chapter_contents;
import puzzleleaf.tistory.com.teamclinic.Story.second.chapter_sec;
import puzzleleaf.tistory.com.teamclinic.Story.zero.chapter_zero;


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
                Intent intent = new Intent(getApplicationContext(),chapter_zero.class);
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

        LinearLayout first_info = (LinearLayout)findViewById(R.id.chapter_first_contents_start);
        first_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_contents.class );
                startActivity(intent);
            }
        });

        LinearLayout first_vr = (LinearLayout)findViewById(R.id.chapter_first_contents_vr);
        first_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vr_Info.class);
                intent.putExtra("contents","1");
                startActivity(intent);
            }
        });


        ImageView second = (ImageView)findViewById(R.id.chapter_sec);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_sec.class);
                startActivity(intent);
            }
        });

        LinearLayout sec_info = (LinearLayout)findViewById(R.id.chapter_sec_contents_start);
        sec_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_contents.class );
                startActivity(intent);
            }
        });

        LinearLayout sec_vr = (LinearLayout)findViewById(R.id.chapter_sec_contents_vr);
        sec_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vr_Info.class);
                intent.putExtra("contents","2");
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

        LinearLayout thr_info = (LinearLayout)findViewById(R.id.chapter_thr_contents_start);
        thr_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),chapter_contents.class );
                startActivity(intent);
            }
        });

        LinearLayout thr_vr = (LinearLayout)findViewById(R.id.chapter_thr_contents_vr);
        thr_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vr_Info.class);
                intent.putExtra("contents","3");
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
