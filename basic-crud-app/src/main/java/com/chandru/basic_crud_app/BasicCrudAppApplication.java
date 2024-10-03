package com.chandru.basic_crud_app;

import com.chandru.basic_crud_app.ds.IntervalAVLTree;
import com.chandru.basic_crud_app.entity.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// PENDING - write the application business logic
@SpringBootApplication
public class BasicCrudAppApplication {

	public static void main(String[] args) {
		// startup code would be here
		SpringApplication.run(BasicCrudAppApplication.class, args);

		IntervalAVLTree<Book> t = new IntervalAVLTree<>();

		Book[] books = {
				new Book("B", "D"),
				new Book("A", "B"),
				new Book("E", "G"),
				new Book("L", "O"),
				new Book("H", "K")
		};
		
		int i = 0;
		for (Book book : books) {
			t.add(book);
		}


		t.add(new Book("G", "H"));
		t.levelOrder();

		System.out.println(t.add (new Book ("P", "R")));
		t.levelOrder();
		System.out.println(t.add (new Book ("R", "T")));
		System.out.println(t.add (new Book ("T", "Z")));
		System.out.println(t.add (new Book ("O", "P")));

//		t.remove(books[4]);
		t.levelOrder();
//		t.inorder();

		for (Book book : books)
			System.out.println(t.contains(book));

		System.out.println(t.contains(new Book("O", "R")));
		System.out.println(t.remove(books[4]));
		t.levelOrder();
	}
}
