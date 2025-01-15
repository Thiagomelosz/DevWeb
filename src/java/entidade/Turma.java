package entidade;

public class Turma {
//id,professor_id,disciplina_id,aluno_id,codigo_turma,_nota
    private int id;
    private int professorId;
    private int disciplinaId;
    private int alunoId;
    private String codigoTurma;
    private double nota;
    private String nomeDisciplina;
    private String nomeProfessor;

    public Turma(int id, int professorId, int disciplinaId, int alunoId, String codigoTurma, double nota) {
        this.id = id;
        this.professorId = professorId;
        this.disciplinaId = disciplinaId;
        this.alunoId = alunoId;
        this.codigoTurma = codigoTurma;
        this.nota = nota;
        
    }

    public Turma(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }
    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
}
    