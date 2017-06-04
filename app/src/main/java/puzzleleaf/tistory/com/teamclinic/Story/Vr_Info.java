package puzzleleaf.tistory.com.teamclinic.Story;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_info);
        overridePendingTransition(0,0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        contentNum = intent.getExtras().getString("contents");

        if(contentNum.equals(String.valueOf(3)) ||
                contentNum.equals(String.valueOf(2)))
            vr_add_info = false;

        vr_info = (ImageView)findViewById(R.id.vr_info_image);

        Button start_vr = (Button)findViewById(R.id.start_vr);
        start_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("qwe",contentNum);
                if(vr_add_info) {
                    Intent intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                    intent.putExtra("contents", contentNum);
                    startActivity(intent);
                    finish();
                }else
                {
                    if(contentNum.equals(String.valueOf(3)))
                    {
                        vr_info.setImageResource(R.drawable.vr_info_thr);
                        vr_add_info = true;
                    }
                    else if(contentNum.equals(String.valueOf(2)))
                    {
                        vr_info.setImageResource(R.drawable.vr_info_fir);
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
