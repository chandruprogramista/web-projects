package com.chandru.basic_crud_app.endpoints;

import com.chandru.basic_crud_app.applogic.Constants;
import com.chandru.basic_crud_app.entity.Book;
import com.chandru.basic_crud_app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin-api")
public class AdminRestController {

    AdminService adminService;

    @Autowired
    public AdminRestController (AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("books")
    public List<Book> getBooks () {
        return List.of(new Book());
    }

    @GetMapping("books/{pnrId}")
    public Book getBook (@PathVariable(name = "pnrId") String pnrId) {
        return null;
    }
}
