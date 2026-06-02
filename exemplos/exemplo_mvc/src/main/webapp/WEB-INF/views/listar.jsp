<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Listar Usuários</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
  <div class="container">
    <h1>Listar usuários cadastrados</h1>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Email</th>
        </tr>
        <c:forEach var="usuario" items="${usuarios}">
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nome}</td>
                <td>${usuario.email}</td>
            </tr>
        </c:forEach>
    </table>

    <a href="${pageContext.request.contextPath}/">Voltar para o formulário de cadastro</a>
  </div>
</body>
</html>