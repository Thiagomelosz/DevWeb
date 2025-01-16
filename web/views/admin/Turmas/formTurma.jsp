<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Professor"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Aluno"%>
<%@page import="model.ProfessorDAO"%>
<%@page import="model.DisciplinaDAO"%>
<%@page import="model.AlunoDAO"%>
<%@page import="model.TurmaDAO"%>


<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>FormTurma</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
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
                    out.println("Ação no JSP: " + acao);
                    out.println("Turma no JSP: " + turma);
                    out.println("ID Turma no JSP: " + turma.getId()); // Log para o ID da turma
                    out.println("ID Disciplina no JSP: " + turma.getDisciplinaId());
                    out.println("ID Aluno no JSP: " + turma.getAlunoId());
                    out.println("ID Professor no JSP: " + turma.getProfessorId());
                    out.println("Codigo da Turma no JSP: " + turma.getCodigoTurma());
                    out.println("Nota da Turma no JSP: " + turma.getNota());

                    String msgError = (String) request.getAttribute("msgError");
                    if ((msgError != null) && (!msgError.isEmpty())) {%>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError%>
                        </div>
                    <% }%>

                    // Formulário para enviar dados
                %>
                <form action="/aplicacaoMVC/admin/TurmaController" method="POST">
                    <!-- Campo oculto para enviar a acao -->
                    <input type="hidden" name="acao" value="<%= acao %>" />

                    <!-- Campo oculto para ID da Turma -->
                    <input type="hidden" name="id" value="<%=turma.getId()%>" class="form-control">
                    
                    <!-- Outros campos do formulário -->
                    <div class="mb-3">
                        <label for="DisciplinaId" class="form-label">ID Disciplina</label>
                        <input type="number" name="DisciplinaId" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=turma.getDisciplinaId()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="AlunoId" class="form-label">ID Aluno</label>
                        <input type="number" name="AlunoId" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=turma.getAlunoId()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="ProfessorId" class="form-label">ID Professor</label>
                        <input type="number" name="ProfessorId" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=turma.getProfessorId()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="codigoTurma" class="form-label">Codigo da Turma</label>
                        <input type="number" min="1" max="99" name="codigoTurma" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=turma.getCodigoTurma()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="Nota" class="form-label">Nota</label>
                        <input type="text" name="nota" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= turma.getNota() %>" class="form-control">
                    </div>

                    <div>
                        <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                        <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar" class="btn btn-danger">Retornar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>


        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>