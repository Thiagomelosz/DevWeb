<%@page import="entidade.Relatorio"%>
<%@page import="entidade.Aluno"%>
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
                <!-- Remova ou comente as linhas abaixo se não quiser exibir mensagens de erro ou sucesso -->
                <!--
                <c:if test="${not empty msgError}">
                    <div class="alert alert-danger">${msgError}</div>
                </c:if>
                <c:if test="${not empty msgSuccess}">
                    <div class="alert alert-success">${msgSuccess}</div>
                </c:if>
                -->
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID da Turma</th>
                                <th scope="col">Código da Turma</th>
                                <th scope="col">Nota</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Relatorio> listaRelatorios = (List<Relatorio>) request.getAttribute("listaRelatorios");
                                Aluno alunoLogado = (Aluno) request.getAttribute("alunoLogado");

                                if (listaRelatorios != null && !listaRelatorios.isEmpty()) {
                                    for (Relatorio relatorio : listaRelatorios) {
                            %>
                                        <tr>
                                            <td><%= relatorio.getTurmaId() %></td>
                                            <td><%= relatorio.getCodigoTurma() %></td>
                                            <td><%= relatorio.getNota() %></td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="3">Nenhuma nota encontrada.</td>
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
