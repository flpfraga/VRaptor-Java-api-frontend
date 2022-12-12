<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/header.jsp"></c:import>
<div class="main-container">

	<form class="form-box" action="<c:url value='/book/submitBook'/>" method="post"
		enctype="multipart/form-data">
		${msg}
		<h3>Preencha os campos abaixo:</h3>
		<input class="row-input" type="file" name="upload"> <input
			class="row-input" placeholder="Título" name="book.name"> <input
			class="row-input" placeholder="Ano" name="book.year"> <input
			class="row-input" placeholder="Autor" name="book.author"> <input
			class="row-input" placeholder="Páginas" name="book.pags">
		<button type="submit" class="button-save">Salvar</button>
	</form>



</div>
<c:import url="/WEB-INF/jsp/footer.jsp"></c:import>