package model;

import entidade.Aluno;
import entidade.Disciplina;
import entidade.Turma;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.tomcat.dbcp.dbcp2.ConnectionFactory;

public class TurmaDAO {


  public boolean insert(Turma t) {
    Conexao conexao = new Conexao();
    try {
        // Monta a consulta SQL para inserir uma nova turma
        String sqlQuery = "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        
        // Definindo os parâmetros da consulta
        sql.setInt(1, t.getProfessorId());
        sql.setInt(2, t.getDisciplinaId());
        sql.setInt(3, t.getAlunoId());
        sql.setString(4, t.getCodigoTurma());
        sql.setDouble(5, t.getNota());
        
        // Executa a inserção no banco de dados
        int resultado = sql.executeUpdate();
        if (resultado > 0) {
            return true;  // Inserção bem-sucedida
        } else {
            System.out.println("Nenhuma linha foi inserida.");
            return false;  // Caso contrário, falhou
        }
    } catch (SQLException e) {
        // Captura e imprime o erro detalhado
        System.out.println("Erro ao salvar a turma: " + e.getMessage());
        e.printStackTrace();  // Isso vai ajudar a capturar mais detalhes sobre a falha
        return false;  // Falha na operação
    } finally {
        conexao.closeConexao();
    }
}
public ArrayList<Turma> getAll() {
    ArrayList<Turma> turmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Consulta SQL para pegar apenas 'codigo_turma' e 'nome' (da disciplina)
        String sqlQuery = "SELECT t.id, t.codigo_turma, d.nome AS nome_disciplina "
                        + "FROM turmas t "
                        + "JOIN disciplina d ON t.disciplina_id = d.id;";

        Statement stmt = conexao.getConexao().createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);

