import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class frmSobre extends JFrame
{
	 public frmSobre() 
    {
    	super("Sobre");
    	Container tela = getContentPane();
    	tela.setLayout(new BorderLayout());
    	//--------------------------------------------------------------------------------------------------------------------------//
		JPanel pnlTeoria = new JPanel();
		pnlTeoria.setBorder(BorderFactory.createEtchedBorder(1));
		pnlTeoria.setLayout(new BorderLayout());
		tela.add(pnlTeoria, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloTeoria = new JLabel("Sobre o modo de escalonamento");
		lblTituloTeoria.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTeoria.add(lblTituloTeoria, BorderLayout.NORTH); 
		//--------------------------------------------------------------------------------------------------------------------------//
		JTextArea txtTeoria = new JTextArea();
		txtTeoria.append(
							"          O escalonamento FCFS (First-Come, First Served), sendo a forma mais elementar para escalonar processos e consiste em atender as tarefas na ordem em que estas chegam a fila de tarefas prontas.\n" +
							"          Este projeto une a fila de processos prontos e a fila de processos bloqueados em uma unica fila de processos bloqueados. O algoritmo apresenta FCFS cooperativo, no qual o processador executa uma tarefa por vez.\n" +
							"          Para melhorar o desempenho, os processos sao alocados na memoria de acordo sua posicao na fila de bloqueados. O processo em execucao e os primeiros processos da fila de bloqueados sao armazenados na memoria principal. Caso a memoria principal fique lotada, os ultimos processos da fila de bloqueados sao armazenados na memoria secundaria. Para que esta realocacao de memoria (swap) possa ser executada, e necessario que haja na memoria secundaria um espaco igual ou superior ao tamanho do maior processo."           
						);
		txtTeoria.setEditable(false);
		txtTeoria.setPreferredSize(new Dimension(600, 210));
		txtTeoria.setWrapStyleWord(true);
		txtTeoria.setLineWrap(true);
		JLabel lblTeorialn02 = new JLabel("");
		pnlTeoria.add(txtTeoria, BorderLayout.CENTER); 
		//--------------------------------------------------------------------------------------------------------------------------//
		JPanel pnlCreditos = new JPanel();
		pnlCreditos.setLayout(new BorderLayout());
		pnlCreditos.setBorder(BorderFactory.createEtchedBorder(1));
		tela.add(pnlCreditos, BorderLayout.CENTER);
		//--------------------------------------------------------------------------------------------------------------------------//
		JLabel lblTituloCreditos = new JLabel("Desenvolvedor");
		lblTituloCreditos.setHorizontalAlignment(SwingConstants.CENTER);
		pnlCreditos.add(lblTituloCreditos, BorderLayout.NORTH);
		//--------------------------------------------------------------------------------------------------------------------------//
		JTextArea txtCreditos = new JTextArea();
		txtCreditos.setEditable(false);
		txtCreditos.append(
							"Aluno: Amarildo Santos .\n" +
							"Curso: Sistemas de Informacoes.\n" + 
							"Disciplina: Sistemas Operacionais.\n" +
							"Professor : Bruno Rafael Vasconcellos.\n" + 
							"Universidade: Unifacisa."
						  );
		pnlCreditos.add(txtCreditos, BorderLayout.CENTER);
	    //--------------------------------------------------------------------------------------------------------------------------//
    	JPanel pnlBotoes = new JPanel();
    	pnlBotoes.setLayout(new FlowLayout());
    	tela.add(pnlBotoes, BorderLayout.SOUTH);
    	//--------------------------------------------------------------------------------------------------------------------------//
		JButton btnFechar = new JButton("Fechar");
		pnlBotoes.add(btnFechar);
		btnFechar.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}	
			}		
		);    	
    	//--------------------------------------------------------------------------------------------------------------------------//
    	setResizable(false);
    	pack();
    	setLocationRelativeTo(null);
    	setVisible(true);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}