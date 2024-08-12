package com.example.techtasknerdysoft.mapper;

import com.example.techtasknerdysoft.config.MapperConfig;
import com.example.techtasknerdysoft.dto.book.BookDto;
import com.example.techtasknerdysoft.dto.book.CreateBookRequest;
import com.example.techtasknerdysoft.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequest request);
}
