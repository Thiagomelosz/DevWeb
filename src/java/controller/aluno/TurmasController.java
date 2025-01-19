package controller.aluno;

import entidade.Relatorio;
import model.AlunoDAO;
import entidade.Aluno;
import entidade.Turma;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AlunoDAO;
import model.TurmaDAO;
import model.RelatorioDAO;

@WebServlet(name = "TurmasController", urlPatterns = {"/aluno/TurmasController"})
public class TurmasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if (acao == null || acao.isEmpty()) {
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
            rd.forward(request, response);
            return;
        }
        switch (acao) {
            case "Listar":
                HttpSession session = request.getSession(false);
                Aluno alunoLogado = (Aluno) session.getAttribute("aluno");
               
                TurmaDAO turmaDAO = new TurmaDAO();
                ArrayList<Turma> listaTurmas = turmaDAO.listaDeTurmas();
                request.setAttribute("listaTurmas", listaTurmas);
                request.setAttribute("alunoLogado", alunoLogado); 
                rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if ("Inscrever".equals(acao)) {
            try {
                
                HttpSession session = request.getSession(false);
                Aluno alunoLogado = (Aluno) session.getAttribute("aluno");
                 int turmaId = Integer.parseInt(request.getParameter("idTurma"));

                TurmaDAO turmaDAO = new TurmaDAO();
                Turma turma = turmaDAO.get(turmaId);
                turma.setAlunoId(alunoLogado.getId());

                boolean sucesso = turmaDAO.insert(turma);

                if (sucesso) {
                    request.setAttribute("msgSuccess", "Inscrição realizada com sucesso!");
                } else {
                    request.setAttribute("msgError", "Falha na inscrição. Tente novamente.");
                }
         
                request.setAttribute("link", "/aplicacaoMVC/aluno/TurmasController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                
                e.printStackTrace();
                request.setAttribute("msgError", "Erro ao realizar inscrição: " + e.getMessage());
                request.setAttribute("link", "/aplicacaoMVC/aluno/TurmasController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);
            }
        }
    }
}
