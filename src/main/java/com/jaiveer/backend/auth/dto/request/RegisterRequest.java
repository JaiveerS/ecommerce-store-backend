package com.jaiveer.backend.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public boolean validateFirstname() throws Exception {
        if (this.firstname.length() < 2) {
            throw new Exception("Firstname must be at least 2 characters long");
        }
        return true;
    }

    public boolean validateLastname() throws Exception {
        if (this.lastname.length() < 2) {
            throw new Exception("Last name must be at least 2 characters long");
        }
        return true;
    }

    public boolean validatePassword() throws Exception {
        if (this.password.length() < 6) {
            throw new Exception("Password must be at least 6 characters long");
        }
        return true;
    }

    public boolean validateEmail() throws Exception {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if (this.email.length() < 2) {
            throw new Exception("Email must be at least 2 characters long");
        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(this.email).matches()) {
            throw new Exception("Use a valid email address");
        }
        return true;
    }

    public void validateUserDetails() throws Exception {
        this.validateFirstname();
        this.validateLastname();
        this.validateEmail();
        this.validatePassword();
    }
}
