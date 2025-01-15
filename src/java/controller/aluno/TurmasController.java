package controller.aluno;

import entidade.Relatorio;
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

    System.out.println("Ação recebida: " + acao);
    switch (acao) {
        case "Listar":
            TurmaDAO turmaDAO = new TurmaDAO();
            ArrayList<Turma> listaTurmas = turmaDAO.listaDeTurmas();
            request.setAttribute("listaTurmas", listaTurmas);
            rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
            rd.forward(request, response);
            break;

        case "GerarRelatorio":
            try {
                int turmaId = Integer.parseInt(request.getParameter("id"));
           
                RelatorioDAO relatorioDAO = new RelatorioDAO();
                List<Relatorio> listaRelatorios = relatorioDAO.getRelatorioPorTurmaId(turmaId);

                request.setAttribute("listaRelatorios", listaRelatorios);
                request.setAttribute("idTurma", turmaId);
                rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("msgError", "ID da turma inválido.");
                rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
            }
            break;

        default:
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/Aluno/Turmas/listaTurmas.jsp");
            rd.forward(request, response);
            break;
    }
}
}
