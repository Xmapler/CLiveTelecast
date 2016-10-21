package live.xhf.asus.clivetelecast.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import live.xhf.asus.clivetelecast.R;
import live.xhf.asus.clivetelecast.bean.Home_Hot;
import live.xhf.asus.clivetelecast.glideiv.GlideRoundTransform;

/**
 * Created by asus on 2016/10/11.
 */
public class MyHotLvAdapter extends BaseAdapter {

    private RequestManager glideRequest;
    List<Home_Hot.Content.LList> llist;
    Context context;

    public MyHotLvAdapter(List<Home_Hot.Content.LList> llist, Context context) {
        this.llist = llist;
        this.context = context;
    }

 /*   public void flush(List<Home_Hot.Content.LList> llist){
        this.llist.addAll(llist);
        this.notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return llist.size();
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
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.hot_lv_item,null);
            holder.item_bg = (ImageView) convertView.findViewById(R.id.item_bg);
            holder.item_icon = (ImageView)convertView.findViewById(R.id.item_icon);
            holder.item_name = (TextView)convertView.findViewById(R.id.item_name);
            holder.item_address = (TextView)convertView.findViewById(R.id.item_address);
            holder.item_status = (TextView)convertView.findViewById(R.id.item_status);
            holder.item_livename = (TextView)convertView.findViewById(R.id.item_livename);
            holder.l = (LinearLayout)convertView.findViewById(R.id.item_l);
          /*  holder.target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                      holder.item_bg.setImageBitmap(resource);
                }
            };*/
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        glideRequest.load(llist.get(position).bigheadimg).placeholder(R.mipmap.default_dynamic_bg).into(holder.item_bg);
        glideRequest.load(llist.get(position).bigheadimg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.default_dynamic_bg).transform(new GlideRoundTransform(context,6)).into(holder.item_bg);
        Glide.with(context).load(llist.get(position).smallheadimg).into(holder.item_icon);
        holder.item_name.setText(llist.get(position).name);
        holder.item_address.setText(llist.get(position).place);
        holder.item_status.setText("LIVE "+llist.get(position).online);
        holder.item_status.setTextColor(Color.RED);
        holder.item_status.setTextSize(13);
        if(llist.get(position).livename != null ){
            holder.item_livename.setText(llist.get(position).livename);
            holder.l.setBackgroundResource(R.mipmap.abc_list_selector_disabled_holo_dark);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView item_bg,item_icon;
        TextView item_name,item_address,item_status,item_livename;
        LinearLayout l;
//        SimpleTarget target;
    }
}
