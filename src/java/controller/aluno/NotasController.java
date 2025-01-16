package controller.aluno;

import entidade.Relatorio;
import entidade.Aluno;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.RelatorioDAO;

@WebServlet(name = "NotasController", urlPatterns = {"/aluno/NotasController"})
public class NotasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if (acao == null || acao.isEmpty()) {
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/Aluno/Relatorios/listaNotas.jsp");
            rd.forward(request, response);
            return;
        }

        switch (acao) {
            case "Listar":
                HttpSession session = request.getSession(false);
                Aluno alunoLogado = (Aluno) session.getAttribute("aluno");

                RelatorioDAO relatorioDAO = new RelatorioDAO();
                List<Relatorio> listaRelatorios = relatorioDAO.getRelatorioPorAlunoId(alunoLogado.getId());

                request.setAttribute("listaRelatorios", listaRelatorios);
                request.setAttribute("alunoLogado", alunoLogado);
                rd = request.getRequestDispatcher("/views/Aluno/Relatorios/listaNotas.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/Aluno/Relatorios/listaNotas.jsp");
                rd.forward(request, response);
                break;
        }
    }
}
