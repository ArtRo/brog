package com.ndm.dao;

import com.ndm.entity.UserProfile;

import java.util.List;

public interface UserProfileDao {

    List<UserProfile> selectByUserProfile(UserProfile record);

    int insertSelective(UserProfile record);
  
    int deleteByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(UserProfile record);
    
}