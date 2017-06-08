package puzzleleaf.tistory.com.teamclinic.Story;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Unity.UnityPlayerActivity;


public class Vr_Info extends AppCompatActivity {

    String contentNum;
    ImageView vr_info;
    boolean vr_add_info = true;
    private TypedArray content_arr;
    private int idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_info);
        overridePendingTransition(0,0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        init();


    }

    void init()
    {
        Intent intent = getIntent();
        contentNum = intent.getExtras().getString("contents");

        vr_info = (ImageView)findViewById(R.id.vr_info_image);

        if(contentNum.equals(String.valueOf(3)) ||
                contentNum.equals(String.valueOf(2)))
            vr_add_info = false;

        //청각 2번
        if(contentNum.equals(String.valueOf(2))) {
            content_arr = getResources().obtainTypedArray(R.array.vr_info_fir);
            vr_info.setImageResource(content_arr.getResourceId(idx++,0));
        }
        // 정신 3번
        if(contentNum.equals(String.valueOf(3))) {
            vr_info.setImageResource(R.drawable.vr_info_thr);
        }




        Button start_vr = (Button)findViewById(R.id.start_vr);
        start_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vr_add_info) {
                    Intent intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                    intent.putExtra("contents", contentNum);
                    startActivity(intent);
                    finish();
                }else
                {
                    if(contentNum.equals(String.valueOf(3)))
                    {
                        vr_info.setImageResource(R.drawable.vr_info);
                        vr_add_info = true;
                    }
                    else if(contentNum.equals(String.valueOf(2)))
                    {
                        vr_info.setImageResource(content_arr.getResourceId(idx++, 0));
                        if(idx==4)
                            vr_add_info = true;

                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
        
    }
}
