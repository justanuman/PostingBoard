package com.postingBoard.service.implementation;


import com.postingBoard.dto.UserDto;
import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbRole;
import com.postingBoard.entity.DbUser;
import com.postingBoard.entity.UserRole;
import com.postingBoard.exception.IncorrectInputException;
import com.postingBoard.repo.IRoleDAO;
import com.postingBoard.repo.IUserDAO;
import com.postingBoard.repo.IUserRoleDAO;
import com.postingBoard.service.interfaces.IUserService;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import com.postingBoard.utility.Mappers.UserProfileDtoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    IUserDAO userDAO;
    IRoleDAO roleDAO;
    BCryptPasswordEncoder passwordEncoder;
    IUserRoleDAO userRoleDAO;

//@Autowire кидал мне в лицо npe неизвестно почему так что di не вышло
    UserDtoAdapter userDtoAdapter = new UserDtoAdapter();

    UserProfileDtoAdapter userProfileDtoAdapter= new UserProfileDtoAdapter();


    @Autowired
    public UserService(IUserDAO userDAO, IRoleDAO roleDAO, BCryptPasswordEncoder passwordEncoder,
                       IUserRoleDAO userRoleDAO
                       ) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.userRoleDAO = userRoleDAO;

    }

    @Override
    public DbUser register(DbUser user) {
        DbRole roleUser = roleDAO.findByName("ROLE_USER");
        List<DbRole> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus("ENABLED");
        user.setPersonalRating(100);
        DbUser check = userDAO.findByUsername(user.getUsername());
        if (check != null) {
            throw new IncorrectInputException(" change name or pass or email");
        }
        DbUser registeredUser = userDAO.save(user);
        logger.info("registered User", registeredUser.getId());
        return registeredUser;
    }

    @Override
    public List<DbUser> getAll() {
        logger.info("get all");
        List<DbUser> result = userDAO.findAll();

        return result;
    }

    @Override
    public DbUser findByUsername(String username) {
        DbUser result = userDAO.findByUsername(username);
        logger.info("findByUsername {}", username);
        return result;
    }

    @Override
    public DbUser findById(int id) {
        DbUser result = userDAO.findById(id).orElse(null);
        logger.info("findById {}", id);
        return result;
    }

    @Override
    public void delete(int id) {
        userDAO.deleteById(id);
    }

    @Override
    @PreAuthorize("#id == authentication.principal.id")
    public UserDto updateProfile(Integer id, UserProfileDto userProfileDto, DbUser user) {
        if (userProfileDto.getUsername() != null) {
            user.setUsername(userProfileDto.getUsername());
        }
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userProfileDto.getFirstName() != null) {
            user.setFirstName(userProfileDto.getFirstName());
        }
        if (userProfileDto.getLastName() != null) {
            user.setLastName(userProfileDto.getLastName());
        }
        if (userProfileDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));
        }
        if (userProfileDto.getEmail() != null) {
            user.setEmail(userProfileDto.getEmail());
        }
        if (userProfileDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userProfileDto.getPhoneNumber());
        }
        if (userProfileDto.getBankNumber() != null) {
            user.setBankNumber(userProfileDto.getBankNumber());
        }
        logger.info("profile of {} update", id);
        DbUser user1 = userDAO.save(user);
       // return user1.touserDTO();
        return  userDtoAdapter.modelToDto(user1);
    }

    @Override
    @Deprecated
    public Boolean isUser(DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        for (UserRole row : rows) {
            // тут обязательно будет значение нет смысла проверять
            //TODO custom exeption
            DbRole role = roleDAO.findById(row.getRoleId()).orElse(null);
            if (role.getName().equals("ROLE_USER")) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public Boolean isModerator(DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        for (UserRole row : rows) {
            // тут обязательно будет значение нет смысла проверять
            //TODO custom exeption
            DbRole role = roleDAO.findById(row.getRoleId()).orElse(null);
            if (role.getName().equals("ROLE_MODERATOR")) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public Boolean isAdmin(DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        for (UserRole row : rows) {
            DbRole role = roleDAO.findById(row.getRoleId()).orElse(null);
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }


  /*  @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or #id == authentication.principal.id")
    public UserDTO deactivateUser(int id, Principal principal) {
        Users user = userDAO.findById(id).orElse(null);
        Users performer = userDAO.findByUsername(principal.getName());
        List<Roles> roles = user.getRoles();
        if (user.getId().equals(performer.getId())) {
            user.setStatus("DISABLED");
            userDAO.save(user);
            return user.touserDTO();
        }
        for (Roles row : roles) {
            if (row.getName().equals("ROLE_ADMIN") ) {
               throw new AccessDeniedException("cant deactivate admin");
            }
            if (row.getName().equals("ROLE_MODERATOR") ) {
                throw new AccessDeniedException("cant deactivate admin");
            }
        }
        userDAO.save(user);
        return user.touserDTO();
    }*/

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto deactivateModerator(int id, DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        user.setStatus("DISABLED");
        userRoleDAO.deleteAll(rows);
        //return user.touserDTO();
        return  userDtoAdapter.modelToDto(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public UserDto activateUser(DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        DbRole roleUser = roleDAO.findByName("ROLE_USER");
        List<DbRole> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        for (UserRole row : rows) {
            // тут обязательно будет значение нет смысла проверять
            //TODO custom exeption
            DbRole role = roleDAO.findById(row.getRoleId()).orElse(null);
            if (role.getName().equals("ROLE_MODERATOR") || role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_USER")) {
                return null;
            }
        }
        UserRole userRoles1 = new UserRole();
        userRoles1.setUserId(user.getId());
        userRoles1.setRoleId(roleUser.getId());
        userRoleDAO.save(userRoles1);
        user.setStatus("ENABLED");
        userDAO.save(user);
       // return user.touserDTO();
        return  userDtoAdapter.modelToDto(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto promoteUser(DbUser user) {
        List<UserRole> rows = userRoleDAO.findAllByUserId(user.getId());
        DbRole roleUser = roleDAO.findByName("ROLE_USER");
        List<DbRole> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        for (UserRole row : rows) {
            DbRole role = roleDAO.findById(row.getRoleId()).orElse(null);
            if (role.getName().equals("ROLE_MODERATOR") || role.getName().equals("ROLE_ADMIN")) {
                return null;
            }
        }
        UserRole userRoles1 = new UserRole();
        userRoles1.setUserId(user.getId());
        userRoles1.setRoleId(roleDAO.findByName("ROLE_MODERATOR").getId());
        userRoleDAO.save(userRoles1);
        logger.info("user {} promoted", user.getId());
       // return user.touserDTO();
        return  userDtoAdapter.modelToDto(user);
    }

    @Override
    @Deprecated
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public UserDto changeUserRating(DbUser user, int score) {
        user.setPersonalRating(user.getPersonalRating() + score);
        userDAO.save(user);
        logger.info("changed Users Rating {} ", user.getId());
       // return user.touserDTO();
        return  userDtoAdapter.modelToDto(user);
    }

    @Override
    @Deprecated
    public UserDto payForRating(DbUser user, int score) {
        user.setPersonalRating(user.getPersonalRating() + score);
        userDAO.save(user);
        logger.info("payed for Rating {} ", user.getId());
      //  return user.touserDTO();
        return  userDtoAdapter.modelToDto(user);
    }

    @Override
    public void checkForNull(Object object) {
        if (object.equals(null)) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}
