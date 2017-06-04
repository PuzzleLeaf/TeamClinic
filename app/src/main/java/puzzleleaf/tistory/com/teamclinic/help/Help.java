package puzzleleaf.tistory.com.teamclinic.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import puzzleleaf.tistory.com.teamclinic.R;


public class Help extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button btn1 = (Button) findViewById(R.id.help1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://happybean.naver.com/happybeansearch/RaiseDonationSearch.nhn?query=%EC%B2%AD%EA%B0%81%EC%9E%A5%EC%95%A0");
                i.setData(u);
                startActivity(i);
            }
        });

        Button btn2 = (Button) findViewById(R.id.help2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://happybean.naver.com/happybeansearch/RaiseDonationSearch.nhn?query=%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0");
                i.setData(u);
                startActivity(i);
            }
        });

        Button btn3 = (Button) findViewById(R.id.help3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://happybean.naver.com/happybeansearch/RaiseDonationSearch.nhn?query=%EC%A0%95%EC%8B%A0%EC%9E%A5%EC%95%A0");
                i.setData(u);
                startActivity(i);
            }
        });

        Button btn4 = (Button) findViewById(R.id.help4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://happybean.naver.com/");
                i.setData(u);
                startActivity(i);
            }
        });
    }
}
