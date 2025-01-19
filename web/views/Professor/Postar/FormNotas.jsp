<%@page import="entidade.Turma"%>
<%@page import="entidade.Relatorio"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>FormTurma</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        // Recupera a turma e a ação
                        Turma turma = (Turma) request.getAttribute("turma");
                        if (turma == null) {
                            turma = new Turma(); // Inicializa para evitar erro
                        }

                        String acao = (String) request.getAttribute("acao");
                        if (acao == null) {
                            acao = "Incluir"; // Define um valor padrão
                        }

                        // Exibe a ação que será realizada
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Turma</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Turma</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Turma</h1>");
                                break;
                        }

                        // Logs para verificar os valores no JSP
                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                            <div class="alert alert-danger" role="alert">
                                <%= msgError%>
                            </div>
                        <% }%>

                        // Formulário para enviar dados
                    %>
                    <form action="/aplicacaoMVC/Professor/PostarNotasController" method="POST">
                        <!-- Campo oculto para enviar a acao -->
                        <input type="hidden" name="acao" value="Atualizar" />

                        <!-- Campo oculto para ID da Turma -->
                        <input type="hidden" name="id" value="<%=turma.getId()%>" class="form-control">

                        <!-- Outros campos do formulário -->
                        <div class="mb-3">
                            <label for="DisciplinaId" class="form-label">ID Disciplina</label>
                            <input type="number" name="disciplinaId" readonly value="<%=turma.getDisciplinaId()%>" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="AlunoId" class="form-label">ID Aluno</label>
                            <input type="number" name="alunoId" readonly value="<%=turma.getAlunoId()%>" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="ProfessorId" class="form-label">ID Professor</label>
                            <input type="number" name="professorId" readonly value="<%=turma.getProfessorId()%>" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="codigoTurma" class="form-label">Código da Turma</label>
                            <input type="text" name="codigoTurma" readonly value="<%=turma.getCodigoTurma()%>" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" min="0" max="10" name="nota" value="<%= turma.getNota() %>" class="form-control">
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="Atualizar" class="btn btn-primary">
                            <a href="/aplicacaoMVC/Professor/PostarNotasController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
