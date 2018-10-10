package com.ndm.api.service.thirdParty;

import com.ndm.api.service.po.UserPo;

public interface ThirdUserService {
    //注册
    Object thirdInfo(String accessToken, String appSecret, String openid, String appId, UserPo userPo);

}
