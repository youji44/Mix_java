
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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

public class DetailFrame extends JFrame implements TreeSelectionListener, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 2L;

    private JScrollPane htmlView;
    private JTree tree;
    private JPanel cardPane;

    public void buildUI() {

        // Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contents");
        createNodes(top);

        // Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        // Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        expandTree(tree);

        htmlView = new JScrollPane(new JPanel());
        displayValue((MixData) Res.MIX_INFO[0]);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250);
        splitPane.setPreferredSize(new Dimension(500, 300));

        // Add the split pane to this panel.
        setContentPane(splitPane);
        setTitle("");
        pack();
        setSize(880, 640);
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

    private void createNodes(DefaultMutableTreeNode top) {

        Object[] items = Res.MIX_INFO;

        int index = 0;
        Map<Integer, DefaultMutableTreeNode> nodesMap = new HashMap<>();

        while (index < items.length) {
            MixData info = (MixData) items[index];
            Integer id = info.id;
            Integer parentId = info.parent;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode();
            node.setUserObject(info);

            nodesMap.put(id, node);

            if (parentId == 0) {

                top.add(node);
            } else {
                nodesMap.get(parentId).add(node);
            }
            index++;
        }

    }

    private void expandTree(JTree tree) {
        int row = 0;
        while (tree.getRowCount() > row) {
            tree.expandRow(row++);
        }
    }

    private JPanel buildRadioOptionPanel(String question) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup bg = new ButtonGroup();

        JRadioButton btnYes = new JRadioButton("Yes", true);
        btnYes.putClientProperty("id", 0);
        btnYes.addActionListener(this);

        JRadioButton btnNo = new JRadioButton("No");
        btnNo.putClientProperty("id", 1);
        btnNo.addActionListener(this);

        bg.add(btnYes);
        bg.add(btnNo);

        panel.add(new JLabel(question));
        panel.add(btnYes);
        panel.add(btnNo);

        return panel;
    }

    private JPanel buildListCheckboxPanel(Object[] data) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < data.length; i++) {
            MixData item = (MixData) data[i];
            panel.add(new JCheckBox(item.value));
        }

        return panel;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode.getUserObject() instanceof MixData) {
                    displayValue((MixData) selectedNode.getUserObject());
                }
            }
        });

    }

    private void displayValue(MixData selectedMixData) {

        JPanel panel = ((JPanel) htmlView.getViewport().getView());
        panel.removeAll();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        switch (selectedMixData.type) {
            case Res.MIX_STRING: {
                panel.add(new JLabel(selectedMixData.value));
            }
            break;
            case Res.MIX_DATA: {
                String title = selectedMixData.value;
                JPanel main_panel = new JPanel();
                main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
                if (!Objects.equals(title, ""))
                    main_panel.add(new JLabel(title));
                Object[] mix_data = selectedMixData.data;

                JPanel jPanel;

                for (Object mix_datum : mix_data) {
                    MixData item = (MixData) mix_datum;

                    switch (item.type) {
                        case Res.MIX_EMPTY: {
                            JLabel jLabel = new JLabel();
                            jLabel.setText(" ");
                            main_panel.add(jLabel);
                        }
                        break;
                        case Res.MIX_STRING: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BoxLayout(sub_panel, BoxLayout.Y_AXIS));

                            JLabel jLabel = new JLabel();
                            jLabel.setText(item.value);
                            sub_panel.add(jLabel);
                            main_panel.add(sub_panel);
                        }
                        break;
                        case Res.MIX_TABLE: {

                            String strFlag = item.value;
                            JTable table = new JTable(new BooleanTableModel(strFlag.equals("Open")));
                            JScrollPane jScrollPane = new JScrollPane(table);
                            Dimension d = table.getPreferredSize();
                            int rows = strFlag.equals("Open") ? Res.OPEN_DATA.length : Res.CLOSE_DATA.length;
                            jScrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * (rows + 3)));
                            main_panel.add(jScrollPane);

                        }
                        break;
                        case Res.MIX_CHECKBOX: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new CardLayout());

                            JCheckBox jCheckBox = new JCheckBox();
                            jCheckBox.setText(item.value);
                            sub_panel.add(jCheckBox);
                            main_panel.add(sub_panel);

                        }
                        break;
                        case Res.MIX_TEXTINPUT: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BorderLayout());

                            JLabel jLabel = new JLabel(item.value);
                            sub_panel.add(jLabel, BorderLayout.NORTH);

                            JTextField jTextField = new JTextField();
                            sub_panel.add(jTextField, BorderLayout.SOUTH);
                            main_panel.add(sub_panel);
                        }
                        break;
                        case Res.MIX_RADIO: {

                            cardPane = new JPanel(new CardLayout());
                            cardPane.add(buildListCheckboxPanel(item.true_value), "Yes");
                            cardPane.add(buildListCheckboxPanel(item.false_value), "No");

                            jPanel = buildRadioOptionPanel(item.value);
                            main_panel.add(jPanel);
                            main_panel.add(cardPane);
                        }
                        break;
						default:
							break;
                    }
                }
                panel.add(main_panel);
            }
            break;
            case Res.MIX_CHECKBOX: {
                JCheckBox checkbox = new JCheckBox(selectedMixData.value);
                panel.add(checkbox);
            }
            break;
            case Res.MIX_TEXTINPUT: {
                JPanel txtPane = new JPanel();
                txtPane.setLayout(new BoxLayout(txtPane, BoxLayout.Y_AXIS));
                txtPane.add(new JLabel(selectedMixData.value));
                txtPane.add(new JTextField("", 20));
                panel.add(txtPane);
            }
            break;
            case Res.MIX_RADIO: {
                JPanel txtPane = new JPanel();
                txtPane.setLayout(new BoxLayout(txtPane, BoxLayout.Y_AXIS));

                cardPane = new JPanel(new CardLayout());
                cardPane.add(buildListCheckboxPanel(selectedMixData.true_value), "Yes");
                cardPane.add(buildListCheckboxPanel(selectedMixData.false_value), "No");

                txtPane.add(buildRadioOptionPanel(selectedMixData.value));
                txtPane.add(cardPane);

                panel.add(txtPane);
            }
            break;
            case Res.MIX_EMPTY: {
                JLabel jLabel = new JLabel();
                jLabel.setText(" ");
                panel.add(jLabel);
            }
            break;
            default: // Note, Warning
                break;
        }

        panel.revalidate();
        panel.repaint();
    }

    static class BooleanTableModel extends AbstractTableModel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        String[] columns;
        Object[][] data;

        public BooleanTableModel(boolean isOpen) {

            columns = isOpen ? Res.OPEN_HEADERS : Res.CLOSE_HEADERS;
            data = isOpen ? Res.OPEN_DATA : Res.CLOSE_DATA;
        }

        public int getRowCount() {
            return data.length;
        }

        public int getColumnCount() {
            return columns.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 2; // Only the first column in the table should be editable
        }

        // This method is used by the JTable to define the default
        // renderer or editor for each cell. For example if you have
        // a boolean data it will be rendered as a checkbox. A
        // number value is right aligned.
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 2)
                return Boolean.class;

            return String.class;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                data[rowIndex][columnIndex] = data[rowIndex][columnIndex] == Boolean.FALSE ? Boolean.TRUE
                        : Boolean.FALSE;
            }

            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object property = ((JComponent) e.getSource()).getClientProperty("id");
        if (property instanceof Integer) {
            int radioType = ((Integer) property);
            CardLayout cl = (CardLayout) (cardPane.getLayout());
            cl.show(cardPane, radioType == 0 ? "Yes" : "No");
        }
    }
}
