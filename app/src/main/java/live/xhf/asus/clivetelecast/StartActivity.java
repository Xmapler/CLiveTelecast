package live.xhf.asus.clivetelecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private SharedPreferences spf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
                    spf = getSharedPreferences("LoginInfo",MODE_PRIVATE);
                    if(!(spf.getBoolean("flag",true))){

                        startActivity(new Intent(StartActivity.this,CLiveActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(StartActivity.this,MainActivity.class));
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
