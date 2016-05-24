package com.company.farmerpocket.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GHY on 2016/5/24.
 */
public class HomeBean {


    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * carouselImgId : 45
         * carouselImgName : 轮播二
         * carouselImgUrl : http://114.215.95.55:9995/Uploads/Picture/2015-11-12/5644a132acbf2.jpg
         * carouselImgType : 1
         * carouselUrl :
         */

        private List<CarouselImgEntity> carouselImg;
        /**
         * classId : 40
         * className : 茶叶
         * classImgUrl : http://114.215.95.55:9995/Uploads/Picture/2015-12-06/5663fba9cd0ac.jpg
         */

        @SerializedName("class")
        private List<ClassEntity> classX;
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

        private List<ShopEntity> shop;

        public List<CarouselImgEntity> getCarouselImg() {
            return carouselImg;
        }

        public void setCarouselImg(List<CarouselImgEntity> carouselImg) {
            this.carouselImg = carouselImg;
        }

        public List<ClassEntity> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassEntity> classX) {
            this.classX = classX;
        }

        public List<ShopEntity> getShop() {
            return shop;
        }

        public void setShop(List<ShopEntity> shop) {
            this.shop = shop;
        }

        public static class CarouselImgEntity {
            private int carouselImgId;
            private String carouselImgName;
            private String carouselImgUrl;
            private int carouselImgType;
            private String carouselUrl;

            public int getCarouselImgId() {
                return carouselImgId;
            }

            public void setCarouselImgId(int carouselImgId) {
                this.carouselImgId = carouselImgId;
            }

            public String getCarouselImgName() {
                return carouselImgName;
            }

            public void setCarouselImgName(String carouselImgName) {
                this.carouselImgName = carouselImgName;
            }

            public String getCarouselImgUrl() {
                return carouselImgUrl;
            }

            public void setCarouselImgUrl(String carouselImgUrl) {
                this.carouselImgUrl = carouselImgUrl;
            }

            public int getCarouselImgType() {
                return carouselImgType;
            }

            public void setCarouselImgType(int carouselImgType) {
                this.carouselImgType = carouselImgType;
            }

            public String getCarouselUrl() {
                return carouselUrl;
            }

            public void setCarouselUrl(String carouselUrl) {
                this.carouselUrl = carouselUrl;
            }
        }

        public static class ClassEntity {
            private String classId;
            private String className;
            private String classImgUrl;

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getClassImgUrl() {
                return classImgUrl;
            }

            public void setClassImgUrl(String classImgUrl) {
                this.classImgUrl = classImgUrl;
            }
        }

        public static class ShopEntity {
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
}
