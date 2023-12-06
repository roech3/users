package edu.depaul.users.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fullName;

    @NotNull
    @Size(max = 16)
    private String userName;

    @NotNull
    @Size(max = 255)
    private String passwordHash;

}
