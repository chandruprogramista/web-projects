package com.chandru.basic_crud_app.dao;

import com.chandru.basic_crud_app.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ConsumerAccessDAO {

    public Optional<Book> book (Book book);

    public Optional<Book> cancel (Book book);

    public void delete (String id);
}
