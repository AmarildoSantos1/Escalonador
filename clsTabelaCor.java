import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
public class clsTabelaCor 
// credito:https://github.com/titenq/united-colors-of-devs, adaptado com GPT Chat
{
	
    JTable tblTabela;
    Color cor;
	
    public clsTabelaCor(int pLargula, int pAltura) 
    {
    	tblTabela = new JTable();
    	cor = Color.YELLOW;
    	tblTabela.setPreferredScrollableViewportSize(new Dimension(pLargula,pAltura));
    	tblTabela.setTableHeader(null);
    	tblTabela.setRowHeight(pAltura);
    	atribuirModelo();
    	atribuirRenderer();
    	atribuirListeners();
    }
	 
	public JTable getTable()
	{
		return tblTabela;
	}
	  
    public void setCor()
    {
    	cor = new JColorChooser().showDialog(null, "Selecione uma cor", cor);
    	atualizarTabela();
    }
    public Color getCor()
    {
    	return cor;
    }
	public void atualizarTabela()
	{
		tblTabela.updateUI();
	}
	private void atribuirRenderer()
    {
		tblTabela.setDefaultRenderer(Object.class, 
										new DefaultTableCellRenderer()
										{
									   		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
									   		{
									   			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
									   			setBackground(cor);
									   			return this;
									   		}		
										}
									);
    }
	private void atribuirModelo()
    {
    	tblTabela.setModel(
							new AbstractTableModel()
							{
								public int getColumnCount()
								{
									return 1;
								}
								public int getRowCount()
								{
									return 1;
								}
								public Object getValueAt(int row, int col)
								{
									return new String("");
								}
							}
						   );
    }	
	private void atribuirListeners()
    {
		tblTabela.addMouseListener(
									new MouseAdapter()
									{
									    public void mouseClicked(MouseEvent me) 
									    {  
									    	setCor();  
									    }  		
									}
								  );
    }	     
}