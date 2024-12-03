<%@page import="entidade.Relatorio"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista Disciplinas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
 <style>
        .container {
            margin-top: 20px;
        }
        .table th, .table td {
            text-align: center;
        }
        .alert {
            margin-top: 20px;
        }
    </style>
</head>

<body>

    <div class="container">
        <h2 class="text-center">Relatório de Disciplinas, Turmas e Alunos</h2>

        <!-- Exibindo mensagens de erro, se houver -->
        <c:if test="${not empty msgError}">
            <div class="alert alert-danger">
                <strong>Erro!</strong> ${msgError}
            </div>
        </c:if>

        <!-- Tabela do Relatório -->
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Código da Turma</th>
                        <th>Disciplina</th>
                        <th>Aluno</th>
                        <th>Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="relatorio" items="${relatorioList}">
                        <tr>
                            <td>${relatorio.codigoTurma}</td>
                            <td>${relatorio.nomeDisciplina}</td>
                            <td>${relatorio.nomeAluno}</td>
                            <td>${relatorio.nota}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Link para voltar ou outro botão, se necessário -->
        <a href="/admin" class="btn btn-primary">Voltar ao Painel de Administração</a>
    </div>
    </body>
</html>