<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/header.jsp"></c:import>
<ul class="books-list">
	<c:forEach items="${books}" var="b">

		<li><a class="link-box" href="/teste/book/details/${b.id}">
				<div class="img-box">
					<img alt="IMG" src="<c:url value="${b.img}"/>">
				</div>
				<div class="description-box">
					<div>Título: ${b.name}</div>
					<div>Autor: ${b.author}</div>
					<div>Ano: ${b.year}</div>
					<div>Páginas: ${b.pags}</div>
				</div>
		</a></li>

	</c:forEach>

</ul>
<c:import url="/WEB-INF/jsp/footer.jsp"></c:import>