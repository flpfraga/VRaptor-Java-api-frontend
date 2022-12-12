package br.com.smartcrono.teste.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.smartcrono.teste.model.Book;
import br.com.smartcrono.teste.persistencia.CriadorDeSessionFactory;

public interface SessionDao<T, D>{
	
	void createSession(CriadorDeSessionFactory criadorDeSessionFactory, Proxifier proxifier);

	List<T> getAll();

	Book getById(D id);

	Book save(T entity);

	Book update(T entity, D id);

	void delete(D id);

}