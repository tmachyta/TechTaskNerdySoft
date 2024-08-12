package com.example.techtasknerdysoft.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserUpdateNameRequest {
    private String name;
}
