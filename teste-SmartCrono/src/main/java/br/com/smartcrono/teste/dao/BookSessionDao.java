package br.com.smartcrono.teste.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.smartcrono.teste.model.Book;
import br.com.smartcrono.teste.persistencia.CriadorDeSession;
import br.com.smartcrono.teste.persistencia.CriadorDeSessionFactory;

@Component
public class BookSessionDao implements SessionDao<Book, Long> {

	private Session session;
	private Transaction transaction;
	private CriadorDeSession criadorDeSession;

	public BookSessionDao() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	@Override
	public void createSession(CriadorDeSessionFactory criadorDeSessionFactory, Proxifier proxifier) {
		this.criadorDeSession = new CriadorDeSession(criadorDeSessionFactory.getInstance(), proxifier);
		this.session = criadorDeSession.getSession();
	}

	@Override
	public List<Book> getAll() {
		transaction = session.beginTransaction();
		return session.createQuery("from Book").list();

	}

	@Override
	public Book getById(Long id) {
		
		try {
			transaction = session.beginTransaction();
			Book book = (Book) session.get(Book.class, id);
			transaction.commit();
			return book;
		} catch (Exception e) {
			return null;
		}
		
		
	}

	@Override
	public Book save(Book book) {
		transaction = session.beginTransaction();
		session.save(book);
		transaction.commit();
		return book;
	}

	@Override
	public Book update(Book book, Long id) {

		Book entity = getById(id);
		transaction = session.beginTransaction();
		entity.setAuthor(book.getAuthor());
		entity.setName(book.getName());
		entity.setPags(book.getPags());
		entity.setYear(book.getYear());
		session.update(entity);
		transaction.commit();
		session.close();
		return book;
	}

	@Override
	public void delete(Long id) {
		Book entity = getById(id);
		transaction = session.beginTransaction();
		session.delete(entity);
		transaction.commit();
		session.close();
	}
}
