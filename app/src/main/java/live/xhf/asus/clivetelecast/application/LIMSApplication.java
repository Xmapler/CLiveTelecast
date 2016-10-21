package live.xhf.asus.clivetelecast.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import live.xhf.asus.clivetelecast.database.DbManager;

/**
 * Created by asus on 2016/10/17.
 */
public class LIMSApplication extends Application {
    public static RequestQueue rQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();

        manager = new DbManager(getApplicationContext());
        /*
         * Volley配置
         * 建立Volley的Http请求队列
         */
        rQueue = Volley.newRequestQueue(getApplicationContext());
    }
    private static Context context;
    public static DbManager manager;

    public static Context getContext(){
        return context;

    }
    // 开放Volley的HTTP请求队列接口
    public static RequestQueue getRequestQueue() {
        return rQueue;
    }
}
