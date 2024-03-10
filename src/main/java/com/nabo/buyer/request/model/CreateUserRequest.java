package com.nabo.buyer.request.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String firstname;
    private String lastname;
    private String nickname;
    private String phone;
    private String deliveryAddress;
    private String email;
    private String password;
    private String latitude;
    private String longitude;
    private String neighborhoodName;
    private String placeId;
    private String postalCode;
    private boolean isTestUser;
}
