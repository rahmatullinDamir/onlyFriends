package dev.rahmatullin.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    private Long id;
    private String name;
    private String path;
    private String extension;
}
