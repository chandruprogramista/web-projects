package com.chandru.basic_crud_app.service.impl;

import com.chandru.basic_crud_app.dao.impl.BookingDAO;
import com.chandru.basic_crud_app.entity.Book;
import com.chandru.basic_crud_app.service.AdminService;
import com.chandru.basic_crud_app.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements AdminService, ConsumerService {

    BookingDAO bookingDAO;

    @Autowired
    public ReservationService (BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<Book> getBooks() {
        Optional<List<Book>> books = bookingDAO.getBooks();
        return books.orElseGet(List::of);
    }

    @Override
    public Book getBook(String id) {
        return bookingDAO.getBook(id).orElseGet(Book::new);
    }

    @Override
    public Book doBook(Book book) {
        return bookingDAO.book(book).orElseGet(Book::new);
    }

    @Override
    public Book doCancel(Book book) {
        return bookingDAO.cancel(book).orElseGet(Book::new);
    }

    @Override
    public void doTotalCancel(String id) {
        bookingDAO.delete(id);
    }
}
