package com.example.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Exception.BookNotFoundException;
import com.example.Model.Book;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    
    private Book copyBook(Book book) {
        return new Book(
            book.getBookId(),
            book.getBookName(),
            book.getAuthor(),
            book.getPrice(),
            book.isAvailable()
        );
    }

    // add
    public Book addBook(Book book) {
        Book newBook = copyBook(book);
        newBook.setBookId(nextId++);
        books.add(newBook);
        return copyBook(newBook);
    }

    // display
    public List<Book> getAllBooks() {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            result.add(copyBook(book));
        }

        return result;
    }

    // get by id
    public Book getBookById(Long id) {
        for (Book book : books) {
            if (book.getBookId().equals(id)) {
                return copyBook(book);
            }
        }

        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    // update
    public Book updateBook(Long id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (book.getBookId().equals(id)) {
                Book newBook = copyBook(updatedBook);
                newBook.setBookId(id);
                books.set(i, newBook);
                return copyBook(newBook);
            }
        }

        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    //  Update Availability
    public Book updateAvailability(Long id, boolean available) {
        for (Book book : books) {
            if (book.getBookId().equals(id)) {
                book.setAvailable(available);
                return copyBook(book);
            }
        }

        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    // delete
    public String deleteBook(Long id) {
        for (Book book : books) {
            if (book.getBookId().equals(id)) {
                books.remove(book);
                return "Book deleted successfully";
            }
        }

        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    //  Search by author
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(copyBook(book));
            }
        }

        return result;
    }

    // Search by book name
    public List<Book> searchByBookName(String name) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getBookName().toLowerCase()
                    .contains(name.toLowerCase())) {
                result.add(copyBook(book));
            }
        }

        return result;
    }

    //  Available books
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.isAvailable()) {
                result.add(copyBook(book));
            }
        }

        return result;
    }

    // Filter by price range
    public List<Book> getBooksByPriceRange(
            BigDecimal minPrice,  BigDecimal maxPrice) {

        List<Book> result = new ArrayList<>();

        for (Book book : books) {
        	if(book.getPrice()  != null
                     && book.getPrice().compareTo(minPrice) >= 0
                     && book.getPrice().compareTo(maxPrice) <= 0) {
                 result.add(copyBook(book));
             }
        }

        return result;
    }
    
  

}