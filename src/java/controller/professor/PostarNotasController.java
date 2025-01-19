package controller.professor;

import entidade.Turma;
import model.TurmaDAO;
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
            rd = request.getRequestDispatcher("/views/Professor/Postar/listaNotas.jsp");
            rd.forward(request, response);
            return;
        }

        switch (acao) {
            case "Listar":
                HttpSession session = request.getSession(false);
                Professor professorLogado = (Professor) session.getAttribute("professor");

                if (professorLogado == null) {
                    request.setAttribute("msgError", "Professor não está logado.");
                    rd = request.getRequestDispatcher("/views/Professor/Postar/listaNotas.jsp");
                    rd.forward(request, response);
                    return;
                }

                RelatorioDAO relatorioDAO = new RelatorioDAO();
                List<Relatorio> listaRelatorios = relatorioDAO.getRelatorioPorProfessorId(professorLogado.getId());

                // Adicionando logs para depuração
               
                for (Relatorio relatorio : listaRelatorios) {
                   }

                request.setAttribute("listaRelatorios", listaRelatorios);
                request.setAttribute("professorLogado", professorLogado);
                rd = request.getRequestDispatcher("/views/Professor/Postar/PostarNotas.jsp");
                rd.forward(request, response);
                break;
                
            case "Alterar": 
                try {
                    int turmaId = Integer.parseInt(request.getParameter("id"));
                    RelatorioDAO relatorioDAOAlterar = new RelatorioDAO();
                    List<Relatorio> listaRelatoriosAlterar = relatorioDAOAlterar.getRelatorioPorTurmaId(turmaId);

                    // Adicionando logs para depuração
                                        for (Relatorio relatorio : listaRelatoriosAlterar) {
                       }

                    // Adicionando a turma ao request
                    TurmaDAO turmaDAO = new TurmaDAO();
                    Turma turma = turmaDAO.getTurmaPorId(turmaId);
                    request.setAttribute("turma", turma);

                    request.setAttribute("listaRelatorios", listaRelatoriosAlterar);
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/Professor/Postar/FormNotas.jsp");
                    rd.forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("msgError", "Erro ao buscar a turma: " + e.getMessage());
                    rd = request.getRequestDispatcher("/views/Professor/Postar/PostarNotas.jsp");
                    rd.forward(request, response);
                }
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/Professor/Postar/PostarNotas.jsp");
                rd.forward(request, response);
                break;
        }
    }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String acao = request.getParameter("acao");

            if ("Atualizar".equals(acao)) {
                try {
                    int turmaId = Integer.parseInt(request.getParameter("id"));
                    int professorId = Integer.parseInt(request.getParameter("professorId"));
                    int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
                    int alunoId = Integer.parseInt(request.getParameter("alunoId"));
                    String codigoTurma = request.getParameter("codigoTurma");
                    double nota = Double.parseDouble(request.getParameter("nota"));

                    Turma turma = new Turma();
                    turma.setId(turmaId);
                    turma.setProfessorId(professorId);
                    turma.setDisciplinaId(disciplinaId);
                    turma.setAlunoId(alunoId);
                    turma.setCodigoTurma(codigoTurma);
                    turma.setNota(nota);

                    TurmaDAO turmaDAO = new TurmaDAO();
                    turmaDAO.update(turma);

                    request.getSession().setAttribute("msgSuccess", "Turma atualizada com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/Professor/PostarNotasController?acao=Listar");
                } catch (Exception e) {
                    request.getSession().setAttribute("msgError", "Erro ao atualizar a turma: " + e.getMessage());
                    response.sendRedirect(request.getContextPath() + "/Professor/PostarNotasController?acao=Listar");
                }
            }
        }


}



