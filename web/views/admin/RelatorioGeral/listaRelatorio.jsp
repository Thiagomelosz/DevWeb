<%@page import="entidade.Turma"%>
<%@page import="entidade.Relatorio"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Relatórios</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Relatórios de Turmas</h2>
                
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Código da Turma</th>
                                <th scope="col">Nome da Matéria</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");

                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    for (Turma turma : listaTurmas) {
                            %>
                                        <tr>
                                            <td><%= turma.getCodigoTurma() %></td>
                                            <td><%= turma.getNomeDisciplina() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/RelatorioController?acao=GerarRelatorio&id=<%= turma.getId() %>" 
                                                   class="btn btn-info">Gerar Relatório</a>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="3">Nenhuma turma cadastrada.</td>
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
