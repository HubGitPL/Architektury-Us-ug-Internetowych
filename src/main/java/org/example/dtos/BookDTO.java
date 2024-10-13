package org.example.dtos;

import lombok.*;


@Data
@AllArgsConstructor

public class BookDTO implements Comparable<BookDTO>{
    private String title;
    private Integer pages;
    private String authorName;

    @Override
    public int compareTo(BookDTO book){

        return this.title.compareTo(book.title);
    }

    @Override
    public String toString(){
        return "Title of the book: " + title + ", Number of pages: " + pages + ", Author name: " + authorName;
    }


}
