<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Erro</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
  <div class="container">
    <h1 class="error">Ocorreu um erro 😥</h1>
    <p>${mensagem}</p>
    <a class="error" href="${pageContext.request.contextPath}/">Voltar para o formulário de cadastro</a>
  </div>
</body>
</html>