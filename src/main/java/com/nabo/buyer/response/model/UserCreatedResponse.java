package com.nabo.buyer.response.model;

import com.nabo.buyer.repository.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreatedResponse {
    private User user;
    private String message;
    private String jwtToken;
}
