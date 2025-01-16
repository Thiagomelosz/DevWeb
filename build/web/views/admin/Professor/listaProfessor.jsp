<%@page import="entidade.Professor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Alunos</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">

                <h1>Área Restrita</h1>
                <h2>Lista de Professores</h2>
                
                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Incluir" class="mb-2 btn btn-primary">Criar Professor</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Email</th>
                                <th scope="col">Senha</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                //verificando se a lista é null
                                ArrayList<Professor> listaProfessor = (ArrayList<Professor>) request.getAttribute("listaProfessor");
                                if (listaProfessor == null) {
                                   // System.out.println("A lista de Professor é NULA na JSP!");
                                } else {
                                   // System.out.println("Tamanho da lista de Professor na JSP: " + listaProfessor.size());
                                }

                                if (listaProfessor != null && !listaProfessor.isEmpty()) {
                                    for (Professor professor : listaProfessor) {
                            %>
                                        <tr>
                                            <td><%= professor.getId() %></td>
                                            <td><%= professor.getNome() %></td>
                                            <td><%= professor.getEmail() %></td>
                                            <td><%= professor.getSenha() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Alterar&id=<%= professor.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Excluir&id=<%= professor.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhum Professor cadastrado.</td>
                                    </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>

    </body>
</html>
