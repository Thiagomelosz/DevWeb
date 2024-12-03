package controller.admin;

import entidade.Administrador;
import entidade.Professor;
import entidade.Aluno;
import model.AdministradorDAO;
import model.ProfessorDAO;
import model.AlunoDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarUsuarioController", urlPatterns = {"/admin/RegistrarUsuario"})
public class RegistrarUsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Exibe o formulário de registro
        request.getRequestDispatcher("/views/admin/RegistrarUsuario/RegistroUsuario.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String senha2 = request.getParameter("senha2");
        String tipoUsuario = request.getParameter("tipo_usuario");

        // Verifica se as senhas coincidem
        if (!senha.equals(senha2)) {
            request.setAttribute("msgError", "As senhas não coincidem.");
            request.getRequestDispatcher("/views/admin/RegistrarUsuario/RegistroUsuario.jsp").forward(request, response);
            return;
        }

        try {
            // Verifica o tipo de usuário e executa a inserção correspondente
            if ("administrador".equals(tipoUsuario)) {
                // Para Administrador
                String nomeAdmin = nome;
                String enderecoAdmin = endereco;
                String cpfAdmin = cpf;
                String senhaAdmin = senha;

                Administrador admin = new Administrador(nomeAdmin, enderecoAdmin, cpfAdmin, senhaAdmin);
                AdministradorDAO adminDAO = new AdministradorDAO();
                adminDAO.Inserir(admin);
            } else if ("professor".equals(tipoUsuario)) {
                // Para Professor
                String nomeProfessor = nome;
                String emailProfessor = request.getParameter("email");
                String cpfProfessor = cpf;
                String senhaProfessor = senha;

                Professor professor = new Professor(nomeProfessor, emailProfessor, cpfProfessor, senhaProfessor);
                ProfessorDAO professorDAO = new ProfessorDAO();
                professorDAO.Inserir(professor);
            } else if ("aluno".equals(tipoUsuario)) {
                // Para Aluno
                String nomeAluno = nome;
                String emailAluno = request.getParameter("email");
                String celularAluno = request.getParameter("aluno_celular");
                String cpfAluno = cpf;
                String senhaAluno = senha;
                String enderecoAluno = endereco;
                String cidadeAluno = request.getParameter("cidade");
                String bairroAluno = request.getParameter("bairro");
                String cepAluno = request.getParameter("cep");

                Aluno aluno = new Aluno(nomeAluno, emailAluno, celularAluno, cpfAluno, senhaAluno, enderecoAluno, cidadeAluno, bairroAluno, cepAluno);
                AlunoDAO alunoDAO = new AlunoDAO();
                alunoDAO.Inserir(aluno);
            }

            // Redireciona para uma página de sucesso ou volta ao formulário com uma mensagem de sucesso
            request.setAttribute("msgSuccess", "Usuário registrado com sucesso!");
            request.getRequestDispatcher("/views/admin/RegistrarUsuario/RegistroUsuario.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msgError", "Erro ao registrar usuário: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/RegistrarUsuario/RegistroUsuario.jsp").forward(request, response);
        }
    }
}
