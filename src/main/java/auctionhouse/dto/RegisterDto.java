package auctionhouse.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterDto {
    @NotBlank
    @Getter @Setter
    private String username;

    @NotBlank
    @Email
    @Getter @Setter
    private String email;

    @NotBlank
    @Getter @Setter
    private String password;
}
