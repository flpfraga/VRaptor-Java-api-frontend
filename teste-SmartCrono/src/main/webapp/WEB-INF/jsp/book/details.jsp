<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/header.jsp"></c:import>
<div class="main-container">

	<div class="details-box">

		<div class="details-img">
			<img alt="IMG" src="<c:url value="${book.img}"/>">
		</div>
		<div class="details-description">

			<form action="/teste/book/update/${book.id}" method="post">
				Título: <input class="row-input" value="${book.name}"
					name="book.name"> Ano: <input class="row-input"
					value="${book.year}" name="book.year"> Autor: <input
					class="row-input" value="${book.author}" name="book.author">
				Número de Páginas: <input class="row-input" value="${book.pags}"
					name="book.pags">
				<div class="button-box">

					<button type="submit" name="_method" value="PUT">Salvar</button>
			</form>


		</div>
	</div>

</div>
<form action="/teste/book/${book.id}" method="post">
	<button class="del-button" type="submit" name="_method" value="DELETE">Deletar</button>
</form>

<c:import url="/WEB-INF/jsp/footer.jsp"></c:import>