package com.example.springsecurityexample.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class OrderDto {
    private UUID id;
}
