import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;

public class clsTabelaProcesso {
    clsProcesso vetProcessos[];
    JTable tblTabela;
    int linhas;

    public clsTabelaProcesso(int pLinhas, int pLargura, int pAltura) {
        linhas = pLinhas;
        vetProcessos = new clsProcesso[linhas];
        inicializarVetor(pLinhas);
        tblTabela = new JTable();
        atribuirModelo();
        atribuirRenderer();
        atribuirListeners();
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
    }

    public JTable getTable() {
        return tblTabela;
    }

    public void setCor(int pLinha, Color cor) {
        vetProcessos[pLinha].setCor(cor);
    }

    public Color getCor(int pLinha) {
        return vetProcessos[pLinha].getCor();
    }

    public void setPid(int pLinha, int pid) {
        vetProcessos[pLinha].setPid(pid);
    }

    public String getPid(int pLinha) {
        return vetProcessos[pLinha].getPid();
    }

    public int getIntPid(int pLinha) {
        return vetProcessos[pLinha].getIntPid();
    }

    public void setEstado(int pLinha, String pEstado) {
        vetProcessos[pLinha].setEstado(pEstado);
    }

    public String getEstado(int pLinha) {
        return vetProcessos[pLinha].getEstado();
    }

    public void setTipo(int pLinha, String pTipo) {
        vetProcessos[pLinha].setTipo(pTipo);
    }

    public String getTipo(int pLinha) {
        return vetProcessos[pLinha].getTipo();
    }

    public void setFrames(int pLinha, int pFrames) {
        vetProcessos[pLinha].setFrames(pFrames);
    }

    public String getFrames(int pLinha) {
        return vetProcessos[pLinha].getFrames();
    }

    public int getIntFrames(int pLinha) {
        return vetProcessos[pLinha].getIntFrames();
    }

    public void setTempo(int pLinha, int pTempo) {
        vetProcessos[pLinha].setTempo(pTempo);
    }

    public String getTempo(int pLinha) {
        return vetProcessos[pLinha].getTempo();
    }

    public int getIntTempo(int pLinha) {
        return vetProcessos[pLinha].getIntTempo();
    }

    public void setTempoUcp(int pLinha, int pTempoUcp) {
        vetProcessos[pLinha].setTempoUcp(pTempoUcp);
    }

    public String getTempoUcp(int pLinha) {
        return vetProcessos[pLinha].getTempoUcp();
    }

    public int getIntTempoUcp(int pLinha) {
        return vetProcessos[pLinha].getIntTempoUcp();
    }

    public void setTempoDeCriacao(int pLinha, int pTempoDeCriacao) {
        vetProcessos[pLinha].setTempoCriacao(pTempoDeCriacao);
    }

    public String getTempoDeCriacao(int pLinha) {
        return vetProcessos[pLinha].getTempoCriacao();
    }

    public int getIntTempoDeCriacao(int pLinha) {
        return vetProcessos[pLinha].getIntTempoCriacao();
    }

    public void adicionarProcesso(Color pCor, int pFrames, clsTabelaMemoria pMemoriaPrincipal,
            clsTabelaMemoria pMemoriaSecundaria, clsTabelaProcessosBloqueados pProcessosBloqueados, int pTempo,
            String pTipo) {
        int linha;
        boolean temEspaco = false;
        int tamanhoMaiorProcesso = verificarTamanhoDoMaiorProcesso();
        linha = getPosicaoVazia();
        clsProcesso processoAuxiliar = new clsProcesso();
        processoAuxiliar.setPid(ObterId());
        processoAuxiliar.setCor(pCor);
        processoAuxiliar.setEstado("Bloqueado");
        processoAuxiliar.setFrames(pFrames);
        processoAuxiliar.setTempoUcp(0);
        processoAuxiliar.setTempoCriacao(0);
        processoAuxiliar.setTempo(pTempo);
        processoAuxiliar.setTipo(pTipo);
        if (!pMemoriaPrincipal.adicionarProcesso(processoAuxiliar, 0)) {
            if (!pMemoriaSecundaria.adicionarProcesso(processoAuxiliar, tamanhoMaiorProcesso)) {
                JOptionPane.showMessageDialog(null,
                        "necessario que haja espaco livre na memoria secundaria para realizar o swap de memoria e por este motivo voce nao pode adicionar o processo.");
            } else {
                temEspaco = true;
            }
        } else {
            temEspaco = true;
        }
        if (temEspaco) {
            vetProcessos[linha].setPid(ObterId());
            vetProcessos[linha].setCor(pCor);
            vetProcessos[linha].setEstado("Bloqueado");
            vetProcessos[linha].setFrames(pFrames);
            vetProcessos[linha].setTempoUcp(0);
            vetProcessos[linha].setTempoCriacao(0);
            vetProcessos[linha].setTempo(pTempo);
            vetProcessos[linha].setTipo(pTipo);
            atualizarTabela();
            pProcessosBloqueados.adicionarProcesso(vetProcessos[linha], pMemoriaPrincipal, pMemoriaSecundaria);
        }
    }

