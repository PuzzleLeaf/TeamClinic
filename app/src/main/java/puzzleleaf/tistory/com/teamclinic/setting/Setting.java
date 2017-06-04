package puzzleleaf.tistory.com.teamclinic.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import puzzleleaf.tistory.com.teamclinic.R;


public class Setting extends AppCompatActivity {

    TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_slide_in_up,R.anim.anim_slide_out_up);


    }
}
