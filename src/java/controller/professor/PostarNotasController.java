package controller.professor;

import entidade.Relatorio;
import entidade.Professor;
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

@WebServlet(name = "PostarNotasController", urlPatterns = {"/Professor/PostarNotasController"})
public class PostarNotasController extends HttpServlet {
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
                Professor professorLogado = (Professor) session.getAttribute("professor");

                if (professorLogado == null) {
                    request.setAttribute("msgError", "Professor não está logado.");
                    rd = request.getRequestDispatcher("/views/Aluno/Relatorios/listaNotas.jsp");
                    rd.forward(request, response);
                    return;
                }

                RelatorioDAO relatorioDAO = new RelatorioDAO();
                List<Relatorio> listaRelatorios = relatorioDAO.getRelatorioPorProfessorId(professorLogado.getId());

                // Adicionando logs para depuração
                System.out.println("Professor ID: " + professorLogado.getId());
                System.out.println("Número de relatórios encontrados: " + listaRelatorios.size());

                for (Relatorio relatorio : listaRelatorios) {
                    System.out.println("Relatório - Turma ID: " + relatorio.getTurmaId() + ", Aluno Nome: " + relatorio.getAlunoNome() + ", Código Turma: " + relatorio.getCodigoTurma() + ", Nota: " + relatorio.getNota());
                }

                request.setAttribute("listaRelatorios", listaRelatorios);
                request.setAttribute("professorLogado", professorLogado);
                rd = request.getRequestDispatcher("/views/Professor/Postar/PostarNotas.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/Professor/Postar/PostarNotas.jsp");
                rd.forward(request, response);
                break;
        }
    }
}
