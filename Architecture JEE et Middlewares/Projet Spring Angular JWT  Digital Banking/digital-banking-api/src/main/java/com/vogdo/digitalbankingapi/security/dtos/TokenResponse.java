package com.vogdo.digitalbankingapi.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
}