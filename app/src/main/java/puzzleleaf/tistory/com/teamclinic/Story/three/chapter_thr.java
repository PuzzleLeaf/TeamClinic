package puzzleleaf.tistory.com.teamclinic.Story.three;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Story.Vr_Info;
import puzzleleaf.tistory.com.teamclinic.Unity.UnityPlayerActivity;
import puzzleleaf.tistory.com.teamclinic.processing.StageData;


public class chapter_thr extends AppCompatActivity {

    private LinearLayout thr_frame;
    //이미지 리소스 사용을 위한 TypedArray
    private TypedArray thr_story;
    private int story_idx = 0;
    private StageData setData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_frame);
        overridePendingTransition(R.anim.anim_slide_in_up_story, R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        thr_story = getResources().obtainTypedArray(R.array.chapter_thr1);
        init();

    }

    void init()
    {
        setData = new StageData(getApplicationContext());
        thr_frame = (LinearLayout) findViewById(R.id.chapter_story);
        thr_frame.setBackgroundResource(thr_story.getResourceId(story_idx,-1));

        thr_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(story_idx<17 && story_idx!=11) {
                    thr_frame.setBackgroundResource(thr_story.getResourceId(story_idx, -1));
                    story_idx++;
                }
                else if(story_idx == 11)
                {
                    Intent intent = new Intent(getApplicationContext(), Vr_Info.class);
                    intent.putExtra("contents","3");
                    story_idx++;
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),chapter_final.class);
                    setData.saveStage(++StageData.stageNum);
                    setData.getStage();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_slide_in_up,R.anim.anim_slide_out_up);
        
    }
}
