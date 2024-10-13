package org.example.entities;

import lombok.*;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book implements Comparable<Book>, Serializable{
    private String title;
    private Integer pages;
    private Author author;

    @Override
    public int compareTo(Book book){
        return this.title.compareTo(book.title);
    }

//    @Override
//    public int hashCode(){
//        return Objects.hash(title, pages, author);
//    }
//
//    @Override
//    public boolean equals(Object obj){
//        if(this == obj){
//            return true;
//        }
//        if(obj == null || getClass() != obj.getClass()){
//            return false;
//        }
//        Book book = (Book) obj;
//        return title.equals(book.title) && pages.equals(book.pages) && author.equals(book.author);
//    }

    @Override
    public String toString(){
        return "Title of the book: " + title + ", Number of pages: " + pages + ", Author: " + author;
    }

}
