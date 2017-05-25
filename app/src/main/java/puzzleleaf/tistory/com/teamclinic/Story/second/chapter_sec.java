package puzzleleaf.tistory.com.teamclinic.Story.second;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Unity.UnityPlayerActivity;


public class chapter_sec extends AppCompatActivity {

    private LinearLayout second_frame;
    //이미지 리소스 사용을 위한 TypedArray
    private TypedArray second_story;
    private int story_idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_frame);
        overridePendingTransition(R.anim.anim_slide_in_up_story, R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        second_story = getResources().obtainTypedArray(R.array.chapter_sec);
        init();

    }

    void init()
    {
        second_frame = (LinearLayout) findViewById(R.id.chapter_story);
        second_frame.setBackgroundResource(second_story.getResourceId(story_idx++,-1));

        second_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(story_idx<9) {
                    second_frame.setBackgroundResource(second_story.getResourceId(story_idx++, -1));
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.anim_slide_out_up);
        
    }
}
