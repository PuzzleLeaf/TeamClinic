package puzzleleaf.tistory.com.teamclinic.Story.zero;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import puzzleleaf.tistory.com.teamclinic.R;


public class chapter_zero extends AppCompatActivity {

    private LinearLayout zero_frame;
    //이미지 리소스 사용을 위한 TypedArray
    private TypedArray zero_story;
    private int story_idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_frame);
        overridePendingTransition(R.anim.anim_slide_in_up_story,R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        zero_story = getResources().obtainTypedArray(R.array.chapter_zero);
        init();

    }

    void init()
    {
        zero_frame = (LinearLayout) findViewById(R.id.chapter_story);
        zero_frame.setBackgroundResource(zero_story.getResourceId(story_idx++,-1));

        zero_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(story_idx<6) {
                    zero_frame.setBackgroundResource(zero_story.getResourceId(story_idx++, -1));
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
