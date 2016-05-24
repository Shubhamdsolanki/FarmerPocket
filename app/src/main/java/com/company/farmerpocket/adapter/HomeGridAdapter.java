package com.company.farmerpocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.farmerpocket.R;
import com.company.farmerpocket.bean.HomeBean;
import com.company.farmerpocket.helper.ImageHelper;

import java.util.List;

/**
 * Created by GHY on 2016/5/24.
 */
public class HomeGridAdapter extends BaseAdapter {

    private Context context;
    private List<HomeBean.DataEntity.ShopEntity> shopEntityList;

    public HomeGridAdapter(Context context, List<HomeBean.DataEntity.ShopEntity> shopEntityList) {
        this.context = context;
        this.shopEntityList = shopEntityList;
    }

    @Override
    public int getCount() {
        return shopEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_grid_adapter,null);
        }
        ImageView imageView = ViewHolder.get(convertView,R.id.item_home_iv);
        TextView tvTitle = ViewHolder.get(convertView,R.id.item_home_tv_title);
        TextView tvPrice = ViewHolder.get(convertView,R.id.item_home_tv_price);
        TextView tvOldPrice = ViewHolder.get(convertView,R.id.item_home_tv_old_price);

        ImageHelper.getInstance().loadImage(imageView,shopEntityList.get(position).getShopImg());
        tvTitle.setText(shopEntityList.get(position).getShopName());
        tvPrice.setText(shopEntityList.get(position).getShopNewPrice());
        tvOldPrice.setText(shopEntityList.get(position).getShopOrdPrice());

        return convertView;
    }
}
