package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import puzzleleaf.tistory.com.teamclinic.R;

// 댓글 창 구성
public class Board_ReplyActivity extends Activity{
    //static변수
    private static ArrayList<Board_Reply_Item> reply_list;
    private static String key; // 어느게시글의 댓글인지
    // 일반변수
    private RecyclerView replyView;
    private ReplyAdapter replyAdapter;
    private EditText reply_edt;

    String s_reply; // 댓글을 임시로 저장할 String값
    ChildEventListener childEventListener; //리스너
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_view);
        intent = getIntent();
        key = intent.getStringExtra("Key");
        // 초기설정
        init();
        firebase_init();
    }

    @Override
    protected void onDestroy() {
        BasicInfo.databaseReference.child("ReplyBoard").child("re" + key).removeEventListener(childEventListener);
        reply_list = null;
        replyAdapter = null;
        key = null;
        super.onDestroy();
    }

    void init(){
        reply_edt = (EditText) findViewById(R.id.reply_edt);

        Button reply_btn = (Button) findViewById(R.id.reply_btn);

        replyView = (RecyclerView) findViewById(R.id.reply_view);
        reply_list = new ArrayList<Board_Reply_Item>();
        replyAdapter = new ReplyAdapter(getApplicationContext(),reply_list,R.layout.activity_reply_item);
        replyView.setAdapter(replyAdapter);
        replyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_reply = reply_edt.getText().toString();

                if(Reply_Check() && BasicInfo.firebaseUser != null) // 글이 있고 로그인 시 댓글 작성가능
                    Push_Reply();


                if(BasicInfo.firebaseUser == null)
                    Toast.makeText(getApplicationContext(), "로그인이 필요한 컨텐츠입니다.", Toast.LENGTH_SHORT).show();

                s_reply = "";
                reply_edt.setText("");
            }
        });
    }

    void firebase_init(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { // 댓글이 등록되었을경우
                Board_Reply_Item boardReply_Item = dataSnapshot.getValue(Board_Reply_Item.class);
                boardReply_Item.setKey(dataSnapshot.getKey()); // 저장되있는 키값이아닌 실제키값으로 저장해줌
                reply_list.add(boardReply_Item);
                replyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { // 댓글 삭제시
                replyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        BasicInfo.databaseReference.child("ReplyBoard").child("re" + key).addChildEventListener(childEventListener);
    }

    private boolean Reply_Check(){
        if(s_reply.trim().length() < 1) return false;

        return true;
    }

    private void Push_Reply(){
        if(BasicInfo.databaseReference.child("Board").child(key).getKey() != null) {
            Board_Reply_Item boardReply_item = new Board_Reply_Item("Key", BasicInfo.firebaseUser.getEmail(),
                    BasicInfo.firebaseUser.getDisplayName(), s_reply, getNow());
            BasicInfo.databaseReference.child("ReplyBoard").child("re" + key).push().setValue(boardReply_item);
        }
        else{
            Toast.makeText(getApplicationContext(),"게시글이 삭제되어 댓글을 등록 할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }
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
        BasicInfo.databaseReference.child("ReplyBoard").child("re" + key).removeEventListener(childEventListener);
        reply_list = null;
        replyAdapter = null;
        key = null;
        finish();
    }

    // 어댑터 클래스
    public static class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder>{
        public Context context;
        private ArrayList<Board_Reply_Item> ReplyList;
        private int itemLayout;

        public ReplyAdapter(Context context, ArrayList<Board_Reply_Item> ReplyList, int itemLayout) {
            super();
            this.context = context;
            this.ReplyList = ReplyList;
            this.itemLayout = itemLayout;
        }

        //Layout을 만들어서 ViewHolder에 저장
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰홀더 이용 및 Item의 레이아웃 설정
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reply_item,parent,false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(params);
            return new ViewHolder(v);
        }

        @Override
        public int getItemCount() {
            return this.ReplyList.size();
        }

        public Object getItem(int position) { return ReplyList.get(position); }

        @Override
        public void onBindViewHolder(ViewHolder viewholder, final int position) {
            final Board_Reply_Item boardReply_item = ReplyList.get(position);
            viewholder.writer_id.setText(boardReply_item.getU_name() + "(" + boardReply_item.getU_id() + ")");
            viewholder.reply.setText(boardReply_item.getReply());

            if(BasicInfo.firebaseUser == null || !BasicInfo.firebaseUser.getEmail().equals(boardReply_item.getU_id()))
                viewholder.del_btn.setVisibility(View.INVISIBLE);
            else
                viewholder.del_btn.setVisibility(View.VISIBLE);

            viewholder.del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(BasicInfo.databaseReference.child("Board").child(key).getKey() != null) {
                        BasicInfo.firebaseDatabase
                                .getReference("ReplyBoard/" + "re" + key + "/" + boardReply_item.getKey())
                                .removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(context, "댓글 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        reply_list.remove(position);
                    }
                    else{
                        Toast.makeText(context,"게시글이 삭제되어 댓글을 삭제 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return;
        }

        // 뷰 재활용을 위한 ViewHolder
        public static class ViewHolder extends RecyclerView.ViewHolder{

            public TextView writer_id;
            public TextView reply;
            public Button del_btn;

            public ViewHolder(View itemView) {
                super(itemView);
                writer_id = (TextView) itemView.findViewById(R.id.writer_id);
                writer_id .setTextSize(15);
                writer_id.setPaintFlags(writer_id.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG); // 글자 진하게
                reply = (TextView) itemView.findViewById(R.id.reply);
                reply.setTextSize(15);
                del_btn = (Button) itemView.findViewById(R.id.delete_btn);
            }
        }
    }
}
