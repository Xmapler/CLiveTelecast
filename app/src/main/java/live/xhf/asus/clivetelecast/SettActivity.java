package live.xhf.asus.clivetelecast;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import live.xhf.asus.clivetelecast.base.BaseActivity;

public class SettActivity extends BaseActivity {

    private Button exit;
    private SharedPreferences spf;
    private ImageView title_back;
    private TextView titleInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);

        initHeader();
        initControl();

    }

    private void initControl() {
        exit = (Button)findViewById(R.id.exit);
            exit.setOnClickListener(this);
        title_back = (ImageView)findViewById(R.id.title_back);
            title_back.setOnClickListener(this);
        titleInfo = (TextView)findViewById(R.id.titleInfo);
        titleInfo.setText("设置");
    }

    @Override
    public void initHeader() {
        //初始化头部控件
        initHeaderWidget();


    }

    @Override
    public void initWidget() {

    }

    @Override
    public void setWidgetState() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.exit:
                //弹框
                AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
                builder.setTitle("提示"); //设置标题
                builder.setMessage("确定退出吗？"); //设置内容
                builder.setIcon(R.mipmap.logo);//设置图标，图片id即可

                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spf = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.clear();
                        edit.commit();
                        dialog.dismiss();
                        startActivity(new Intent(SettActivity.this,MainActivity.class));
                        finish();
                    }
                });
                //参数都设置完成了，创建并显示出来
                builder.create().show();
                break;
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
                break;
        }
    }

}
