package controller.admin;

import entidade.Aluno;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.AlunoDAO;

@WebServlet(name = "AlunoController", urlPatterns = {"/admin/AlunoController"})
public class AlunoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if (acao == null || acao.isEmpty()) {
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Alunos/listaAluno.jsp");
            rd.forward(request, response);
            return;
        }

        AlunoDAO alunoDAO = new AlunoDAO();

        switch (acao) {
            case "Listar":
  
                ArrayList<Aluno> listaAlunos = alunoDAO.ListaDeAlunos();
                System.out.println("Tamanho da lista de alunos: " + (listaAlunos == null ? "null" : listaAlunos.size()));
                request.setAttribute("listaAluno", listaAlunos);

                System.out.println("Lista de alunos foi passada para a JSP.");
                rd = request.getRequestDispatcher("/views/admin/Alunos/listaAluno.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                try {
                    int id = Integer.parseInt(request.getParameter("id")); // Validação do ID
                    try {
                        Aluno aluno = alunoDAO.getAluno(id); 
                        request.setAttribute("aluno", aluno);
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/Alunos/formAluno.jsp");
                        rd.forward(request, response);
                    } catch (Exception e) {

                        request.setAttribute("msgError", "Erro ao buscar o aluno: " + e.getMessage());
                        rd = request.getRequestDispatcher("/views/admin/Alunos/listaAluno.jsp");
                        rd.forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Alunos/listaAluno.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                request.setAttribute("aluno", new Aluno());
                request.setAttribute("acao", "Incluir");
                rd = request.getRequestDispatcher("/views/admin/Alunos/formAluno.jsp");
                rd.forward(request, response);
                break;

            default:
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Alunos/listaAluno.jsp");
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
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Alunos/formAluno.jsp");
            rd.forward(request, response);
            return;
        }

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String celular = request.getParameter("celular");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("endereco");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep");

        Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
        AlunoDAO alunoDAO = new AlunoDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Incluir":
                    alunoDAO.Inserir(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;

                case "Alterar":
                    alunoDAO.Alterar(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;

                case "Excluir":
                    alunoDAO.Excluir(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }
            request.setAttribute("link", "/aplicacaoMVC/admin/AlunoController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.out.println("Erro ao realizar operação: " + e.getMessage());
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/AlunoController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Alunos/formAluno.jsp");
            rd.forward(request, response);
        }
    }
}
