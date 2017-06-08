package puzzleleaf.tistory.com.teamclinic.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.processing.StageData;


public class Setting extends AppCompatActivity {

    private TextView link;
    private TextView link2;
    private Button aniReset;
    private StageData setData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    void init()
    {
        setData = new StageData(getApplicationContext());

        aniReset = (Button)findViewById(R.id.animaton_reset);
        aniReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData.saveStage(0);
                setData.getStage();
                setData.saveStage(3);
                setData.getTree();
            }
        });

        link = (TextView)findViewById(R.id.setting_page);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://puzzleleaf.tistory.com/130");
                i.setData(u);
                startActivity(i);
            }
        });

        link2 = (TextView)findViewById(R.id.processing_page);
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("https://github.com/processing/processing-android/wiki");
                i.setData(u);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
