package br.com.smartcrono.teste.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.XStream;

import br.com.caelum.vraptor.ioc.Component;
import net.vidageek.mirror.dsl.Mirror;

@Component
public class DaoXML<T> {
	
	private static final String ENCODING = "UTF-8";
	private String DIRETORIO_XML = "";
	
	@Inject
	public DaoXML(ServletContext context) {
		String SEPARADOR = System.getProperty("file.separator");
		this.DIRETORIO_XML = context.getRealPath("")+SEPARADOR;
	}

	private XStream xstream;
	protected Class<T> classe;
	
	public void salvar(Class<T> classe, T objeto) throws Exception
	{
		this.classe = classe;
		
		configurarXStream();
		Writer writer = null;
		try
		{
			File f =  new File(getLocalizacaoDoArquivo());
			
			FileOutputStream out = new FileOutputStream(f);
			
			writer = new OutputStreamWriter(out , ENCODING);
			writer.write("<?xml version=\"1.0\" encoding=\""+ENCODING+"\"?>\n");
			xstream.toXML(objeto, writer);
			
			writer.close();
			
			try{
				out.close();
			}catch(Exception e){
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao salvar no arquivo");
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public T carregar(Class<T> classe) throws Exception
	{
		this.classe = classe;
		
		configurarXStream();
		InputStreamReader reader = null;
		try
		{
			File f = new File(getLocalizacaoDoArquivo());
			FileInputStream in = new FileInputStream(f);
			reader = new InputStreamReader(in, ENCODING);
		
			T objeto = (T) Objects.firstNonNull(xstream.fromXML(reader), new Mirror().on(classe).invoke().constructor().withoutArgs());

			
			try{
				reader.close();
				in.close();
			}catch(Exception e){
				
			}
			
			return objeto;
		}
		catch (FileNotFoundException e)
		{
			T objeto = new Mirror().on(classe).invoke().constructor().withoutArgs();
			return objeto;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void configurarXStream()
	{
		xstream = new XStream();
		xstream.ignoreUnknownElements();//DJB-27/01/2020
		xstream.processAnnotations(classe);
	}
	
	private String getLocalizacaoDoArquivo()
	{
		String s = new File(DIRETORIO_XML, getNomeDoArquivo() + ".xml").getAbsolutePath();
		return s;
	}
	
	protected String getNomeDoArquivo()
	{
		return classe.getSimpleName().toLowerCase();
	}

	public void deletar() throws Exception {
		File f = new File(getLocalizacaoDoArquivo());
		if (!f.exists()) {
			throw new Exception("Arquivo não existe!");
		}
		f.delete();
	}

}
