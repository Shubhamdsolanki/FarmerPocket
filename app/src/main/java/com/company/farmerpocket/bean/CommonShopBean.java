package com.company.farmerpocket.bean;

import java.util.List;

/**
 * Created by GHY on 2016/5/24.
 * 通过商品列表bean
 */
public class CommonShopBean {

    /**
     * point : 操作成功
     * status : 1
     * data : [{"shopId":"22","shopName":"铁观音 安溪铁观音 茶叶 乌龙茶  中闽弘泰铁观音 礼盒装250g秋茶","shopOrdPrice":"0.00","shopNewPrice":"50.00","shopSource":1,"shopUrl":"http://114.215.95.55:9995/index.php?s=/Home/Goods/appjump/id/22/qudao/123456789.html","shopImg":"http://img.alicdn.com/bao/uploaded/i2/TB1DeCAJVXXXXcCXXXXXXXXXXXX_!!0-item_pic.jpg","shopNum_iid":"41162455062"}]
     */

    private String point;
    private int status;
    /**
     * shopId : 22
     * shopName : 铁观音 安溪铁观音 茶叶 乌龙茶  中闽弘泰铁观音 礼盒装250g秋茶
     * shopOrdPrice : 0.00
     * shopNewPrice : 50.00
     * shopSource : 1
     * shopUrl : http://114.215.95.55:9995/index.php?s=/Home/Goods/appjump/id/22/qudao/123456789.html
     * shopImg : http://img.alicdn.com/bao/uploaded/i2/TB1DeCAJVXXXXcCXXXXXXXXXXXX_!!0-item_pic.jpg
     * shopNum_iid : 41162455062
     */

    private List<DataEntity> data;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String shopId;
        private String shopName;
        private String shopOrdPrice;
        private String shopNewPrice;
        private int shopSource;
        private String shopUrl;
        private String shopImg;
        private String shopNum_iid;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopOrdPrice() {
            return shopOrdPrice;
        }

        public void setShopOrdPrice(String shopOrdPrice) {
            this.shopOrdPrice = shopOrdPrice;
        }

        public String getShopNewPrice() {
            return shopNewPrice;
        }

        public void setShopNewPrice(String shopNewPrice) {
            this.shopNewPrice = shopNewPrice;
        }

        public int getShopSource() {
            return shopSource;
        }

        public void setShopSource(int shopSource) {
            this.shopSource = shopSource;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public void setShopUrl(String shopUrl) {
            this.shopUrl = shopUrl;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public String getShopNum_iid() {
            return shopNum_iid;
        }

        public void setShopNum_iid(String shopNum_iid) {
            this.shopNum_iid = shopNum_iid;
        }
    }
}
