import java.awt.Color;
import java.awt.Component; // Importe a classe Component
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.*;

public class clsTabelaMemoria {
    clsProcesso mtzMemoria[][];
    JTable tblTabela;
    int linhas;
    int colunas;

    public clsTabelaMemoria(int pLinhas, int pColunas, int pLargura, int pAltura) {
        mtzMemoria = new clsProcesso[pLinhas][pColunas];
        inicializarMatriz(pLinhas, pColunas);
        tblTabela = new JTable();
        linhas = pLinhas;
        colunas = pColunas;
        atribuirModelo();
        atribuirRenderer();
        tblTabela.setTableHeader(null);
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
    }

    public clsTabelaMemoria(int pLinhas, int pColunas, int pLargura, int pAltura, clsProcesso[][] pMtzMemoria) {
        mtzMemoria = pMtzMemoria;
        linhas = pLinhas;
        colunas = pColunas;
        tblTabela = new JTable();
        atribuirModelo();
        atribuirRenderer();
        tblTabela.setTableHeader(null);
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
    }

    public JTable getTable() {
        return tblTabela;
    }

    public boolean adicionarProcesso(clsProcesso pProcesso, int tamanhoMaiorProcesso) {
        int posicao[];
        int y = 0;
        int x = 0;
        int contaGravacao = 0;
        posicao = getPosicaoLivreDaMemoria();
        if (posicao[0] == -1 || posicao[2] < (pProcesso.getIntFrames() + tamanhoMaiorProcesso)) {
            return false;
        }
        y = posicao[0];
        x = posicao[1];
        do {
            do {
                mtzMemoria[y][x].setPid(pProcesso.getIntPid());
                mtzMemoria[y][x].setCor(pProcesso.getCor());
                mtzMemoria[y][x].setEstado(pProcesso.getEstado());
                mtzMemoria[y][x].setFrames(pProcesso.getIntFrames());
                mtzMemoria[y][x].setSelecionado(pProcesso.getSelecionado());
                mtzMemoria[y][x].setTempo(pProcesso.getIntTempo());
                mtzMemoria[y][x].setTempoCriacao(pProcesso.getIntTempoCriacao());
                mtzMemoria[y][x].setTempoUcp(pProcesso.getIntTempoUcp());
                mtzMemoria[y][x].setTipo(pProcesso.getTipo());
                mtzMemoria[y][x].setEliminado(pProcesso.getEliminado());
                contaGravacao++;
                x++;
            } while (x < colunas && contaGravacao < pProcesso.getIntFrames());
            x = 0;
            y++;
        } while (y < linhas && contaGravacao < pProcesso.getIntFrames());
        atualizarTabela();
        return true;
    }

