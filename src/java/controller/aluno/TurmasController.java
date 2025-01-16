package controller.aluno;

import entidade.Relatorio;
import model.AlunoDAO;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        if ("Inscrever".equals(acao)) {
            try {
                // Pegue os valores da sessão
                Aluno alunoLogado = (Aluno) request.getSession().getAttribute("aluno");
                int turmaId = Integer.parseInt(request.getParameter("idTurma"));

                // Obtenha os detalhes da turma
                TurmaDAO turmaDAO = new TurmaDAO();
                Turma turma = turmaDAO.get(turmaId);

                // Defina os valores do aluno na turma
                turma.setAlunoId(alunoLogado.getId());

                // Use a função insert para inserir a turma com o aluno
                boolean sucesso = turmaDAO.insert(turma);

                // Define a mensagem a ser exibida
                if (sucesso) {
                    request.setAttribute("msgSuccess", "Inscrição realizada com sucesso!");
                } else {
                    request.setAttribute("msgError", "Falha na inscrição. Tente novamente.");
                }

                // Redireciona para a página de mensagens
                request.setAttribute("link", "/aplicacaoMVC/aluno/TurmasController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                // Tratamento de exceções
                e.printStackTrace();
                request.setAttribute("msgError", "Erro ao realizar inscrição: " + e.getMessage());
                request.setAttribute("link", "/aplicacaoMVC/aluno/TurmasController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);
            }
        }
    }
}
