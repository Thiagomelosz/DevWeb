package controller.admin;

import entidade.Disciplina;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DisciplinaDAO;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/admin/DisciplinaController"})
public class DisciplinaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = (String) request.getParameter("acao");
        Disciplina disciplina = new Disciplina();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;
        switch (acao) {
            case "Listar":
                ArrayList<Disciplina> listaDisciplinas = disciplinaDAO.getAll();
                request.setAttribute("listaDisciplinas", listaDisciplinas);

                rd = request.getRequestDispatcher("/views/admin/disciplina/listaDisciplinas.jsp");
                rd.forward(request, response);

                break;
            case "Alterar":
            case "Excluir":

                int id = Integer.parseInt(request.getParameter("id"));
                disciplina = disciplinaDAO.get(id);

                
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
                rd.forward(request, response);
                break;
            case "Incluir":
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
                rd.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String requisito = request.getParameter("requisito");
        String ementa = request.getParameter("ementa");
        int cargaHoraria = Integer.parseInt(request.getParameter("cargaHoraria"));
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (nome.isEmpty()) {
            Disciplina disciplina = new Disciplina();
            switch (btEnviar) {
                case "Alterar":
                case "Excluir":
                    try {
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                    disciplina = disciplinaDAO.get(id);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException("Falha em uma query para cadastro de Administrador");
                }
                break;
            }

            request.setAttribute("disciplina", disciplina);
            request.setAttribute("acao", btEnviar);

            request.setAttribute("msgError", "É necessário preencher todos os campos");

            rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
            rd.forward(request, response);

        } else {
            
             Disciplina disciplina = new Disciplina(id,nome,requisito,ementa,cargaHoraria);
             DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        disciplinaDAO.insert(disciplina);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;
                    case "Alterar":
                        disciplinaDAO.update(disciplina);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;
                    case "Excluir":
                        disciplinaDAO.delete(id);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/DisciplinaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (IOException | ServletException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha em uma query para cadastro de Administrador");
            }
        }
    }

}
