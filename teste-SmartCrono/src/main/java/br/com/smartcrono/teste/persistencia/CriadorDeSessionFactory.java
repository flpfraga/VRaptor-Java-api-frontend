package br.com.smartcrono.teste.persistencia;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class CriadorDeSessionFactory implements ComponentFactory<SessionFactory> {
	
	public final static String TENANT_ANY = "";

	private final SessionFactory conexao;

	private Map<String, ConnectionProvider> mapaConexoes;

	private final AbstractMultiTenantConnectionProvider multiTenantConnectionProvider;

	private Configuration configuracaoGenerica;

	private ServiceRegistryImplementor serviceRegistry;

	public CriadorDeSessionFactory() {
		mapaConexoes = new HashMap<String, ConnectionProvider>();
		
		multiTenantConnectionProvider = new AbstractMultiTenantConnectionProvider() {
			private static final long serialVersionUID = 7923571834863288510L;
			
			private ConnectionProvider get(final String tenantIdentifier) {
				ConnectionProvider conexaoT = mapaConexoes.get(tenantIdentifier);
				if (conexaoT == null ) {
					System.out.println("======== Inicializando tenant ["+tenantIdentifier+"] ========"+new Date());
					conexaoT = buildConnectionProvider(tenantIdentifier);
					if (conexaoT == null) return null;
					mapaConexoes.put(tenantIdentifier, conexaoT);
					if (!tenantIdentifier.equals(TENANT_ANY)) {
						SchemaUpdate schema = new SchemaUpdate(HibernateConfigLoader.load(tenantIdentifier));
						schema.execute(false, true);
					}
					System.gc();
				}
				return conexaoT;
			}

			@Override
			protected ConnectionProvider getAnyConnectionProvider() {
				return get(TENANT_ANY);
			}
			
			@Override
			protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
				return get(tenantIdentifier);
			}
		};
		
		conexao = abrirSessionFactory();
	}

	protected ConnectionProvider buildConnectionProvider(String tenantIdentifier) {
		C3P0ConnectionProvider connectionProvider = new C3P0ConnectionProvider();
		connectionProvider.injectServices(serviceRegistry);
		Configuration config = HibernateConfigLoader.load(tenantIdentifier);
		connectionProvider.configure(config.getProperties());
		return connectionProvider;
	}

	private SessionFactory abrirSessionFactory() {
		System.out.println("======== Criando session factory ========");
		configuracaoGenerica = HibernateConfigLoader.load(TENANT_ANY);
		configuracaoGenerica.getProperties().put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);

		serviceRegistry = (ServiceRegistryImplementor) new ServiceRegistryBuilder()
				.applySettings(configuracaoGenerica.getProperties()).addService(MultiTenantConnectionProvider.class, multiTenantConnectionProvider)
				.buildServiceRegistry();

		return configuracaoGenerica.buildSessionFactory(serviceRegistry);
	}

	@Override
	public SessionFactory getInstance() {
		return conexao;
	}
	
	public ConnectionProvider getConnection(String tenant) {
		ConnectionProvider conexao1 = mapaConexoes.get(tenant);
		if (conexao1 == null) {
			System.out.println("======== Inicializando tenant ["+tenant+"] ========");
			conexao1 = buildConnectionProvider(tenant);
			if (conexao1 == null) return null;
			mapaConexoes.put(tenant, conexao1);
			if (!tenant.equals(TENANT_ANY)) {
				SchemaUpdate schema = new SchemaUpdate(HibernateConfigLoader.load(tenant));
				schema.execute(false, true);
			}
			System.gc();
			
		}
		return conexao1;
	}


	@PreDestroy
	public void fecha() {
		conexao.close();
	}

	public Map<String, ConnectionProvider> getMapaConexoes() {
		return mapaConexoes;
	}

	public void setMapaConexoes(Map<String, ConnectionProvider> mapaConexoes) {
		this.mapaConexoes = mapaConexoes;
	}

	public ServiceRegistryImplementor getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistryImplementor serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	
	

}