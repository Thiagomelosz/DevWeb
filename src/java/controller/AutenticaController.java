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

@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Chamado o método GET, redirecionando para o formulário de login.");
        
        
        HttpSession session = request.getSession(false); 
        if (session != null && (session.getAttribute("aluno") != null ||
                                session.getAttribute("professor") != null ||
                                session.getAttribute("administrador") != null)) {
            
            response.sendRedirect(request.getContextPath() + "/home");

        } else {
            // Exibe o formulário de login
            RequestDispatcher rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String tipoUsuario = request.getParameter("tipo_usuario");

        System.out.println("Recebido: CPF=" + cpf + ", Tipo de Usuário=" + tipoUsuario);

        
        if (cpf == null || senha == null || tipoUsuario == null ||
            cpf.isEmpty() || senha.isEmpty() || tipoUsuario.isEmpty()) {
            redirecionarComErro(request, response, "Todos os campos são obrigatórios.");
            return;
        }

        try {
           
            Object usuario = autenticarUsuario(cpf, senha, tipoUsuario);
            if (usuario != null) {
                
                HttpSession session = request.getSession();
                session.setAttribute(tipoUsuario, usuario);
                System.out.println(tipoUsuario + " autenticado com sucesso.");

                redirecionarParaDashboard(request, response, tipoUsuario);
            } else {
                redirecionarComErro(request, response, "Usuário ou senha incorretos.");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao autenticar usuário: " + ex.getMessage());
            redirecionarComErro(request, response, "Erro interno. Tente novamente mais tarde.");
        }
    }

    private Object autenticarUsuario(String cpf, String senha, String tipoUsuario) throws Exception {
        switch (tipoUsuario.toLowerCase()) {
            case "administrador":
                return new AdministradorDAO().Logar(new Administrador(cpf, senha));
            case "professor":
                return new ProfessorDAO().Logar(new Professor(cpf, senha));
            case "aluno":
                return new AlunoDAO().Logar(new Aluno(cpf, senha));
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido.");
        }
    }

    private void redirecionarParaDashboard(HttpServletRequest request, HttpServletResponse response, String tipoUsuario)
            throws ServletException, IOException {
        String destino;
        switch (tipoUsuario.toLowerCase()) {
            case "administrador":
                destino = "/admin/dashboard";
                break;
            case "professor":
                destino = "/professor/Acesso";
                break;
            case "aluno":
                destino = "/Aluno/Acesso";
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido.");
        }
        RequestDispatcher rd = request.getRequestDispatcher(destino);
        rd.forward(request, response);
    }

    private void redirecionarComErro(HttpServletRequest request, HttpServletResponse response, String mensagemErro)
            throws ServletException, IOException {
        System.out.println("Erro: " + mensagemErro);
        request.setAttribute("msgError", mensagemErro);
        RequestDispatcher rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
        rd.forward(request, response);
    }
}
