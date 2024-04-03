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

    public void paginacaoESwap(clsPagina pagina, clsFrame frame) {
        if (objMemoriaPrincipal.verificarSeEstaNaMemoria(pagina.getProcesso())) {
            // Página já está na memória principal, não é necessário swap
            return;
        }

        // Verifica se há espaço livre na memória principal para carregar a página
        if (objMemoriaPrincipal.contarFramesDisponiveis() > 0) {
            objMemoriaPrincipal.adicionarProcesso(pagina.getProcesso(), 0);
            return;
        }

        // Se não houver espaço livre na memória principal, é necessário realizar o swap
        clsProcesso processoASerRemovido = objMemoriaPrincipal.escolherProcessoParaSwap();
        objMemoriaPrincipal.removerProcesso(processoASerRemovido.getIntPid());
        objMemoriaSecundaria.adicionarProcesso(processoASerRemovido, 0);

        // Carrega a nova página na memória principal
        objMemoriaPrincipal.adicionarProcesso(pagina.getProcesso(), 0);
    }
}
