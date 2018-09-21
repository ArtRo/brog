package com.ndm.api.service.thirdParty;

public interface ThirdUserService {
    //注册
    Object thirdInfo(String accessToken, String appSecret, String openid, String appId, UserPo userPo);

}
