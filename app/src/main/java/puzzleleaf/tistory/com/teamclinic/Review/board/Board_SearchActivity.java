package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import puzzleleaf.tistory.com.teamclinic.R;


/**
 * Created by 희석 on 2017-04-26.
 */

public class Board_SearchActivity extends Activity {

    private RecyclerView recyclerView; // 리사이클러뷰 변수
    ArrayList<Board_Item> SearchList;
    Board_RecyclerAdapter SearchAdapter;
    EditText Search_edt;
    String Search_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        init();
        Intent intent = getIntent(); // 인텐트 받아오기
        Search_key = intent.getStringExtra("Key");
        Search_edt.setText(Search_key);
        TitleSeach();
    }

    private void init(){
        //Layout set
        recyclerView = (RecyclerView) findViewById(R.id.r_view); // 리사이클러뷰 인스턴스 받기
        Button btn = (Button) findViewById(R.id.wb_bt); // 글쓰기버튼
        Search_edt = (EditText) findViewById(R.id.search_txt); // 검색 쓰기

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 글쓰기 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), Board_Activity.class);
                intent.putExtra(BasicInfo.Mode, BasicInfo.Board_Write);
                startActivity(intent);
            }
        });

        // 로그인사용자와 로그인하지않은 사용자 글쓰기 제한
        if(BasicInfo.firebaseUser == null){ // 로그인 하지않은경우
            btn.setVisibility(View.GONE);  // 화면에 안보임
        }else{ // 로그인 한경우
            btn.setVisibility(View.VISIBLE);   // 화면에보임
        }

        btn = (Button) findViewById(R.id.search_bt); //검색버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditText_Check()){
                    Toast.makeText(getApplicationContext(),"검색 단어를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }
                else{
                    SearchList.clear();
                    Search_key = Search_edt.getText().toString();

                    TitleSeach();
                }
            }
        });
        // 리스트 설정
        SearchList = new ArrayList<Board_Item>();

        SearchAdapter = new Board_RecyclerAdapter(getApplicationContext(),SearchList,R.layout.activity_board_item);
        recyclerView.setAdapter(SearchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void TitleSeach(){ // 데이터 검색화면을 보여주는 함수
        for(Board_Item bi : BasicInfo.boardList) {
            if(bi.get_title().trim().contains(Search_key)){
                SearchList.add(bi);
            }
        }
        SearchAdapter.notifyDataSetChanged();
    }

    private boolean EditText_Check() {
        String Searchtxt = Search_edt.getText().toString();

        if (Searchtxt.trim().length() < 1) {
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BasicInfo.REQ_INSERT_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    SearchAdapter.notifyDataSetChanged();
                }
                break;

            default:
                break;
        }
    }
}
