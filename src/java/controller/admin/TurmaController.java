package controller.admin;

import entidade.Turma;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recuperar o parâmetro "acao" da requisição
        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        System.out.println("Ação recebida no GET: " + acao);

        if (acao == null || acao.isEmpty()) {
            System.out.println("Ação inválida recebida.");
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
            rd.forward(request, response);
            return;
        }

        switch (acao) {
            case "Listar":
                // Listar todas as turmas
                TurmaDAO turmaDAO = new TurmaDAO();
                System.out.println("Listando todas as turmas...");
                request.setAttribute("listaTurmas", turmaDAO.getAll());
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                // Recuperar turma com base no id
                try {
                    String idParam = request.getParameter("id");
                    System.out.println("ID recebido na URL: " + idParam);
                    System.out.println("ID recebido no GET (Alterar/Excluir): " + idParam);
                    int id = Integer.parseInt(idParam); // Tentativa de conversão para inteiro
                    System.out.println("ID convertido: " + id);
                    if (id == 0) {
                        System.out.println("ID é zero, possível problema na URL.");
                    }
                    TurmaDAO turmaDAOAlterarExcluir = new TurmaDAO();
                    Turma turma = turmaDAOAlterarExcluir.get(id);
                    request.setAttribute("turma", turma);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                    rd.forward(request, response);
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao tentar converter o ID no GET: " + e.getMessage());
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                // Incluir nova turma
                Turma novaTurma = new Turma(); // Nova turma vazia
                request.setAttribute("turma", novaTurma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);
                request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;

            default:
                System.out.println("Ação desconhecida recebida.");
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recuperar parâmetros do formulário
        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");
        System.out.println("ID recebido no POST: " + idParam); // Log do ID
        int id = 0;
        try {
            id = Integer.parseInt(idParam); // Tentando converter o ID para inteiro
            System.out.println("ID convertido no POST: " + id); // Log após conversão
        } catch (NumberFormatException e) {
            System.out.println("Erro ao tentar converter o ID no POST: " + e.getMessage());
            request.setAttribute("msgError", "Formato de ID inválido.");
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        // Verificar se o ID está vindo como zero
        if (id == 0) {
            System.out.println("ID está vindo como zero no POST.");
        }

        int professorID = Integer.parseInt(request.getParameter("ProfessorId"));
        int disciplinaID = Integer.parseInt(request.getParameter("DisciplinaId"));
        int alunoId = Integer.parseInt(request.getParameter("AlunoId"));
        String codigoTurma = request.getParameter("codigoTurma");
        String notaStr = request.getParameter("nota");

        System.out.println("Ação recebida no POST: " + acao);
        System.out.println("ProfessorID: " + professorID + ", DisciplinaID: " + disciplinaID + ", AlunoID: " + alunoId);
        System.out.println("Código da Turma: " + codigoTurma + ", Nota: " + notaStr);

        RequestDispatcher rd;

        // Verificar se campos obrigatórios estão vazios
        if (codigoTurma == null || codigoTurma.isEmpty()) {
            System.out.println("Campo 'codigoTurma' está vazio.");
            Turma turma = new Turma();
            request.setAttribute("turma", turma);
            request.setAttribute("msgError", "É necessário preencher todos os campos");
            request.setAttribute("acao", acao);
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        // Tratar a nota
        double nota = 0.0;
        try {
            if (notaStr != null && !notaStr.isEmpty()) {
                notaStr = notaStr.replace(',', '.');
                nota = Double.parseDouble(notaStr);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter a nota: " + e.getMessage());
            request.setAttribute("msgError", "Formato de nota inválido.");
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        Turma turma = new Turma(id, professorID, disciplinaID, alunoId, codigoTurma, nota);
        TurmaDAO turmaDAO = new TurmaDAO();

        try {
            switch (acao) {
                case "Incluir":
                    System.out.println("Incluir nova turma...");
                    boolean sucesso = turmaDAO.insert(turma);
                    if (sucesso) {
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    } else {
                        request.setAttribute("msgError", "Erro ao realizar a operação: Nenhuma linha inserida.");
                    }
                    break;

                case "Alterar":
                    System.out.println("Alterar turma com ID: " + turma.getId());
                    turmaDAO.update(turma);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;

                case "Excluir":
                    System.out.println("Excluir turma com ID: " + id);
                    turmaDAO.delete(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            // Redirecionar para a lista de turmas
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.out.println("Erro ao realizar operação: " + e.getMessage());
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
        }
    }
}

