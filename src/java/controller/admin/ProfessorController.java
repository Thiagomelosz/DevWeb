package controller.admin;

import entidade.Professor;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.ProfessorDAO;

@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/ProfessorController"})
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if (acao == null || acao.isEmpty()) {
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
            rd.forward(request, response);
            return;
        }

        ProfessorDAO professorDAO = new ProfessorDAO();

        switch (acao) {
            case "Listar":
  
                ArrayList<Professor> listaProfessor = professorDAO.ListaDeProfessores();
                
                request.setAttribute("listaProfessor", listaProfessor);

               
                rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                try {
                    int id = Integer.parseInt(request.getParameter("id")); // Validação do ID
                    try {
                        Professor professor = professorDAO.getProfessor(id); 
                        request.setAttribute("professor", professor);
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
                        rd.forward(request, response);
                    } catch (Exception e) {

                        request.setAttribute("msgError", "Erro ao buscar o Professor: " + e.getMessage());
                        rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
                        rd.forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                request.setAttribute("professor", new Professor());
                request.setAttribute("acao", "Incluir");
                rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        int id = 0;
        try {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Formato de ID inválido.");
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
            rd.forward(request, response);
            return;
        }

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");

        Professor professor = new Professor(id, nome, email, cpf, senha);
        ProfessorDAO professorDAO = new ProfessorDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Incluir":
                    professorDAO.Inserir(professor);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;

                case "Alterar":
                    professorDAO.Alterar(professor);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;

                case "Excluir":
                    professorDAO.Excluir(professor);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }
            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.out.println("Erro ao realizar operação: " + e.getMessage());
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
            rd.forward(request, response);
        }
    }
}
