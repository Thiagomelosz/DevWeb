package controller.professor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AlunoDAO;
import model.TurmaDAO;
import model.ProfessorDAO;
import entidade.Aluno;
import entidade.Turma;
import entidade.Professor;  
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@WebServlet(name = "GerenciarNotasController", urlPatterns = {"/professor/notas"})
public class AlunosNotas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica a sessão do professor
        HttpSession session = request.getSession(false);
        Professor professorLogado = (Professor) session.getAttribute("professor");

        if (professorLogado == null) {
            // Redireciona para login caso não esteja autenticado
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        // Obtém a lista de alunos associados ao professor
        ArrayList<Turma> turma = TurmaDAO.obterAlunosPorProfessor(professorLogado.getId());

        // Adiciona a lista de alunos no request
        request.setAttribute("alunos", turma);

        // Encaminha para a página JSP
        request.getRequestDispatcher("/professor/Alunos/notasAluno.jsp").forward(request, response);
    }

/*
    @Override;;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtém o ID do aluno e a nova nota
        int idAluno = Integer.parseInt(request.getParameter("idAluno"));
        String codigoTurma = 
        double novaNota = Double.parseDouble(request.getParameter("nota"));

        // Atualiza a nota do aluno no banco de dados
        AlunoDAO.atualizarNota(idAluno, novaNota);

        // Redireciona para a mesma página para atualizar a lista
        response.sendRedirect("/aplicacaoMVC/professor/gerenciarNotas");
    }
*/
}