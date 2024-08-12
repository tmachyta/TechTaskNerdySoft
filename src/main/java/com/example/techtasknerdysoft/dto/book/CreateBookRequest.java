package com.example.techtasknerdysoft.dto.book;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateBookRequest {
    private String title;
    private String author;
    private int amount;
}
