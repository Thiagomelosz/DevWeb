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
                    Aluno aluno = (Aluno) request.getAttribute("aluno");
                    if (aluno == null) {
                        aluno = new Aluno(); // Inicializa para evitar erro
                    }

                    String acao = (String) request.getAttribute("acao");
                    if (acao == null) {
                        acao = "Incluir"; // Define um valor padrão
                    }

                    // Exibe a ação que será realizada
                    switch (acao) {
                        case "Alterar":
                            out.println("<h1>Alterar Turma</h1>");
                            break;
                        case "Excluir":
                            out.println("<h1>Excluir Turma</h1>");
                            break;
                    }


                    String msgError = (String) request.getAttribute("msgError");
                    if ((msgError != null) && (!msgError.isEmpty())) {%>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError%>
                        </div>
                    <% }%>

                    // Formulário para enviar dados
                %>
                <form action="/aplicacaoMVC/admin/AlunoController" method="POST">
                    <!-- Campo oculto para enviar a acao -->
                    <input type="hidden" name="acao" value="<%= acao %>" />

                    <input type="hidden" name="id" value="<%=aluno.getId()%>" class="form-control">
                    
                    <div class="mb-3">
                        <label for="Nome" class="form-label">Nome</label>
                        <input type="text" name="nome" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=aluno.getNome()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="Email" class="form-label">Email</label>
                        <input type="text" name="email" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=aluno.getEmail()%>" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label for="Celular" class="form-label">Celular</label>
                        <input type="text" name="celular" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=aluno.getCelular()%>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="Senha" class="form-label">Senha</label>
                        <input type="password" name="senha" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= aluno.getSenha() %>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="CPF" class="form-label">CPF</label>
                        <input type="text" name="cpf" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=aluno.getCpf()%>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="Endereco" class="form-label">Endereço</label>
                        <input type="text" name="endereco" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= aluno.getEndereco() %>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="Cidade" class="form-label">Cidade</label>
                        <input type="text" name="cidade" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= aluno.getCidade() %>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="Bairro" class="form-label">Bairro</label>
                        <input type="text" name="bairro" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= aluno.getBairro() %>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="cep" class="form-label">CEP</label>
                        <input type="text" name="cep" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= aluno.getCep() %>" class="form-control">
                    </div>

                    <div>
                        <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                        <a href="/aplicacaoMVC/admin/AlunoController?acao=Listar" class="btn btn-danger">Retornar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>


        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>