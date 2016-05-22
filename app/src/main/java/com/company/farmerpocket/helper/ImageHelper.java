package com.company.farmerpocket.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.company.farmerpocket.FarmerPocketApplication;

/**
 * Created by GHY on 2016/4/29.
 * 图片加载助手类
 */
public class ImageHelper {

    // String url = "res:// /" + R.mipmap.ic_launcher;
    // String url_gif = "http://img3.3lian.com/2006/013/08/20051103121420947.gif";

    public interface OnLoadCompleteListener{
        void onLoadComplete();
    }

    private static ImageHelper mInstance;

    private Context mContext;


    public ImageHelper(Context mContext) {
        this.mContext = mContext;
    }

    public static ImageHelper getInstance() {
        if (mInstance == null) {
            synchronized (ImageHelper.class) {
                if (mInstance == null) {
                    mInstance = new ImageHelper(FarmerPocketApplication.getInstance().getApplicationContext());
                }
            }
        }
        return mInstance;
    }


    /**
     * 使用Glide加载imageView
     * @param imageView
     * @param url
     */
    public void loadImage(ImageView imageView,String url){
        Glide.with(mContext).load(url).into(imageView);
    }

    /**
     * 使用Glide加载imageView
     * @param imageView
     * @param url
     * @param listener
     */
    public void loadImage(ImageView imageView, String url, final OnLoadCompleteListener listener){
        Glide.with(mContext).load(url).into(new GlideDrawableImageViewTarget(imageView){
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                if (listener != null) listener.onLoadComplete();
            }
        });
    }

}
