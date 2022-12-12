package br.com.smartcrono.teste.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {
	
	@Inject
	private Result linkTo;

	@Path("/")
    public void index(){}
	
	public void linkTo(String url) {
		System.out.println("url");
		linkTo.redirectTo(BookController.class).lista();
	}
}
