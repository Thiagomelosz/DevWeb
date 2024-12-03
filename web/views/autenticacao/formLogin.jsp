<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Login</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <div class="col-sm-6 offset-3 mt-5">

                <h3>Login</h3>

                <%
                    String msgError = (String) request.getAttribute("msgError");
                    if ((msgError != null) && (!msgError.isEmpty())) {%>
                <div class="alert alert-danger" role="alert">
                    <%= msgError %>
                </div>
                <% } %>

                <!-- Formulário de Login -->
                <form action="/aplicacaoMVC/AutenticaController?acao=login" method="POST">
                    <!-- Campo CPF -->
                    <div class="mb-3">
                        <label for="cpf" class="form-label">CPF</label>
                        <input type="text" name="cpf" value="249.252.810-38" class="form-control" required>
                    </div>

                    <!-- Campo Senha -->
                    <div class="mb-3">
                        <label for="senha" class="form-label">Senha</label>
                        <input type="password" name="senha" value="123" class="form-control" required>
                    </div>

                    <!-- Campo Tipo de Usuário -->
                    <div class="mb-3">
                        <label for="tipo_usuario" class="form-label">Tipo de Usuário</label>
                        <select name="tipo_usuario" class="form-control" required>
                            <option value="administrador">Administrador</option>
                            <option value="professor">Professor</option>
                            <option value="aluno">Aluno</option>
                        </select>
                    </div>

                    <!-- Botão de Envio -->
                    <div class="row">
                        <div class="col-sm-2">
                            <input type="submit" value="Entrar" class="btn btn-primary">  
                        </div>
                        <div class="col-sm-6">                                 
                            <h6>Não possui acesso? <a href="/aplicacaoMVC/RegistrarController">Registre-se aqui</a></h6>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Scripts -->
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
