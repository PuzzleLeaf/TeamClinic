package puzzleleaf.tistory.com.teamclinic.Review.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import puzzleleaf.tistory.com.teamclinic.R;

public class AuthActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private SignInButton signInButton;
    private Button anon_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signInButton = (SignInButton) findViewById(R.id.sign_btn);
        BasicInfo.mFirebaseAuth = FirebaseAuth.getInstance();
        // 구글 옵션 설정
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // 웹 클라이언트 아이디 사용
                .requestEmail()
                .build();

        // api 클라이언트 생성
        BasicInfo.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() { // 로그인버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(BasicInfo.mGoogleApiClient);
                startActivityForResult(intent,100); // 100 이라는 값으로 인텐트 실행
            }
        });

        anon_btn = (Button) findViewById(R.id.anon_btn);
        anon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicInfo.firebaseUser = null;
                BasicInfo.mFirebaseAuth = null;
                Toast.makeText(getApplicationContext(), "익명 계정으로 로그인 하셨습니다.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AuthActivity.this, Board_MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { // Connection실패시
        Toast.makeText(getApplicationContext(),"인증이 실패하였습니다. 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();
            if(result.isSuccess()){ // 제대로 넘어왔는지 확인
                firebaseWithGoogle(account);
            } else{
                Toast.makeText(getApplicationContext(),"인증이 실패하였습니다. 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseWithGoogle(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

        BasicInfo.mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"로그인이 실패하였습니다. 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            BasicInfo.firebaseUser = task.getResult().getUser();
                            Toast.makeText(getApplicationContext(), BasicInfo.firebaseUser.getEmail() + "계정으로 로그인 하셨습니다.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AuthActivity.this, Board_MainActivity.class));
                            finish();
                        }
                    }
                });

    }
}
