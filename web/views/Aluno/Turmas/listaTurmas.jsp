<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.List, entidade.Turma, entidade.Aluno" %>
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
                    Aluno alunoLogado = (Aluno) session.getAttribute("aluno");
                    List<Turma> turmas = (List<Turma>) request.getAttribute("turmas"); 
                    out.println("Aluno logado: " + (alunoLogado != null ? alunoLogado.getNome() : "Nenhum aluno encontrado"));
                    // Verificação de sessão e turmas
                    if (alunoLogado != null) {
                        out.println("Aluno logado: " + alunoLogado.getNome());
                    } else {
                        out.println("Nenhum aluno logado.");
                    }

                    if (turmas == null || turmas.isEmpty()) {
                        out.println("Nenhuma turma foi passada para a JSP.");
                    } else {
                        out.println("Turmas recebidas na JSP: " + turmas.size());
                    }
                %>
                <h3>Usuário logado com sucesso</h3>
                <h2>Nome: <%= alunoLogado != null ? alunoLogado.getNome() : "Desconhecido" %></h2>
                <h3>Turmas disponíveis:</h3>

                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome da Turma</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (turmas != null && !turmas.isEmpty()) {
                                for (Turma turma : turmas) {
                                    out.println("Exibindo turma: " + turma.getId());
                        %>
                        <tr>
                            <td><%= turma.getId() %></td>
                            <td><%= turma.getCodigoTurma() %></td>
                            <td>
                                <form action="TurmasController" method="POST" class="d-inline">
                                    <input type="hidden" name="acao" value="Inscrever">
                                    <input type="hidden" name="idTurma" value="<%= turma.getId() %>">
                                    <input type="hidden" name="idAluno" value="<%= alunoLogado.getId() %>">
                                    <button type="submit" class="btn btn-primary">Inscrever-se</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="3" class="text-center">Nenhuma turma disponível.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
