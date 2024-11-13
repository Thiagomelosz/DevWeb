<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Bem-vindo ao Sistema Escolar</title>
        <link href="./home.css" rel="stylesheet">
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
        
     <!-- CSS -->
       <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f6f9;
        }

        /* Seção Hero */
        .hero-section {
            background-color: #f8f9fa;
            padding: 80px 0;
            text-align: center;
            border-bottom: 2px solid #ddd;
        }

        .hero-section h1 {
            font-size: 3rem;
            font-weight: bold;
            color: #343a40;
        }

        .hero-section p {
            font-size: 1.25rem;
            color: #6c757d;
        }

        /* Seção de Notícias */
        .news-section {
            background-color: #e9ecef;
            padding: 40px 0;
            margin-top: 20px;
        }

        .news-section h3 {
            font-size: 2rem;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
            color: #007bff;
        }

        .news-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            background-color: #fff;
            margin-bottom: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .news-item h5 {
            font-size: 1.5rem;
            font-weight: bold;
            color: #343a40;
        }

        .news-item p {
            color: #6c757d;
            font-size: 1rem;
            margin-top: 10px;
        }

        .news-item a {
            text-decoration: none;
            font-weight: bold;
            color: #007bff;
        }

        .news-item a:hover {
            text-decoration: underline;
        }

        /* Seção de Eventos */
        .events-section {
            padding: 40px 0;
            background-color: #f1f3f5;
            margin-top: 40px;
        }

        .events-section h3 {
            font-size: 2rem;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
            color: #28a745;
        }

        .event-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fff;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .event-item h5 {
            font-size: 1.5rem;
            font-weight: bold;
            color: #343a40;
        }

        .event-item p {
            color: #6c757d;
            font-size: 1rem;
            margin-top: 10px;
        }
    </style>
    
    </head>
    <body>
        <div class="container">
            <!-- Menu -->
            <jsp:include page="../comum/menu.jsp" />

            <!-- Hero Section -->
            <section class="hero-section">
                <h1>Bem-vindo ao Sistema Escolar</h1>
                <p>Acompanhe as últimas notícias e eventos da nossa escola.</p>
            </section>
            <div class="container mt-5">
                <div class="card text-center p-4 shadow-lg" style="max-width: 400px; margin: auto; background-color: #f8f9fa; border-radius: 10px;">
                    <h4 class="card-title mb-3">Acesse sua Conta</h4>
                    <p class="card-text">Clique no botão abaixo para fazer login e acessar a área restrita.</p>
                    <a href="/aplicacaoMVC/AutenticaController?acao=Login" class="btn btn-lg btn-primary mt-3" style="width: 100%;">Fazer Login</a>
                </div>
            </div>


            <!-- Notícias da Escola -->
            <section class="news-section">
                <h3>Últimas Notícias</h3>

                <div class="news-item">
                    <div>
                        <h5>Nova Biblioteca da Escola Inaugurada!</h5>
                        <p>Após meses de planejamento e construção, nossa nova biblioteca está pronta para receber todos os alunos. Um espaço moderno para estudos e leitura.</p>
                    </div>
                    <a href="noticiaDetalhe.jsp" class="btn btn-link">Ler mais</a>
                </div>

                <div class="news-item">
                    <div>
                        <h5>Dia da Cultura Escolar</h5>
                        <p>Participe do evento cultural que acontecerá no próximo mês, com apresentações de dança, música e teatro feitas pelos alunos!</p>
                    </div>
                    <a href="noticiaDetalhe.jsp" class="btn btn-link">Ler mais</a>
                </div>

                <div class="news-item">
                    <div>
                        <h5>Reforma nas Salas de Aula</h5>
                        <p>As reformas nas salas de aula começaram esta semana! O objetivo é melhorar o ambiente de aprendizado com mais conforto e acessibilidade para todos.</p>
                    </div>
                    <a href="noticiaDetalhe.jsp" class="btn btn-link">Ler mais</a>
                </div>
            </section>

            <!-- Eventos da Escola -->
            <section class="events-section">
                <h3>Próximos Eventos</h3>

                <div class="event-item">
                    <div>
                        <h5>Workshop de Tecnologia Educacional</h5>
                        <p>Não perca o workshop sobre as últimas inovações em tecnologia educacional, com participação de especialistas da área.</p>
                    </div>
                    <a href="eventoDetalhe.jsp" class="btn btn-link">Saber mais</a>
                </div>

                <div class="event-item">
                    <div>
                        <h5>Feira de Ciências</h5>
                        <p>Alunos de todas as séries irão apresentar seus projetos científicos na nossa tradicional feira anual. Venha prestigiar os futuros cientistas!</p>
                    </div>
                    <a href="eventoDetalhe.jsp" class="btn btn-link">Saber mais</a>
                </div>

                <div class="event-item">
                    <div>
                        <h5>Exposição de Arte dos Alunos</h5>
                        <p>Uma exposição com as melhores obras de arte criadas pelos alunos. As produções vão desde pintura até escultura!</p>
                    </div>
                    <a href="eventoDetalhe.jsp" class="btn btn-link">Saber mais</a>
                </div>
            </section>

        </div>

        <!-- Scripts -->
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
