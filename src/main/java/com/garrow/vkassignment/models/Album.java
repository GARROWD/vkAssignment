package com.garrow.vkassignment.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private Long userId;

    private Long id;

    private String title;
}
