package live.xhf.asus.clivetelecast.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.cyberplayer.core.BVideoView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.adapter.MyPopAdapter;
import live.xhf.asus.clivetelecast.application.LIMSApplication;
import live.xhf.asus.clivetelecast.bean.Pop_gift;
import live.xhf.asus.clivetelecast.bean.Student;
import live.xhf.asus.clivetelecast.play.info.HeartLayout;


public class VideoViewPlayingActivity extends Activity implements BVideoView.OnPreparedListener, BVideoView.OnCompletionListener,
        BVideoView.OnErrorListener, BVideoView.OnInfoListener, BVideoView.OnPlayingBufferCacheListener,View.OnClickListener {
    private final String TAG = "VideoViewPlayingActivity";

    private String path = "http://api.vas.jufan.tv/cgi/gift/list?r=vtwyunpml&sign=B3E3A6B2B76D88C8D6425A2520589E1F7408FE64";
    /**
     * 您的AK
     * 请到http://console.bce.baidu.com/iam/#/iam/accesslist获取
     */
    private String AK = "3d6104a32fc9483ea76116007a976242";   //请录入您的AK !!!

    private String mVideoSource = null;

    /**
     * 记录播放位置
     */
    private int mLastPos = 0;
    private Uri uriPath;
    private ImageView play_msg,play_chat,play_share,play_gift,play_close;
    private HeartLayout heartLayout;
    private LinearLayout play_linear,linearLayout;
    private ImageView play_image,play_concern;
    private TextView play_name,play_num,play_room,play_date;
    private String name;
    private int num;
    private String play_icon;
    private int play_room1;


    /**
     * 播放状态
     */
    private enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }

    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;

    private BVideoView mVV = null;

    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;

    private final Object SYNC_Playing = new Object();

    private PowerManager.WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "VideoViewPlayingActivity";

    private boolean mIsHwDecode = false;

    private final int EVENT_PLAY = 0;
    private final int UI_EVENT_UPDATE_CURRPOSITION = 1;
    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PLAY:
                    /**
                     * 如果已经播放了，等待上一次播放结束
                     */
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    /**
                     * 设置播放url
                     */
                    mVV.setVideoPath(mVideoSource);

                    /**
                     * 续播，如果需要如此
                     */
                    if (mLastPos > 0) {

                        mVV.seekTo(mLastPos);
                        mLastPos = 0;
                    }

                    /**
                     * 显示或者隐藏缓冲提示
                     */
                    mVV.showCacheInfo(true);

                    /**
                     * 开始播放
                     */
                    mVV.start();

                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                default:
                    break;
            }
        }
    }

    Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_EVENT_UPDATE_CURRPOSITION:
                    mUIHandler.sendEmptyMessageDelayed(UI_EVENT_UPDATE_CURRPOSITION, 200);
                    break;
            }
        }
    };
    private List<Pop_gift.PGift.JUF.PList> plist;
    private GridView pop_grid;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pop_grid.setAdapter(new MyPopAdapter(VideoViewPlayingActivity.this,plist));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view_playing);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);

        //初始化控件
        initUI();

        mIsHwDecode = getIntent().getBooleanExtra("isHW", false);
        uriPath = getIntent().getData();

        // 直播室信息
        name = getIntent().getStringExtra("play_name");
            play_name.setText(name);
        num = getIntent().getIntExtra("play_num",0);
            play_num.setText(num+"");
        play_icon = getIntent().getStringExtra("play_icon");
            Glide.with(this).load(play_icon).into(play_image);
        play_room1 = getIntent().getIntExtra("play_room",0);
            play_room.setText("ID:"+play_room1);

        //显示当前日期
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");
        String str = sdformat.format(date);

        play_date.setText(str);

        Log.e("主播地址==========", uriPath.toString());
        if (null != uriPath) {
            String scheme = uriPath.getScheme();
            if (null != scheme) {
                mVideoSource = uriPath.toString();
            } else {
                mVideoSource = uriPath.getPath();
            }
        }
        /**
         * 开启后台事件处理线程
         */
        mHandlerThread = new HandlerThread("event handler thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());

    }

    private void getData() {
        RequestQueue rQueue = Volley.newRequestQueue(VideoViewPlayingActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET,path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                System.out.println("返回的数据"+s);
                Gson gson = new Gson();
                Pop_gift pop = gson.fromJson(s,Pop_gift.class);
                plist = pop.content.jufan.get(0).list;
                Log.d("=====plist",plist.toString());

                handler.sendEmptyMessage(0x123);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(request);
        rQueue.start();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        play_share = (ImageView) findViewById(R.id.play_share);
            play_share.setOnClickListener(this);
        play_close = (ImageView) findViewById(R.id.play_close);
            play_close.setOnClickListener(this);
        play_chat = (ImageView)findViewById(R.id.play_chat);
            play_chat.setOnClickListener(this);
        play_gift = (ImageView)findViewById(R.id.play_gift);
            play_gift.setOnClickListener(this);
        play_msg = (ImageView)findViewById(R.id.play_msg);
            play_msg.setOnClickListener(this);

        play_image = (ImageView)findViewById(R.id.play_image);
            play_image.setOnClickListener(this);
        play_concern = (ImageView)findViewById(R.id.play_concern);
            play_concern.setOnClickListener(this);
        play_name = (TextView)findViewById(R.id.play_name);
        play_num = (TextView)findViewById(R.id.play_numers);
        play_room = (TextView)findViewById(R.id.play_room);
        play_date = (TextView)findViewById(R.id.play_date);

        play_linear = (LinearLayout)findViewById(R.id.play_linearLayout);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);// 底部图片容器

        heartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        findViewById(R.id.member_send_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_linear.setVisibility(View.GONE);
                heartLayout.addFavor();
            }
        });
        /**
         * 设置ak
         */
        BVideoView.setAK(AK);

        /**
         *获取BVideoView对象
         */
        mVV = (BVideoView) findViewById(R.id.video_view);

        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnInfoListener(this);

        /**
         * 设置解码模式
         */
        mVV.setDecodeMode(mIsHwDecode ? BVideoView.DECODE_HW : BVideoView.DECODE_SW);
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        /**
         * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
         */
        if (mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARED) {
            mLastPos = mVV.getCurrentPosition();
            mVV.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
        /**
         * 发起一次播放任务,当然您不一定要在这发起
         */
        mEventHandler.sendEmptyMessage(EVENT_PLAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 退出后台事件处理线程
         */
        mHandlerThread.quit();
    }

    @Override
    public boolean onInfo(int what, int extra) {
        // TODO Auto-generated method stub
        switch (what) {
            /**
             * 开始缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_START:
                break;
            /**
             * 结束缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_END:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     */
    @Override
    public void onPlayingBufferCache(int percent) {
        // TODO Auto-generated method stub

    }

    /**
     * 播放出错
     */
    @Override
    public boolean onError(int what, int extra) {
        // TODO Auto-generated method stub
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion() {
        // TODO Auto-generated method stub
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
    }

    /**
     * 准备播放就绪
     */
    @Override
    public void onPrepared() {
        // TODO Auto-generated method stub
        mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
    }

    @Override
    public void onClick(View v) {
        List<Pop_gift.PGift.JUF.PList> plist;
        switch (v.getId()){
            case R.id.play_close:
                    finish();
                    overridePendingTransition(R.anim.anim_back_in,R.anim.anim_back_out);
                break;
                        //关注
            case R.id.play_concern:

                Student stu = new Student();
                stu.setMidheadimg(play_icon);
                stu.setName(name);

                LIMSApplication.manager.insert(stu);
                break;

            case R.id.play_msg:
                play_linear.setVisibility(View.VISIBLE);
                break;

            case R.id.play_chat:
                play_linear.setVisibility(View.GONE);

                break;

            case R.id.play_image:

                PersonInfo();
                break;

            case R.id.play_gift:
                play_linear.setVisibility(View.GONE);
                new Thread(){
                    @Override
                    public void run() {
                        getData();
                    }
                }.start();
                showPopWindow();
                break;

            case R.id.play_share:
                play_linear.setVisibility(View.GONE);

                ShareSDK.initSDK(this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle("聚范直播");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl(uriPath + "");
                // text是分享文本，所有平台都需要这个字段
                oks.setText(name);
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl(uriPath + "");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("聚范直播");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite("ShareSDK");
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(uriPath + "");

                // 启动分享GUI
                oks.show(this);
                break;
        }
    }

    private void PersonInfo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View dd = View.inflate(VideoViewPlayingActivity.this, R.layout.dialog_layout, null);
        dialog.setView(dd);

        ImageView guan = (ImageView) dd.findViewById(R.id.close_info);
        //头像
        ImageView tou = (ImageView) dd.findViewById(R.id.touxiang_info);
        Glide.with(this).load(play_icon).into(tou);
        //名字
        TextView info_name = (TextView) dd.findViewById(R.id.name_info);
        info_name.setText(name);
        //地址
        TextView city = (TextView) dd.findViewById(R.id.city);


        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //显示出来
        dialog.show();

    }

    private void showPopWindow() {
        //layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindowlayout,null);

        pop_grid = (GridView)view.findViewById(R.id.pop_grid);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        //设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 必须要给调用这个方法，否则点击popWindow以外部分，popWindow不会消失
        // window.setBackgroundDrawable(new BitmapDrawable());

        //实例化一个color颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 在参照的View控件下方显示
        // window.showAsDropDown(MainActivity.this.findViewById(R.id.start));

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(VideoViewPlayingActivity.this.findViewById(R.id.play_gift),
                Gravity.BOTTOM, 0, 0);

        //检验popwindow里的button是否可以点击

        pop_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("第一个按钮被点击了");

            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });


        pop_grid.setOnKeyListener(new View.OnKeyListener() {//按下android回退物理键 PopipWindow消失解决

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (window != null && window.isShowing()) {
                        window.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
