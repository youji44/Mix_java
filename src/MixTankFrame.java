
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class MixTankFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
    
	public void buildUI() {
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.bottom = 20;
		
		JLabel lblTitle = new JLabel("Starting Mix Tank Procedure");
		lblTitle.setFont(new Font("Serif", Font.PLAIN, 20));
		mainPanel.add(lblTitle, gbc);
		
		JButton btnCategory = new JButton("Starting Mix Tank Procedure");		
		btnCategory.putClientProperty("id",3);
		btnCategory.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets.bottom = 5;
		mainPanel.add(btnCategory,gbc);
		setContentPane(mainPanel);
		setSize(480, 320);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		MainFrame.getInstance().setVisible(true);
        		dispose();
        	}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object property = ((JComponent) e.getSource()).getClientProperty("id");
		if (property instanceof Integer) {
			SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	//hide MainFrame
	            	setVisible(false);
	            
            		//show Detail Frame
	            	DetailFrame detailFrame = new DetailFrame();
	            	detailFrame.buildUI();
	            	detailFrame.addWindowListener(new WindowAdapter() {
	            	    @Override
	            	    public void windowClosing(WindowEvent windowEvent) {
	            	    	for (int i=0;i<6;i++) {
	            	    		detailFrame.checkedList.set(29+i,detailFrame.CLOSE_DATA[i][2]==Boolean.TRUE?"true":"false");
	            	    	}
	            	    	for (int i=0;i<3;i++) {
	            	    		detailFrame.checkedList.set(35+i,detailFrame.OPEN_DATA[i][2]==Boolean.TRUE?"true":"false");
	            	    	}
	            	    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out.txt")))) {
	                            // write the content to the file
	                            writer.write(detailFrame.checkedList.toString()+'-'+detailFrame.inputList.toString());
	                        } catch (IOException e1) {
	                            e1.printStackTrace();
	                        }
	            	    }
	            	});
	            }
	        });
		}
	}
	
	
}
