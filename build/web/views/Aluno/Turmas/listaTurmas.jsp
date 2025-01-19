<%@page import="entidade.Turma"%>
<%@page import="entidade.Aluno"%>
<%@page import="model.TurmaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">

                <h2>Lista de Turmas</h2>

                <div class="alert alert-info" role="alert">
                    Observa��o: 
                    <ul>
                        <li>Voc� n�o pode se inscrever em turmas nas quais j� est� inscrito.</li>
                        <li>Turmas com 2 alunos j� est�o lotadas.</li>
                    </ul>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Codigo da Turma</th>
                                <th scope="col">Nome da Mat�ria</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Inscritos</th>
                                <th scope="col">A��es</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                                Aluno alunoLogado = (Aluno) request.getAttribute("alunoLogado");

                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    TurmaDAO turmaDAO = new TurmaDAO();
                                    for (Turma turma : listaTurmas) {
                                        
                                        
                                        boolean isInscrito = (alunoLogado != null) && turmaDAO.isAlunoInscrito(alunoLogado.getId(), turma.getCodigoTurma());
                                        int numeroAlunos = turmaDAO.contarAlunosNaTurma(turma.getCodigoTurma());
                                        boolean isTurmaLotada = numeroAlunos >= 2;  // Verifica se a turma tem 2 alunos ou mais

                         %>
                                        <tr>
                                            <td><%= turma.getCodigoTurma() %></td>
                                            <td><%= turma.getNomeDisciplina() %></td>
                                            <td><%= turma.getNomeProfessor() %></td>
                                            <td><%= numeroAlunos %>/2</td>
                                            <td>
                                                <form action="/aplicacaoMVC/aluno/TurmasController" method="POST">
                                                    <input type="hidden" name="acao" value="Inscrever">
                                                    <input type="hidden" name="idTurma" value="<%= turma.getId() %>">
                                                    
                                                   
                                                   
                                                    
                                              
                                                    <button type="submit" class="btn btn-success" 
                                                            <%= (isInscrito || isTurmaLotada) ? "disabled" : "" %> 
                                                            data-bs-toggle="tooltip" 
                                                            title="<%= isInscrito ? "Voc� j� est� inscrito nesta turma." : (isTurmaLotada ? "Turma lotada." : "") %>">
                                                        Se Inscrever
                                                    </button>
                                                </form>
                                                
                                                <%
                                                    if (isInscrito) {
                                                %>
                                                    <span class="text-danger">Voc� j� est� inscrito nesta turma.</span>
                                                <%
                                                    } else if (isTurmaLotada) {
                                                %>
                                                    <span class="text-warning">Turma lotada.</span>
                                                <%
                                                    }
                                                %>
                                            </td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                    <tr>
                                        <td colspan="5">Nenhuma turma cadastrada.</td>
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

        <script>
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        </script>

    </body>
</html>
