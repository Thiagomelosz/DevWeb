<%@page import="entidade.Relatorio"%>
<%@page import="entidade.Aluno"%>
<%@page import="entidade.Professor"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de Notas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Notas do Aluno</h1>
                
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID da Turma</th>
                                <th scope="col">Código da Turma</th>
                                <th scope="col">Nome do Aluno</th>
                                <th scope="col">Nota</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Relatorio> listaRelatorios = (List<Relatorio>) request.getAttribute("listaRelatorios");
                                Professor professorLogado = (Professor) request.getAttribute("professorLogado");

                                if (listaRelatorios != null && !listaRelatorios.isEmpty()) {
                                    for (Relatorio relatorio : listaRelatorios) {
                            %>
                                        <tr>
                                            <td><%= relatorio.getTurmaId() %></td>
                                            <td><%= relatorio.getCodigoTurma() %></td>
                                            <td><%= relatorio.getAlunoNome() %></td>
                                            <td><%= relatorio.getNota() %></td>
                                            <td>
                                                <form action="AlterarNotaController" method="post">
                                                    <input type="hidden" name="turmaId" value="<%= relatorio.getTurmaId() %>">
                                                    <input type="hidden" name="codigoTurma" value="<%= relatorio.getCodigoTurma() %>">
                                                    <input type="hidden" name="alunoNome" value="<%= relatorio.getAlunoNome() %>">
                                                    <input type="hidden" name="nota" value="<%= relatorio.getNota() %>">
                                                    <a href="/aplicacaoMVC/professor/PostarNotasController?acao=Alterar&id=<%= relatorio.getTurmaId() %>" class="btn btn-warning">Alterar</a>
                                                </form>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhuma nota encontrada.</td>
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
