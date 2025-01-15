package model;

import entidade.Turma;
import entidade.Aluno;
import java.sql.*;
import java.util.ArrayList;

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


 
public ArrayList<Turma> listaDeTurmas() {
    ArrayList<Turma> minhasTurmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Consulta SQL para pegar as informações necessárias
        String selectSQL = "SELECT t.id, t.codigo_turma, d.nome AS nome_disciplina, p.nome AS professor "
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
            String sql = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (?, ?)";
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
}

