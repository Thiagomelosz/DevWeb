<%@page import="entidade.Turma"%>
<%@page import="entidade.Aluno"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

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

                <h2>Lista de Turmas</h2>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Codigo da Turma</th>
                                <th scope="col">Nome da Matéria</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                                Aluno alunoLogado = (Aluno) request.getAttribute("alunoLogado");

                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    for (Turma turma : listaTurmas) {
                                        
                                        boolean isInscrito = (alunoLogado != null) && turma.isAlunoInscrito(alunoLogado.getId());
                            %>
                                        <tr>
                                            <td><%= turma.getCodigoTurma() %></td>
                                            <td><%= turma.getNomeDisciplina() %></td>
                                            <td><%= turma.getNomeProfessor() %></td>
                                            <td>
                                                <form action="/aplicacaoMVC/aluno/TurmasController" method="POST">
                                                    <input type="hidden" name="acao" value="Inscrever">
                                                    <input type="hidden" name="idTurma" value="<%= turma.getId() %>">
                                                    <button type="submit" class="btn btn-success" <%= isInscrito ? "disabled" : "" %>>Se Inscrever</button>
                                                </form>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="4">Nenhuma turma cadastrada.</td>
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
