<%@page import="entidade.Administrador"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Administrador</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">

                <h1>Área Restrita</h1>
                <h2>Lista de Administrador</h2>
                
                <a href="/aplicacaoMVC/admin/AdminController?acao=Incluir" class="mb-2 btn btn-primary">Criar Admin</a>
                 

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">CPF</th>
                                <th scope="col">Endereco</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                //verificando se a lista é null
                                ArrayList<Administrador> getAll = (ArrayList<Administrador>) request.getAttribute("getAll");
                                if (getAll == null) {
                                   
                                } else {
                                    
                                }

                                if (getAll != null && !getAll.isEmpty()) {
                                    for (Administrador administrador : getAll) {
                            %>
                                        <tr>
                                            <td><%= administrador.getId() %></td>
                                            <td><%= administrador.getNome() %></td>
                                            <td><%= administrador.getCpf() %></td>
                                            <td><%= administrador.getEndereco() %></td>
                                            <td>
                                            <% if (administrador.getAprovado().equals("N")) { %>
                                                <a href="/aplicacaoMVC/admin/AdminController?acao=Aprovar&id=<%= administrador.getId() %>" 
                                                   class="btn btn-success">Aprovar</a>
                                            <% } else { %>
                                                <span class="badge bg-secondary">Aprovado</span>
                                            <% } %>
                                                <a href="/aplicacaoMVC/admin/AdminController?acao=Alterar&id=<%= administrador.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/AdminController?acao=Excluir&id=<%= administrador.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhum administrador cadastrado.</td>
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