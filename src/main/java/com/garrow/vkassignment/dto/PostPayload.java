package com.garrow.vkassignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class PostPayload {
    @Schema(name = "PostPayloadCreate")
    @Data
    public static class Create {
        @Size(max = 200, message = "post.error.titleLength")
        @NotNull(message = "post.error.nullField")
        private String title;

        @Size(max = 200, message = "post.error.bodyLength")
        @NotNull(message = "post.error.nullField")
        private String body;
    }

    @Schema(name = "PostPayloadUpdate")
    @Data
    public static class Update {
        @Size(max = 200, message = "post.error.titleLength")
        @NotNull(message = "post.error.nullField")
        private String title;

        @Size(max = 200, message = "post.error.bodyLength")
        @NotNull(message = "post.error.nullField")
        private String body;
    }

    @Schema(name = "PostPayloadRequest")
    @Data
    public static class Request {
        private Long id;

        private String title;

        private String body;

        private Long userId;
    }
}

