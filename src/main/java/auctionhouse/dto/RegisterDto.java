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
    @Size(max = 50)
    @Email
    @Getter @Setter
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Getter @Setter
    private String password;
}
