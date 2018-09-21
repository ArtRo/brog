package com.ndm.main.serviceImpl.thirdParty;

import com.ndm.api.service.UserProfileService;
import com.ndm.api.service.thirdParty.ThirdPartyInfoService;
import com.ndm.main.dao.UserProfileDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ThirdPartyInfoServiceImpl implements ThirdPartyInfoService {

    Logger logger = LoggerFactory.getLogger(ThirdPartyInfoServiceImpl.class);

    @Autowired
    private ThirdPartyInfoDao thirdPartyInfoDao;

    @Autowired
    private ThirdUserInfoDao thirdUserInfoDao;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegService regService;

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private UserFrontService userFrontService;


    @Override
    public ThirdPartyInfo queryById(Long id) {
        return thirdPartyInfoDao.queryById(id);
    }

    @Override
    public PageInfo<ThirdPartyInfo> queryPageInfoByEntity(ThirdPartyInfo entity, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<ThirdPartyInfo> entityList = thirdPartyInfoDao.queryListByEntity(entity);
        return new PageInfo<>(entityList);
    }

    @Override
    public int update(ThirdPartyInfo entity) {
        return thirdPartyInfoDao.updateByEntity(entity);
    }

    @Override
    public int save(ThirdPartyInfo entity) {
        return thirdPartyInfoDao.saveEntity(entity);
    }

    @Override
    public List<ThirdPartyInfo> queryListByEntity(ThirdPartyInfo entity) {
        return thirdPartyInfoDao.queryListByEntity(entity);
    }

    @Override
    public Object getThirdPartyUserInfo(String loginType, Integer type, String code, String accessToken, String openid, UserPo userPo) throws AppRuntimeException {
        ThirdPatryType thirdPatryType = ThirdPatryType.valueOf(loginType);
        HashMap<String, Object> result = new HashMap<>();
        if (null != thirdPatryType) {
            ThirdPartyInfo thirdPartyInfo = new ThirdPartyInfo();
            thirdPartyInfo.setThirdName(thirdPatryType.getMsg());
            thirdPartyInfo.setIsDel(Constant.TABLE_PARAM_NORMAL);
            ThirdPartyInfo partyInfo = thirdPartyInfoDao.queryByThirdParty(thirdPartyInfo);
            if (null == partyInfo || null == partyInfo.getThirdKey()) {
                throw new AppRuntimeException(InfoCode.NOT_SUPPORT_THIRD_PARTY_TYPES);
            } else {
                String collbackUrl = partyInfo.getCollbackUrl();
                String domainName = partyInfo.getDomainName();
                String thirdKey = partyInfo.getThirdKey();
                String thirdSecret = partyInfo.getThirdSecret();
                if (thirdPatryType.getType().equals(ThirdPatryType.qq.getType())) {
                    //登陆
                    if (type == 0 && thirdPartyUpload(openid, ThirdPatryType.qq.getType(), result,userPo) && result.size() > 0) {
                        return result;
                    }
                    //用户三方信息
                    QqServiceApi.getThirdUserServiceImpl().thirdInfo(accessToken, thirdSecret, openid, thirdKey, userPo);
                }
                if (thirdPatryType.getType().equals(ThirdPatryType.wb.getType())) {
                    if (type == 0 && thirdPartyUpload(openid, ThirdPatryType.wb.getType(), result,userPo) && result.size() > 0) {
                        return result;
                    }
                    WeiboServiceApi.getThirdUserServiceImpl().thirdInfo(accessToken, thirdSecret, openid, thirdKey, userPo);
                }
                if (thirdPatryType.getType().equals(ThirdPatryType.wx.getType())) {
                    WeiXinServiceApi.AccessToken access_Token = WeiXinServiceApi.getAccessToken(code, thirdKey, thirdSecret);
                    //登录
                    if (type == 0 && thirdPartyUpload(access_Token.getOpenid(), ThirdPatryType.wx.getType(), result,userPo) && result.size() > 0) {
                        return result;
                    }
                    WeiXinServiceApi.getThirdUserServiceImpl().thirdInfo(access_Token.getAccess_token(), thirdSecret, access_Token.getOpenid(), thirdKey, userPo);
                }
                try {
                    if (type == 1) {
                        //绑定
                        return thirdBind(userPo);
                    }
                    Long profileId = regService.quickLoginOrRegByThird(userPo,result);
                    UserProfile userProfile = profileService.queryById(profileId);
                    userPo.setProfileId(profileId);
                    userPo.setAccountId(userProfile.getAccountId());
                    String token = loginService.getToken(userPo);
                    result.put("profile_id", profileId);
                    result.put("token", token);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    logger.error("三方绑定或登录注册失败", e);
                    if (e.getErrorCode() == UserErrorCode.MOBILE_INVALID.getCode()) {
                        throw new AppRuntimeException(InfoCode.INVALID_MOBILE);
                    } else if (e.getErrorCode() == UserErrorCode.INVALID_VERIFYCODE.getCode()) {
                        throw new AppRuntimeException(InfoCode.INVALID_VERIFYCODE);
                    } else if (e.getErrorCode() == UserErrorCode.VERIFYFAIL_MORE_LIMIT.getCode()) {
                        throw new AppRuntimeException(InfoCode.VERIFYFAIL_MORE_LIMIT);
                    } else if (e.getErrorCode() == UserErrorCode.USER_LOCK.getCode()) {
                        throw new AppRuntimeException(InfoCode.LOCKED_MOBILE);
                    } else if (e.getErrorCode() == UserErrorCode.THIS_ACCOUNT_HAS_A_BINDING.getCode()) {
                        throw new AppRuntimeException(InfoCode.THIS_ACCOUNT_HAS_A_BINDING);
                    }
                    throw new AppRuntimeException(InfoCode.SERVICE_UNAVAILABLE);
                }
                return result;
            }
        } else {
            throw new AppRuntimeException(InfoCode.NOT_SUPPORT_THIRD_PARTY_TYPES);
        }
    }

    @Override
    public Object thirdUntie(Long profileId, ThirdPatryType thirdPatryType)throws AppRuntimeException {
        UserProfile userProfile = userProfileDao.queryById(profileId);
        if (null != userProfile) {
            Map<String, Object> stringObjectMap = userFrontService.accountManagement(profileId);
            if(null == stringObjectMap || stringObjectMap.size() <=1){
                throw new AppRuntimeException(InfoCode.THE_LAST_ACCOUNT_CANNOT_BE_UNTIED);
            }
            Long accountId = userProfile.getAccountId();
            ThirdUserInfo thirdUserInfo = new ThirdUserInfo();
            thirdUserInfo.setAccountId(accountId);
            thirdUserInfo.setThirdType(thirdPatryType.getType());
            thirdUserInfo.setIsDel(Constant.TABLE_PARAM_NORMAL);
            List<ThirdUserInfo> thirdUserInfos = thirdUserInfoDao.queryListByEntity(thirdUserInfo);
            if (null != thirdUserInfos && thirdUserInfos.size() > 0) {
                ThirdUserInfo thirdUserInfo1 = thirdUserInfos.get(0);
                thirdUserInfo1.setIsDel(Constant.TABLE_PARAM_DELETE);
                thirdUserInfoDao.updateByEntity(thirdUserInfo1);
                userLoginDao.deleteByAccountIdAndLoginName(accountId, thirdUserInfo1.getLoginName());
                return new Integer(1);
            } else {
                throw new AppRuntimeException(InfoCode.TRIPARTITE_INFORMATION_DOES_NOT_EXIST);
            }
        } else {
            throw new AppRuntimeException(InfoCode.INADEQUATE_USER_INFORMATION);
        }
    }

    @Override
    public Object thirdBind(UserPo userPo)throws AppRuntimeException{
        Map<Object, Object> result = Maps.newHashMap();
        UserProfile userProfile = userProfileDao.queryById(userPo.getProfileId());
        if (null != userProfile && userProfile.getAccountId() > 0) {
            userPo.setAccountId(userProfile.getAccountId());
            boolean bind = false;
            try {
                bind = regService.bind(userPo);
                if (bind) {
                    ThirdUserInfo thirdUserInfo = new ThirdUserInfo();
                    thirdUserInfo.setLoginName(userPo.getLoginName());
                    thirdUserInfo.setThirdType(Long.valueOf(userPo.getLoginType().getType() + ""));
                    List<ThirdUserInfo> thirdUserInfos = thirdUserInfoDao.queryListByEntity(thirdUserInfo);
                    if (null != thirdUserInfos && thirdUserInfos.size() > 0) {
                        //存在 绑定
                        ThirdUserInfo thirdUserInfo1 = thirdUserInfos.get(0);
                        thirdUserInfo1.setAccountId(userProfile.getAccountId());
                        thirdUserInfo1.setIsDel(Constant.TABLE_PARAM_NORMAL);
                        thirdUserInfoDao.updateByEntity(thirdUserInfo1);
                        result.put("third_name",thirdUserInfo1.getName());
                    } else {
                        //不存在 绑定
                        thirdUserInfo.setName(userPo.getNickName());
                        thirdUserInfo.setImage(userPo.getHeadPortrait());
                        thirdUserInfo.setThirdToken(userPo.getLoginToken());
                        thirdUserInfo.setAccountId(userProfile.getAccountId());
                        thirdUserInfo.setIsDel(Constant.TABLE_PARAM_NORMAL);
                        thirdUserInfoDao.saveEntity(thirdUserInfo);
                        result.put("third_name",thirdUserInfo.getName());
                    }
                    return result;
                } else {
                    throw new AppRuntimeException(InfoCode.BINDING_FAILED);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
                throw new AppRuntimeException(InfoCode.BINDING_FAILED);
            }
        } else {
            throw new AppRuntimeException(InfoCode.THE_BOUND_USER_DOES_NOT_EXIST);
        }
    }

    //登陆
    private boolean thirdPartyUpload(String openid, Long thirdType, HashMap<String, Object> result,UserPo userPo) {
        ThirdUserInfo thirdUserInfo = new ThirdUserInfo();
        thirdUserInfo.setLoginName(openid);
        thirdUserInfo.setThirdType(thirdType);
        thirdUserInfo.setThirdType(ThirdPatryType.wx.getType());
        thirdUserInfo.setIsDel(Constant.TABLE_PARAM_NORMAL);
        List<ThirdUserInfo> thirdUserInfos = thirdUserInfoDao.queryListByEntity(thirdUserInfo);
        boolean upload = false;
        if (null != thirdUserInfos && thirdUserInfos.size() > 0) {
            ThirdUserInfo thirdUserInfo1 = thirdUserInfos.get(0);
            UserAccount userAccount = userAccountDao.queryById(thirdUserInfo1.getAccountId());
            Long accountId = userAccount.getId();
            if (null != accountId && accountId >0) {
                UserProfile userProfile = new UserProfile();
                userProfile.setAccountId(accountId);
                List<UserProfile> userProfiles = userProfileDao.queryListByEntity(userProfile);
                if (null != userProfiles && userProfiles.size() > 0) {
                    Long id = userProfiles.get(0).getId();
                    userPo.setProfileId(id);
                    userPo.setAccountId(accountId);
                    try {
                        String token = loginService.getToken(userPo);
                        result.put("profile_id", id);
                        result.put("token", token);
                        result.put("is_new_user",-1);
                        upload = true;
                    } catch (ServiceException e) {
                        logger.error("第三方快速登录失败", e);
                        throw new AppRuntimeException(InfoCode.SERVICE_UNAVAILABLE);
                    }
                }
            }
        }
        return upload;
    }

}
