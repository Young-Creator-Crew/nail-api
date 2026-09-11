package sptech.school.nail_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "The email cannot be null, empty, or contain only whitespace.")
    @Email(message = "The email must be correctly formatted.")
    private String email;

    @NotBlank(message = "The email cannot be null, empty, or contain only whitespace.")
    @Size(min = 8, message = "The password must be at least 8 characters long.")
    private String password;

    @NotBlank
    private String name;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
