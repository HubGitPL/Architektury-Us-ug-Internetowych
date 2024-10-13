package org.example.entities;
//MF 2024
import java.util.*;
import java.io.*;

import lombok.*;
@Getter
public class Author implements Serializable{
    private String name;
    private Integer age;
    private List<Book> books = new ArrayList<>();

    public Author(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Author author = (Author) obj;
        return age.equals(author.age) && name.equals(author.name);
    }

    @Override
    public String toString(){
        return "Author Name: " + name + ", Age: " + age;
    }

}
