package hznu.edu.cn.android_final.beans;

import java.io.Serializable;

public class DynamicItem implements Serializable {
//  头部信息
    private String head;
    private String send_name;
    private String send_level;
    private String send_sex;
    private String send_rank;
    private String send_time;
//  主体内容
    private String send_content;
    private String send_image1;
    private String send_image2;
    private String send_image3;
//  评论部分
    private int commit_num;
    private int like_num;

    public DynamicItem(String head, String send_name, String send_level, String send_sex, String send_rank, String send_time, String send_content, String send_image1, String send_image2, String send_image3, int commit_num, int like_num) {
        this.head = head;
        this.send_name = send_name;
        this.send_level = send_level;
        this.send_sex = send_sex;
        this.send_rank = send_rank;
        this.send_time = send_time;
        this.send_content = send_content;
        this.send_image1 = send_image1;
        this.send_image2 = send_image2;
        this.send_image3 = send_image3;
        this.commit_num = commit_num;
        this.like_num = like_num;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_level() {
        return send_level;
    }

    public void setSend_level(String send_level) {
        this.send_level = send_level;
    }

    public String getSend_sex() {
        return send_sex;
    }

    public void setSend_sex(String send_sex) {
        this.send_sex = send_sex;
    }

    public String getSend_rank() {
        return send_rank;
    }

    public void setSend_rank(String send_rank) {
        this.send_rank = send_rank;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getSend_content() {
        return send_content;
    }

    public void setSend_content(String send_content) {
        this.send_content = send_content;
    }

    public String getSend_image1() {
        return send_image1;
    }

    public void setSend_image1(String send_image1) {
        this.send_image1 = send_image1;
    }

    public String getSend_image2() {
        return send_image2;
    }

    public void setSend_image2(String send_image2) {
        this.send_image2 = send_image2;
    }

    public String getSend_image3() {
        return send_image3;
    }

    public void setSend_image3(String send_image3) {
        this.send_image3 = send_image3;
    }

    public int getCommit_num() {
        return commit_num;
    }

    public void setCommit_num(int commit_num) {
        this.commit_num = commit_num;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

}
