<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Professor"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Administrador"%>
<%@page import="model.ProfessorDAO"%>
<%@page import="model.DisciplinaDAO"%>
<%@page import="model.AdministradorDAO"%>
<%@page import="model.TurmaDAO"%>


<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>FormAdmin</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>

<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="row mt-5">
            <div class="col-sm-4 offset-3">
                <%
                    Administrador administrador = (Administrador) request.getAttribute("administrador");
                    if (administrador == null) {
                        administrador = new Administrador();
                    }

                    String acao = (String) request.getAttribute("acao");
                    if (acao == null) {
                        acao = "Incluir";
                    }

                    switch (acao) {
                        case "Alterar":
                            out.println("<h1>Alterar Admin</h1>");
                            break;
                        case "Excluir":
                            out.println("<h1>Excluir Admin</h1>");
                            break;
                    }

                    String msgError = (String) request.getAttribute("msgError");
                    if ((msgError != null) && (!msgError.isEmpty())) {%>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError%>
                        </div>
                    <% }%>

                  
                <form action="/aplicacaoMVC/admin/AdminController" method="POST">
                    <!-- Campo oculto para enviar a acao -->
                    <input type="hidden" name="acao" value="<%= acao %>" />
                    
                    <input type="hidden" name="id" value="<%=administrador.getId()%>" class="form-control">
                    
                    <div class="mb-3">
                        <label for="Nome" class="form-label">Nome</label>
                        <input type="text" name="nome" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=administrador.getNome()%>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="Senha" class="form-label">Senha</label>
                        <input type="password" name="senha" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= administrador.getSenha() %>" class="form-control">
                    </div>
                    
                    <div class="mb-3">
                        <label for="CPF" class="form-label">CPF</label>
                        <input type="text" name="cpf" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%=administrador.getCpf()%>" class="form-control">
                    </div>
                    
                    <input type="hidden" name="aprovado" value="N">
                                      
                    <div class="mb-3">
                        <label for="Endereco" class="form-label">Endereço</label>
                        <input type="text" name="endereco" <%= acao.equals("Excluir") ? "readonly" : ""%> value="<%= administrador.getEndereco() %>" class="form-control">
                    </div>

                    <div>
                        <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                        <a href="/aplicacaoMVC/admin/AdminController?acao=Listar" class="btn btn-danger">Retornar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>


        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>