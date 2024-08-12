package com.example.techtasknerdysoft.dto.book;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateBookTitleRequest {
    private String title;
}
