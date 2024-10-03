package com.chandru.basic_crud_app.service;

import com.chandru.basic_crud_app.entity.Book;

import java.util.List;

public interface AdminService {

    List<Book> getBooks ();

    Book getBook (String id);
}
