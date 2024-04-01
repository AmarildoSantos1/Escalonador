import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;

public class clsTabelaProcessoEmExecucao {
    JTable tblTabela;
    clsProcesso objProcesso;
    clsTabelaProcessosBloqueados objTabelaProcessosBloqueados;
    clsTabelaProcesso objListaProcessos;
    int tempo;
    clsTabelaMemoria memoriaPrincipal;
    clsTabelaMemoria memoriaSecundaria;

    public clsTabelaProcessoEmExecucao(int pLargura, int pAltura, clsTabelaProcessosBloqueados pProcessosBloqueados, clsTabelaProcesso pProcessos, clsTabelaMemoria memoriaPrincipal, clsTabelaMemoria memoriaSecundaria) {
        tempo = 0;
        objProcesso = new clsProcesso();
        objTabelaProcessosBloqueados = pProcessosBloqueados;
        objListaProcessos = pProcessos;
        tblTabela = new JTable();
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
        this.memoriaPrincipal = memoriaPrincipal;
        this.memoriaSecundaria = memoriaSecundaria;
        atribuirRenderer();
        atribuirModelo();
    }

    public clsTabelaProcessoEmExecucao(int pLargura, int pAltura, clsTabelaProcessosBloqueados pProcessosBloqueados, clsTabelaProcesso pProcessos, clsTabelaMemoria memoriaPrincipal, clsTabelaMemoria memoriaSecundaria, clsTabelaTempoTotal objTabelaTempoTotal, clsTabelaProcessoEmExecucao objProcessoEmExecucao) {
        this(pLargura, pAltura, pProcessosBloqueados, pProcessos, memoriaPrincipal, memoriaSecundaria);
    }

    public String getTempo() {
        if (tempo == 0 || objProcesso.getIntPid() == -1) {
            return "";
        }
        return Integer.toString(tempo);
    }

    public JTable getTable() {
        return tblTabela;
    }

    public void atualizarTabela() {
        tblTabela.updateUI();
    }

    public void removerProceso(clsProcesso pProcesso) {
        if (pProcesso.getIntPid() == objProcesso.getIntPid()) {
            objProcesso.restaurar();
            tempo = 0;
        }
    }

    public boolean incrementarTempo() {
        boolean retorno = false;
        if (objProcesso.getIntPid() != -1) {
            tempo++;
            if (tempo > objProcesso.getIntTempo()) {
                tempo = 0;
                retorno = true;
            } else {
                objListaProcessos.incrementarTempoDeCpu(objProcesso);
            }
            atualizarTabela();
        } else {
            retorno = true;
        }
        return retorno;
    }

    public void executarProcesso(clsTabelaMemoria pMemoriaPrincipal, clsTabelaMemoria pMemoriaSecundaria) {
        clsProcesso auxiliar;
        auxiliar = objTabelaProcessosBloqueados.obterProcessoAExecutar();
        if (auxiliar.getIntPid() != -1) {
            objProcesso.setCor(auxiliar.getCor());
            objProcesso.setEstado(auxiliar.getEstado());
            objProcesso.setFrames(auxiliar.getIntFrames());
            objProcesso.setPid(auxiliar.getIntPid());
            objProcesso.setSelecionado(auxiliar.getSelecionado());
            objProcesso.setTempo(auxiliar.getIntTempo());
            objProcesso.setTempoCriacao(auxiliar.getIntTempoCriacao());
            objProcesso.setTempoUcp(auxiliar.getIntTempoUcp());
            objProcesso.setTipo(auxiliar.getTipo());
            objProcesso.setEliminado(auxiliar.getEliminado());
            objListaProcessos.atualizarEstados(auxiliar);
        }
        atualizarTabela();
    }

    public void bloquearProcesso(clsTabelaMemoria pMemoriaPrincipal, clsTabelaMemoria pMemoriaSecundaria) {
        if (objProcesso.getIntPid() != -1) {
            objTabelaProcessosBloqueados.adicionarProcesso(objProcesso, pMemoriaPrincipal, pMemoriaSecundaria);
            objProcesso.restaurar();
            objTabelaProcessosBloqueados.atualizarTabela();
        }
        atualizarTabela();
    }

    private void atribuirRenderer() {
        tblTabela.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                        setHorizontalAlignment(CENTER);
                        setBackground(objProcesso.getCor());
                        return this;
                    }
                }
        );
    }

    private void atribuirModelo() {
        tblTabela.setModel(
                new AbstractTableModel() {
                    String colunas[] = {"Processo", "Tempo"};

                    public int getColumnCount() {
                        return 2;
                    }

                    public int getRowCount() {
                        return 1;
                    }

                    public Object getValueAt(int row, int col) {
                        if (col == 0) {
                            return new String(objProcesso.getPid());
                        }
                        return new String(getTempo());
                    }

                    public String getColumnName(int num) {
                        return colunas[num];
                    }
                }
        );
    }

    public void paginarProcesso(clsProcesso processo) {
        int framesNecessarios = processo.getIntFrames();
        int framesDisponiveis = memoriaPrincipal.contarFramesDisponiveis();

        if (framesDisponiveis >= framesNecessarios) {
            memoriaPrincipal.alocarProcesso(processo);
        } else {
            swapProcessos(processo, framesDisponiveis);
        }
    }

    private void swapProcessos(clsProcesso processo, int framesDisponiveis) {
        memoriaPrincipal.alocarProcessoParcial(processo, framesDisponiveis);
        memoriaSecundaria.alocarProcesso(processo); // Remova o segundo argumento aqui
    }
}
