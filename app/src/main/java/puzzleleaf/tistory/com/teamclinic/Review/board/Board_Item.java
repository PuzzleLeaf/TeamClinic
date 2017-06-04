package puzzleleaf.tistory.com.teamclinic.Review.board;

/**
 * Created by 희석 on 2017-04-05.
 */

public class Board_Item{
    private String b_id; // 게시판아이디(키)
    private String u_id; // 사용자아이디
    private String u_name; // 작성자 이름
    private String title; // 제목
    private String contents; // 내용
    private long replyNum; // 댓글 갯수
    private String date; // 작성일자


    public Board_Item() { }
    public Board_Item(String b_id, String u_id, String u_name, String title, String contents, long replyNum, String date){
        this.b_id = b_id;
        this.u_id = u_id;
        this.u_name = u_name;
        this.title = title;
        this.contents = contents;
        this.replyNum = replyNum;
        this.date = date;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String get_title() {
        return title;
    }

    public void set_title(String title) {
        this.title = title;
    }

    public String get_contents() {
        return contents;
    }

    public void set_contents(String contents) {
        this.contents = contents;
    }

    public long get_reply() { return replyNum; }

    public void set_reply(int replyNum) { this.replyNum = replyNum; }
}
