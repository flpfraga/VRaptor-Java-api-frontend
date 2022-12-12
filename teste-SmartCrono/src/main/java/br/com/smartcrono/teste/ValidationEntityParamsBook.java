package br.com.smartcrono.teste;

import java.sql.Date;
import java.time.LocalDate;

import org.jsoup.helper.Validate;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.smartcrono.teste.model.Book;

public class ValidationEntityParamsBook {

	private final Validator validator;

	public ValidationEntityParamsBook(Validator validator) {
		this.validator = validator;
	}

	public Validator validBookParams(Book book) {
		validName(book);
		validYear(book);
		return validator;
	}

	private void validName(Book book) {
		if (book.getName() == null) {
			validator.add(new ValidationMessage("Campo título obrigatório", "erro"));
		} else if (book.getName().isEmpty()) {
			validator.add(new ValidationMessage("Campo título obrigatório", "erro"));
		}
	}

	private void validYear(Book book) {
		if (book.getYear() == null) {
			validator.add(new ValidationMessage("Campo ano obrigatório", "erro"));
		} else if (book.getYear() > LocalDate.now().getYear()) {
			validator.add(new ValidationMessage("Data inválida", "erro"));
		}

	}

}
