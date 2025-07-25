package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.UserDTO;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        return modelMapper.map(userRepository.save(modelMapper.map(userDTO, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found exception"));
        user.setName(userDTO.getName());
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        user.setImageName(userDTO.getImageName());
        user.setPassword(userDTO.getPassword());
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public UserDTO getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found Exception"));
        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers( int pageNumber, int pageSize) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        Page<User> all = userRepository.findAll(pageable);
        List<User> content = all.getContent();
        return content.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found Exception"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found given Email"));
        return modelMapper.map(byEmail,UserDTO.class);
    }

    @Override
    public List<UserDTO> searchUserByKey(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        return users.stream().map((user) -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}
