package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import puzzleleaf.tistory.com.teamclinic.R;

/**
 * Created by 희석 on 2017-04-05.
 * 게시판 어뎁터 뷰홀더를 수정해서 리사이클러뷰에 보이는것을 다르게 할수있음 현제는 제목만
 */

public class Board_RecyclerAdapter extends RecyclerView.Adapter<Board_RecyclerAdapter.ViewHolder>{
    public Context context;
    private ArrayList<Board_Item> BoardList;
    private int itemLayout;
    private static ClickListener clickListener;

    public Board_RecyclerAdapter(Context context, ArrayList<Board_Item> BoardList, int itemLayout) {
        super();
        this.context = context;
        this.BoardList = BoardList;
        this.itemLayout = itemLayout;
    }

    //Layout을 만들어서 ViewHolder에 저장
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰홀더 이용 및 Item의 레이아웃 설정
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_board_item,parent,false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                200);
        v.setLayoutParams(params);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return this.BoardList.size();
    }

    public Object getItem(int position) { return BoardList.get(position); }

    // ListView의 getView역할을 하는 핵심 메소드
    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        Board_Item board_item = BoardList.get(position);
        viewholder.text_title.setText(board_item.get_title()); // 방식을 수정해야함
        viewholder.text_id.setText(cut_id(board_item.getU_id()));
        return;
    }

    public String cut_id(String id){
        int pos = 0;
        while(id.charAt(pos) != '@') pos++;

        if(pos < 12) return id.substring(0,pos);
        else return id.substring(0,9) + "...";
    }

    public interface ClickListener { // ClickListener인터페이스 설정
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) { // 리스너 설정
       this.clickListener = clickListener;
    }

    // 뷰 재활용을 위한 ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView text_title;
        public TextView text_id;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            text_title = (TextView) itemView.findViewById(R.id.tv1);
            text_title.setTextSize(20);
            text_id = (TextView) itemView.findViewById(R.id.tv2);
            text_id.setTextSize(15);
        }

        @Override
        public void onClick(View v) { // 일반클릭
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) { // 롱클릭
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
}
