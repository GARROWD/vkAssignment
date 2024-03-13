package com.garrow.vkassignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class CommentPayload {
    @Schema(name = "CommentPayloadCreate")
    @Data
    public static class Create {
        private String body;
    }

    @Schema(name = "CommentPayloadUpdate")
    @Data
    public static class Update {

    }

    @Schema(name = "CommentPayloadRequest")
    @Data
    public static class Request {
        private Long id;

        private Long postId;

        private String name;

        private String email;

        private String body;
    }
}

