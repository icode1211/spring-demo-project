package com.estsoft.springdemoproject.book.controller;

import com.estsoft.springdemoproject.book.domain.Book;
import com.estsoft.springdemoproject.book.service.BookService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest2 {
    @InjectMocks
    BookController controller;

    @Mock
    BookService bookService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void test() throws Exception {
        // given
        Book book1 = new Book("first", "name", "author");
        Book book2 = new Book("second", "name2", "author2");

        List<Book> bookList = List.of(book1, book2);
        Mockito.when(bookService.findAll()).thenReturn(bookList);

        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("bookList"))
                .andExpect(model().attribute("bookList", hasSize(2)))
                ;
    }

}