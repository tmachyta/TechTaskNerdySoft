package com.example.techtasknerdysoft.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.techtasknerdysoft.dto.user.UserBorrowBookRequest;
import com.example.techtasknerdysoft.dto.user.UserRegistrationRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.dto.user.UserUpdateNameRequest;
import com.example.techtasknerdysoft.exception.EntityNotFoundException;
import com.example.techtasknerdysoft.exception.RegistrationException;
import com.example.techtasknerdysoft.mapper.UserMapper;
import com.example.techtasknerdysoft.model.Book;
import com.example.techtasknerdysoft.model.Role;
import com.example.techtasknerdysoft.model.Role.RoleName;
import com.example.techtasknerdysoft.model.User;
import com.example.techtasknerdysoft.repository.BookRepository;
import com.example.techtasknerdysoft.repository.UserRepository;
import com.example.techtasknerdysoft.service.role.RoleService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String EMAIL = "test@gmail.com";
    private static final String UPDATED_NAME = "Test";
    private static final String OLD_NAME = "Test1";
    private static final String PASSWORD = "12345678";
    private static final String REPEAT_PASSWORD = "12345678";
    private static final RoleName ROLE = RoleName.USER;
    private static final Long USER_ID = 1L;
    private static final Long BOOK_ID = 1L;
    private static final int AMOUNT = 5;
    private static final Long NON_EXISTED_USER_ID = 50L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegistrationUserSuccessfully() throws RegistrationException {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail(EMAIL);
        request.setPassword(PASSWORD);
        request.setRepeatPassword(REPEAT_PASSWORD);
        request.setName(OLD_NAME);

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        User userToSave = new User();

        when(userMapper.toModel(request)).thenReturn(userToSave);

        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = new Role();
        userRole.setRoleName(ROLE);
        when(roleService.getRoleByRoleName(ROLE)).thenReturn(userRole);
        userToSave.setRoles(new HashSet<>(Set.of(userRole)));

        when(userRepository.save(userToSave)).thenReturn(userToSave);

        UserResponseDto userResponseDto = new UserResponseDto();

        when(userMapper.toDto(userToSave)).thenReturn(userResponseDto);

        UserResponseDto result = userService.register(request);

        assertNotNull(result);
        assertNotNull(result);
        assertEquals(userResponseDto, result);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = List.of(new User());
        List<UserResponseDto> expectedResult = List.of(new UserResponseDto());
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(pageable)).thenReturn(page);

        when(userMapper.toDto(user)).thenReturn(new UserResponseDto());

        List<UserResponseDto> result = userService.getAll(pageable);

        Assertions.assertEquals(expectedResult.size(), result.size());
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(USER_ID);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(USER_ID);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.findById(USER_ID);

        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testFindUserNonExistedId() {
        when(userRepository.findById(NON_EXISTED_USER_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.findById(NON_EXISTED_USER_ID));
    }

    @Test
    public void testUpdateUserNameSuccessfully() {
        UserUpdateNameRequest request = new UserUpdateNameRequest();
        request.setName(UPDATED_NAME);

        UserResponseDto expectedResult = new UserResponseDto();
        expectedResult.setName(UPDATED_NAME);

        User user = new User();
        user.setName(OLD_NAME);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.toDto(user)).thenReturn(expectedResult);

        UserResponseDto result = userService.updateNameById(USER_ID, request);

        Assertions.assertEquals(result.getName(), expectedResult.getName());
    }

    @Test
    public void testBorrowBookSuccessfully() {
        UserBorrowBookRequest request = new UserBorrowBookRequest();
        request.setBookId(BOOK_ID);

        User user = new User();
        user.setId(USER_ID);
        user.setBooks(new HashSet<>());

        UserResponseDto expectedResult = new UserResponseDto();
        expectedResult.setId(USER_ID);

        Book book = new Book();
        book.setId(BOOK_ID);
        book.setAmount(AMOUNT);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        when(bookRepository.findById(request.getBookId()))
                .thenReturn(Optional.of(book));

        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.toDto(user)).thenReturn(expectedResult);

        UserResponseDto result = userService.borrowBookById(USER_ID, request);

        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReturnBookSuccessfully() {
        UserBorrowBookRequest request = new UserBorrowBookRequest();
        request.setBookId(BOOK_ID);

        User user = new User();
        user.setId(USER_ID);
        user.setBooks(new HashSet<>());

        UserResponseDto expectedResult = new UserResponseDto();
        expectedResult.setId(USER_ID);

        Book book = new Book();
        book.setId(BOOK_ID);
        book.setAmount(AMOUNT);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        when(bookRepository.findById(request.getBookId()))
                .thenReturn(Optional.of(book));

        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.toDto(user)).thenReturn(expectedResult);

        UserResponseDto result = userService.borrowBookById(USER_ID, request);

        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindUserBooksByName() {
        User user = new User();
        user.setName(OLD_NAME);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(OLD_NAME);

        when(userRepository.findByName(user.getName()))
                .thenReturn(Optional.of(user));

        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.findBorrowedBooksByUserName(OLD_NAME);

        Assertions.assertEquals(user.getName(), result.getName());

    }
}
