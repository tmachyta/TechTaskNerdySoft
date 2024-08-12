package com.example.techtasknerdysoft.service.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.techtasknerdysoft.dto.book.BookDto;
import com.example.techtasknerdysoft.dto.book.CreateBookRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAmountRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookAuthorRequest;
import com.example.techtasknerdysoft.dto.book.UpdateBookTitleRequest;
import com.example.techtasknerdysoft.exception.EntityNotFoundException;
import com.example.techtasknerdysoft.mapper.BookMapper;
import com.example.techtasknerdysoft.model.Book;
import com.example.techtasknerdysoft.repository.BookRepository;
import java.util.List;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final Long BOOK_ID = 1L;
    private static final Long NON_EXISTED_BOOK_ID = 50L;
    private static final String OLD_TITLE = "Old";
    private static final String UPDATED_TITLE = "Updated";
    private static final String OLD_AUTHOR = "Author Old";
    private static final String UPDATED_Author = "Author Updated";
    private static final int OLD_AMOUNT = 1;
    private static final int UPDATED_AMOUNT = 2;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testSuccessfullySaveBook() {
        CreateBookRequest request = new CreateBookRequest();
        BookDto bookDto = new BookDto();
        Book bookToSave = new Book();

        when(bookMapper.toModel(request)).thenReturn(bookToSave);

        when(bookRepository.save(bookToSave)).thenReturn(bookToSave);

        when(bookMapper.toDto(bookToSave)).thenReturn(bookDto);

        BookDto result = bookService.save(request);

        assertNotNull(result);
        assertEquals(bookDto, result);
    }

    @Test
    public void testGetAllTrainingPrograms() {
        Book book = new Book();
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(new Book());
        List<BookDto> expectedBooks = List.of(new BookDto());
        Page<Book> page =
                new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(page);

        when(bookMapper.toDto(book)).thenReturn(new BookDto());

        List<BookDto> result = bookService.getAll(pageable);

        Assertions.assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    public void testFindBookById() {
        Book book = new Book();
        book.setId(BOOK_ID);
        BookDto bookDto = new BookDto();
        bookDto.setId(BOOK_ID);

        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(BOOK_ID);

        Assertions.assertEquals(book.getId(), result.getId());
    }

    @Test
    public void testFindBookWithNonExistedId() {
        when(bookRepository.findById(NON_EXISTED_BOOK_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(NON_EXISTED_BOOK_ID));
    }

    @Test
    public void testDeleteBookById() {
        bookService.deleteById(BOOK_ID);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(BOOK_ID));
    }

    @Test
    public void testUpdateBookTitleSuccessfully() {
        UpdateBookTitleRequest request = new UpdateBookTitleRequest();
        request.setTitle(UPDATED_TITLE);

        BookDto expectedResult = new BookDto();
        expectedResult.setTitle(UPDATED_TITLE);

        Book book = new Book();
        book.setTitle(OLD_TITLE);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));

        when(bookRepository.save(book)).thenReturn(book);

        when(bookMapper.toDto(book)).thenReturn(expectedResult);

        BookDto updatedBook = bookService.updateTitleById(BOOK_ID, request);
        Assertions.assertEquals(updatedBook.getTitle(), expectedResult.getTitle());
    }

    @Test
    public void testUpdateBookAuthorSuccessfully() {
        UpdateBookAuthorRequest request = new UpdateBookAuthorRequest();
        request.setAuthor(UPDATED_Author);

        BookDto expectedResult = new BookDto();
        expectedResult.setAuthor(UPDATED_Author);

        Book book = new Book();
        book.setAuthor(OLD_AUTHOR);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));

        when(bookRepository.save(book)).thenReturn(book);

        when(bookMapper.toDto(book)).thenReturn(expectedResult);

        BookDto updatedBook = bookService.updateAuthorById(BOOK_ID, request);
        Assertions.assertEquals(updatedBook.getAuthor(), expectedResult.getAuthor());
    }

    @Test
    public void testUpdateBookAmountSuccessfully() {
        UpdateBookAmountRequest request = new UpdateBookAmountRequest();
        request.setAmount(UPDATED_AMOUNT);

        BookDto expectedResult = new BookDto();
        expectedResult.setAmount(UPDATED_AMOUNT);

        Book book = new Book();
        book.setAmount(OLD_AMOUNT);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));

        when(bookRepository.save(book)).thenReturn(book);

        when(bookMapper.toDto(book)).thenReturn(expectedResult);

        BookDto updatedBook = bookService.updateAmountById(BOOK_ID, request);
        Assertions.assertEquals(updatedBook.getAmount(), expectedResult.getAmount());
    }
}
