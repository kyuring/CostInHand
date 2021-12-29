package org.techtown.costinhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public static String userid;
    public static String userName;
    // 프래그 먼트 사용하기 위한 선언
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment_home fragment_home = new Fragment_home();
    private Fragment_mypage fragment_mypage = new Fragment_mypage();
    private Fragment_more fragment_more = new Fragment_more();
    LinearLayout LinearLayout_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 홈버튼 클릭
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 프레임 레이아웃 안에 있는 렐러티브레이아웃(프래그먼트) 변경 : transaction.replace
        transaction.replace(R.id.fragment, fragment_home).commitAllowingStateLoss();

        Intent intent = getIntent();
        userid = intent.getExtras().getString("userID");
        userName = intent.getExtras().getString("userName");
        // BottomNavigation 사용
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new MenuClickEventListener());
    }
    // BottomNavigation 이벤트 처리
    class MenuClickEventListener implements NavigationBarView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.button_bottom_home:
                    //홈버튼 클릭
                    transaction.replace(R.id.fragment, fragment_home).commitAllowingStateLoss();
                    break;
                case R.id.button_bottom_my:
                    // 마이버튼 클릭
                    transaction.replace(R.id.fragment, fragment_mypage).commitAllowingStateLoss();
                    break;
                case R.id.button_bottom_more:
                    // 몰 버튼 클릭
                    transaction.replace(R.id.fragment, fragment_more).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
    public static void alert(Context context, String detail, String titleText, int buttonCount){
        AlertDialogActivity dlg = new AlertDialogActivity(context, detail, titleText, buttonCount);
        dlg.setCancelable(false);
        dlg.show();
    }
}