package com.example.techtasknerdysoft.service.user;

import com.example.techtasknerdysoft.dto.user.UserBorrowBookRequest;
import com.example.techtasknerdysoft.dto.user.UserRegistrationRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.dto.user.UserUpdateNameRequest;
import com.example.techtasknerdysoft.exception.RegistrationException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;

    List<UserResponseDto> getAll(Pageable pageable);

    UserResponseDto findById(Long id);

    UserResponseDto updateNameById(Long id, UserUpdateNameRequest request);

    UserResponseDto borrowBookById(Long id, UserBorrowBookRequest request);

    UserResponseDto returnBookById(Long id, UserBorrowBookRequest request);

    UserResponseDto findBorrowedBooksByUserName(String name);
}
