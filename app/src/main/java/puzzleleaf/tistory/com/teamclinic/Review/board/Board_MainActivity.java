package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import puzzleleaf.tistory.com.teamclinic.R;
//게시판 보기 Class
public class Board_MainActivity extends AppCompatActivity implements ChildEventListener{

    private RecyclerView recyclerView; // 리사이클러뷰 변수
    Board_RecyclerAdapter boardRecyclerAdapter;
    EditText Search_edt; // 검색 Text
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000; // 뒤로가기버튼 제어 2초

    static{
        BasicInfo.firebaseDatabase = FirebaseDatabase.getInstance();
        BasicInfo.firebaseDatabase.setPersistenceEnabled(true); // 디스크 지속성 추가
        BasicInfo.databaseReference = BasicInfo.firebaseDatabase.getReference(); // DataReference 가져오기
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);
        init();
    }

    @Override
    protected void onDestroy() {
        BasicInfo. databaseReference.child("Board").removeEventListener(this);
        super.onDestroy();
    }

    private void init(){
        BasicInfo.Changed_position = -11; // 초기값 = -11
        BasicInfo.databaseReference.child("Board").addChildEventListener(this);

        //Layout set
        recyclerView = (RecyclerView) findViewById(R.id.r_view); // 리사이클러뷰 인스턴스 받기
        Button btn = (Button) findViewById(R.id.wb_bt); // 글쓰기버튼

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 글쓰기 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), Board_Activity.class);
                intent.putExtra(BasicInfo.Mode, BasicInfo.Board_Write);
                startActivityForResult(intent,BasicInfo.REQ_INSERT_ACTIVITY);
            }
        });

        // 로그인사용자와 로그인하지않은 사용자 글쓰기 제한
        if(BasicInfo.firebaseUser == null){ // 로그인 하지않은경우
            btn.setVisibility(View.GONE);  // 화면에 안보임
        }else{ // 로그인 한경우
            btn.setVisibility(View.VISIBLE);   // 화면에보임
        }

        Search_edt = (EditText) findViewById(R.id.search_txt);

        btn = (Button) findViewById(R.id.search_bt); //검색버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditText_Check()){
                    Toast.makeText(getApplicationContext(),"검색 단어를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Board_SearchActivity.class);
                    intent.putExtra(BasicInfo.Mode, BasicInfo.Search_Borad);
                    intent.putExtra("Key", Search_edt.getText().toString());
                    startActivityForResult(intent,BasicInfo.REQ_SEARCH_ACTIVITY);
                }
            }
        });

        // 리스트 설정
        BasicInfo.boardList = new ArrayList<Board_Item>();

        boardRecyclerAdapter = new Board_RecyclerAdapter(getApplicationContext(),BasicInfo.boardList,R.layout.activity_board_item);
        recyclerView.setAdapter(boardRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        boardRecyclerAdapter.setOnItemClickListener(new Board_RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), Board_ContentsActivity.class);
                intent.putExtra(BasicInfo.Mode, BasicInfo.Board_View);
                intent.putExtra("Position",position);
                startActivityForResult(intent,BasicInfo.REQ_VIEW_ACTIVITY);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(getApplicationContext(),"짧게 눌러주십시오",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    // 파이어베이스에 데이터가 추가되면 호출 되는 콜백함수
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Board_Item boardItem = dataSnapshot.getValue(Board_Item.class);
        boardItem.setB_id(dataSnapshot.getKey()); // 저장되있는 키값이아닌 실제키값으로 저장해줌
        BasicInfo.boardList.add(boardItem);
        boardRecyclerAdapter.notifyDataSetChanged();
        if(BasicInfo.pager_adapter != null) BasicInfo.pager_adapter.notifyDataSetChanged(); // 어댑터 초기화
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) { // 데이터변경시 호출
        if(BasicInfo.Changed_position == -11) return; // 초기값일경우 아무일도 하지않음

        if(BasicInfo.Changed_position == -1 ){ // 게시판 업데이트 실패 혹은 비정상적인 게시글 수정시
            Toast.makeText(getApplicationContext(),"게시판 업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        Board_Item boardItem = (Board_Item) dataSnapshot.getValue(Board_Item.class);
        boardItem.setB_id(dataSnapshot.getKey()); // 저장되있는 키값이아닌 실제키값으로 저장해줌
        BasicInfo.boardList.set(BasicInfo.Changed_position,boardItem);
        boardRecyclerAdapter.notifyDataSetChanged();
        if(BasicInfo.pager_adapter != null) BasicInfo.pager_adapter.notifyDataSetChanged(); // 어댑터 초기화
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if(BasicInfo.Changed_position == -1){ // 게시판 업데이트 실패 혹은 비정상적인 게시글 삭제시
            Toast.makeText(getApplicationContext(),"게시판 업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        BasicInfo.firebaseDatabase
                .getReference("ReplyBoard/" + "re" + dataSnapshot.getKey())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    }
                });
        BasicInfo.boardList.remove(BasicInfo.Changed_position);
        boardRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        boardRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        boardRecyclerAdapter.notifyDataSetChanged();
    }

    private boolean EditText_Check() {
        String Searchtxt = Search_edt.getText().toString();

        if (Searchtxt.trim().length() < 1) {
            return false;
        }

        return true;
    }

    private void logout(){
        if(BasicInfo.mFirebaseAuth != null){
            BasicInfo.mGoogleApiClient.connect();
            BasicInfo.mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {

                    BasicInfo.mFirebaseAuth.signOut();
                    BasicInfo.firebaseUser.delete();

                    if(BasicInfo.mGoogleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(BasicInfo.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (status.isSuccess()) {
                                    Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Board_MainActivity.this,AuthActivity.class)); // 다시 인증화면으로
                                    finish();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.d("메인", "Google API Client 연결 실패");
                }
            });



            //startActivity(new Intent(Board_MainActivity.this,AuthActivity.class)); // 다시 인증화면으로
           // finish();
        }
        else{
            startActivity(new Intent(Board_MainActivity.this,AuthActivity.class)); // 다시 인증화면으로
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BasicInfo.REQ_INSERT_ACTIVITY: // 입력 후
                if(resultCode == RESULT_OK) {
                    Search_edt.setText("");
                    boardRecyclerAdapter.notifyDataSetChanged();
                }
                break;

            case BasicInfo.REQ_SEARCH_ACTIVITY: // 검색 후
                if(resultCode == RESULT_OK) {
                    Search_edt.setText("");
                    boardRecyclerAdapter.notifyDataSetChanged();
                }
                break;
            case BasicInfo.REQ_VIEW_ACTIVITY: // 게시판보기후
                if(resultCode == RESULT_OK) {
                    Search_edt.setText("");
                    boardRecyclerAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
