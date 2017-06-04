package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import puzzleleaf.tistory.com.teamclinic.R;

import static java.lang.Math.random;

// 리스너추가 정안되면 static으로 어댑터를 돌림
// 내용 및 제목등을 보여줄 게시판
public class Board_ContentsActivity extends Activity {
    private Intent intent;
    private int now_position = -1; // 현재 포지션
    private int count; // 뷰페이저의 총갯수
    ImageView randomTree;
    TypedArray tree_res;
    // 뷰페이저
    ViewPager pager; // 뷰페이저

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);



        intent = getIntent(); // 인텐트 받아오기
        now_position = intent.getIntExtra("Position",-1); // 현재 포지션값 받아오기

        pager = (ViewPager) findViewById(R.id.pager);
        BasicInfo.pager_adapter = new ViewPagerAdapter(this);
        pager.setAdapter( BasicInfo.pager_adapter);

        pager.setCurrentItem(now_position);

        if(BasicInfo.boardList.size() < 4) pager.setOffscreenPageLimit(BasicInfo.boardList.size());
        else pager.setOffscreenPageLimit(4);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        BasicInfo.pager_adapter = null;
        super.onDestroy();
    }

    public class Mk_ContentView extends LinearLayout {
        Context mContext;
        //xml 관련 변수
        TextView title;
        EditText title_edt;
        TextView contents;
        EditText contents_edt;
        TextView writer;
        TextView date;
        Button btn1; // 수정 및 저장
        Button btn2; // 삭제 및 취소
        Button replypage_btn; // 댓글 등록 버튼
        // data
        Board_Item board_item;
        String Mode; // 읽기인지 수정인지를 확인
        String s_title;
        String s_contents;
        //
        int position;

        public Mk_ContentView(Context context,int position) {
            super(context);
            mContext = context;

            this.position = position;
            init(mContext);
        }

        public Mk_ContentView(Context context, AttributeSet attrs, int position) {
            super(context, attrs);

            this.position = position;
            init(mContext);
        }

        private void init(Context context) {
            mContext = context;

            // inflate XML layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_contents_view, this, true);


            //랜덤 트리
            randomTree = (ImageView)inflater.inflate(R.layout.activity_contents_view,this,true).findViewById(R.id.board_random_tree);
            tree_res = getResources().obtainTypedArray(R.array.trees);

            processIntent();
            initXml();
            ListenerManager();
        }

        private void initXml(){
            //글
            title = (TextView) findViewById(R.id.title_view);
            writer = (TextView) findViewById(R.id.writer);
            contents = (TextView) findViewById(R.id.contents_view);
            date = (TextView) findViewById(R.id.date);
            title_edt = (EditText) findViewById(R.id.title_edt);
            contents_edt = (EditText) findViewById(R.id.contents_edt);

            //댓글
            replypage_btn = (Button) findViewById(R.id.replypage_btn);

            OnTextView();
            // 받아온 정보를 텍스트뷰에 입력
            title.setText(board_item.get_title());
            writer.setText(board_item.getU_name() + "(" + board_item.getU_id() + ")");
            date.setText(board_item.getDate());
            contents.setText(board_item.get_contents());

            // 수정 및 저장 버튼
            btn1 = (Button) findViewById(R.id.change_btn);
            // 게시글을 작성한 이용자가 아닐경우 수정버튼을 보이지않게함
            if(BasicInfo.firebaseUser == null || !BasicInfo.firebaseUser.getEmail().equals(board_item.getU_id()))
                btn1.setVisibility(View.GONE);
            else
                btn1.setVisibility(View.VISIBLE);

            // 삭제 및 취소 버튼
            btn2 = (Button) findViewById(R.id.delete_btn);
            // 게시글을 작성한 이용자가 아닐경우 삭제버튼을 보이지않게함
            if(BasicInfo.firebaseUser == null || !BasicInfo.firebaseUser.getEmail().equals(board_item.getU_id()))
                btn2.setVisibility(View.GONE);
            else
                btn2.setVisibility(View.VISIBLE);

            int idx = (int)((random()*10))%5;
            randomTree.setImageResource(tree_res.getResourceId(idx,-1));
        }

        private void ListenerManager(){
            btn1.setOnClickListener(new OnClickListener() { // 수정 및 저장버튼 리스너
                @Override
                public void onClick(View v) {
                    if(Mode.equals(BasicInfo.Board_View)){
                        OnEditText();
                        title_edt.setText(s_title);
                        contents_edt.setText(s_contents);
                        btn1.setText("저장");
                        btn2.setText("취소");
                        Mode = BasicInfo.Modify_Board;
                    }
                    else{
                        s_title = title_edt.getText().toString();
                        s_contents = contents_edt.getText().toString();

                        if(EditText_Check()) { // 내용입력이 있을경우만 수정
                            Update();
                            title.setText(s_title);
                            contents.setText(s_contents);
                            OnTextView();
                            btn1.setText("수정");
                            btn2.setText("삭제");
                            Mode = BasicInfo.Board_View;
                        }
                    }
                }
            });

            btn2.setOnClickListener(new OnClickListener() { // 삭제버튼 구현
                @Override
                public void onClick(View v) {
                    if(Mode.equals(BasicInfo.Board_View)){ // View모드일경우는 삭제
                        Delete();
                    }else{
                        OnTextView();
                        title.setText(s_title);
                        contents.setText(s_contents);
                        btn1.setText("수정");
                        btn2.setText("삭제");
                        Mode = BasicInfo.Board_View;
                    }
                }
            });

            replypage_btn.setOnClickListener(new OnClickListener() { // 댓글 보기 버튼
                @Override
                public void onClick(View v) {
                    if(BasicInfo.databaseReference.child("Board").child(board_item.getB_id()).getKey() != null) {
                        Intent re_intent = new Intent(getApplicationContext(), Board_ReplyActivity.class);
                        re_intent.putExtra("Key", board_item.getB_id());
                        startActivity(re_intent);
                    }else{
                        Toast.makeText(mContext,"게시글이 삭제되어 댓글을 볼 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void OnTextView(){ // TextView보이기
            title_edt.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            contents_edt.setVisibility(View.GONE);
            contents.setVisibility(View.VISIBLE);
        }

        private void OnEditText(){ // EditText보이기
            title_edt.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            contents_edt.setVisibility(View.VISIBLE);
            contents.setVisibility(View.GONE);
        }

        private void processIntent(){ // 인텐트를 받아오는 작업을 수행
            Mode = intent.getStringExtra(BasicInfo.Mode);
            board_item = BasicInfo.boardList.get(position);
            s_title = board_item.get_title();
            s_contents = board_item.get_contents();
        }

        private boolean EditText_Check(){
            if(s_title.trim().length() < 1 || s_contents.trim().length() < 1) {
                Toast.makeText(getApplicationContext(),"제목과 내용을 입력하여 주십시오",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(s_title.equals(title.getText().toString()) && s_contents.equals(contents.getText().toString())){
                Toast.makeText(getApplicationContext(),"제목또는 내용을 수정하여 주십시오",Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        private void Update(){
            Board_Item new_board_item = new Board_Item("Key",board_item.getU_id(),board_item.getU_name(),
                    s_title,s_contents,board_item.get_reply(),board_item.getDate());

            BasicInfo.firebaseDatabase
                    .getReference("Board/"+board_item.getB_id())
                    .setValue(new_board_item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"수정이 완료되었습니다",Toast.LENGTH_SHORT).show();
                        }
                    });

            BasicInfo.Changed_position = position; // 아무것도 못받았을경우 -1로 체크
        }

        private void Delete(){
            BasicInfo.firebaseDatabase
                    .getReference("Board/"+board_item.getB_id())
                    .removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(getApplicationContext(),"삭제가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK); // 정상적으로 삭제됨
                            finish();
                        }
                    });

            BasicInfo.Changed_position = position; // 아무것도 못받았을경우 -1로 체크
        }
    }

    // 뷰페이저 어댑터
    public class ViewPagerAdapter extends PagerAdapter {
        /**
         * Context 객체
         */

        private Context mContext;

        // 초기화
        public ViewPagerAdapter( Context context ) {
            mContext = context;
            count = BasicInfo.boardList.size();
        }

        // 페이지 갯수
        public int getCount() {
            return count;
        }

        // 뷰페이저가 만들어졌을 때 호출됨
        public Object instantiateItem(ViewGroup container, int position) {
            Mk_ContentView cv = null;

            cv = new Mk_ContentView(mContext, position);

            // 컨테이너에 추가
            container.addView(cv, 0);
            return cv;
        }

        public void destroyItem(ViewGroup container, int position, Object view) { // 페이지 삭제
            container.removeView((View)view);
        }

        public boolean isViewFromObject(View view, Object object) { return view.equals(object); }

        @Override
        public int getItemPosition(Object item) {
            return POSITION_NONE;
        }
    }
}
