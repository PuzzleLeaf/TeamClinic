package puzzleleaf.tistory.com.teamclinic.Story.three;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Review.Review;
import puzzleleaf.tistory.com.teamclinic.Story.Vr_Info;
import puzzleleaf.tistory.com.teamclinic.processing.StarEnd;
import puzzleleaf.tistory.com.teamclinic.processing.StarTitle;


public class chapter_final extends AppCompatActivity {

    private LinearLayout thr_frame;
    //이미지 리소스 사용을 위한 TypedArray
    private TypedArray thr_story;
    private int story_idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        overridePendingTransition(R.anim.anim_slide_in_up_story, R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.Story_end, new StarEnd());
        fragmentTransaction.commit();

    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.anim_slide_out_up);
        
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), Review.class);
        Toast.makeText(getApplicationContext(),"체험을 진행한 후의 느낌을 한 줄로 나타낸다면?",Toast.LENGTH_LONG).show();
        startActivity(intent);
        super.onDestroy();
    }
}
