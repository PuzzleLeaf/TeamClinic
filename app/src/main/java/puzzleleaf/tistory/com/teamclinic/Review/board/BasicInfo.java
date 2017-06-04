package puzzleleaf.tistory.com.teamclinic.Review.board;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by 희석 on 2017-04-09.
 */

public class BasicInfo {
    // 파이어베이스 변수
    public static DatabaseReference databaseReference;
    public static FirebaseDatabase firebaseDatabase;
    public static FirebaseUser firebaseUser = null;
    public static FirebaseAuth mFirebaseAuth = null;
    public static GoogleApiClient mGoogleApiClient = null;
    // 모드 변수
    public static final String Mode = "MODE";
    public static final String Board_View = "Board_View"; // 보드 보기
    public static final String Board_Write = "Board_Write"; // 보드 쓰기
    public static final String Modify_Board = "Modify_Board"; // 보드 수정
    public static final String Search_Borad = "Search_Board"; // 검색 화면 보기
    public static int Changed_position;
    // 보드 리스트
    public static ArrayList<Board_Item> boardList;
    public static Board_ContentsActivity.ViewPagerAdapter pager_adapter = null; // 뷰페이저 어댑터

    //엑티비티 관련
    public static final int REQ_VIEW_ACTIVITY = 1001;
    public static final int REQ_INSERT_ACTIVITY = 1002;
    public static final int REQ_SEARCH_ACTIVITY = 1003;
}
