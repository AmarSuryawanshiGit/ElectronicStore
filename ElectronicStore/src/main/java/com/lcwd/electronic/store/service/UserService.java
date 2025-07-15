package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.UserDTO;

import java.util.List;

public interface UserService {
 
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO,String userId);
    UserDTO getUser(String userId);
    List<UserDTO> getAllUsers();
    void deleteUser(String userId);
    UserDTO getUserByEmail(String email);
    List<UserDTO> searchUserByKey(String keyword);

}
