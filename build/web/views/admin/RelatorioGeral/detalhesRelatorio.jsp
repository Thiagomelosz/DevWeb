<%@ page import="java.util.List" %>
<%@ page import="entidade.Relatorio" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Relatório</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />

        <div class="mt-5">
            <h1>Detalhes do Relatório</h1>

            <%
                // Obtém a lista de relatórios da requisição
                List<Relatorio> listaRelatorios = (List<Relatorio>) request.getAttribute("listaRelatorios");

                // Verifica se a lista de relatórios não é nula nem vazia
                if (listaRelatorios != null && !listaRelatorios.isEmpty()) {
            %>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">ID do Aluno</th>
                        <th scope="col">Nome do Aluno</th>
                        <th scope="col">Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Itera sobre a lista de relatórios e exibe os dados na tabela
                        for (Relatorio relatorio : listaRelatorios) {
                    %>
                        <tr>
                            <td><%= relatorio.getAlunoId() %></td>
                            <td><%= relatorio.getAlunoNome() %></td>
                            <td><%= relatorio.getNota() %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <%
                } else {
            %>
                <p>Nenhum relatório disponível.</p>
            <%
                }
            %>
        </div>

    </div>

    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