        while (rs.next()) {
            // Criação do objeto Turma
            Turma turma = new Turma();
            
            // Preenchendo com os dados da consulta (apenas 'codigo_turma' e 'nome_disciplina')
            turma.setCodigoTurma(rs.getString("codigo_turma"));
            turma.setNomeDisciplina(rs.getString("nome_disciplina"));
            turma.setId(rs.getInt("id"));
            // Adiciona a turma na lista
            turmas.add(turma);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar turmas: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return turmas;
}


 
public ArrayList<Turma> listaDeTurmas() {
    ArrayList<Turma> minhasTurmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Consulta SQL para pegar as informações necessárias
        String selectSQL = "SELECT DISTINCT t.id, t.codigo_turma, d.nome AS nome_disciplina, p.nome AS professor "
                   + "FROM turmas t "
                   + "JOIN disciplina d ON t.disciplina_id = d.id "
                   + "JOIN professores p ON p.id = t.professor_id "
                   + "ORDER BY t.codigo_turma";

        PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
        ResultSet resultado = preparedStatement.executeQuery();

        if (resultado != null) {
            while (resultado.next()) {
                // Criação do objeto Turma com os dados do banco
                Turma turma = new Turma();
                turma.setId(resultado.getInt("id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setNomeDisciplina(resultado.getString("nome_disciplina"));
                turma.setNomeProfessor(resultado.getString("professor"));

                // Adiciona a turma à lista
                minhasTurmas.add(turma);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar turmas: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }

    // Log para verificar o tamanho da lista
    System.out.println("Tamanho da lista de turmas: " + minhasTurmas.size());
    return minhasTurmas;
}



    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = null;
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM turmas WHERE id = ?");         
           
            sql.setInt(1, id);
            ResultSet rs = sql.executeQuery();
            
            if (rs.next()) {
  
                turma = new Turma(
                rs.getInt("id"),
                rs.getInt("professor_id"),
                rs.getInt("disciplina_id"),
                rs.getInt("aluno_id"),
                rs.getString("codigo_turma"),
                rs.getDouble("nota")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    public void update(Turma t) {
        Conexao conexao = new Conexao();
        try {
            // Consulta SQL para atualizar as informações de uma turma
            String sqlQuery = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigo_turma = ?, nota = ? WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            
            // Definindo os parâmetros da consulta
            sql.setInt(1, t.getProfessorId());
            sql.setInt(2, t.getDisciplinaId());
            sql.setInt(3, t.getAlunoId());
            sql.setString(4, t.getCodigoTurma());
            sql.setDouble(5, t.getNota());
            sql.setInt(6, t.getId()); 
            
            // Executando a atualização
            sql.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            // Consulta para excluir uma turma pelo ID
            String sqlQuery = "DELETE FROM turmas WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            sql.setInt(1, id);
            
            // Executa a exclusão
            sql.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
        public boolean inscreverAluno(int alunoId, int turmaId) {
            Conexao conexao = new Conexao();
            String sql = "INSERT INTO turma(turma_id, aluno_id) VALUES (?, ?)";
            try {
                PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
                stmt.setInt(1, turmaId);
                stmt.setInt(2, alunoId);
                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas > 0; 
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conexao.closeConexao();
            }
        }
        
         public ArrayList<Turma> TurmasRelatorio() {
        ArrayList<Turma> turmas = new ArrayList<>();
        String query = "SELECT t.id AS turma_id, t.codigo_turma, a.id AS aluno_id, a.nome AS aluno_nome, t.nota, d.id AS disciplina_id " +
                       "FROM turmas t " +
                       "JOIN alunos a ON a.id = t.aluno_id " +
                       "JOIN disciplina d ON d.id = t.disciplina_id";

        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            ResultSet resultado = sql.executeQuery();


            while (resultado.next()) {

                Aluno aluno = new Aluno();
                aluno.setId(resultado.getInt("aluno_id"));
                aluno.setNome(resultado.getString("aluno_nome"));
                
                DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                Disciplina disciplina = new Disciplina();


                Turma turma = new Turma();
                turma.setId(resultado.getInt("turma_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                turma.setNota(resultado.getDouble("nota"));


                turma.addAluno(aluno);

                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas com alunos e notas: " + e.getMessage());
        }

        return turmas;
    }
         
    public static ArrayList<Turma> obterAlunosPorProfessor(int idProfessor) {
    ArrayList<Turma> lista = new ArrayList<>();
    Conexao conexao = new Conexao();
    String sqlQuery = "SELECT " +
                 "  t.nota, " +
                 "  a.nome AS nome_aluno, " +
                 "  d.nome AS nome_disciplina, " +
                 "  d.id AS id_disciplina, " +
                 "  t.id AS id_turma, " +
                 "  t.id_aluno, " +  // ID do aluno (já existente na classe)
                 "  t.codigo_turma, a.cpf" +
                 "FROM TURMA t " +
                 "JOIN ALUNO a ON t.id_aluno = a.id " +
                 "JOIN DISCIPLINA d ON t.id_disciplina = d.id " +
                 "WHERE t.id_professor = ? " +
                 "ORDER BY d.id";
    
     try (PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        ) {

        sql.setInt(1, idProfessor);

        try (ResultSet rs = sql.executeQuery()) {
            while (rs.next()) {
                // Cria o objeto Turma com dados da tabela
                Turma turma = new Turma(
                    rs.getInt("id_turma"), 
                    idProfessor, // Professor ID já está na sessão ou contexto
                    rs.getInt("id_disciplina"), 
                    rs.getInt("id_aluno"),
                    rs.getString("codigo_turma"), // Se você tiver o código da turma
                    rs.getDouble("nota")
                );
                
                // Preenche o nome da disciplina e do aluno na turma
                turma.setNomeDisciplina(rs.getString("nome_disciplina"));
                // Não podemos mudar a classe, mas você pode criar um método que retorna os dados adicionais para manipulação
                // No caso do nome do aluno, você pode usar outro método ou campo auxiliar para armazenar.
                // Exemplo de preencher a lista de alunos se necessário (já tem alunoId)
                Aluno aluno = new Aluno(rs.getInt("id_aluno"), rs.getString("nome_aluno"), rs.getString("cpf"));
                turma.getAlunos().add(aluno);  // Preenchendo a lista de alunos se necessário

                // Adiciona a turma à lista
                lista.add(turma);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
    }
}

