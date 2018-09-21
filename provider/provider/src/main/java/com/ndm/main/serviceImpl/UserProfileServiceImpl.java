package com.ndm.main.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ndm.main.dao.UserProfileDao;
import com.ndm.api.entity.UserProfile;
import com.ndm.api.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileDao userProfileDao;
    
    @Override
    public List<UserProfile> selectByUserProfile(UserProfile record) {
        List<UserProfile> result = userProfileDao.selectByUserProfile(record);
        return result;
    }    

    @Override
    public int insertSelective(UserProfile record) {
         return userProfileDao.insertSelective(record);
    }   
    
    @Override
    public int updateByPrimaryKeySelective(UserProfile record) {
        return userProfileDao.updateByPrimaryKeySelective(record);
    }    

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userProfileDao.deleteByPrimaryKey(id);
    }
    
}