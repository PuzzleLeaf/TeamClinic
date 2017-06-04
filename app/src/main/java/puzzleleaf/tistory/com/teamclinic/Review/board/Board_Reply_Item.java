package puzzleleaf.tistory.com.teamclinic.Review.board;

import java.io.Serializable;

/**
 * Created by 희석 on 2017-05-16.
 */

public class Board_Reply_Item implements Serializable {
    private String key; // 어느 게시판의 댓글인지의 키값.
    private String u_id; // 사용자아이디
    private String u_name; // 사용자 이름
    private String reply; // 댓글
    private String date; // 작성일자

    Board_Reply_Item(){}
    Board_Reply_Item(String key, String u_id, String u_name, String reply, String date){
        this.key = key; // 어느게시판의 댓글인지
        this.u_id = u_id;
        this.u_name = u_name;
        this.reply = reply;
        this.date = date;
    }

    public void setKey(String key){ this.key = key; }
    public String getKey(){ return key; }

    public void setU_id(String u_id){ this.u_id = u_id; }
    public String getU_id(){ return u_id; }

    public void setU_name(String u_name){ this.u_name = u_name; }
    public String getU_name(){ return u_name; }

    public void setReply(String reply){ this.reply = reply; }
    public String getReply(){ return reply; }

    public void setDate(String date){ this.date = date; }
    public String getDate(){ return date; }
}