    public void eliminarProcesso(clsTabelaMemoria pMemoriaPrincipal, clsTabelaMemoria pMemoriaSecundaria,
            clsTabelaProcessosBloqueados pProcessosBloqueados, clsTabelaProcessoEmExecucao pProcessoEmExecucao) {
        int op = 1;
        int x;
        if (vetProcessos[tblTabela.getSelectedRow()].getIntPid() != -1) {
            op = JOptionPane.showConfirmDialog(null, "Remover o processo selecionado? ");
            if (op == 0) {
                x = tblTabela.getSelectedRow();
                if (!pMemoriaPrincipal.removerProcesso(vetProcessos[x].getIntPid())) {
                    pMemoriaSecundaria.removerProcesso(vetProcessos[x].getIntPid());
                }
                pProcessosBloqueados.eliminarProcesso(vetProcessos[x].getIntPid());
                pProcessoEmExecucao.removerProceso(vetProcessos[x]);
                do {
                    vetProcessos[x].setCor(vetProcessos[x + 1].getCor());
                    vetProcessos[x].setEstado(vetProcessos[x + 1].getEstado());
                    vetProcessos[x].setFrames(vetProcessos[x + 1].getIntFrames());
                    vetProcessos[x].setPid(vetProcessos[x + 1].getIntPid());
                    vetProcessos[x].setSelecionado(vetProcessos[x + 1].getSelecionado());
                    vetProcessos[x].setTempoCriacao(vetProcessos[x + 1].getIntTempoCriacao());
                    vetProcessos[x].setTempoUcp(vetProcessos[x + 1].getIntTempoUcp());
                    vetProcessos[x].setTempo(vetProcessos[x + 1].getIntTempo());
                    vetProcessos[x].setTipo(vetProcessos[x + 1].getTipo());
                    vetProcessos[x].setEliminado(vetProcessos[x + 1].getEliminado());
                    x++;
                } while (x < linhas && vetProcessos[x - 1].getIntPid() != -1);
            }
        }
        atualizarTabela();
    }

    public void atualizarTabela() {
        tblTabela.updateUI();
    }

    public void incrementarTempoDeCriacao() {
        int y = 0;
        do {
            if (vetProcessos[y].getIntPid() != -1) {
                vetProcessos[y].incrementarTempoDeCriacao();
            }
            y++;
        } while (y < linhas && vetProcessos[y - 1].getIntPid() != -1);
        atualizarTabela();
    }

    public void incrementarTempoDeCpu(clsProcesso pProcesso) {
        int y = 0;
        do {
            if (vetProcessos[y].getIntPid() == pProcesso.getIntPid()) {
                vetProcessos[y].incrementarTempoDeCpu();
            }
            y++;
        } while (y < linhas && vetProcessos[y - 1].getIntPid() != -1 && vetProcessos[y - 1].getIntPid() != pProcesso.getIntPid());
        atualizarTabela();
    }

    public void atualizarEstados(clsProcesso pProcesso) {
        int y = 0;
        do {
            if (vetProcessos[y].getIntPid() != pProcesso.getIntPid() && vetProcessos[y].getIntPid() != -1) {
                vetProcessos[y].setEstado("Bloqueado");
            } else {
                if (vetProcessos[y].getIntPid() != -1) {
                    vetProcessos[y].setEstado("Executando");
                }
            }
            y++;
        } while (y < linhas && vetProcessos[y - 1].getIntPid() != -1);
    }

    public int verificarTamanhoDoMaiorProcesso() {
        int tamanhoMaiorProcesso = 0;
        int y = 0;
        do {
            if (vetProcessos[y].getIntPid() != -1 && vetProcessos[y].getIntFrames() > tamanhoMaiorProcesso) {
                tamanhoMaiorProcesso = vetProcessos[y].getIntFrames();
            }
            y++;
        } while (vetProcessos[y - 1].getIntPid() != -1);
        return tamanhoMaiorProcesso;
    }

    private void inicializarVetor(int linhas) {
        for (int y = 0; y < linhas; y++) {
            vetProcessos[y] = new clsProcesso();
        }
    }

    private void atribuirRenderer() {
        tblTabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(CENTER);
                setBackground(vetProcessos[row].getCor());
                if (vetProcessos[row].getSelecionado()) {
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                } else {
                    setBorder(null);
                }
                return this;
            }
        });
    }

    private void atribuirModelo() {
        tblTabela.setModel(new AbstractTableModel() {
            private String colunas[] = { "PID", "Tipo", "Estado", "Frames", "Tempo", "Tempo de UCP",
                    "Tempo de criacao" };

            public String getColumnName(int num) {
                return colunas[num];
            }

            public int getColumnCount() {
                return 7;
            }

            public int getRowCount() {
                return linhas;
            }

            public Object getValueAt(int row, int col) {
                switch (col) {
                case 0:
                    return new String(vetProcessos[row].getPid());
                case 1:
                    return new String(vetProcessos[row].getTipo());
                case 2:
                    return new String(vetProcessos[row].getEstado());
                case 3:
                    return new String(vetProcessos[row].getFrames());
                case 4:
                    return new String(vetProcessos[row].getTempo());
                case 5:
                    return new String(vetProcessos[row].getTempoUcp());
                case 6:
                    return new String(vetProcessos[row].getTempoCriacao());
                }
                return new String("");
            }
        });
    }

    private void atribuirListeners() {
        tblTabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JTable tabela = (JTable) me.getSource();
                for (int x = 0; x < linhas; x++) {
                    if (x != tabela.getSelectedRow()) {
                        vetProcessos[x].setSelecionado(false);
                    } else {
                        vetProcessos[x].setSelecionado(true);
                    }
                }
                atualizarTabela();
            }
        });
    }

    private int getPosicaoVazia() {
        int x = 0;
        int pos = 0;
        do {
            if (vetProcessos[x].getIntPid() == -1) {
                pos = x;
            }
            x++;
        } while (x < linhas && vetProcessos[x - 1].getIntPid() != -1);
        return pos;
    }

    private int ObterId() {
        int maior = 0;
        int x = 0;
        do {
            if (vetProcessos[x].getIntPid() > maior) {
                maior = vetProcessos[x].getIntPid();
            }
            x++;
        } while (x < linhas && vetProcessos[x - 1].getIntPid() != -1);
        return (maior + 1);
    }
}
