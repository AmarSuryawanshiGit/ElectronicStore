package com.lcwd.electronic.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Users")
public class User {

    @Id
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_pass", length = 10)
    private String password;
    @Column(name = "user_gen")
    private String gender;
    @Column(length = 100)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;
}
