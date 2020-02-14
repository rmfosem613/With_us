package com.example.with_us;

import android.app.Activity;
import android.widget.Toast;

// 두번 클릭 시 앱 종료 클래스
class BackPressCloseHandler {
    //초기 시간 설정
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    BackPressCloseHandler(Activity context) {
        this.activity = context;
    }
    void onBackPressed() {
        //한번 클릭 후 2초 초과 : 앱 종료 안됨
        //2초보다 작거나 같다 : 앱 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    //앱 종료 알림 토스트 설정
    private void showGuide() {
        toast = Toast.makeText(activity, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}


