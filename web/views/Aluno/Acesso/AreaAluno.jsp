<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Aluno" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Área Restrita</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <%
                    Aluno alunoLogado = (Aluno) session.getAttribute("aluno");
                    
                    if (alunoLogado == null) {
                        // Se o aluno não está na sessão, redireciona para o login
                        response.sendRedirect(request.getContextPath() + "/AutenticaController");

                        return;
                    }
                %>
                
                <!-- Exibir os dados do aluno logado -->
                <h3>Aluno logado com sucesso</h3>
                <h2>Nome: <%= alunoLogado.getNome() %></h2>
            </div>
        </div>
        
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
