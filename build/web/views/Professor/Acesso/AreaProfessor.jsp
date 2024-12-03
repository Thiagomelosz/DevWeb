<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Professor" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Área Restrita</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <%
                    Professor professorLogado = (Professor) session.getAttribute("professor");
                    out.println("<h3>Usuário logado com sucesso</h3>");
                    out.println("<h2>Seja bem-vindo " + professorLogado.getNome() + "!</h2>");
                %>
            </div>
            <div class="container mt-5">
                <div class="card text-center p-4 shadow-lg" style="max-width: 400px; margin: auto; background-color: #f8f9fa; border-radius: 10px;">
                    <h4 class="card-title mb-3">Cadastrar Usuário</h4>
                    <p class="card-text">Clique no botão abaixo para cadastrar um novo usuário.</p>
                    <a href="/aplicacaoMVC/admin/RegistrarAluno" class="btn btn-lg btn-primary mt-3" style="width: 100%;">Fazer Cadastro</a>
                </div>
            </div>
            <div class="container mt-5">
                <div class="card text-center p-4 shadow-lg" style="max-width: 400px; margin: auto; background-color: #f8f9fa; border-radius: 10px;">
                    <h4 class="card-title mb-3">Criar Nova Turma</h4>
                    <p class="card-text">Clique no botão abaixo para criar uma nova turma.</p>
                    <a href="/aplicacaoMVC/admin/CadastroTurmas" class="btn btn-lg btn-primary mt-3" style="width: 100%;">Criar Turma</a>
                </div>
            </div>
            <div class="container mt-5">
                <div class="card text-center p-4 shadow-lg" style="max-width: 400px; margin: auto; background-color: #f8f9fa; border-radius: 10px;">
                    <h4 class="card-title mb-3">Criar Nova Disciplina</h4>
                    <p class="card-text">Clique no botão abaixo para criar uma nova disciplina.</p>
                    <a href="/aplicacaoMVC/admin/CadastroDisciplinas" class="btn btn-lg btn-primary mt-3" style="width: 100%;">Criar Disciplina</a>
                </div>
            </div>
            <div class="container mt-5">
                <div class="card text-center p-4 shadow-lg" style="max-width: 400px; margin: auto; background-color: #f8f9fa; border-radius: 10px;">
                    <h4 class="card-title mb-3">Gerar Relatórios</h4>
                    <p class="card-text">Clique no botão abaixo para gerar relatórios de disciplina.</p>
                    <a href="/aplicacaoMVC/admin/CadastroDisciplinas" class="btn btn-lg btn-primary mt-3" style="width: 100%;">Gerar Relatório</a>
                </div>
            </div>
                
            
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
