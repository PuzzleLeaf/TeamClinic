package puzzleleaf.tistory.com.teamclinic.Story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.Unity.UnityPlayerActivity;


public class Vr_Info extends AppCompatActivity {

    String contentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_info);
        overridePendingTransition(0,0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        contentNum = intent.getExtras().getString("contents");
        Log.d("qwe",String.valueOf(contentNum));

        Button start_vr = (Button)findViewById(R.id.start_vr);
        start_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UnityPlayerActivity.class);
                intent.putExtra("contents",contentNum);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
        
    }
}
