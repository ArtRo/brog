package com.ndm.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ndm.api.entity.UserProfile;
import com.ndm.api.service.UserProfileService;
import org.springframework.stereotype.Controller;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Controller
@Path("v1/userProfile")
public class UserProfileController {

    @Reference
    private UserProfileService userProfileService;

    @GET
    @Path("/list")
    public List<UserProfile> getList(){
        return userProfileService.selectByUserProfile(new UserProfile());
    }
    
}
