package com.ndm.api.entity;

import java.util.Date;

/* 读取表结构信息 */
public class UserProfile {
    
    public UserProfile() {

    }
    
    /*   */
    private Long id;
    /*   */
    private String nickName;
    /* 0为设置 1 男 2 女 3 不确定 4 保密  */
    private Integer sex;
    /*   */
    private Long tel;
    /*   */
    private String email;
    /*   */
    private Date brithday;
    /*   */
    private Integer address;
    /*   */
    private String addInfo;
    /*   */
    private Long accountId;
    /* 身高 精确到毫米  */
    private Integer height;
    /* 头像  */
    private String image;
    /* 体重 精确到克  */
    private Integer weight;
    /* 身份证号  */
    private String idCard;
    /*   */
    private Integer idType;
    /* 签名  */
    private String signature;
    
    public Long getId() {
        return this.id;
    }
    public String getNickName() {
        return this.nickName;
    }
    public Integer getSex() {
        return this.sex;
    }
    public Long getTel() {
        return this.tel;
    }
    public String getEmail() {
        return this.email;
    }
    public Date getBrithday() {
        return this.brithday;
    }
    public Integer getAddress() {
        return this.address;
    }
    public String getAddInfo() {
        return this.addInfo;
    }
    public Long getAccountId() {
        return this.accountId;
    }
    public Integer getHeight() {
        return this.height;
    }
    public String getImage() {
        return this.image;
    }
    public Integer getWeight() {
        return this.weight;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public Integer getIdType() {
        return this.idType;
    }
    public String getSignature() {
        return this.signature;
    }
   
    public void setId(Long id) {
        this.id = id;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public void setTel(Long tel) {
        this.tel = tel;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }
    public void setAddress(Integer address) {
        this.address = address;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public void setIdType(Integer idType) {
        this.idType = idType;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
  
}