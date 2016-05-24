package com.company.farmerpocket.api;

/**
 * Created by GHY on 2016/5/3.
 * 把外层的point 和 status 提出来
 * point：请求成功或失败，status：状态码，还可以加字段如msg表示提示信息等
 */
public class ResponseData {

    public String point;
    public int status;
    public String msg;

    public String getPoint() {
        return point;
    }

    public void setPoint(String desc) {
        this.point = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "point='" + point + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
