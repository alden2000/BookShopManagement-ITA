package com.finalTest.library.books.entity;

import com.finalTest.library.genres.entity.Genre;
import com.finalTest.library.languages.entity.Language;
import com.finalTest.library.states.entity.State;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books", schema = "library")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    private String primaryTitle;
    @Basic(optional = false)
    private String title;

    private Integer yearOfRelease;
    //@Basic(optional = false)
    @Column(nullable = false)
    private Integer inStock;
    @Basic(optional = false)
    private BigDecimal price;

    private String Author;

    private String imgUrl;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    //@JoinColumn(name = "genre_id", referencedColumnName = "id")
    private List<Genre> genre;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    //@JoinColumn(name = "language_id", referencedColumnName = "id")
    private List<Language> language;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    public Book() {
    }

    public Book(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Genre> getGenreList() {
        return genre;
    }

    public void setGenreList(List<Genre> genre) {
        this.genre = genre;
    }

    public List<Language> getLanguageList() {
        return language;
    }

    public void setLanguageList(List<Language> language) {
        this.language = language;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", primaryTitle='" + primaryTitle + '\'' +
                ", title='" + title + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", price=" + price +
                ", inStock=" + inStock +
                ", Author='" + Author + '\'' +
                ", genre=" + genre +
                ", language=" + language +
                ", state=" + state +
                '}';
    }
}
