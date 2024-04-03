import java.awt.Color;
import java.awt.Component; 
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.*;

public class clsTabelaMemoria {
    private clsProcesso mtzMemoria[][];
    private JTable tblTabela;
    private int linhas;
    private int colunas;

    public clsTabelaMemoria(int pLinhas, int pColunas, int pLargura, int pAltura) {
        linhas = pLinhas;
        colunas = pColunas;
        mtzMemoria = new clsProcesso[linhas][colunas];
        inicializarMatriz(linhas, colunas);
        tblTabela = new JTable();
        atribuirModelo();
        atribuirRenderer();
        tblTabela.setTableHeader(null);
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
    }

    public clsTabelaMemoria(int pLinhas, int pColunas, int pLargura, int pAltura, clsProcesso[][] pMtzMemoria) {
        linhas = pLinhas;
        colunas = pColunas;
        mtzMemoria = pMtzMemoria;
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
        int[] posicao = getPosicaoLivreDaMemoria();
        if (posicao[0] == -1 || posicao[2] < (pProcesso.getIntFrames() + tamanhoMaiorProcesso)) {
            return false;
        }

        int y = posicao[0];
        int x = posicao[1];
        int contaGravacao = 0;

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
                    removeu = true;
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
                        } while (xl < colunas && !achou);

                        xl = 0;
                        yl++;
                    } while (yl < linhas && !achou);
                }

                x++;
            } while (x < colunas && achou);

            x = 0;
            y++;
        } while (y < linhas && achou);

        atualizarTabela();
    }

    public boolean verificarSeEstaNaMemoria(clsProcesso pProcesso) {
        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                if (mtzMemoria[y][x].getIntPid() == pProcesso.getIntPid()) {
                    return true;
                }
            }
        }

        return false;
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
                    @Override
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
        int[] posicoes = {-1, -1, -1};

        for (int y = 0; y < linhas; y++) {
            for (int x = 0; x < colunas; x++) {
                if (mtzMemoria[y][x].getIntPid() == -1) {
                    if (posicoes[0] == -1) {
                        posicoes[0] = y;
                        posicoes[1] = x;
                    }

                    posicoes[2]++;
                }
            }
        }

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

    // Método para realizar paginação e swap
    public void paginacaoESwap(clsPagina pagina, clsTabelaMemoria objMemoriaPrincipal, clsTabelaMemoria objMemoriaSecundaria) {
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
    
    // Método para escolher um processo para swap
    public clsProcesso escolherProcessoParaSwap() {
        // Implemente sua lógica para escolher um processo para swap aqui
        // Por exemplo, você pode escolher o processo que está na memória há mais tempo,
        // ou o processo com menor prioridade, dependendo dos requisitos do seu sistema
        return null; // Aqui estou retornando null apenas como exemplo
    }
}
