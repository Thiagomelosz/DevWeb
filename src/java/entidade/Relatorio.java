package entidade;

public class Relatorio {
    
    private int alunoId;
    private String alunoNome;
    private String codigoTurma;
    private double nota;

      public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String alunoNome) {
        this.codigoTurma = codigoTurma;
    }
    
    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
