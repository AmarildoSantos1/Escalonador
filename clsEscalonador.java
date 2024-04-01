import javax.swing.*;
import java.awt.event.*;

public class clsEscalonador {
    Timer timer;
    int tempo;
    JPanel pnlClock;
    boolean bloqueou = false;
    clsTabelaProcesso objTabelaProcessos;
    clsTabelaProcessosBloqueados objTabelaProcessosBloqueados;
    clsTabelaMemoria objMemoriaPrincipal;
    clsTabelaMemoria objMemoriaSecundaria;
    clsTabelaTempoTotal objTempoTotal;
    clsTabelaProcessoEmExecucao objProcessoEmExecucao;

    public clsEscalonador(JPanel pPanel, int pTempo, clsTabelaProcesso pProcesso, clsTabelaProcessosBloqueados pProcessosBloqueados, clsTabelaMemoria pMemoriaPrincipal, clsTabelaMemoria pMemoriaSecundaria, clsTabelaTempoTotal pTempoTotal, clsTabelaProcessoEmExecucao pProcessoEmExecucao) {
        pnlClock = pPanel;
        tempo = pTempo;
        objTabelaProcessos = pProcesso;
        objTabelaProcessosBloqueados = pProcessosBloqueados;
        objMemoriaPrincipal = pMemoriaPrincipal;
        objMemoriaSecundaria = pMemoriaSecundaria;
        objTempoTotal = pTempoTotal;
        objProcessoEmExecucao = pProcessoEmExecucao;
        carregarClock();
        iniciar();
    }

    public void iniciar() {
        timer.start();
    }

    public void parar() {
        timer.stop();
    }

    private void carregarClock() {
        timer = new Timer(tempo, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pnlClock.getBackground().equals(java.awt.Color.RED)) {
                    pnlClock.setBackground(java.awt.Color.WHITE);
                } else {
                    pnlClock.setBackground(java.awt.Color.RED);
                    objTabelaProcessos.incrementarTempoDeCriacao();
                    objTempoTotal.incrementarValor();
                    if (bloqueou) {
                        objProcessoEmExecucao.executarProcesso(objMemoriaPrincipal, objMemoriaSecundaria);
                    }
                    bloqueou = objProcessoEmExecucao.incrementarTempo();
                    if (bloqueou) {
                        objProcessoEmExecucao.bloquearProcesso(objMemoriaPrincipal, objMemoriaSecundaria);
                    }
                    objMemoriaPrincipal.desfragmentar();
                }
            }
        });
    }
}
