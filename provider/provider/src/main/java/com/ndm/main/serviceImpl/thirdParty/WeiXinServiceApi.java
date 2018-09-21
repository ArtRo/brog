package com.ndm.main.serviceImpl.thirdParty;

import com.ndm.api.service.po.UserPo;
import com.ndm.api.service.thirdParty.ThirdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class WeiXinServiceApi implements ThirdUserService {
    //参考网站https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&token=88d880a041fab4300511846bbd8b88f5e96bacb2&lang=zh_CN
    private final static Logger logger = LoggerFactory.getLogger(WeiXinServiceApi.class);
    private final static String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";
    private final static String REFRESH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refresh_token}";
    private final static String VERIFY_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth?access_token={access_token}&openid={openid}";
    private final static String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token={access_token}&openid={openid}";
    private static final WeiXinServiceApi weiXinServiceApi = new WeiXinServiceApi();


    public static ThirdUserService getThirdUserServiceImpl() {
        return weiXinServiceApi;
    }

    @Override
    public Object thirdInfo(String accessToken, String appSecret, String openid, String appId, UserPo userPo) {
        WeiXinServiceApi.WeixinUserInfo usersShow = WeiXinServiceApi.getUsersShow(accessToken, openid);
        //注册
        userPo.setNickName(usersShow.getNickname());
        userPo.setRegType(4);
        userPo.setBeVerified(1);
        userPo.setHeadPortrait(usersShow.getHeadimgurl());
        userPo.setLoginToken(accessToken);
        userPo.setLoginName(openid);
        userPo.setAccountType(AccountType.GENERAL_USER);
        userPo.setLoginType(LoginType.wx);
        userPo.setRegType(Const.REG_TYPE_THIRDPARY);
        userPo.setLoginName(openid);
        userPo.setLoginToken(accessToken);
        return userPo;
    }

    public static WeixinUserInfo getUsersShow(String accessToken, String openid) {
        Optional.of(accessToken);
        WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
        String userInfoUrl = StringUtil.genStr("", GET_USER_INFO, ParamMap.createParamMap()
                .addParam("access_token", accessToken)
                .addParam("openid", openid)
                .addParam("lang", "zh-CN")
                .build());
        weixinUserInfo = (WeixinUserInfo) DoGetRequest.executeGetRequest(userInfoUrl, weixinUserInfo);
        if (null == weixinUserInfo.getNickname()) {
            throw new AppRuntimeException(InfoCode.WECHAT_USER_INFORMATION_FAILED_TO_BE_OBTAINED);
        }
        return weixinUserInfo;
    }

    public static ReturnCode verifyToken(AccessToken accessToken) {
        Optional.of(accessToken);
        ReturnCode returnCode = null;
        String verifyTokenUrl = StringUtil.genStr("", VERIFY_ACCESS_TOKEN, ParamMap.createParamMap()
                .addParam("access_token", accessToken.getAccess_token())
                .addParam("openid", accessToken.getOpenid())
                .build());
        returnCode = (ReturnCode) DoGetRequest.executeGetRequest(verifyTokenUrl, returnCode);
        return returnCode;
    }

    public static AccessToken refreshToken(String refresh_token, String appId) {
        Optional.of(refresh_token);
        AccessToken result = null;
        String refreshTokenUrl = StringUtil.genStr("", REFRESH_ACCESS_TOKEN, ParamMap.createParamMap()
                .addParam("appid", appId)
                .addParam("grant_type", "refresh_token")
                .addParam("refresh_token", refresh_token)
                .build());
        result = (AccessToken) DoGetRequest.executeGetRequest(refreshTokenUrl, result);
        if (null == result) {
            throw new AppRuntimeException(InfoCode.WECHAT_REFRESH_TOKEN_FAILED);
        }
        return result;
    }

    public static AccessToken getAccessToken(String code, String appId, String appSecrect) {
        Optional.of(code);
        AccessToken result = new AccessToken();
        String getAccessTokenUrl = StringUtil.genStr("", GET_ACCESS_TOKEN, ParamMap.createParamMap()
                .addParam("appid", appId)
                .addParam("secret", appSecrect)
                .addParam("code", code)
                .build());
        result = (AccessToken) DoGetRequest.executeGetRequest(getAccessTokenUrl, result);
        if (null == result.getOpenid()) {
            throw new AppRuntimeException(InfoCode.WECHAT_REFRESH_TOKEN_FAILED);
        }
        return result;
    }


    public static class AccessToken implements Serializable {
        private String access_token, refresh_token, openid, scope, unionid;
        private Long expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public AccessToken setAccess_token(String access_token) {
            this.access_token = access_token;
            return this;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public AccessToken setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
            return this;
        }

        public String getOpenid() {
            return openid;
        }

        public AccessToken setOpenid(String openid) {
            this.openid = openid;
            return this;
        }

        public String getScope() {
            return scope;
        }

        public AccessToken setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public String getUnionid() {
            return unionid;
        }

        public AccessToken setUnionid(String unionid) {
            this.unionid = unionid;
            return this;
        }

        public Long getExpires_in() {
            return expires_in;
        }

        public AccessToken setExpires_in(Long expires_in) {
            this.expires_in = expires_in;
            return this;
        }
    }

    private class ReturnCode {
        private Integer errcode;
        private String errmsg;

        public Integer getErrcode() {
            return errcode;
        }

        public ReturnCode setErrcode(Integer errcode) {
            this.errcode = errcode;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ReturnCode that = (ReturnCode) o;

            if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
            return !(errmsg != null ? !errmsg.equals(that.errmsg) : that.errmsg != null);

        }

        @Override
        public int hashCode() {
            int result = errcode != null ? errcode.hashCode() : 0;
            result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ReturnCode{" +
                    "errcode=" + errcode +
                    ", errmsg='" + errmsg + '\'' +
                    '}';
        }
    }

    public static class WeixinUserInfo {
        private String openid, nickname, language, province, city, country, headimgurl, unionid;
        private List<String> privilege;
        private Integer sex;

        public String getOpenid() {
            return openid;
        }

        public WeixinUserInfo setOpenid(String openid) {
            this.openid = openid;
            return this;
        }

        public String getNickname() {
            return nickname;
        }

        public WeixinUserInfo setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public String getProvince() {
            return province;
        }

        public WeixinUserInfo setProvince(String province) {
            this.province = province;
            return this;
        }

        public String getCity() {
            return city;
        }

        public WeixinUserInfo setCity(String city) {
            this.city = city;
            return this;
        }

        public String getCountry() {
            return country;
        }

        public WeixinUserInfo setCountry(String country) {
            this.country = country;
            return this;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public WeixinUserInfo setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
            return this;
        }

        public List<String> getPrivilege() {
            return privilege;
        }

        public WeixinUserInfo setPrivilege(List<String> privilege) {
            this.privilege = privilege;
            return this;
        }

        public String getUnionid() {
            return unionid;
        }

        public WeixinUserInfo setUnionid(String unionid) {
            this.unionid = unionid;
            return this;
        }

        public Integer getSex() {
            return sex;
        }

        public WeixinUserInfo setSex(Integer sex) {
            this.sex = sex;
            return this;
        }

        public String getLanguage() {
            return language;
        }

        public WeixinUserInfo setLanguage(String language) {
            this.language = language;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeixinUserInfo that = (WeixinUserInfo) o;

            if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
            if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
            if (language != null ? !language.equals(that.language) : that.language != null) return false;
            if (province != null ? !province.equals(that.province) : that.province != null) return false;
            if (city != null ? !city.equals(that.city) : that.city != null) return false;
            if (country != null ? !country.equals(that.country) : that.country != null) return false;
            if (headimgurl != null ? !headimgurl.equals(that.headimgurl) : that.headimgurl != null) return false;
            if (unionid != null ? !unionid.equals(that.unionid) : that.unionid != null) return false;
            if (privilege != null ? !privilege.equals(that.privilege) : that.privilege != null) return false;
            return !(sex != null ? !sex.equals(that.sex) : that.sex != null);

        }

        @Override
        public int hashCode() {
            int result = openid != null ? openid.hashCode() : 0;
            result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
            result = 31 * result + (language != null ? language.hashCode() : 0);
            result = 31 * result + (province != null ? province.hashCode() : 0);
            result = 31 * result + (city != null ? city.hashCode() : 0);
            result = 31 * result + (country != null ? country.hashCode() : 0);
            result = 31 * result + (headimgurl != null ? headimgurl.hashCode() : 0);
            result = 31 * result + (unionid != null ? unionid.hashCode() : 0);
            result = 31 * result + (privilege != null ? privilege.hashCode() : 0);
            result = 31 * result + (sex != null ? sex.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "WeixinUserInfo{" +
                    "openid='" + openid + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", language='" + language + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", country='" + country + '\'' +
                    ", headimgurl='" + headimgurl + '\'' +
                    ", unionid='" + unionid + '\'' +
                    ", privilege=" + privilege +
                    ", sex=" + sex +
                    '}';
        }
    }

}
