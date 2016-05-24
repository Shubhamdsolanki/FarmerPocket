package com.company.farmerpocket.adapter;

import android.content.Context;

import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.baserecycler.BaseQuickAdapter;
import com.company.farmerpocket.adapter.baserecycler.BaseViewHolder;
import com.company.farmerpocket.bean.CommonShopBean;

import java.util.List;

/**
 * Created by GHY on 2016/5/24.
 */
public class CommonRecyclerAdapter extends BaseQuickAdapter<CommonShopBean.DataEntity> {

    public CommonRecyclerAdapter(Context context,List<CommonShopBean.DataEntity> data) {
        super(context, R.layout.item_common_recycler_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonShopBean.DataEntity item) {
        helper.setImageUrl(R.id.item_home_iv,item.getShopImg());
        helper.setText(R.id.item_home_tv_title,item.getShopName());
        helper.setText(R.id.item_home_tv_price,item.getShopNewPrice());
        helper.setText(R.id.item_home_tv_old_price,item.getShopOrdPrice());
    }
}
