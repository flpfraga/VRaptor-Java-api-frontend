package br.com.smartcrono.teste.controllers;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.thoughtworks.xstream.XStream;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.com.smartcrono.teste.ValidationEntityParamsBook;
import br.com.smartcrono.teste.arquivo.Arquivo;
import br.com.smartcrono.teste.dao.BookSessionDao;
import br.com.smartcrono.teste.model.Book;

@Resource
@Path("/book")
public class BookController {

	@Inject
	private BookSessionDao dao;

	@Inject
	public Result result;

	@Inject
	public Arquivo arquivo;

	public ValidationEntityParamsBook validationEntityParamsBook;

	private Validator validator;

	protected BookController() {
		this(null);
	}

	@Inject
	public BookController(Validator validator) {
		this.validator = validator;
		this.validationEntityParamsBook = new ValidationEntityParamsBook(validator);
	}

	@Get("/id/{id}")
	public void findById(Long id) {
		System.out.println("get by id " + id);
		Book book = dao.getById(id);
		System.out.println(book.toString());
		XStream xStream = new XStream();
		result.use(Results.json()).from(book).recursive().serialize();

	}

	@Get("/")
	public void lista() {
		List<Book> books = dao.getAll();
		for (Book b : books) {
			b.setImg("/assets/bookImg/" + b.getImg());
		}
		result.include("books", books);
	}

	@Get
	@Path({ "/createBook/{msg}", "/createBook" })
	public void form(String msg) {
		if (!(msg == null)) {
			result.include("msg", msg);
		}
	}

	@Put("/update/{id}")
	public void update(Book book, Long id) {
		dao.update(book, id);
		result.redirectTo(IndexController.class).index();
	}

	@Post("/submitBook")
	public void submitBook(UploadedFile upload, Book book) {

		validator = validationEntityParamsBook.validBookParams(book);

		if (validator.hasErrors()) {
			System.out.println(validator.getErrors().toString());
		}

		validator.onErrorRedirectTo(this).form("Verifique os erros antes de prosseguir!");

		if (upload == null) {
			System.out.println("upload is null");
		} else {
			int hashImgBook = book.hashCode();
			String file = "D:\\Java\\VRAPTOR\\exem\\teste-SmartCrono\\src\\main\\webapp\\assets\\bookImg\\";
			String[] name = upload.getContentType().split("/");
			String fileName = hashImgBook + "." + name[1];
			try {
				arquivo.upload(file, fileName, upload.getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			book.setImg(hashImgBook + "." + name[1]);
			dao.save(book);
			String msg = "Livro incluído com sucesso!";
			result.include("msg", msg);
			result.redirectTo(this).form(msg);
		}
	}

	@Get
	@Path("/details/{id}")
	public void details(Long id) {
		Book book = dao.getById(id);
		book.setImg("/assets/bookImg/" + book.getImg());
		result.include("book", book);
	}

	@Delete("/{id}")
	public void delete(Long id) {
		dao.delete(id);
		result.redirectTo(IndexController.class).index();
	}

}
