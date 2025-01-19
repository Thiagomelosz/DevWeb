package controller.admin;

import entidade.Disciplina;
import entidade.Turma;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DisciplinaDAO;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Turma turma = new Turma();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;


        switch (acao) {
            case "Listar":
                request.setAttribute("listaTurmas", turmaDAO.listaDeTurmas());
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                turma = turmaDAO.get(id);

                request.setAttribute("turma", turma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                Turma novaTurma = new Turma();
                request.setAttribute("turma", novaTurma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);
                request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");
        int id = 0;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter o ID no POST: " + e.getMessage());
            request.setAttribute("msgError", "Formato de ID inválido.");
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        int professorID = Integer.parseInt(request.getParameter("ProfessorId"));
        int disciplinaID = Integer.parseInt(request.getParameter("DisciplinaId"));
        int alunoId = Integer.parseInt(request.getParameter("AlunoId"));
        String codigoTurma = request.getParameter("codigoTurma");
        String notaStr = request.getParameter("nota");

        RequestDispatcher rd;

        if (codigoTurma == null || codigoTurma.isEmpty()) {
            System.err.println("Campo 'codigoTurma' está vazio.");
            Turma turma = new Turma();
            request.setAttribute("turma", turma);
            request.setAttribute("msgError", "É necessário preencher todos os campos");
            request.setAttribute("acao", acao);
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        double nota = 0.0;
        try {
            if (notaStr != null && !notaStr.isEmpty()) {
                notaStr = notaStr.replace(',', '.');
                nota = Double.parseDouble(notaStr);
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter a nota: " + e.getMessage());
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

                    // Verificar se o professor já possui duas turmas
                    int contagemTurmas = turmaDAO.contarProfessorNaTurma(professorID);

                    if (contagemTurmas >= 2) {
                        System.err.println("Professor já possui o número máximo de turmas.");
                        request.setAttribute("msgError", "Este professor já possui o número máximo de turmas permitido.");
                        request.setAttribute("turma", turma);
                        request.setAttribute("acao", acao);
                        request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    boolean sucesso = turmaDAO.insert(turma);
                    if (sucesso) {
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    } else {
                        request.setAttribute("msgError", "Erro ao realizar a operação: Nenhuma linha inserida.");
                    }
                    break;

                case "Alterar":
                    turmaDAO.update(turma);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;

                case "Excluir":
                    turmaDAO.delete(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.err.println("Erro ao realizar operação: " + e.getMessage());
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
        }
    }
}
