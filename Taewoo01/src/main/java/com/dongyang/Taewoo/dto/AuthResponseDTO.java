package com.dongyang.Taewoo.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
}
