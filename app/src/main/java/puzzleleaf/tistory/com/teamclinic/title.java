package puzzleleaf.tistory.com.teamclinic;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import puzzleleaf.tistory.com.teamclinic.Review.Review;
import puzzleleaf.tistory.com.teamclinic.Splash.Splash;
import puzzleleaf.tistory.com.teamclinic.Story.Story;
import puzzleleaf.tistory.com.teamclinic.help.Help;
import puzzleleaf.tistory.com.teamclinic.processing.StageData;
import puzzleleaf.tistory.com.teamclinic.processing.StarTitle;
import puzzleleaf.tistory.com.teamclinic.setting.Setting;

/**
 * Created by cmtyx on 2017-04-08.
 */

//새로운 코드를 추가했다.
public class title extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle dtToggle;
    boolean drawerChcek = false;
    StageData setData ;
    int i=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //화면 꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
        buttonInit();
        setPermission();



        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, new StarTitle());
        fragmentTransaction.commit();

        Intent intent = new Intent(this, Splash.class);
        startActivity(intent);
    }


    //초기화
    void init()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerChcek = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerChcek = false;
            }
        };

        drawerLayout.addDrawerListener(dtToggle);
        dtToggle.syncState();

        setData = new StageData(getApplicationContext());
        setData.getStage();
    }

    void buttonInit()
    {
        ImageView storyBtn = (ImageView) findViewById(R.id.story);
        storyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Story.class);
                startActivity(intent);
            }
        });

        ImageView reviewBtn = (ImageView) findViewById(R.id.review);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Review.class);
                startActivity(intent);
            }
        });

        ImageView helpBtn = (ImageView) findViewById(R.id.reward);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
        });

        ImageView settingBtn = (ImageView) findViewById(R.id.setting);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });
    }

    void setPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {

                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA
                        },
                        1);

                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다

            }
        }

    }


    @Override
    public void onBackPressed() {
        if(drawerChcek)
            drawerLayout.closeDrawers();
        else {
            super.onBackPressed();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
// 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
// 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }
}
