package br.com.smartcrono.teste.persistencia;

import org.hibernate.cfg.Configuration;

import br.com.smartcrono.teste.model.Book;


public class HibernateConfigLoader {
	
	
	public static Configuration load(String tenantId) {
		Configuration configuracaoDesteTenant = new Configuration();
		configuracaoDesteTenant.addAnnotatedClass(Book.class);
		configuracaoDesteTenant.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		configuracaoDesteTenant.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		configuracaoDesteTenant.setProperty("hibernate.connection.url", ConfiguracoesBancoDeDados.getString("url")); 
		configuracaoDesteTenant.setProperty("hibernate.connection.username", ConfiguracoesBancoDeDados.getString("usuario"));
		configuracaoDesteTenant.setProperty("hibernate.connection.password", ConfiguracoesBancoDeDados.getString("senha"));
		configuracaoDesteTenant.setProperty("hibernate.show_sql", "true");
		configuracaoDesteTenant.setProperty("hibernate.hbm2ddl.auto", "update");
		return configuracaoDesteTenant;
	}
	

}
