package puzzleleaf.tistory.com.teamclinic.Review;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Review.board.AuthActivity;
import puzzleleaf.tistory.com.teamclinic.processing.ReviewTree;
import puzzleleaf.tistory.com.teamclinic.processing.StageData;
import puzzleleaf.tistory.com.teamclinic.processing.StarTitle;


public class Review extends AppCompatActivity {


    private StageData stageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_index);
        overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_zero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        init();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.reviewIndex, new ReviewTree());
        fragmentTransaction.commit();


    }

    void init()
    {
        Button review_start = (Button)findViewById(R.id.review_start);
        review_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        stageData = new StageData(getApplicationContext());
        stageData.saveTree(++StageData.treeNum);
        stageData.getTree();

        Log.d("qwe",String.valueOf(StageData.treeNum));

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.anim_slide_out_right);
    }
}
