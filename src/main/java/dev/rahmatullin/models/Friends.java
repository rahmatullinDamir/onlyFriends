package dev.rahmatullin.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friends {
    private Long id;
    private Long userId;
    private Long friendId;
    private String status;
}
