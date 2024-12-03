<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exemplo MVC - Registro</title>
    <link href="views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Codigo JavaScript para puxar do UserType a escolha do tipo de aluno -->
    <script> 
        function showFieldsByUserType() {
            const userType = document.getElementById("tipo_usuario").value;
            const adminFields = document.getElementById("adminFields");
            const professorFields = document.getElementById("professorFields");
            const alunoFields = document.getElementById("alunoFields");

            // Esconder todos os campos
            adminFields.style.display = "none";
            professorFields.style.display = "none";
            alunoFields.style.display = "none";

            // Mostrar campos específicos com base no tipo de usuário
            if (userType === "administrador") {
                adminFields.style.display = "block";
            } else if (userType === "professor") {
                professorFields.style.display = "block";
            } else if (userType === "aluno") {
                alunoFields.style.display = "block";
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <jsp:include page="/views/comum/menu.jsp" />
        <div class="col-sm-6 offset-3 mt-5">
            <h3>Registro</h3>
            <form action="/aplicacaoMVC/admin/RegistrarUsuario" method="POST">
                <!-- Campos Comuns -->
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" name="nome" class="form-control" placeholder="Seu nome">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" name="email" class="form-control" placeholder="Seu E-mail">
                </div>
                <div class="mb-3">
                    <label for="cpf" class="form-label">CPF</label>
                    <input type="text" name="cpf" class="form-control" placeholder="999.999.999-99">
                </div>                

                <!-- Escolher o Tipo de Usuário -->
                <div class="mb-3">
                    <label for="tipo_usuario" class="form-label">Tipo de Usuário</label>
                    <select id="tipo_usuario" name="tipo_usuario" class="form-control" required onchange="showFieldsByUserType()">
                        <option value="">Selecione</option>
                        <option value="administrador">Administrador</option>
                        <option value="professor">Professor</option>
                        <option value="aluno">Aluno</option>
                    </select>
                </div>

                <!-- Campos para Administrador -->
                <div id="adminFields" style="display:none;">
                    <div class="mb-3">
                        <label for="aprovado" class="form-label">Aprovado</label>
                        <input type="text" name="aprovado" class="form-control" placeholder="0 ou 1">
                    </div>                
                    <div class="mb-3">
                        <label for="endereco_admin" class="form-label">Endereço</label>
                        <input type="text" name="endereco_admin" class="form-control" placeholder="Endereço">
                    </div>
                </div>

                <!-- Campos para Professor -->
                <div id="professorFields" style="display:none;">
                    
                </div>

                <!-- Campospara Aluno -->
                <div id="alunoFields" style="display:none;">
                    <div class="mb-3">
                        <label for="celular" class="form-label">Celular</label>
                        <input type="text" name="aluno_celular" class="form-control" placeholder="Seu celular">
                    </div>
                    <div class="mb-3">
                        <label for="endereco_aluno" class="form-label">Endereço</label>
                        <input type="text" name="endereco_aluno" class="form-control" placeholder="Endereço">
                    </div>
                    <div class="mb-3">
                        <label for="cidade" class="form-label">Cidade</label>
                        <input type="text" name="cidade" class="form-control" placeholder="Cidade">
                    </div>
                    <div class="mb-3">
                        <label for="bairro" class="form-label">Bairro</label>
                        <input type="text" name="bairro" class="form-control" placeholder="Bairro">
                    </div>
                    <div class="mb-3">
                        <label for="cep" class="form-label">CEP</label>
                        <input type="text" name="cep" class="form-control" placeholder="CEP">
                    </div>
                </div>

                <!-- Campo Senha -->
                <div class="mb-3">
                    <label for="senha" class="form-label">Senha</label>
                    <input type="password" name="senha" class="form-control" placeholder="*****">
                </div>
                <div class="mb-3">
                    <label for="senha2" class="form-label">Redigite a senha</label>
                    <input type="password" name="senha2" class="form-control" placeholder="*****">
                </div>

                <!-- Botão de Enviar -->
                <div class="row">
                    <div class="col-sm-2">
                        <input type="submit" value="Registrar" class="btn btn-primary btn-padding-bottom">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <script src="views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
