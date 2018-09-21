package com.ndm.api.service.thirdParty;

public interface ThirdPartyInfoService {
    String GRANT_TYPE = "authorization_code", WEIBO_SCOPE = "all", WEIXIN_SCOPE = "snsapi_userinfo", QQ_SCOPE = "get_user_info";
    /**
     * 根据ID查询对象
     * @param id
     * @return
     */
    ThirdPartyInfo queryById(Long id);

    /**
     * 分页查询
     * @param record
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<ThirdPartyInfo> queryPageInfoByEntity(ThirdPartyInfo record, int pageNumber, int pageSize);

    /**
     * 查询列表
     * @param record
     * @return
     */
    List<ThirdPartyInfo> queryListByEntity(ThirdPartyInfo record);
    /**
     * 保存
     * @param record
     * @return
     */
    int save(ThirdPartyInfo record);

    /**
     * 更新
     * @param record
     * @return
     */
    int update(ThirdPartyInfo record);

    /**
     * 获取用户信息
     * @param loginType  三方类型 qq wx wb
     * @param type 0 登陆注册 1 绑定
     * @param accessToken wb qq时传递 token
     * @param openid wb qq时传递 用户表示
     * @param userPo  userPo基本信息
     * @return
     */
    Object getThirdPartyUserInfo(String loginType,Integer type, String code,String accessToken,String openid, UserPo userPo) throws AppRuntimeException;

    /**
     * 解绑三方
     * @param profileId
     * @param thirdPatryType  三方类型
     * @return
     */
    Object thirdUntie(Long profileId, ThirdPatryType thirdPatryType)throws AppRuntimeException;

    Object thirdBind(UserPo userPo)throws AppRuntimeException;

}
