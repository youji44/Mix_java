import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Mix Tank Procedure entry confirm UI Class
 */
public class MixTankFrame extends JFrame implements ActionListener {

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
                    //hide MainFrame
                    setVisible(false);

                    //show Detail Frame
                    DetailFrame detailFrame = new DetailFrame();
                    detailFrame.buildUI();

                    /*
                     * when close Mix Tank procedure UI, storing inputted data as json data on 'out.txt'
                     */
                    detailFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent windowEvent) {
                        for (int i = 0; i < detailFrame.CLOSE_DATA.length; i++) {
                            detailFrame.checkedList.put((String) detailFrame.CLOSE_DATA[i][0], detailFrame.CLOSE_DATA[i][2] == Boolean.TRUE ? "checked" : "unchecked");
                        }
                        for (int i = 0; i < detailFrame.OPEN_DATA.length; i++) {
                            detailFrame.checkedList.put((String) detailFrame.OPEN_DATA[i][0], detailFrame.OPEN_DATA[i][2] == Boolean.TRUE ? "checked" : "unchecked");
                        }
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out.txt")))) {
                            // write the content to the file
                            writer.write(detailFrame.checkedList.toString() + '-' + detailFrame.inputList.toString() + '-' + detailFrame.doneList.toString());
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
