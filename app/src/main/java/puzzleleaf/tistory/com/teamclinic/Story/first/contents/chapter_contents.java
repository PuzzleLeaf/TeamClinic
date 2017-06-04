package puzzleleaf.tistory.com.teamclinic.Story.first.contents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


import java.util.ArrayList;

import puzzleleaf.tistory.com.teamclinic.R;


public class chapter_contents extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<Integer> res;
    int contentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_contents);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
    }

    private void init()
    {
        Intent intent = getIntent();
        contentValue = intent.getIntExtra("content",1);
        res = new ArrayList<>();
        viewPager = (ViewPager)findViewById(R.id.chapter_contents_viewpager);
        viewPager.setOffscreenPageLimit(3);
        contentInit();
        chapter_contents_item item = new chapter_contents_item(getApplicationContext(),res);
        viewPager.setAdapter(item);
    }

    private void contentInit()
    {
        if(contentValue==1) {
            res.add(R.drawable.chapter_first_content_a);
            res.add(R.drawable.chapter_first_content_b);
        }
        else if(contentValue==2)
        {
            res.add(R.drawable.chapter_sec_content_a);
            res.add(R.drawable.chapter_sec_content_b);
            res.add(R.drawable.chapter_sec_content_c);
            res.add(R.drawable.chapter_sec_content_d);
        }
        else
        {
            res.add(R.drawable.chapter_thr_content_a);
            res.add(R.drawable.chapter_thr_content_b);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.anim_slide_out_left);
    }
}
