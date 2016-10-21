package live.xhf.asus.clivetelecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.bean.Home_find;
import live.xhf.asus.clivetelecast.glideiv.GlideRoundTransform;

/**
 * Created by asus on 2016/10/18.
 */
public class MyConAdapter extends BaseAdapter {

    private RequestManager glideRequest;
    List<Home_find.FindBean.FBean> findlist;
    Context context;

    public MyConAdapter(List<Home_find.FindBean.FBean> findlist, Context context) {
        this.findlist = findlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        glideRequest = Glide.with(context);
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.con_gv_item,null);
            holder.con_bg = (ImageView)convertView.findViewById(R.id.con_img);
            holder.con_name = (TextView)convertView.findViewById(R.id.con_name);
            holder.con_address = (TextView)convertView.findViewById(R.id.con_address);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(findlist.get(position).midheadimg).placeholder(R.mipmap.li_default_head).into(holder.find_bg);
        glideRequest.load(findlist.get(position).bigheadimg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.li_default_head).transform(new GlideRoundTransform(context,6)).into(holder.con_bg);
        holder.con_name.setText(findlist.get(position).name);
        holder.con_address.setText(findlist.get(position).place);
        return convertView;
    }

    class ViewHolder{
        TextView con_name;
        TextView con_address;
        ImageView con_bg;
    }
}
