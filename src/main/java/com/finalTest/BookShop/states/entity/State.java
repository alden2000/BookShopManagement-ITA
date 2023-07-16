package com.finalTest.BookShop.states.entity;

import com.finalTest.BookShop.books.entity.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "states", schema = "library")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "state")
    private List<Book> book;

    public State() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + "i" + id;
    }

    public State splitStateString(String toString){
        String[] arrayOfString = toString.split("i");
        setName(arrayOfString[0]);
        setId((long) Integer.parseInt(arrayOfString[1]));
        return this;
    }
}
