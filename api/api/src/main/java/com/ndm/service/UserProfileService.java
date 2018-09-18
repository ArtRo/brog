package com.ndm.service;

import com.ndm.entity.UserProfile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> selectByUserProfile(UserProfile record);

    int insertSelective(UserProfile record);
    
    int deleteByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(UserProfile record);
    
}