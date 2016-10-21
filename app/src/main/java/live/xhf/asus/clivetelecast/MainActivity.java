package live.xhf.asus.clivetelecast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import live.xhf.asus.clivetelecast.login.PhoneLoginActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView xieyi;
    private ImageView Q, Weixin, phone;
    private Platform qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this);
        initControl();

    }

    private void initControl() {
        xieyi = (TextView) findViewById(R.id.xieyi);
        xieyi.setOnClickListener(this);
        Q = (ImageView) findViewById(R.id.QQ);
        Q.setOnClickListener(this);
        Weixin = (ImageView) findViewById(R.id.WeiXin);
        Weixin.setOnClickListener(this);
        phone = (ImageView) findViewById(R.id.Phone);
        phone.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QQ:
                qq = ShareSDK.getPlatform(QQ.NAME);
                qq.authorize();
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        String userName = qq.getDb().getUserName();
                        String icon = qq.getDb().getUserIcon();

                        SharedPreferences spf = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putString("QQname", userName);
                        edit.putString("QQicon", icon);
                        edit.putBoolean("flag",false);
                        edit.commit();

                        startActivity(new Intent(MainActivity.this,CLiveActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });

                break;
            case R.id.WeiXin:

                break;
            case R.id.Phone:

                startActivity(new Intent(MainActivity.this, PhoneLoginActivity.class));

                break;
            case R.id.xieyi:

                break;
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}
