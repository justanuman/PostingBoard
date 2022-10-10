package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postingBoard.dto.UserDto;
import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.IUserService;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    IUserService userService;




    UserDtoAdapter userDtoAdapter=new UserDtoAdapter();

    @Autowired
    public UserProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(
            value = "/user/{id}/getProfileInfo",
            produces = "application/json"
    )
    @ResponseBody
    public UserDto getUserInfo(@PathVariable int id) throws JsonProcessingException {
        UserDto user = userService.findById(id).touserDTO();
        return user;
    }

    @PutMapping(
            value = "/user/editProfile",
            produces = "application/json"
    )
    @ResponseBody
    public UserDto editProfileInfo(@RequestParam(required = false) String username, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String password,
                                   @RequestParam(required = false) String status, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String bankNumber, @RequestParam(required = false) String email, Principal principal) throws JsonProcessingException {
        DbUser user = userService.findByUsername(principal.getName());
        UserProfileDto userProfileDto = new UserProfileDto(username, firstName, lastName, password, status, phoneNumber, bankNumber, email);
        UserDto userDTO = userService.updateProfile(user.getId(), userProfileDto, user);
        return userDTO;
    }


    @PostMapping(
            value = "/user/{id}/promoteUser",
            produces = "application/json"
    )
    @ResponseBody
    public UserDto promoteUser(@PathVariable int id) throws JsonProcessingException {
        DbUser user = userService.findById(id);
        UserDto userDTO = user.touserDTO();
        userService.promoteUser(user);
        return userDTO;
    }

    @GetMapping(
            value = "/user/personalinfo",
            produces = "application/json"
    )
    @ResponseBody
    public UserDto getPersonalInfo(Principal principal) throws JsonProcessingException {
       DbUser userModel = userService.findByUsername(principal.getName());
       UserDto user = userDtoAdapter.modelToDto(userModel);
       return user;

    }
}
