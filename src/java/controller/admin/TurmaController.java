package controller.admin;

import entidade.Turma;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obter o parâmetro ação indicando o que fazer
        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        // Verificar se a ação foi informada
        if (acao == null || acao.isEmpty()) {
            // Caso não seja informada, redirecionar para uma página de erro ou lista de turmas
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
            rd.forward(request, response);
            return;
        }
        
        System.out.println("Ação recebida: " + acao);
        switch (acao) {
            case "Listar":
                // Listar todas as turmas
                TurmaDAO turmaDAO = new TurmaDAO();
                ArrayList<Turma> listaTurmas = turmaDAO.getAll();
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                // Recuperar a turma com base no id
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    TurmaDAO turmaDAOAlterarExcluir = new TurmaDAO();
                    Turma turma = turmaDAOAlterarExcluir.get(id);
                    request.setAttribute("turma", turma);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                    rd.forward(request, response);
                } catch (NumberFormatException e) {
                    // Caso o parâmetro "id" não seja válido
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                // Incluir uma nova turma
                Turma novaTurma = new Turma(); // Nova turma vazia para o formulário
                request.setAttribute("turma", novaTurma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;

            default:
                // Ação desconhecida
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurmas.jsp");
                rd.forward(request, response);
                break;
        }
    }
        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Recuperando os parâmetros do formulário
        String acao = request.getParameter("acao"); // Definindo a acao corretamente
        int id = Integer.parseInt(request.getParameter("id"));
        int professorID = Integer.parseInt(request.getParameter("ProfessorId"));
        int disciplinaID = Integer.parseInt(request.getParameter("DisciplinaId"));
        int alunoId = Integer.parseInt(request.getParameter("AlunoId"));
        String codigoTurma = request.getParameter("codigoTurma");
        String notaStr = request.getParameter("nota");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        // Verificando se algum campo obrigatório está vazio
        if (codigoTurma == null || codigoTurma.isEmpty()) {
            Turma turma = new Turma();
            request.setAttribute("turma", turma);
            request.setAttribute("msgError", "É necessário preencher todos os campos");
            request.setAttribute("acao", acao); // Passando a ação correta
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }

        // Verificar e tratar a nota
        double nota = 0.0;
        try {
            if (notaStr != null && !notaStr.isEmpty()) {
                notaStr = notaStr.replace(',', '.');  
                nota = Double.parseDouble(notaStr);
            } else {
                throw new IllegalArgumentException("Nota não pode ser vazia.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msgError", "Formato de nota inválido.");
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("msgError", e.getMessage());
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
            return;
        }


        Turma turma = new Turma(id, professorID, disciplinaID, alunoId, codigoTurma, nota);
        TurmaDAO turmaDAO = new TurmaDAO();
        System.out.println("Ação recebida: " + acao);
        System.out.println("Turma: " + turma);

        try {
                switch (acao) {
          case "Incluir":
              boolean sucesso = turmaDAO.insert(turma);
              if (sucesso) {
                  System.out.println("Turma inserida com sucesso!");
                  request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
              } else {
                  System.out.println("Erro ao inserir a turma.");
                  request.setAttribute("msgError", "Erro ao realizar a operação: Nenhuma linha inserida.");
              }
              break;
          case "Alterar":
              turmaDAO.update(turma);
              request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
              break;
          case "Excluir":
              turmaDAO.delete(id);
              request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
              break;
      }
            rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurma.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
        }
    }
    
}
