package live.xhf.asus.clivetelecast.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import live.xhf.asus.clivetelecast.CLiveActivity;
import live.xhf.asus.clivetelecast.R;

public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back, Q, Weixin;// 后退 、 第三方QQ 、 微信
    private EditText phoneName,phonePass; // （手机号、 密码）输入框
    private Button phone_Login; // 登录按钮
    private TextView phone_regist,phone_forget;// 注册 、 忘记密码
    private Platform qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        ShareSDK.initSDK(this);
        initControl();
    }

    private void initControl() {
        back = (ImageView)findViewById(R.id.back);
            back.setOnClickListener(this); //后退

        Q = (ImageView)findViewById(R.id.Qq);
            Q.setOnClickListener(this);//第三方QQ

        Weixin = (ImageView)findViewById(R.id.WeiX);
            Weixin.setOnClickListener(this);//微信

        phone_regist = (TextView)findViewById(R.id.phone_regist);
            phone_regist.setOnClickListener(this);//账号注册

        phone_forget = (TextView)findViewById(R.id.phone_forget);
            phone_forget.setOnClickListener(this);//忘记密码

        phone_Login = (Button)findViewById(R.id.phone_login);
            phone_Login.setOnClickListener(this);// 登录按钮

        phoneName = (EditText)findViewById(R.id.phoneName);//手机号
        phonePass = (EditText)findViewById(R.id.phonePass);//密码

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
                break;
            case R.id.phone_login:

                break;
            case R.id.phone_regist:

                    startActivity(new Intent(PhoneLoginActivity.this,RegisterActivity.class));
                break;
            case R.id.phone_forget:

                break;
            case R.id.Qq:
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
                        edit.commit();

                        startActivity(new Intent(PhoneLoginActivity.this,CLiveActivity.class));
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
        }
    }
}
