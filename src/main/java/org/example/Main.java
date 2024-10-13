package org.example;

import org.example.dtos.BookDTO;
import org.example.entities.Author;
import org.example.entities.Book;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.concurrent.ForkJoinPool;

/**
 Task 2: The authors and their books are printed in the order they were added.
 Task 3: All books from all authors are printed.
 Task 4: Books with more than 250 pages are filtered, sorted by title, and printed.
 Task 5: The books are transformed into BookDTO objects, sorted by title, and printed.
 Task 6: The authors and their books are serialized to a file and then deserialized and printed.
 Task 7: Each author and their books are printed in a separate thread, simulating workload with Thread.sleep().
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //task 2
        System.out.println("Task 2");
        Author author1 = new Author("Author1", 30);
        Author author2 = new Author("Author2", 40);
        Author author3 = new Author("Author3", 50);

        Book book1 = Book.builder().title("Book1").pages(100).author(author1).build();
        Book book2 = Book.builder().title("Book2").pages(200).author(author1).build();
        Book book3 = Book.builder().title("Book3").pages(300).author(author2).build();
        Book book4 = Book.builder().title("Ba").pages(400).author(author2).build();
        Book book5 = Book.builder().title("Bb").pages(500).author(author3).build();

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book3);
        author2.addBook(book4);
        author3.addBook(book5);


        //List<Author> authors = new ArrayList<>();
        List<Author> authors = Arrays.asList(author1, author2, author3);

        //I print here catogieres and elements foreach
        authors.forEach(
                author -> {
                    System.out.println(author);
                    author.getBooks().forEach(
                            System.out::println
                    );
                }
        );

        //task 3
        System.out.println("Task 3");
        Set<Book> allBooksSet = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toSet());

        allBooksSet.forEach(System.out::println);

        //task 4
        System.out.println("Task 4");
        allBooksSet.stream()
                .filter(book -> book.getPages() > 250)
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);

        //task 5
        System.out.println("Task 5");
        List<BookDTO> bookDTOList = allBooksSet.stream()
                .map(book -> new BookDTO(book.getTitle(), book.getPages(), book.getAuthor().getName()))
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .collect(Collectors.toList());

        bookDTOList.forEach(System.out::println);

        //task 6
        System.out.println("Task 6");
        System.out.println("Starting serialization");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("booksFile.ser"))) {
            oos.writeObject(authors);
        }
        System.out.println("Serialization done");
        System.out.println("Starting deserialization");
        List<Author> deserializedAuthors;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("booksFile.ser"))) {
            deserializedAuthors = (List<Author>) ois.readObject();
        }
        System.out.println("Deserialization done");

        deserializedAuthors.forEach(
                author -> {
                    System.out.println(author);
                    author.getBooks().forEach(
                            System.out::println
                    );
                }
        );

        //task 7
        System.out.println("Task 7");
        ForkJoinPool customThreadPool = new ForkJoinPool(2);
        try {
            customThreadPool.submit(() ->
                    authors.parallelStream()
                            .forEach(author -> {
                                System.out.println(Thread.currentThread().getName() + " " + author);
                                author.getBooks().forEach(book -> {
                                    System.out.println(Thread.currentThread().getName() + " " + book);
                                    try {
                                        //overload the thread
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                });
                            })
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            customThreadPool.shutdown();
        }
    }
}