package model;

import entidade.Relatorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<Relatorio> getRelatorioPorTurmaId(int turmaId) {
        Conexao conexao = new Conexao();
        List<Relatorio> relatorios = new ArrayList<>();
        try {
            String sqlQuery = "SELECT a.id AS aluno_id, a.nome AS aluno_nome, t.nota " +
                              "FROM alunos a " +
                              "JOIN turmas t ON a.id = t.aluno_id " +
                              "WHERE t.id = ?";

            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            sql.setInt(1, turmaId);

            ResultSet rs = sql.executeQuery();

            // Adiciona um log para ver quantos resultados foram encontrados
            int count = 0;
            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setAlunoId(rs.getInt("aluno_id"));
                relatorio.setAlunoNome(rs.getString("aluno_nome"));
                relatorio.setNota(rs.getDouble("nota"));
                relatorios.add(relatorio);
                count++;
            }

            
        } catch (SQLException e) {
        
            e.printStackTrace();
        } finally {
            conexao.closeConexao();
        }
        return relatorios;
    }

    public List<Relatorio> getRelatorioPorAlunoId(int alunoId) {
        Conexao conexao = new Conexao();
        List<Relatorio> relatorios = new ArrayList<>();
        try {
            String sqlQuery = "SELECT t.id AS id, t.codigo_turma, t.nota " +
                              "FROM turmas t " +
                              "JOIN alunos a ON t.aluno_id = a.id " +
                              "WHERE a.id = ?";

            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            sql.setInt(1, alunoId);

            ResultSet rs = sql.executeQuery();

            // Adiciona um log para ver quantos resultados foram encontrados
            int count = 0;
            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setTurmaId(rs.getInt("id"));
                relatorio.setCodigoTurma(rs.getString("codigo_turma"));
                relatorio.setNota(rs.getDouble("nota"));
                relatorios.add(relatorio);
                count++;
            }

            
        } catch (SQLException e) {
          
            e.printStackTrace();
        } finally {
            conexao.closeConexao();
        }
        return relatorios;
    }
    
 public List<Relatorio> getRelatorioPorProfessorId(int professorId) {
    Conexao conexao = new Conexao();
    List<Relatorio> relatorios = new ArrayList<>();
    try {
        String sqlQuery = "SELECT t.id AS id, a.nome, t.codigo_turma, t.nota " +
                          "FROM turmas t " +
                          "JOIN alunos a ON t.aluno_id = a.id " +
                          "WHERE t.professor_id = ?";

        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        sql.setInt(1, professorId);

        ResultSet rs = sql.executeQuery();

        // Adiciona um log para ver quantos resultados foram encontrados
        int count = 0;
        while (rs.next()) {
            Relatorio relatorio = new Relatorio();
            relatorio.setTurmaId(rs.getInt("id"));
            relatorio.setAlunoNome(rs.getString("nome"));
            relatorio.setCodigoTurma(rs.getString("codigo_turma"));
            relatorio.setNota(rs.getDouble("nota"));
            relatorios.add(relatorio);
            count++;
        }

        System.out.println("Número de relatórios encontrados: " + count);

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        conexao.closeConexao();
    }
    return relatorios;
}
}