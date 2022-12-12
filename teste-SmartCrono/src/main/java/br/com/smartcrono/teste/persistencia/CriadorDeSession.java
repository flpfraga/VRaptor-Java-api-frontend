package br.com.smartcrono.teste.persistencia;

import java.lang.reflect.Method;

import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;
import net.vidageek.mirror.dsl.Mirror;


@RequestScoped 
@Component
public class CriadorDeSession implements ComponentFactory<Session>
{

	private final SessionFactory conexao;
	private Session sessao;
	
	private static final Method CLOSE =
            new Mirror().on(Session.class).reflect().method("close").withoutArgs();
    private static final Method FINALIZE =
            new Mirror().on(Object.class).reflect().method("finalize").withoutArgs();
    private final Session proxy;

	public CriadorDeSession(SessionFactory factory, Proxifier proxifier) {
		this.conexao = factory;
		this.proxy = proxify(Session.class, proxifier);
	}
	
	/**
     * Cria o JIT Session, que repassa a invocação de qualquer método, exceto
     * {@link Object#finalize()} e {@link Session#close()}, para uma session real,
     * criando uma se necessário.
     */
    private Session proxify(Class<? extends Session> target, Proxifier proxifier) {
        return proxifier.proxify(target, new MethodInvocation<Session>() {
            @Override // *2*
            public Object intercept(Session proxy, Method method, Object[] args,
                                                            SuperMethod superMethod) {
                if (method.equals(CLOSE)
                        || (method.equals(FINALIZE) && sessao == null)) {
                    return null; //skip
                }
                return new Mirror().on(getSession()).invoke().method(method)
                                    .withArgs(args);
            }
        });
    }
    
    public Session getSession() {
        if (sessao == null){ 
        	String tenantId = "teste";
        	sessao = conexao.withOptions().tenantIdentifier(tenantId).openSession();
        }
        return sessao;
    }
	
	
	@Override
	public Session getInstance() {
		return proxy;
	}
	
	@PreDestroy
	public void fechar() {	
		if (sessao != null && sessao.isOpen()) {
			sessao.close();
        }
	}

}