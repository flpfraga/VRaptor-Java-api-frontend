package br.com.smartcrono.teste.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Book implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Length(min=2, message = "O título deve ter no mínimo 2 letras")
	@Column(nullable = false)
	private String name;
	
	@Length(min=2)
	private String author;
	
	@NotNull
	@Length(min = 4, max = 4, message = "O ano deve ter 4 dígitos")
	@Past(message = "A data deve ser anterior a atual")
	@Column(nullable = false)
	private Long year;
	
	@Min(0)
	private Long pags;
	
	private String img;


	public Book() {
		// TODO Auto-generated constructor stub
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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getPags() {
		return pags;
	}

	public void setPags(Long pags) {
		this.pags = pags;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, img, name, pags, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && img == other.img && Objects.equals(name, other.name)
				&& Objects.equals(pags, other.pags) && Objects.equals(year, other.year);
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", year=" + year + ", pags=" + pags
				+ ", img=" + img + "]";
	}
	
	
	
	

	
}
