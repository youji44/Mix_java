import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;



public class MainFrame extends JFrame implements ActionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static MainFrame m_nInstance = null;
	
	public static MainFrame getInstance() {
		if ( m_nInstance == null )
			m_nInstance = new MainFrame();
		
		return m_nInstance;
	}

	public MainFrame() {
		// TODO Auto-generated constructor stub
		m_nInstance = this;
	}
	class Person {
		public String name;
		public Person(String name) {
		this.name = name;
		}
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainFrame f = new MainFrame();
				f.menuUI();
			}
		});
	}
	
	public void askUI() {
        int input = JOptionPane.showConfirmDialog(this, 
                Res.ASK_QUESTION, "", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null); //icon
        
        if ( input == JOptionPane.OK_OPTION ) {
        	menuUI();
        }                
	}
	
public void menuUI() {
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
						
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.bottom = 50;
		
		JLabel lblTitle = new JLabel(Res.MENU_TITLE);
		lblTitle.setFont(new Font("Serif", Font.PLAIN, 20));
		mainPanel.add(lblTitle, gbc);
		
		for ( int i = 0; i < Res.MENU_ITEMS.length; i ++ ) {
			JButton btnCategory = new JButton(Res.MENU_ITEMS[i]);		
			btnCategory.putClientProperty("id", i);
			btnCategory.addActionListener(this);
			
			gbc.gridx = 0;
			gbc.gridy = i+1;
			gbc.insets.bottom = 5;
			mainPanel.add(btnCategory, gbc);
		}

		setContentPane(mainPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 320);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);				
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
		Object property = ((JComponent) e.getSource()).getClientProperty("id");
		if (property instanceof Integer) {
			int menuId = ((Integer)property);
			
			   SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		            	//hide MainFrame
		            	setVisible(false);
		            	if(menuId == 0) {
		    				//hide MainFrame
		    				MixTankFrame mixTank = new MixTankFrame();
		    				mixTank.buildUI();
		    			}
		    			else {
		    				
		    				Procedure mixTank = new Procedure();
		    				mixTank.buildUI(menuId+1);
		    			}
		            }
		        });
		}
	}
}
