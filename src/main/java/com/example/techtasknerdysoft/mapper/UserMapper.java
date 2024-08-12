package com.example.techtasknerdysoft.mapper;

import com.example.techtasknerdysoft.config.MapperConfig;
import com.example.techtasknerdysoft.dto.user.UserRegistrationRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(UserRegistrationRequest request);
}
