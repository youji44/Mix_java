
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    public ArrayList<String> checkedList = new ArrayList<String>();
    public ArrayList<String> inputList = new ArrayList<String>();
    public ArrayList<String> radioList = new ArrayList<String>();
    public static Object[][] CLOSE_DATA = {
            {"MIXXV100UI", "Closed", Boolean.FALSE},
            {"MIXXV101UI", "Closed", Boolean.FALSE},
            {"MIXXV102UI", "Closed", Boolean.FALSE},
            {"MIXXV105UI", "Closed", Boolean.FALSE},
            {"MIXXV106UI", "Closed", Boolean.FALSE},
            {"MIXXV108UI", "Closed", Boolean.FALSE},
    };

    public static Object[][] OPEN_DATA = {
            {"MIXXV103UI", "Open", Boolean.FALSE},
            {"MIXXV104UI", "Open", Boolean.FALSE},
            {"MIXXV107UI", "Open", Boolean.FALSE},
    };

    public void readData() {
        Scanner fs = null;
        try {
            fs = new Scanner(new File("out.txt"));
        } catch (FileNotFoundException e1) {
            System.out.println("out.txt" + "not found");
        }
        String checkstr = null;
        String inputstr = null;
        while (fs.hasNextLine()) {
            String line = fs.nextLine();
            String[] t = line.split("-");
            checkstr = t[0];
            inputstr = t[1];
            checkstr = checkstr.replace("[", "");
            checkstr = checkstr.replace("]", "");
            inputstr = inputstr.replace("[", "");
            inputstr = inputstr.replace("]", "");
        }
        for (String str : checkstr.split(", ")) {
            if (str.equals("true")) {
                this.checkedList.add("true");
            } else {
                this.checkedList.add("false");
            }
        }
        for (String str : inputstr.split(", ")) {
            if (str == "") {
                this.inputList.add("*");
            } else {
                this.inputList.add(str);
            }
        }
        for (int i = 0; i < 6; i++) {
            this.CLOSE_DATA[i][2] = this.checkedList.get(29 + i) == "true" ? Boolean.TRUE : Boolean.FALSE;
        }
        for (int i = 0; i < 3; i++) {
            this.OPEN_DATA[i][2] = this.checkedList.get(35 + i) == "true" ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public void buildUI() {
        readData();
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
        ArrayList<String> checkedLists = this.checkedList;

        ItemListener itemCheckBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() instanceof JCheckBoxWithID) {
                    JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
                    if (checkBoxWithID.isSelected()) {
                        checkedLists.set(checkBoxWithID.getId() - 401, "true");
                    } else {
                        checkedLists.set(checkBoxWithID.getId() - 401, "false");
                    }
                }
            }
        };

        for (int i = 0; i < data.length; i++) {
            MixData item = (MixData) data[i];
            switch (item.type) {
                case Res.MIX_CHECKBOX:
                    JCheckBoxWithID checkBoxWithID = new JCheckBoxWithID(item.value, item.id);
                    checkBoxWithID.addItemListener(itemCheckBoxListener);
                    checkBoxWithID.setSelected(this.checkedList.get(item.id - 401) == "true" ? true : false);
                    panel.add(checkBoxWithID);
                    break;
                case Res.MIX_STRING:
                    panel.add(new JLabel(item.value));
                    break;
                case Res.MIX_EMPTY:
                    panel.add(new JLabel(" "));
                    break;
                default:
                    break;
            }
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
        ArrayList<String> checkedLists = this.checkedList;
        ArrayList<String> inputLists = this.inputList;

        ItemListener itemCheckBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() instanceof JCheckBoxWithID) {
                    JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
                    if (checkBoxWithID.isSelected()) {
                        checkedLists.set(checkBoxWithID.getId() - 401, "true");
                    } else {
                        checkedLists.set(checkBoxWithID.getId() - 401, "false");
                    }
                }
            }
        };

        JPanel panel = ((JPanel) htmlView.getViewport().getView());
        panel.removeAll();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        switch (selectedMixData.type) {
            case Res.MIX_STRING: {
                panel.add(new JLabel(selectedMixData.value));
            }
            break;
            case Res.MIX_BUTTON: {
                JButton btnDone = new JButton("Done");
                btnDone.putClientProperty("id", selectedMixData.id);
                btnDone.addActionListener(this);
                panel.add(btnDone);
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
                            sub_panel.setLayout(new BorderLayout());

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
                            JCheckBoxWithID checkBoxWithID = new JCheckBoxWithID(item.value, item.id);
                            checkBoxWithID.addItemListener(itemCheckBoxListener);
                            checkBoxWithID.setSelected(Objects.equals(this.checkedList.get(item.id - 401), "true"));
                            sub_panel.add(checkBoxWithID);
                            main_panel.add(sub_panel);

                        }
                        break;
                        case Res.MIX_TEXTINPUT: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BorderLayout());

                            JLabel jLabel = new JLabel(item.value);
                            sub_panel.add(jLabel, BorderLayout.NORTH);
                            JTextFieldWithID textfieldWithID = new JTextFieldWithID(this.inputList.get(item.id - 501), item.id);
                            textfieldWithID.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
                                inputLists.set(item.id - 501, textfieldWithID.getText());
                            });
                            sub_panel.add(textfieldWithID, BorderLayout.SOUTH);
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
                        case Res.MIX_BUTTON:{
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BorderLayout());

                            JButton btnDone = new JButton("Done");
                            btnDone.putClientProperty("id", item.key);
                            btnDone.addActionListener(this);
                            sub_panel.add(btnDone);

                            main_panel.add(sub_panel);
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
                JPanel sub_panel = new JPanel();
                sub_panel.setLayout(new BoxLayout(sub_panel, BoxLayout.Y_AXIS));

                JCheckBoxWithID checkBoxWithID = new JCheckBoxWithID(selectedMixData.value, selectedMixData.id);
                checkBoxWithID.addItemListener(itemCheckBoxListener);
                checkBoxWithID.setSelected(Objects.equals(this.checkedList.get(selectedMixData.id - 401), "true"));
                sub_panel.add(checkBoxWithID);

                JButton btnDone = new JButton("Done");
                btnDone.putClientProperty("id", selectedMixData.key);
                btnDone.addActionListener(this);
                sub_panel.add(btnDone);
                panel.add(sub_panel);
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
            data = isOpen ? OPEN_DATA : CLOSE_DATA;
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

    @FunctionalInterface
    public interface SimpleDocumentListener extends DocumentListener {
        void update(DocumentEvent e);

        @Override
        default void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        default void removeUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        default void changedUpdate(DocumentEvent e) {
            update(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object property = ((JComponent) e.getSource()).getClientProperty("id");
        if (property instanceof Integer) {
            int radioType = ((Integer) property);
            CardLayout cl = (CardLayout) (cardPane.getLayout());
            cl.show(cardPane, radioType == 0 ? "Yes" : "No");
        }
    }
}

class JCheckBoxWithID extends JCheckBox {
    /* I use Integer but the id could be whatever you want, the concept is the same */
    private Integer _id;

    public JCheckBoxWithID(String text, Integer id) {
        super(text);
        _id = id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public Integer getId() {
        return _id;
    }
}

class JTextFieldWithID extends JTextField {
    /* I use Integer but the id could be whatever you want, the concept is the same */
    private Integer _id;

    public JTextFieldWithID(String text, Integer id) {
        super(text);
        _id = id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public Integer getId() {
        return _id;
    }
}