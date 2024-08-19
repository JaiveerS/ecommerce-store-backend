package com.jaiveer.backend.auth;

import com.jaiveer.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;

    UserInfoResponse(User user) {
        this.id = user.getId().toString();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }

}
