package com.example.techtasknerdysoft.controller;

import com.example.techtasknerdysoft.dto.user.UserBorrowBookRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.dto.user.UserUpdateNameRequest;
import com.example.techtasknerdysoft.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users management",
        description = "Endpoints for managing Users")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Get list of users",
            description = "Get valid list of users")
    public List<UserResponseDto> getAll(@ParameterObject Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get user by ID",
            description = "Get valid user by ID")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/name/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update user name by ID",
            description = "Update valid user name by ID")
    public UserResponseDto updateUserNameById(@PathVariable Long id,
                                              @RequestBody UserUpdateNameRequest request) {
        return userService.updateNameById(id, request);
    }

    @PutMapping("/borrow/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "User borrow book by ID",
            description = "User borrow valid book by ID")
    public UserResponseDto borrowBookById(@PathVariable Long id,
                                              @RequestBody UserBorrowBookRequest request) {
        return userService.borrowBookById(id, request);
    }

    @PutMapping("/returnBook/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "User return book by ID",
            description = "User return valid book by ID")
    public UserResponseDto returnBookById(@PathVariable Long id,
                                          @RequestBody UserBorrowBookRequest request) {
        return userService.returnBookById(id, request);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get borrowed books  by user name",
            description = "Get valid borrowed books by user name")
    public UserResponseDto getBorrowedBooksByUserName(@PathVariable String name) {
        return userService.findBorrowedBooksByUserName(name);
    }
}
