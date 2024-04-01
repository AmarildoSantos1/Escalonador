import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.text.*; 
import javax.swing.JOptionPane;
public class frmAdicionarProcesso extends JFrame
{
	JTable tblCor;
    JSpinner spnFrames;
    JSpinner spnTempo;
    JComboBox cbbTipo;
	clsTabelaProcesso objProcesso;
	clsTabelaProcessosBloqueados objProcessosBloqueados;
	clsTabelaMemoria objMemoriaPrincipal;
	clsTabelaMemoria objMemoriaSecundaria;
	clsTabelaCor objTblCor;
	public frmAdicionarProcesso(clsTabelaProcesso pProcesso, clsTabelaMemoria pMemoriaPrincipal, clsTabelaMemoria pMemoriaSecundaria, clsTabelaProcessosBloqueados pProcessosBloqueados) 
    {
		super("Adicionar processo");
    	Container tela = getContentPane();
    	tela.setLayout(new BorderLayout());
    	objProcesso = pProcesso;
    	objProcessosBloqueados = pProcessosBloqueados;
    	objMemoriaPrincipal= pMemoriaPrincipal; 
    	objMemoriaSecundaria= pMemoriaSecundaria;
    	
		//CENTRO DA TELA//
		
    	JPanel pnlCentro = new JPanel();
    	pnlCentro.setLayout(new GridLayout(1,2));
    	pnlCentro.setBorder(BorderFactory.createEtchedBorder(1));
    	tela.add(pnlCentro, BorderLayout.CENTER);
		//CENTRO DA TELA -> FRAMES//
		JPanel pnlFrames = new JPanel();
		pnlFrames.setLayout(new BorderLayout());
		pnlFrames.setBorder(BorderFactory.createEtchedBorder(1));
		pnlCentro.add(pnlFrames);
		//CENTRO DA TELA -> FRAMES -> TITULO//
		JPanel pnlTituloFrames = new JPanel();
		pnlTituloFrames.setLayout(new FlowLayout());
		pnlFrames.add(pnlTituloFrames, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloFrames = new JLabel("Frames");
		pnlTituloFrames.add(lblTituloFrames);
		//CENTRO DA TELA -> FRAMES -> SPINNER//
		JPanel pnlSpinnerFrames = new JPanel();
		pnlSpinnerFrames.setLayout(new FlowLayout());
		pnlFrames.add(pnlSpinnerFrames, BorderLayout.CENTER);
		//--------------------------------------------------------------------------------------------------------------------------//
		spnFrames = new JSpinner(new SpinnerNumberModel(5, 1, 16, 1));
		spnFrames.setEditor(new JSpinner.NumberEditor(spnFrames, "000"));
		pnlSpinnerFrames.add(spnFrames);
		spnFrames.setPreferredSize(new Dimension(100, 25));
		//CENTRO DA TELA -> TEMPO//
		JPanel pnlTempo = new JPanel();
		pnlTempo.setLayout(new BorderLayout());
		pnlTempo.setBorder(BorderFactory.createEtchedBorder(1));
		pnlCentro.add(pnlTempo);
		//CENTRO DA TELA -> TEMPO -> TITULO//
		JPanel pnlTituloTempo = new JPanel();
		pnlTituloTempo.setLayout(new FlowLayout());
		pnlTempo.add(pnlTituloTempo, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloTempo = new JLabel("Tempo");
		pnlTituloTempo.add(lblTituloTempo);
		//CENTRO DA TELA -> TEMPO -> SPINNER//
		JPanel pnlSpinnerTempo = new JPanel();
		pnlSpinnerTempo.setLayout(new FlowLayout());
		pnlTempo.add(pnlSpinnerTempo, BorderLayout.CENTER);
		//--------------------------------------------------------------------------------------------------------------------------//
		spnTempo = new JSpinner(new SpinnerNumberModel(10, 1, 60, 1));
		spnTempo.setEditor(new JSpinner.NumberEditor(spnTempo, "000"));
		pnlSpinnerTempo.add(spnTempo);
		spnTempo.setPreferredSize(new Dimension(100, 25));
		//CENTRO DA TELA -> TIPO//
		JPanel pnlTipo = new JPanel();
		pnlTipo.setLayout(new BorderLayout());
		pnlTipo.setBorder(BorderFactory.createEtchedBorder(1));
		pnlCentro.add(pnlTipo);
		//CENTRO DA TELA -> TIPO -> T�TULO//
		JPanel pnlTituloTipo = new JPanel();
		pnlTituloTipo.setLayout(new FlowLayout());
		pnlTipo.add(pnlTituloTipo, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloTipo = new JLabel("Tipo");
		pnlTituloTipo.add(lblTituloTipo);
		//CENTRO DA TELA -> TIPO -> JCOMBOBOX//
		JPanel pnlComboBoxTipo = new JPanel();
		pnlComboBoxTipo.setLayout(new FlowLayout());
		pnlTipo.add(pnlComboBoxTipo, BorderLayout.CENTER);
		//--------------------------------------------------------------------------------------------------------------------------//
		cbbTipo = new JComboBox();
		cbbTipo.addItem("I/O Bound");
		cbbTipo.addItem("CPU Bound");
		pnlComboBoxTipo.add(cbbTipo);
		//CENTRO DA TELA -> COR DO PROCESSO//
		JPanel pnlCorDoProcesso = new JPanel();
		pnlCorDoProcesso.setBorder(BorderFactory.createEtchedBorder(1));
		pnlCorDoProcesso.setLayout(new BorderLayout());
		pnlCentro.add(pnlCorDoProcesso);
		//CENTRO DA TELA -> COR DO PROCESSO -> TITULO//
		JPanel pnlTituloCorProcesso = new JPanel();
		pnlTituloCorProcesso.setLayout(new FlowLayout());
		pnlCorDoProcesso.add(pnlTituloCorProcesso, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloCorDoProcesso = new JLabel("Cor do processo");
		pnlTituloCorProcesso.add(lblTituloCorDoProcesso);
		//CENTRO DA TELA -> COR DO PROCESSO -> TABELA//
		JPanel pnlTabelaCorDoProcesso = new JPanel();
		pnlTabelaCorDoProcesso.setLayout(new FlowLayout());
		pnlCorDoProcesso.add(pnlTabelaCorDoProcesso, BorderLayout.CENTER);
		//--------------------------------------------------------------------------------------------------------------------------//
		objTblCor = new clsTabelaCor(100, 23);
		JScrollPane scrCor = new JScrollPane(objTblCor.getTable());
		pnlTabelaCorDoProcesso.add(scrCor);
		//RODAPE DA TELA//
		JPanel pnlRodape = new JPanel();
		pnlRodape.setLayout(new FlowLayout());
		tela.add(pnlRodape, BorderLayout.SOUTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JButton btnCriar = new JButton("Criar");
		pnlRodape.add(btnCriar);
		btnCriar.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												objProcesso.adicionarProcesso(objTblCor.getCor(), Integer.parseInt(spnFrames.getValue().toString()), objMemoriaPrincipal, objMemoriaSecundaria, objProcessosBloqueados, Integer.parseInt(spnTempo.getValue().toString()), cbbTipo.getItemAt(cbbTipo.getSelectedIndex()).toString());												
												dispose();		
											}
										}
								   );
		
		JButton btnCancelar = new JButton("Cancelar");
		pnlRodape.add(btnCancelar);
		btnCancelar.addActionListener(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												dispose();
											}
										}
									  );
		//TELA//
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
    }  
}