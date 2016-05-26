package com.company.farmerpocket.adapter;

import android.content.Context;

import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.baserecycler.BaseQuickAdapter;
import com.company.farmerpocket.adapter.baserecycler.BaseViewHolder;
import com.company.farmerpocket.bean.JifenBean;

import java.util.List;

/**
 * Created by GHY on 2016/5/24.
 * 积分商城adapter
 */
public class JifenShopRecyclerAdapter extends BaseQuickAdapter<JifenBean.DataEntity> {

    public JifenShopRecyclerAdapter(Context context, List<JifenBean.DataEntity> data) {
        super(context, R.layout.item_jifen_recycler_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JifenBean.DataEntity item) {
        helper.setImageUrl(R.id.item_jifen_iv,item.getShopImg());
        helper.setText(R.id.item_jifen_tv_title,item.getShopName());
        helper.setText(R.id.item_jifen_tv_price,item.getShopIntegral()+"积分");
    }
}
