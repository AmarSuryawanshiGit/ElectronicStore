package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.validate.ImageNameValidate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private String userId;

    @Size(min = 3,max = 15,message = "Invalid UserName!!!")
    private String name;
    @Email(message = "Invalid Email !!!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "Password Is Required")
    private String password;
    @Size(min = 4,max = 6,message = "Invalid Gender !!!")
    private String gender;
    @NotBlank(message = "Write Something about yourself")
    private String about;

    @ImageNameValidate
    private String imageName;
}
