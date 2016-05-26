package com.company.farmerpocket.bean;

import java.util.List;

/**
 * Created by GHY on 2016/5/25.
 */
public class JifenBean {

    /**
     * point : 操作成功
     * status : 1
     * data : [{"shopId":"6","shopName":"积分兑换测试","shopIntegral":"1","shopTime":"1463890500","shopImg":"http://114.215.95.55:9995/Uploads/Picture/2016-05-22/574133148d6c9.png"},{"shopId":"5","shopName":"https://item.taobao.com/item.htm?spm=a230r.1.14.20.7uwt92&id=39431335100&ns=1&abbucket=11#detail","shopIntegral":"1000","shopTime":"1447864560","shopImg":"http://114.215.95.55:9995/Uploads/Picture/2015-11-19/564ca9543602c.jpg"},{"shopId":"4","shopName":"茶叶","shopIntegral":"100","shopTime":"1447084860","shopImg":"http://114.215.95.55:9995/Uploads/Picture/2015-11-09/5640bff826c4e.jpg"},{"shopId":"3","shopName":"佐丹奴毛衣 男秋冬装厚实双线提花毛线衣 男士套头针织衫01055504 ","shopIntegral":"1200","shopTime":"1446734700","shopImg":"http://114.215.95.55:9995/Uploads/Picture/2015-11-05/563b6bbed9cec.jpg"},{"shopId":"1","shopName":"韩版女手机包斜跨小包零钱包","shopIntegral":"20","shopTime":"1446561660","shopImg":"http://114.215.95.55:9995/Uploads/Picture/2016-05-14/5737307d9b60d.jpg"}]
     */

    private String point;
    private int status;
    /**
     * shopId : 6
     * shopName : 积分兑换测试
     * shopIntegral : 1
     * shopTime : 1463890500
     * shopImg : http://114.215.95.55:9995/Uploads/Picture/2016-05-22/574133148d6c9.png
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
        private String shopIntegral;
        private String shopTime;
        private String shopImg;

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

        public String getShopIntegral() {
            return shopIntegral;
        }

        public void setShopIntegral(String shopIntegral) {
            this.shopIntegral = shopIntegral;
        }

        public String getShopTime() {
            return shopTime;
        }

        public void setShopTime(String shopTime) {
            this.shopTime = shopTime;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }
    }
}
