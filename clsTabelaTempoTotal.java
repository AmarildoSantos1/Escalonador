import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;

public class clsTabelaTempoTotal {
    JTable tblTabela;
    int valor;
    int numeroDePaginas;
    int enderecoDeMemoria;
    String estadoDaPagina;

    public clsTabelaTempoTotal(int pLargura, int pAltura) {
        tblTabela = new JTable();
        valor = 0;
        numeroDePaginas = 0;
        enderecoDeMemoria = 0;
        estadoDaPagina = "Carregada"; // Estado inicial da página

        atribuirModelo();
        atribuirRenderer();
        tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargura, pAltura));
        tblTabela.setTableHeader(null);
    }

    public JTable getTable() {
        return tblTabela;
    }

    public void atualizarTabela() {
        tblTabela.updateUI();
    }

    public void incrementarValor() {
        valor++;
        atualizarTabela();
    }

    // Método para atualizar as informações da página
    public void atualizarInformacoesPagina(int numPaginas, int endereco, String estado) {
        numeroDePaginas = numPaginas;
        enderecoDeMemoria = endereco;
        estadoDaPagina = estado;
        atualizarTabela();
    }

    private void atribuirRenderer() {
        tblTabela.setDefaultRenderer(Object.class,
            new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                    setHorizontalAlignment(CENTER);
                    return this;
                }
            });
    }

    private void atribuirModelo() {
        tblTabela.setModel(
            new AbstractTableModel() {
                public int getColumnCount() {
                    return 4; // Número de colunas
                }

                public int getRowCount() {
                    return 1;
                }

                public Object getValueAt(int row, int col) {
                    // Retorna os valores com base na coluna
                    switch (col) {
                        case 0:
                            return "Valor: " + valor;
                        case 1:
                            return "Páginas: " + numeroDePaginas;
                        case 2:
                            return "Endereço: " + enderecoDeMemoria;
                        case 3:
                            return "Estado: " + estadoDaPagina;
                        default:
                            return null;
                    }
                }
            });
    }
}
