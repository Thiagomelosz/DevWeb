<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador, entidade.Professor, entidade.Aluno" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <%
                    // Recupera a sessão
                    HttpSession sessao = request.getSession(false);
                    Administrador AdministradorLogado = (Administrador) sessao.getAttribute("administrador");
                    Professor ProfessorLogado = (Professor) sessao.getAttribute("professor");
                    Aluno AlunoLogado = (Aluno) sessao.getAttribute("aluno");

                    String currentURI = request.getRequestURI();

                    if (sessao != null) {
                        if (AdministradorLogado != null) { %>
                            <!-- Menu para Administrador -->
                            <a class="nav-link <%= currentURI.contains("/admin/dashboard") ? "active" : "" %>" href="/aplicacaoMVC/admin/dashboard">Dashboard</a>
                            <a class="nav-link <%= currentURI.contains("/admin/DisciplinaController") ? "active" : "" %>" href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar">Disciplinas</a>
                            <a class="nav-link <%= currentURI.contains("/admin/TurmaController") ? "active" : "" %>" href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Turmas</a>
                            <a class="nav-link <%= currentURI.contains("/admin/AlunoController") ? "active" : "" %>" href="/aplicacaoMVC/admin/AlunoController?acao=Listar">Alunos</a>
                            <a class="nav-link <%= currentURI.contains("/admin/ProfessorController") ? "active" : "" %>" href="/aplicacaoMVC/admin/ProfessorController?acao=Listar">Professores</a>
                            <a class="nav-link <%= currentURI.contains("/admin/AdminController") ? "active" : "" %>" href="/aplicacaoMVC/admin/AdminController?acao=Listar">Admin</a>
                            <a class="nav-link <%= currentURI.contains("/admin/RelatorioController") ? "active" : "" %>" href="/aplicacaoMVC/admin/RelatorioController?acao=Listar">Relatorio</a>
                            <a class="nav-link <%= currentURI.contains("/admin/logOut") ? "active" : "" %>" href="/aplicacaoMVC/admin/logOut">Logout</a>
                <%      } else if (ProfessorLogado != null) { %>
                            <!-- Menu para Professor -->
                            <a class="nav-link <%= currentURI.contains("/Professor/PostarNotasController") ? "active" : "" %>" href="/aplicacaoMVC//Professor/PostarNotasController?acao=Listar">Gerenciar Notas</a>
                            <!-- a class="nav-link <%= currentURI.contains("/professor/Cursos") ? "active" : "" %>" href="/aplicacaoMVC/professor/Cursos">Meus Cursos</a-->
                            <a class="nav-link <%= currentURI.contains("/professor/ProfessorLogoutController") ? "active" : "" %>" href="/aplicacaoMVC/professor/ProfessorLogoutController">Logout</a>
                <%      } else if (AlunoLogado != null) { %>
                            <!-- Menu para Aluno -->
                            <a class="nav-link <%= currentURI.contains("/aluno/TurmasController") ? "active" : "" %>" href="/aplicacaoMVC/aluno/TurmasController?acao=Listar">Inscrição Turmas</a>
                            <a class="nav-link <%= currentURI.contains("/aluno/NotasController") ? "active" : "" %>" href="/aplicacaoMVC/aluno/NotasController?acao=Listar">Minhas Notas</a>
                            <a class="nav-link <%= currentURI.contains("/aluno/AlunoLogoutController") ? "active" : "" %>" href="/aplicacaoMVC/aluno/AlunoLogoutController">Logout</a>
                <%      } else { %>
                            <!-- Menu para visitante (não logado) -->
                            <a class="nav-link <%= currentURI.contains("/home") ? "active" : "" %>" href="/aplicacaoMVC/home">Home</a>
                            <a class="nav-link <%= currentURI.contains("/AutenticaController?acao=Login") ? "active" : "" %>" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                <%      }
                    } else { %>
                        <!-- Sessão não existe, exibe apenas o link de login -->
                        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
                        <a class="nav-link <%= request.getRequestURI().contains("/AutenticaController?acao=Login") ? "active" : "" %>" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                <%  } %>
            </div>
        </div>
    </div>
</nav>
