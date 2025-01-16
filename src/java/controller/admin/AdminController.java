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

        

        if (acao == null || acao.isEmpty()) {
            
            request.setAttribute("msgError", "Ação inválida.");
            rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
            rd.forward(request, response);
            return;
        }

        AdministradorDAO administradorDAO = new AdministradorDAO(); // Instanciado aqui uma única vez
        

        switch (acao) {
         case "Aprovar":
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                administradorDAO.aprovarAdmin(id);

                // Após a aprovação, redireciona para a lista de administradores
                response.sendRedirect("AdminController?acao=Listar");
            } catch (Exception e) {
                // Em caso de erro, adiciona uma mensagem e redireciona para a lista
                
                request.setAttribute("msgError", "Erro ao aprovar administrador: " + e.getMessage());
                rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                rd.forward(request, response);
            }
            break;

            
            case "Listar":
               
                ArrayList<Administrador> listaAdmin = administradorDAO.getAll();
                
                request.setAttribute("getAll", listaAdmin);

                
                rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    
                    try {
                        Administrador administrador = administradorDAO.getAdministrador(id); 
                        
                        request.setAttribute("administrador", administrador);
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
                        rd.forward(request, response);
                    } catch (Exception e) {
                        
                        request.setAttribute("msgError", "Erro ao buscar o administrador: " + e.getMessage());
                        rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                        rd.forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    
                    request.setAttribute("msgError", "ID inválido.");
                    rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdmin.jsp");
                    rd.forward(request, response);
                }
                break;

            case "Incluir":
                
                request.setAttribute("administrador", new Administrador());
                request.setAttribute("acao", "Incluir");
                rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
                rd.forward(request, response);
                break;

            default:
                
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

        

        Administrador administrador = new Administrador(id, nome, cpf, senha, aprovado, endereco);
        AdministradorDAO administradorDAO = new AdministradorDAO(); // Instanciado aqui uma única vez
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Incluir":
                    administradorDAO.Inserir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;

                case "Alterar":
                   
                    administradorDAO.Alterar(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;

                case "Excluir":
                 
                    administradorDAO.Excluir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }
            request.setAttribute("link", "/aplicacaoMVC/admin/AdminController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
           
            request.setAttribute("msgError", "Erro ao realizar a operação: " + e.getMessage());
            request.setAttribute("link", "/aplicacaoMVC/admin/AdminController?acao=Listar");
            rd = request.getRequestDispatcher("/views/admin/Administrador/formAdmin.jsp");
            rd.forward(request, response);
        }
    }
}
