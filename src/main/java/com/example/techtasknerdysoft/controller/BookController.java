package com.example.techtasknerdysoft.controller;

import com.example.techtasknerdysoft.dto.book.BookDto;
import com.example.techtasknerdysoft.dto.book.CreateBookRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAmountRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAuthorRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookTitleRequest;
import com.example.techtasknerdysoft.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Books management",
        description = "Endpoints for managing Books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Save book to repository",
            description = "Save valid book to repository")
    public BookDto save(@RequestBody @Valid CreateBookRequest request) {
        return bookService.save(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get list of books",
            description = "Get valid list of books")
    public List<BookDto> getAll(@ParameterObject Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get book by ID",
            description = "Get valid books by ID")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Delete book by ID",
            description = "Soft-delete valid book by ID")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/title/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update book title by ID",
            description = "Update valid book title by ID")
    public BookDto updateBookTitleById(@PathVariable Long id,
                                       @RequestBody UpdateBookTitleRequest request) {
        return bookService.updateTitleById(id, request);
    }

    @PutMapping("/author/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update book author by ID",
            description = "Update valid book author by ID")
    public BookDto updateBookAuthorById(@PathVariable Long id,
                                        @RequestBody UpdateBookAuthorRequest request) {
        return bookService.updateAuthorById(id, request);
    }

    @PutMapping("/amount/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update book amount by ID",
            description = "Update valid book amount by ID")
    public BookDto updateBookAmountById(@PathVariable Long id,
                                        @RequestBody UpdateBookAmountRequest request) {
        return bookService.updateAmountById(id, request);
    }
}
