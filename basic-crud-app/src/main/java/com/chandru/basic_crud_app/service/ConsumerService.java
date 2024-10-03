package com.chandru.basic_crud_app.service;

import com.chandru.basic_crud_app.entity.Book;

public interface ConsumerService {

    Book doBook (Book book);

    Book doCancel (Book book);

    void doTotalCancel (String id);
}
