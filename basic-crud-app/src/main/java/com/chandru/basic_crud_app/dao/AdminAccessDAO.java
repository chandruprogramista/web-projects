package com.chandru.basic_crud_app.dao;

import com.chandru.basic_crud_app.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AdminAccessDAO {

    public Optional<List<Book>> getBooks ();

    public Optional<Book> getBook (String id);
}
