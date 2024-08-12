package com.example.techtasknerdysoft.service.user;

import com.example.techtasknerdysoft.dto.user.UserBorrowBookRequest;
import com.example.techtasknerdysoft.dto.user.UserRegistrationRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.dto.user.UserUpdateNameRequest;
import com.example.techtasknerdysoft.exception.EntityNotFoundException;
import com.example.techtasknerdysoft.exception.RegistrationException;
import com.example.techtasknerdysoft.mapper.UserMapper;
import com.example.techtasknerdysoft.model.Book;
import com.example.techtasknerdysoft.model.Role;
import com.example.techtasknerdysoft.model.User;
import com.example.techtasknerdysoft.repository.BookRepository;
import com.example.techtasknerdysoft.repository.UserRepository;
import com.example.techtasknerdysoft.service.role.RoleService;
import com.example.techtasknerdysoft.utils.BookUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int AMOUNT = 1;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookRepository bookRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new RegistrationException("Passwords do not match");
        }

        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleService.getRoleByRoleName(Role.RoleName.USER);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateNameById(Long id, UserUpdateNameRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id " + id));
        user.setName(request.getName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto borrowBookById(Long id, UserBorrowBookRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id " + id));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id "
                                + request.getBookId()));

        BookUtils.checkUserBooks(user);
        BookUtils.checkBookAvailability(book);

        user.setBooks(new HashSet<>(Set.of(book)));
        book.setAmount(book.getAmount() - AMOUNT);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto returnBookById(Long id, UserBorrowBookRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id " + id));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id "
                                + request.getBookId()));

        BookUtils.isBookBorrowedByUser(user, book);

        user.getBooks().remove(book);

        book.setAmount(book.getAmount() + AMOUNT);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto findBorrowedBooksByUserName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by name " + name));
        return userMapper.toDto(user);
    }
}
