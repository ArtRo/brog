package com.ndm.serviceImpl;

import com.ndm.dao.UserProfileDao;
import com.ndm.entity.UserProfile;
import com.ndm.service.UserProfileService;
import org.springframework.stereotype.Service;
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