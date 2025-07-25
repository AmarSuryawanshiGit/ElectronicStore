package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Value("@{user.profile.image.path}")
    private String imagepath;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        return modelMapper.map(userRepository.save(modelMapper.map(userDTO, User.class)), UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found exception"));
        user.setName(userDTO.getName());
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        user.setImageName(userDTO.getImageName());
        user.setPassword(userDTO.getPassword());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found Exception"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public PageableResponce<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

        PageableResponce<UserDto> responce = Helper.getPageableResponce(page, UserDto.class);


        return responce;

    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );


        //   image/user/abc.png
        String fullPath = imagepath + user.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
            System.out.println("File deleted successfully.");
        } catch (NoSuchFileException ex) {

            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found given Email"));
        return modelMapper.map(byEmail, UserDto.class);
    }

    @Override
    public List<UserDto> searchUserByKey(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        return users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