    public boolean removerProcesso(int pPid) {
        boolean removeu = false;
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                if (mtzMemoria[y][x].getIntPid() == pPid) {
                    if (!removeu) {
                        removeu = true;
                    }
                    mtzMemoria[y][x].restaurar();
                }
            }
        }
        if (removeu) {
            atualizarTabela();
            desfragmentar();
        }
        return removeu;
    }

    public void desfragmentar() {
        int xl;
        int yl;
        int x = 0;
        int y = 0;
        boolean achou;
        do {
            do {
                achou = true;
                if (mtzMemoria[y][x].getIntPid() == -1) {
                    xl = x;
                    yl = y;
                    do {
                        achou = false;
                        do {
                            if (mtzMemoria[yl][xl].getIntPid() != -1) {
                                achou = true;
                                mtzMemoria[y][x].setPid(mtzMemoria[yl][xl].getIntPid());
                                mtzMemoria[y][x].setCor(mtzMemoria[yl][xl].getCor());
                                mtzMemoria[y][x].setEstado(mtzMemoria[yl][xl].getEstado());
                                mtzMemoria[y][x].setFrames(mtzMemoria[yl][xl].getIntFrames());
                                mtzMemoria[y][x].setTempo(mtzMemoria[yl][xl].getIntTempo());
                                mtzMemoria[y][x].setTempoCriacao(mtzMemoria[yl][xl].getIntTempoCriacao());
                                mtzMemoria[y][x].setTempoUcp(mtzMemoria[yl][xl].getIntTempoUcp());
                                mtzMemoria[y][x].setTipo(mtzMemoria[yl][xl].getTipo());
                                mtzMemoria[y][x].setSelecionado(mtzMemoria[yl][xl].getSelecionado());
                                mtzMemoria[y][x].setEliminado(mtzMemoria[yl][xl].getEliminado());
                                mtzMemoria[yl][xl].restaurar();
                            }
                            xl++;
                        } while (xl < colunas && achou == false);
                        xl = 0;
                        yl++;
                    } while (yl < linhas && achou == false);
                }
                x++;
            } while (x < colunas && achou == true);
            x = 0;
            y++;
        } while (y < linhas && achou == true);
        atualizarTabela();
    }

    public boolean verificarSeEstaNaMemoria(clsProcesso pProcesso) {
        int y = 0;
        int x;
        boolean retorno = false;
        do {
            x = 0;
            do {
                if (mtzMemoria[y][x].getIntPid() == pProcesso.getIntPid()) {
                    retorno = true;
                }
                x++;
            } while (x < colunas && mtzMemoria[y][x - 1].getIntPid() != -1 && !retorno);
            y++;
        } while (y < linhas && mtzMemoria[y - 1][x - 1].getIntPid() != -1 && !retorno);
        return retorno;
    }

    private void inicializarMatriz(int linhas, int colunas) {
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                mtzMemoria[y][x] = new clsProcesso();
            }
        }
    }

    public void atualizarTabela() {
        tblTabela.updateUI();
    }

    private void atribuirRenderer() {
        tblTabela.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    @Override // Adicione o anotador @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                        setHorizontalAlignment(CENTER);
                        setBackground(mtzMemoria[row][col].getCor());
                        return this;
                    }
                }
        );
    }

    private void atribuirModelo() {
        tblTabela.setModel(
                new AbstractTableModel() {
                    public int getColumnCount() {
                        return colunas;
                    }

                    public int getRowCount() {
                        return linhas;
                    }

                    public Object getValueAt(int row, int col) {
                        return new String(mtzMemoria[row][col].getPid());
                    }
                }
        );
    }

    private int[] getPosicaoLivreDaMemoria() {
        int y = 0;
        int x;
        int contPosicoes = 0;
        int posicoes[] = {-1, -1, -1};
        do {
            x = 0;
            do {
                if (mtzMemoria[y][x].getIntPid() == -1) {
                    if (posicoes[0] == -1) {
                        posicoes[0] = y;
                        posicoes[1] = x;
                    }
                    contPosicoes++;
                }
                x++;
            } while (x < colunas);
            y++;
        } while (y < linhas);
        posicoes[2] = contPosicoes;
        return posicoes;
    }

    public int contarFramesDisponiveis() {
        int count = 0;
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                if (mtzMemoria[y][x].getIntPid() == -1) {
                    count++;
                }
            }
        }
        return count;
    }

    public void alocarProcesso(clsProcesso processo) {
        int tamanhoProcesso = processo.getIntFrames();
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x <= colunas - tamanhoProcesso; x++) {
                boolean espacosDisponiveis = true;
                for (int i = 0; i < tamanhoProcesso; i++) {
                    if (mtzMemoria[y][x + i].getIntPid() != -1) {
                        espacosDisponiveis = false;
                        break;
                    }
                }
                if (espacosDisponiveis) {
                    for (int i = 0; i < tamanhoProcesso; i++) {
                        mtzMemoria[y][x + i].setPid(processo.getIntPid());
                        mtzMemoria[y][x + i].setCor(processo.getCor());
                        // atribuir outras propriedades do processo, se necessário
                    }
                    return;
                }
            }
        }
        // Se não houver espaço contíguo suficiente, você pode implementar outra lógica de alocação
        // Por exemplo, você pode procurar espaço em uma lista encadeada de segmentos livres
        // ou implementar uma política de substituição para liberar espaço
    }

    public void alocarProcessoParcial(clsProcesso processo, int qtdFrames) {
        int framesRestantes = qtdFrames;
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                if (mtzMemoria[y][x].getIntPid() == -1) {
                    mtzMemoria[y][x].setPid(processo.getIntPid());
                    mtzMemoria[y][x].setCor(processo.getCor());
                    // atribuir outras propriedades do processo, se necessário
                    framesRestantes--;
                }
                if (framesRestantes == 0) {
                    return;
                }
            }
        }
    }
}
