package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {
 
    UserDto createUser(UserDto userDTO);
    UserDto updateUser(UserDto userDTO, String userId);
    UserDto getUser(String userId);
    PageableResponce<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    void deleteUser(String userId);
    UserDto getUserByEmail(String email);
    List<UserDto> searchUserByKey(String keyword);

}
