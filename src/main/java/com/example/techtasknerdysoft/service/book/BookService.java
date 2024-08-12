package com.example.techtasknerdysoft.service.book;

import com.example.techtasknerdysoft.dto.book.BookDto;
import com.example.techtasknerdysoft.dto.book.CreateBookRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAmountRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAuthorRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookTitleRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequest request);

    List<BookDto> getAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateTitleById(Long id, UpdateBookTitleRequest request);

    BookDto updateAuthorById(Long id, UpdateBookAuthorRequest request);

    BookDto updateAmountById(Long id, UpdateBookAmountRequest request);
}
