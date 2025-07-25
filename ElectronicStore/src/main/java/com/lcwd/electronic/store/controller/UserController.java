package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.ApiResponse;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger= LoggerFactory.getLogger(UserController.class);


    private UserService userService;
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;


    @Autowired
    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDTO){
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDTO, @PathVariable String userId){
        return new ResponseEntity<>(userService.updateUser(userDTO,userId), HttpStatus.CREATED);
    }

    @GetMapping("getUser/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<PageableResponce<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        return new ResponseEntity<>(ApiResponse.builder()
                .message("User Deleted Succesfully")
                .success(true)
                .status(HttpStatus.OK)
                .build(),HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/searchUser/{keyword}")
    public ResponseEntity<List<UserDto>> searchUserByKey(@PathVariable String keyword){
        return new ResponseEntity<>(userService.searchUserByKey(keyword),HttpStatus.OK);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUser(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse =
                ImageResponse.builder()
                        .imageName(imageName)
                        .success(true)
                        .status(HttpStatus.CREATED)
                        .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    @GetMapping("/image/userId")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {

        UserDto user = userService.getUser(userId);
        logger.info("User Image Name : {} " , user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
