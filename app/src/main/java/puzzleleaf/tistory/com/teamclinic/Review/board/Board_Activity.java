package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import puzzleleaf.tistory.com.teamclinic.R;
/**
 * Created by 희석 on 2017-04-09.
 * 게시판 쓰기
 */

public class Board_Activity extends Activity { // Board만들기
    EditText et_title; // 제목입력
    EditText et_contents; // 내용입력
    String title;
    String contents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        init();
    }

    private void init() {
        // 기본 Layout Set
        TextView tv = (TextView) findViewById(R.id.TV_title);
        et_title = (EditText) findViewById(R.id.ET_title);
        et_contents = (EditText) findViewById(R.id.ET_contents);
        Button bt = (Button) findViewById(R.id.complete_bt); // 완료버튼

        bt.setOnClickListener(new View.OnClickListener() { //완료버튼 클릭시
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                contents = et_contents.getText().toString();

                if(EditText_Check() || BasicInfo.firebaseUser != null) { // 제목 내용이 전부 입력되있을 경우
                    // 임시로 관리자로 넣음
                    Board_Item bd = new Board_Item("Key", BasicInfo.firebaseUser.getEmail(), BasicInfo.firebaseUser.getDisplayName(),
                            title, contents, 0, getNow());
                    BasicInfo.databaseReference.child("Board").push().setValue(bd);
                    setResult(RESULT_OK); // 정상적으로 저장됨
                    finish();
                }
            }
        });

        bt = (Button) findViewById(R.id.cancel_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean EditText_Check(){
        if(title.trim().length() < 1 || contents.trim().length() < 1) {
            Toast.makeText(getApplicationContext(),"제목과 내용을 입력주십시오",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String getNow(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

        String datenow = CurDateFormat.format(date);

        return datenow;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
