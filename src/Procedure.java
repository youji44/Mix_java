import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Coming soon Class for extended procedures
 */
public class Procedure extends JFrame implements ActionListener {

    @Serial
	private static final long serialVersionUID = 2L;

    public void buildUI(int viewID) {

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.bottom = 20;

        JLabel lblTitle = new JLabel("Starting Procedure " + viewID);
        lblTitle.setFont(new Font("Serif", Font.PLAIN, 20));
        mainPanel.add(lblTitle, gbc);

        JButton btnCategory = new JButton("Starting Procedure " + viewID);
        btnCategory.putClientProperty("id", 3);
        btnCategory.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets.bottom = 5;
        mainPanel.add(btnCategory, gbc);
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
                }
            });
        }
    }
}
