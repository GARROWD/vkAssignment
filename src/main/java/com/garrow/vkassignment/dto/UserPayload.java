package com.garrow.vkassignment.dto;

import com.garrow.vkassignment.models.utils.Address;
import com.garrow.vkassignment.models.utils.Company;
import com.garrow.vkassignment.utils.enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserPayload {
    @Schema(name = "UserPayloadCreate")
    @Data
    public static class Create {
        @NotNull(message = "user.error.nullField")
        private String name;

        @Size(max = 200, message = "user.error.usernameLength")
        @NotNull(message = "user.error.nullField")
        private String username;

        @NotNull(message = "user.error.nullField")
        private String email;

        private Address address;

        private String phone;

        private String website;

        private Company company;

        @Size(max = 200, message = "user.error.passwordLength")
        @NotNull(message = "user.error.nullField")
        private String password;
    }

    @Schema(name = "UserPayloadUpdate")
    @Data
    public static class Update {
        @NotNull(message = "user.error.nullField")
        private String username;
    }

    @Schema(name = "UserPayloadRequest")
    @Data
    public static class Request {
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
}

