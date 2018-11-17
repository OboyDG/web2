package com.example.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "WishList")
@Table(name = "wishList")
public class WishList implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private UserBookId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private Book book;
 
    @Column(name = "added_on")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date addedOn;
    
    @SuppressWarnings("unused")
	private WishList() {}
    
    public WishList(User user, Book book) 
    {
        this.user = user;
        this.book = book;
        
        if(book != null && user != null)
        {
            this.id = new UserBookId(user.getId(), book.getId());
        }
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        WishList that = (WishList) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(book, that.book);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }

	public UserBookId getId() {
		return id;
	}

	public void setId(UserBookId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	} 
}
