package com.example.techtasknerdysoft.dto.user;

import com.example.techtasknerdysoft.dto.book.BookDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponseDto {
    private Long id;
    private String name;
    private LocalDateTime membershipDate;
    private List<BookDto> books;
}
