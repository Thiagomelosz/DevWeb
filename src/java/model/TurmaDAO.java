package model;

import entidade.Turma;
import entidade.Aluno;
import java.sql.*;
import java.util.ArrayList;

public class TurmaDAO {

    // Método para inserir uma nova turma
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


    // Método para listar todas as turmas
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

    // Método para obter uma turma específica pelo ID
    public Turma get(int id) {
        Turma turma = null;
        Conexao conexao = new Conexao();
        try {
            // Consulta para buscar uma turma pelo ID
            String sqlQuery = "SELECT * FROM turmas WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            sql.setInt(1, id);
            ResultSet rs = sql.executeQuery();
            
            if (rs.next()) {
                // Cria o objeto Turma com os dados encontrados
                turma = new Turma();
                turma.setProfessorId(rs.getInt("professor_id"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setAlunoId(rs.getInt("aluno_id"));
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setNota(rs.getDouble("nota"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    // Método para alterar uma turma
    public void update(Turma t) {
        Conexao conexao = new Conexao();
        try {
            // Consulta SQL para atualizar as informações de uma turma
            String sqlQuery = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, id = ?, nota = ? WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            
            // Definindo os parâmetros da consulta
            sql.setInt(1, t.getProfessorId());
            sql.setInt(2, t.getDisciplinaId());
            sql.setInt(3, t.getAlunoId());
            sql.setString(4, t.getCodigoTurma());
            sql.setDouble(5, t.getNota());
            
            // Executando a atualização
            sql.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    // Método para excluir uma turma
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
}
