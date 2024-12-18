<%@page import="entidade.Aluno"%>
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
                <h2>Lista de Alunos</h2>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Email</th>
                                <th scope="col">Celular</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Obtendo o atributo e verificando se a lista está vazia ou nula
                                ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAluno");
                                if (listaAlunos == null) {
                                    System.out.println("A lista de alunos é NULA na JSP!");
                                } else {
                                    System.out.println("Tamanho da lista de alunos na JSP: " + listaAlunos.size());
                                }

                                if (listaAlunos != null && !listaAlunos.isEmpty()) {
                                    for (Aluno aluno : listaAlunos) {
                            %>
                                        <tr>
                                            <td><%= aluno.getId() %></td>
                                            <td><%= aluno.getNome() %></td>
                                            <td><%= aluno.getEmail() %></td>
                                            <td><%= aluno.getCelular() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/AlunoController?acao=Alterar&id=<%= aluno.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/AlunoController?acao=Excluir&id=<%= aluno.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhum aluno cadastrado.</td>
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
