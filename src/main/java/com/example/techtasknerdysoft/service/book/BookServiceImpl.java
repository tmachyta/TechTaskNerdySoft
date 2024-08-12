package com.example.techtasknerdysoft.service.book;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequest request) {
        Book book = bookMapper.toModel(request);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateTitleById(Long id, UpdateBookTitleRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id " + id));
        book.setTitle(request.getTitle());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateAuthorById(Long id, UpdateBookAuthorRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id " + id));
        book.setAuthor(request.getAuthor());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateAmountById(Long id, UpdateBookAmountRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id " + id));
        book.setAmount(request.getAmount());
        return bookMapper.toDto(bookRepository.save(book));
    }
}
