package com.ndm.api.service.po;

public class UserPo {
    /** 用户ID */
    private Long profileId;
    /** 用户账户ID */
    private Long accountId;

    /** 设备唯一ID */
    private String gid;
    /** 平台号 */
    private Integer plat;
    /** 客户端操作系统 */
    private String sys;
    /** 客户端操作系统版本号 */
    private String sysver;
    /** 客户端版本号 */
    private String sver;
    /** 终端生产厂商 */
    private String mfo;
    /** 终端具体机型 */
    private String mfov;
    /** 登陆账号 */
    private String loginName;
    /** 登录令牌 */
    private String loginToken;
    /** 登录类型 */
    private LoginType loginType;
    /** ip地址 */
    private String regIp;
    /** 时间戳 */
    private Long timestamp;
    /** 验证状态 1 验证通过 -1 待验证 */
    private Integer beVerified;
    /** 密码设置日期：password_time=0说明是老密码，否则为新密码 */
    private Long passwordTime;
    /** 手机号 */
    private String mobile;
    /** 账号 */
    private String accountName;
    /** 密码 */
    private String password;
    /** 验证码 */
    private String verifyCode;
    /** 注册类型 */
    private Integer regType;
    /** 账号类型 */
    private AccountType accountType;

    private String nickName;  //昵称

    private String headPortrait; //头像

    private String source;

    public UserPo(){}

    public UserPo(Long profileId,Long accountId,String gid, Integer plat, String sys, String sysver, String sver,
                  String mfo, String mfov, String loginName, String loginToken, LoginType loginType, String regIp, Long timestamp) {
        this.profileId = profileId;
        this.accountId = accountId;
        this.gid = gid;
        this.plat = plat;
        this.sys = sys;
        this.sysver = sysver;
        this.sver = sver;
        this.mfo = mfo;
        this.mfov = mfov;
        this.loginName = loginName;
        this.loginToken = loginToken;
        this.loginType = loginType;
        this.regIp = regIp;
        this.timestamp = timestamp;
    }
    public UserPo(String gid, Integer plat, String sys, String sysver, String sver, String mfo, String mfov, String loginName, String loginToken, LoginType loginType, String regIp, Long timestamp) {
        this.gid = gid;
        this.plat = plat;
        this.sys = sys;
        this.sysver = sysver;
        this.sver = sver;
        this.mfo = mfo;
        this.mfov = mfov;
        this.loginName = loginName;
        this.loginToken = loginToken;
        this.loginType = loginType;
        this.regIp = regIp;
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getPlat() {
        return plat;
    }

    public void setPlat(Integer plat) {
        this.plat = plat;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSysver() {
        return sysver;
    }

    public void setSysver(String sysver) {
        this.sysver = sysver;
    }

    public String getSver() {
        return sver;
    }

    public void setSver(String sver) {
        this.sver = sver;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getMfov() {
        return mfov;
    }

    public void setMfov(String mfov) {
        this.mfov = mfov;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getBeVerified() {
        return beVerified;
    }

    public void setBeVerified(Integer beVerified) {
        this.beVerified = beVerified;
    }

    public Long getPasswordTime() {
        return passwordTime;
    }

    public void setPasswordTime(Long passwordTime) {
        this.passwordTime = passwordTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
