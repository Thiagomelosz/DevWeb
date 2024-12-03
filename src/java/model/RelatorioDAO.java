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

            System.out.println("Relatórios encontrados: " + count); // Log de contagem
        } catch (SQLException e) {
            System.out.println("Erro ao gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.closeConexao();
        }
        return relatorios;
    }
}