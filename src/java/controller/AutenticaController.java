package controller;

import entidade.Professor;
import entidade.Aluno;
import entidade.Administrador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;
import model.AlunoDAO;
import model.ProfessorDAO;

/**
 * Servlet de autenticação de usuários.
 */
@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redireciona para a página de login
        RequestDispatcher rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = null;
        
        // Pegando os parâmetros do request
        String cpf_user = request.getParameter("cpf");
        String senha_user = request.getParameter("senha");
        String tipo_user = request.getParameter("tipo_usuario");

        // Verificando se algum campo não foi preenchido
        if (cpf_user.isEmpty() || senha_user.isEmpty() || tipo_user.isEmpty()) {
            request.setAttribute("msgError", "Usuário e/ou senha incorreto");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
            return;
        }

        // Variável para armazenar o usuário obtido após a autenticação
        Object usuarioObtido = null;

        // Dependendo do tipo de usuário, consulta a tabela correspondente
        try {
            if (tipo_user.equals("administrador")) {
                Administrador administrador = new Administrador(cpf_user, senha_user);
                AdministradorDAO administradorDAO = new AdministradorDAO();
                usuarioObtido = administradorDAO.Logar(administrador);
            } else if (tipo_user.equals("professor")) {
                Professor professor = new Professor(cpf_user, senha_user);
                ProfessorDAO professorDAO = new ProfessorDAO();
                usuarioObtido = professorDAO.Logar(professor);
            } else if (tipo_user.equals("aluno")) {
                Aluno aluno = new Aluno(cpf_user, senha_user);
                AlunoDAO alunoDAO = new AlunoDAO();
                usuarioObtido = alunoDAO.Logar(aluno);
            }

            // Verifica se o login foi bem-sucedido
            if (usuarioObtido != null) {
                // Salvar o usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute(tipo_user, usuarioObtido);

                // Redirecionar para a página apropriada de acordo com o tipo de usuário
                switch (tipo_user) {
                    case "administrador":
                        rd = request.getRequestDispatcher("/admin/dashboard");
                        break;
                    case "professor":
                        rd = request.getRequestDispatcher("/professor/Acesso");
                        break;
                    case "aluno":
                        rd = request.getRequestDispatcher("/aluno/Acesso");
                        break;
                    default:
                        rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                        request.setAttribute("msgError", "Tipo de usuário inválido");
                        break;
                }
                rd.forward(request, response);
            } else {
                // Se o login falhar, exibe mensagem de erro
                request.setAttribute("msgError", "Usuário e/ou senha incorreto");
                rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            // Tratar a exceção de forma mais detalhada
            System.out.println("Erro na autenticação: " + ex.getMessage());
            request.setAttribute("msgError", "Erro na autenticação. Tente novamente.");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
        }
    }
}
