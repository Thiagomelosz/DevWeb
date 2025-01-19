<%@page import="entidade.Turma"%>
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
        <title>Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">

                <h1>Área Restrita</h1>
                <h2>Lista de Turmas</h2>

                <a href="/aplicacaoMVC/admin/TurmaController?acao=Incluir" class="mb-2 btn btn-primary">Criar Turma</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Codigo da Turma</th>
                                <th scope="col">Nome da Matéria</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Aluno</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");

                                // Verifica se a lista de disciplinas não é nula nem vazia
                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    for (Turma turma : listaTurmas) {
                                        // Log para verificar se o nome do aluno está sendo recuperado corretamente
                                        System.out.println("Nome do aluno: " + turma.getNomeAluno());
                            %>
                                        <tr>
                                            <td><%= turma.getCodigoTurma() %></td>
                                            <td><%= turma.getNomeDisciplina() %></td>
                                            <td><%= turma.getNomeProfessor() %></td>
                                            <td><%= turma.getNomeAluno() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Alterar&id=<%= turma.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Excluir&id=<%= turma.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhuma turma cadastrada.</td>
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
