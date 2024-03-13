package com.garrow.vkassignment.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    private Long albumId;

    private Long id;

    private String title;

    private String url;

    private String thumbnailUrl;
}
