package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.UserDto;
import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.IUserService;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import com.postingBoard.utility.Mappers.UserProfileDtoAdapter;
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
    public String getUserInfo(@PathVariable int id) throws JsonProcessingException {
        UserDto user = userService.findById(id).touserDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        return json;

    }

    @PutMapping(
            value = "/user/editProfile",
            produces = "application/json"
    )
    @ResponseBody
    public String editProfileInfo(@RequestParam(required = false) String username, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String password,
                                  @RequestParam(required = false) String status, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String bankNumber, @RequestParam(required = false) String email, Principal principal) throws JsonProcessingException {
        DbUser user = userService.findByUsername(principal.getName());
        UserProfileDto userProfileDto = new UserProfileDto(username, firstName, lastName, password, status, phoneNumber, bankNumber, email);
        UserDto userDTO = userService.updateProfile(user.getId(), userProfileDto, user);
        // throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userDTO);
        return json;
    }

  /*  @DeleteMapping(
            value = "/user/{id}/deactivateUser",
            produces = "application/json"
    )
    @ResponseBody
    public String deactivateUser(@PathVariable int id, Principal principal) throws JsonProcessingException {
        Users user = userService.findById(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (user != null) {
            UserDTO userDTO = user.touserDTO();
            userService.deactivateUser(id,principal);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return null;
    }*/

    @PostMapping(
            value = "/user/{id}/promoteUser",
            produces = "application/json"
    )
    @ResponseBody
    public String promoteUser(@PathVariable int id) throws JsonProcessingException {
        DbUser user = userService.findById(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        UserDto userDTO = user.touserDTO();
        userService.promoteUser(user);
        String json = ow.writeValueAsString(userDTO);
        return json;
    }

    @GetMapping(
            value = "/user/personalinfo",
            produces = "application/json"
    )
    @ResponseBody
    public String getPersonalInfo(Principal principal) throws JsonProcessingException {
       // UserDto user = userService.findByUsername(principal.getName()).touserDTO();
       DbUser userModel = userService.findByUsername(principal.getName());
       UserDto user = userDtoAdapter.modelToDto(userModel);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        return json;

    }
  /*  @PutMapping(
            value = "/user/{id}/activateUser",
            produces = "application/json"
    )
    @ResponseBody
    public String activateUser(@PathVariable int id) throws JsonProcessingException {
        Users user = userService.findById(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        UserDTO userDTO = user.touserDTO();
        userService.activateUser(user);
        String json = ow.writeValueAsString(userDTO);
        return json;

    }*/

  /*  @PutMapping(
            value = "/user/{id}/payForRating",
            produces = "application/json"
    )
    @ResponseBody
    public String payForRating(@PathVariable int id, @RequestParam int score) throws JsonProcessingException {
        Users user = userService.findById(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        UserDTO userDTO = userService.payForRating(user, score);
        String json = ow.writeValueAsString(userDTO);
        return json;
    }*/
}
