package com.chandru.basic_crud_app.dao.impl;

import com.chandru.basic_crud_app.dao.AdminAccessDAO;
import com.chandru.basic_crud_app.dao.ConsumerAccessDAO;
import com.chandru.basic_crud_app.entity.Book;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookingDAO implements ConsumerAccessDAO, AdminAccessDAO {

    private final EntityManager entityManager;

    @Autowired
    public BookingDAO (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<List<Book>> getBooks() {
        String query = "from Book";
        return Optional.ofNullable(entityManager.createQuery(query, Book.class).getResultList());
    }

    @Override
    public Optional<Book> getBook(String id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> book(Book book) {
        return Optional.ofNullable(entityManager.merge(book));
    }

    @Override
    public Optional<Book> cancel(Book book) {
        return book(book);
    }

    @Override
    public void delete(String id) {
        getBook(id).ifPresent(entityManager::remove);
    }
}
