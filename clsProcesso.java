import java.awt.Color;
import javax.swing.JOptionPane;

public class clsProcesso {
    private int pid;
    private Color cor;
    private String estado;
    private String tipo;
    private int tempo;
    private int frames;
    private int tempoUcp;
    private int tempoCriacao;
    private boolean selecionado;
    private boolean eliminado;
    private boolean paginacao; // Novo campo para estado de paginação

    public clsProcesso() {
        restaurar();
    }

    public void setPid(int pPid) {
        pid = pPid;
    }

    public String getPid() {
        if (pid == -1) {
            return "";
        }
        return Integer.toString(pid);
    }

    public int getIntPid() {
        return pid;
    }

    public void setEliminado(boolean pEliminado) {
        eliminado = pEliminado;
    }

    public boolean getEliminado() {
        return eliminado;
    }

    public void setCor(Color pCor) {
        cor = pCor;
    }

    public Color getCor() {
        return cor;
    }

    public void setEstado(String pEstado) {
        estado = pEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setTipo(String pTipo) {
        tipo = pTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTempo(int pTempo) {
        tempo = pTempo;
    }

    public String getTempo() {
        if (tempo == -1) {
            return "";
        }
        return Integer.toString(tempo);
    }

    public int getIntTempo() {
        return tempo;
    }

    public void setFrames(int pFrames) {
        frames = pFrames;
    }

    public String getFrames() {
        if (frames == -1) {
            return "";
        }
        return Integer.toString(frames);
    }

    public int getIntFrames() {
        return frames;
    }

    public void setTempoUcp(int pTempoUcp) {
        tempoUcp = pTempoUcp;
    }

    public String getTempoUcp() {
        if (tempoUcp == -1) {
            return "";
        }
        return Integer.toString(tempoUcp);
    }

    public int getIntTempoUcp() {
        return tempoUcp;
    }

    public void setTempoCriacao(int pTempoCriacao) {
        tempoCriacao = pTempoCriacao;
    }

    public String getTempoCriacao() {
        if (tempoCriacao == -1) {
            return "";
        }
        return Integer.toString(tempoCriacao);
    }

    public int getIntTempoCriacao() {
        return tempoCriacao;
    }

    public void setSelecionado(boolean pSelecionado) {
        selecionado = pSelecionado;
    }

    public boolean getSelecionado() {
        return selecionado;
    }

    public void setPaginacao(boolean pPaginacao) {
        paginacao = pPaginacao;
    }

    public boolean getPaginacao() {
        return paginacao;
    }

    public void restaurar() {
        pid = -1;
        cor = Color.WHITE;
        estado = "";
        frames = -1;
        tempoUcp = -1;
        tempoCriacao = -1;
        selecionado = false;
        tipo = "";
        tempo = -1;
        eliminado = false;
        paginacao = false;
    }

    public void incrementarTempoDeCriacao() {
        if (pid != -1) {
            tempoCriacao++;
        }
    }

    public void incrementarTempoDeCpu() {
        tempoUcp++;
    }
}
