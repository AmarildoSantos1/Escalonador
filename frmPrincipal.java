import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class frmPrincipal extends JFrame {
    clsTabelaProcesso objTabelaProcessos;
    clsTabelaProcessosBloqueados objProcessosBloqueados;
    clsTabelaMemoria objMemoriaPrincipal;
    clsTabelaMemoria objMemoriaSecundaria;
    clsTabelaTempoTotal objTabelaTempoTotal;
    clsTabelaProcessoEmExecucao objProcessoEmExecucao;
    clsEscalonador objProcessador;
    JTable tblProcessos;

    public frmPrincipal() {
        super("Simulador de escalonamento de processos");
        Container tela = getContentPane();
        tela.setLayout(new BorderLayout());

        // CENTRO DA TELA
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());

        // TELA DE PROCESSOS
        JPanel pnlProcessos = new JPanel();
        pnlProcessos.setLayout(new BorderLayout());
        pnlProcessos.setBorder(BorderFactory.createEtchedBorder(1));
        pnlCentro.add(pnlProcessos, BorderLayout.NORTH);

        // TELA DE PROCESSOS -> TABELA DE PROCESSOS
        JPanel pnlPrcTabela = new JPanel();
        pnlPrcTabela.setLayout(new FlowLayout(FlowLayout.LEFT));

        objTabelaProcessos = new clsTabelaProcesso(256, 930, 160);
        JScrollPane scrProcessos = new JScrollPane(objTabelaProcessos.getTable());
        pnlPrcTabela.add(scrProcessos);
        pnlProcessos.add(pnlPrcTabela, BorderLayout.WEST);

        // TELA DE PROCESSOS -> BOTOES
        JPanel pnlPrcBotoes = new JPanel();
        pnlPrcBotoes.setLayout(new GridLayout(2, 1));
        pnlProcessos.add(pnlPrcBotoes, BorderLayout.EAST);

        JButton btnCriarProcesso = new JButton("Criar processo...");
        pnlPrcBotoes.add(btnCriarProcesso);
        btnCriarProcesso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmAdicionarProcesso adicionarProcesso = new frmAdicionarProcesso(objTabelaProcessos, objMemoriaPrincipal, objMemoriaSecundaria, objProcessosBloqueados);
            }
        });

        JButton btnEliminarProcesso = new JButton("Eliminar processo");
        pnlPrcBotoes.add(btnEliminarProcesso);
        btnEliminarProcesso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                objTabelaProcessos.eliminarProcesso(objMemoriaPrincipal, objMemoriaSecundaria, objProcessosBloqueados, objProcessoEmExecucao);
            }
        });

        // TELA DE MEMORIA
        JPanel pnlMemoria = new JPanel();
        pnlMemoria.setLayout(new BorderLayout());
        pnlCentro.add(pnlMemoria, BorderLayout.WEST);
        pnlMemoria.setBorder(BorderFactory.createEtchedBorder(1));

        // TELA DE MEMORIA -> TITULO
        JPanel pnlTituloMemoria = new JPanel();
        pnlTituloMemoria.setLayout(new FlowLayout());
        pnlMemoria.add(pnlTituloMemoria, BorderLayout.NORTH);

        JLabel lblTituloMemoria = new JLabel("Memoria");
        pnlTituloMemoria.add(lblTituloMemoria);

        // TELA DE MEMORIA -> MEMORIA PRINCIPAL
        JPanel pnlMemoriaPrincipal = new JPanel();
        pnlMemoriaPrincipal.setLayout(new BorderLayout());
        pnlMemoria.add(pnlMemoriaPrincipal, BorderLayout.WEST);
        pnlMemoriaPrincipal.setBorder(BorderFactory.createEtchedBorder(1));

        JPanel pnlTituloMemoriaPrincipal = new JPanel();
        pnlTituloMemoriaPrincipal.setLayout(new FlowLayout());
        pnlMemoriaPrincipal.add(pnlTituloMemoriaPrincipal, BorderLayout.NORTH);

        JLabel lblTituloMemoriaPrincipal = new JLabel("Memoria principal");
        pnlTituloMemoriaPrincipal.add(lblTituloMemoriaPrincipal);

        JPanel pnlTabelaMemoriaPrincipal = new JPanel();
        pnlTabelaMemoriaPrincipal.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlMemoriaPrincipal.add(pnlTabelaMemoriaPrincipal, BorderLayout.CENTER);

        objMemoriaPrincipal = new clsTabelaMemoria(16, 8, 200, 256);
        JScrollPane scrMemoriaPrincipal = new JScrollPane(objMemoriaPrincipal.getTable());
        pnlTabelaMemoriaPrincipal.add(scrMemoriaPrincipal);

        JPanel pnlMemoriaSecundaria = new JPanel();
        pnlMemoriaSecundaria.setLayout(new BorderLayout());
        pnlMemoria.add(pnlMemoriaSecundaria, BorderLayout.EAST);
        pnlMemoriaSecundaria.setBorder(BorderFactory.createEtchedBorder(1));

        JPanel pnlTituloMemoriaSecundaria = new JPanel();
        pnlTituloMemoriaSecundaria.setLayout(new FlowLayout());
        pnlMemoriaSecundaria.add(pnlTituloMemoriaSecundaria, BorderLayout.NORTH);

        JLabel lblTituloMemoriaSecundaria = new JLabel("Memoria secundaria");
        pnlTituloMemoriaSecundaria.add(lblTituloMemoriaSecundaria);

        JPanel pnlTabelaMemoriaSecundaria = new JPanel();
        pnlTabelaMemoriaSecundaria.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlMemoriaSecundaria.add(pnlTabelaMemoriaSecundaria, BorderLayout.CENTER);

        objMemoriaSecundaria = new clsTabelaMemoria(16, 16, 400, 256);
        JScrollPane scrMemoriaSecundaria = new JScrollPane(objMemoriaSecundaria.getTable());
        pnlTabelaMemoriaSecundaria.add(scrMemoriaSecundaria);

        JPanel pnlProcessador = new JPanel();
        pnlProcessador.setLayout(new BorderLayout());
        pnlProcessador.setBorder(BorderFactory.createEtchedBorder(1));
        pnlCentro.add(pnlProcessador, BorderLayout.CENTER);

        JPanel pnlTituloProcessador = new JPanel();
        pnlTituloProcessador.setLayout(new FlowLayout());
        pnlProcessador.add(pnlTituloProcessador, BorderLayout.NORTH);

        JLabel lblTituloProcessador = new JLabel("Processador");
        pnlTituloProcessador.add(lblTituloProcessador);

        JPanel pnlFilaDeBloqueados = new JPanel();
        pnlFilaDeBloqueados.setLayout(new BorderLayout());
        pnlFilaDeBloqueados.setBorder(BorderFactory.createEtchedBorder(1));
        pnlProcessador.add(pnlFilaDeBloqueados, BorderLayout.WEST);

        JPanel pnlTituloFilaDeBloqueados = new JPanel();
        pnlTituloFilaDeBloqueados.setLayout(new FlowLayout());
        pnlFilaDeBloqueados.add(pnlTituloFilaDeBloqueados, BorderLayout.NORTH);

        JLabel lblTituloFilaDeBloqueados = new JLabel("Processos bloqueados");
        pnlTituloFilaDeBloqueados.add(lblTituloFilaDeBloqueados);

        JPanel pnlTabelaProcessosBloqueados = new JPanel();
        pnlTabelaProcessosBloqueados.setLayout(new FlowLayout());
        pnlFilaDeBloqueados.add(pnlTabelaProcessosBloqueados, BorderLayout.CENTER);

        objProcessosBloqueados = new clsTabelaProcessosBloqueados(256, 150, 240);
        JScrollPane scrProcessosBloqueados = new JScrollPane(objProcessosBloqueados.getTable());
        pnlTabelaProcessosBloqueados.add(scrProcessosBloqueados);

        JPanel pnlDetalhesProcessador = new JPanel();
        pnlDetalhesProcessador.setLayout(new GridLayout(3, 1));
        pnlProcessador.add(pnlDetalhesProcessador, BorderLayout.CENTER);

        JPanel pnlTempoDeExecucao = new JPanel();
        pnlTempoDeExecucao.setLayout(new BorderLayout());
        pnlTempoDeExecucao.setBorder(BorderFactory.createEtchedBorder(1));
        pnlDetalhesProcessador.add(pnlTempoDeExecucao);

        JPanel pnlTituloTempoDeExecucao = new JPanel();
        pnlTituloTempoDeExecucao.setLayout(new FlowLayout());
        pnlTempoDeExecucao.add(pnlTituloTempoDeExecucao, BorderLayout.NORTH);

        JLabel lblTituloTempoDeExecucao = new JLabel("Tempo de Execucao");
        pnlTituloTempoDeExecucao.add(lblTituloTempoDeExecucao);

        JPanel pnlTabelaTempoDeExecucao = new JPanel();
        pnlTabelaTempoDeExecucao.setLayout(new FlowLayout());
        pnlTempoDeExecucao.add(pnlTabelaTempoDeExecucao, BorderLayout.CENTER);

        objTabelaTempoTotal = new clsTabelaTempoTotal(100, 16);
        JScrollPane scrTempoDeExecucao = new JScrollPane(objTabelaTempoTotal.getTable());
        pnlTabelaTempoDeExecucao.add(scrTempoDeExecucao);

        JPanel pnlEmExecucao = new JPanel();
        pnlEmExecucao.setLayout(new BorderLayout());
        pnlEmExecucao.setBorder(BorderFactory.createEtchedBorder(1));
        pnlDetalhesProcessador.add(pnlEmExecucao);

        JPanel pnlTituloEmExecucao = new JPanel();
        pnlTituloEmExecucao.setLayout(new FlowLayout());
        pnlEmExecucao.add(pnlTituloEmExecucao, BorderLayout.NORTH);

        JLabel lblTituloEmExecucao = new JLabel("Em Execucao");
        pnlTituloEmExecucao.add(lblTituloEmExecucao);

        JPanel pnlTabelaEmExecucao = new JPanel();
        pnlTabelaEmExecucao.setLayout(new FlowLayout());
        pnlEmExecucao.add(pnlTabelaEmExecucao, BorderLayout.CENTER);

        objProcessoEmExecucao = new clsTabelaProcessoEmExecucao(250, 16, objProcessosBloqueados, objTabelaProcessos, objMemoriaPrincipal, objMemoriaSecundaria);
        JScrollPane scrEmExecucao = new JScrollPane(objProcessoEmExecucao.getTable());
        pnlTabelaEmExecucao.add(scrEmExecucao);

        JPanel pnlClock = new JPanel();
        pnlClock.setLayout(new BorderLayout());
        pnlClock.setBorder(BorderFactory.createEtchedBorder(1));
        pnlDetalhesProcessador.add(pnlClock);

        objProcessador = new clsEscalonador(pnlClock, 1000, objTabelaProcessos, objProcessosBloqueados, objMemoriaPrincipal, objMemoriaSecundaria, objTabelaTempoTotal, objProcessoEmExecucao);

        JPanel pnlInferior = new JPanel();
        pnlInferior.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSobre = new JButton("Sobre...");
        pnlInferior.add(btnSobre);
        btnSobre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmSobre sobre = new frmSobre();
            }
        });

        JButton btnFechar = new JButton("Fechar");
        pnlInferior.add(btnFechar);
        btnFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        tela.add(pnlCentro, BorderLayout.CENTER);
        tela.add(pnlInferior, BorderLayout.SOUTH);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) {
        frmPrincipal Janela = new frmPrincipal();
        Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
