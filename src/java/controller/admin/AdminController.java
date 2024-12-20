package controller.admin;

import entidade.Administrador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.AdministradorDAO;

@WebServlet(name = "AdminController", urlPatterns = {"/admin/AdminController"})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher rd;

        System.out.println("Ação recebida no doGet: " + acao);

        if (acao == null || acao.isEmpty()) {
            System.out.println("Ação inválida.");
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
            rd.forward(request, response);
            return;
        }

        AdministradorDAO administradorDAO = new AdministradorDAO(); // Instanciado aqui uma única vez
        System.out.println("DAO de Administrador instanciado.");

        switch (acao) {
            case "Listar":
                System.out.println("Listando administradores...");
                ArrayList<Administrador> listaAdmin = administradorDAO.ListaDeAdministrador();
                System.out.println("Tamanho da lista de Admin: " + (listaAdmin == null ? "null" : listaAdmin.size()));
                request.setAttribute("ListaDeAdministrador", listaAdmin);

                System.out.println("Lista de Administrador foi passada para a JSP.");
                rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                System.out.println("Ação: " + acao);
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    System.out.println("ID do administrador: " + id);
                    try {
                        Administrador administrador = administradorDAO.getAdministrador(id); 
                        System.out.println("Administrador encontrado: " + administrador);
                        request.setAttribute("administrador", administrador);
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
                        rd.forward(request, response);
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar administrador: " + e.getMessage());
                        request.setAttribute("msgError", "Erro ao buscar o administrador: " + e.getMessage());
                        rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                        rd.forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido: " + e.getMessage());
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                System.out.println("Incluir novo administrador.");
                request.setAttribute("administrador", new Administrador());
                request.setAttribute("acao", "Incluir");
                rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
                rd.forward(request, response);
                break;

            default:
                System.out.println("Ação inválida.");
                request.setAttribute("msgError", "Ação inválida.");
                rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
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
            System.out.println("Formato de ID inválido: " + e.getMessage());
            request.setAttribute("msgError", "Formato de ID inválido.");
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
            rd.forward(request, response);
            return;
        }

        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        int aprovado = Integer.parseInt(request.getParameter("aprovado"));
        String endereco = request.getParameter("endereco");

        System.out.println("Dados recebidos: ID=" + id + ", Nome=" + nome + ", CPF=" + cpf + ", Aprovado=" + aprovado);

        Administrador administrador = new Administrador(id, nome, cpf, senha, aprovado, endereco);
        AdministradorDAO administradorDAO = new AdministradorDAO(); // Instanciado aqui uma única vez
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Incluir":
                    System.out.println("Incluindo administrador...");
                    administradorDAO.Inserir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;

                case "Alterar":
                    System.out.println("Alterando administrador...");
                    administradorDAO.Alterar(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;

                case "Excluir":
                    System.out.println("Excluindo administrador...");
                    administradorDAO.Excluir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }
            request.setAttribute("link", "/aplicacaoMVC/admin/AdminController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.out.println("Erro ao realizar operação: " + e.getMessage());
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/AdminController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
            rd.forward(request, response);
        }
    }
}
