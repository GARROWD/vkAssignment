package com.garrow.vkassignment.models;

import com.garrow.vkassignment.models.utils.Address;
import com.garrow.vkassignment.models.utils.Company;
import com.garrow.vkassignment.utils.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String name;

    private String username;

    private String email;

    private Address address;

    private String phone;

    private String website;

    private Company company;

    private Roles role;
}
