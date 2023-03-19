
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import com.google.gson.Gson;

import static javax.swing.JOptionPane.showMessageDialog;


public class DetailFrame extends JFrame implements TreeSelectionListener, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 2L;

    public static final int num_checkBox = 39;
    public static final int num_inputField = 5;
    public static final int num_doneSection = 7;

    private JScrollPane htmlView;
    private JTree tree;
    private JPanel cardPane;
    private DefaultTreeCellRenderer cellRenderer;

    private Color selected_color = Color.yellow;
    private Color done_color = Color.lightGray;
    private Color sel_done_color = Color.gray;


    Map<String, String> checkedList = new HashMap<String, String>();
    Map<String, String> inputList = new HashMap<String, String>();
    Map<String, String> doneList = new HashMap<String, String>();


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
        File out = new File("out.txt");
        if (out.exists()) {
            try {
                fs = new Scanner(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String checkstr = null;
            String inputstr = null;
            String donestr = null;
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                String[] t = line.split("-");
                checkstr = t[0];
                inputstr = t[1];
                donestr = t[2];

            }
            Gson gson = new Gson();
            this.checkedList = gson.fromJson(checkstr, Map.class);
            this.inputList = gson.fromJson(inputstr, Map.class);
            this.doneList = gson.fromJson(donestr, Map.class);
            for (int i = 0; i < this.CLOSE_DATA.length; i++) {
                this.CLOSE_DATA[i][2] = this.checkedList.get(this.CLOSE_DATA[i][0]).equals("checked") ? Boolean.TRUE : Boolean.FALSE;
            }
            for (int i = 0; i < this.OPEN_DATA.length; i++) {
                this.OPEN_DATA[i][2] = this.checkedList.get(this.OPEN_DATA[i][0]).equals("checked") ? Boolean.TRUE : Boolean.FALSE;
            }
        } else {
            //
            for (int i = 0; i < this.num_checkBox - this.CLOSE_DATA.length - this.OPEN_DATA.length; i++) {
                this.checkedList.put(String.valueOf(401 + i), "unchecked");
            }
            for (int i = 0; i < this.num_inputField; i++) {
                this.inputList.put(String.valueOf(501 + i), "0");
            }
            for (int i = 0; i < this.num_doneSection; i++) {
                this.doneList.put(String.valueOf(601 + i), "x");
            }
            for (int i = 0; i < this.CLOSE_DATA.length; i++) {
                this.CLOSE_DATA[i][2] = Boolean.FALSE;
            }
            for (int i = 0; i < this.OPEN_DATA.length; i++) {
                this.OPEN_DATA[i][2] = Boolean.FALSE;
            }
        }
    }

    private final Icon icon_leaf = createImageIcon("/resources/leaf.png");
    private final Icon icon_done = createImageIcon("/resources/done.png");
    private final Icon icon_req = createImageIcon("/resources/req.png");

    // Returns an ImageIcon, or null if the path was invalid.
    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = DetailFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL) {
            };
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void buildUI() {
        readData();
        // Create the nodes.
        Map<String, String> doneLists = this.doneList;
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

        cellRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Color getBackgroundSelectionColor() {
                return selected_color;
            }

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean focus) {
                JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, focus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                String s = node.getUserObject().toString();
                if (node.getUserObject() instanceof MixData) {

                    if (selected) {
                        c.setBackground(selected_color);
                        c.setOpaque(true);
                    } else {
                        c.setBackground(null);
                        c.setOpaque(true);
                    }

                    setLeafIcon(icon_leaf);

                    if (s.equals("4.4 Approvals & Notifications"))
                        if (doneLists.get("601").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else
                            setLeafIcon(icon_req);

                    if (s.equals("4.5 Preliminary Actions"))
                        if (doneLists.get("602").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else
                            setLeafIcon(icon_req);

                    if (s.equals("4.6 Field Preparations"))
                        if (doneLists.get("603").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else
                            setLeafIcon(icon_req);

                    if (s.equals("5.1 Preparation of Mix Tank for Unloading"))
                        if (doneLists.get("604").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else setLeafIcon(icon_req);

                    if (s.equals("5.2 Mix Tank Unloading"))
                        if (doneLists.get("605").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else setLeafIcon(icon_req);

                    if (s.equals("5.3 Completion of Mix Tank Unloading"))
                        if (doneLists.get("606").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else setLeafIcon(icon_req);

                    if (s.equals("11.0 MIX TANK LOW TEMPERATURE"))
                        if (doneLists.get("607").equalsIgnoreCase("done")) {
                            if (selected) c.setBackground(sel_done_color);
                            else c.setBackground(done_color);

                            setLeafIcon(icon_done);
                        } else setLeafIcon(icon_req);
                }
                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                return this;
            }
        };

        tree.setCellRenderer(cellRenderer);

        htmlView = new JScrollPane(new JPanel());
        displayValue((MixData) Res.MIX_INFO[0]);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButtonWithID jNextBtn = new JButtonWithID("Next >", 100);
        jNextBtn.addActionListener(this);
        JButtonWithID jPrevBtn = new JButtonWithID("< Prev", 101);
        jPrevBtn.addActionListener(this);
        buttonPanel.add(jPrevBtn);
        buttonPanel.add(jNextBtn);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250);
        splitPane.setPreferredSize(new Dimension(500, 300));

        JPanel jPanel1 = new JPanel(new BorderLayout());
        jPanel1.add(splitPane);

        jPanel.add(jPanel1);
//        jPanel.add(buttonPanel);

        // Add the split pane to this panel.
        setContentPane(jPanel);
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
        Map<String, String> checkedLists = this.checkedList;

        ItemListener itemCheckBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() instanceof JCheckBoxWithID) {
                    JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
                    if (checkBoxWithID.isSelected()) {
                        checkedLists.replace(String.valueOf(checkBoxWithID.getId()), "checked");
                    } else {
                        checkedLists.replace(String.valueOf(checkBoxWithID.getId()), "unchecked");
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
                    checkBoxWithID.setSelected(this.checkedList.get(String.valueOf(item.id)).equals("checked"));
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
        Map<String, String> checkedLists = this.checkedList;
        Map<String, String> inputLists = this.inputList;

        ItemListener itemCheckBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() instanceof JCheckBoxWithID) {
                    JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
                    if (checkBoxWithID.isSelected()) {
                        checkedLists.replace(String.valueOf(checkBoxWithID.getId()), "checked");
                    } else {
                        checkedLists.replace(String.valueOf(checkBoxWithID.getId()), "unchecked");
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
                            checkBoxWithID.setSelected(Objects.equals(this.checkedList.get(String.valueOf(item.id)), "checked"));
                            sub_panel.add(checkBoxWithID);
                            main_panel.add(sub_panel);

                        }
                        break;
                        case Res.MIX_TEXTINPUT: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BorderLayout());

                            JLabel jLabel = new JLabel(item.value);
                            sub_panel.add(jLabel, BorderLayout.NORTH);
                            JTextFieldWithID textfieldWithID = new JTextFieldWithID(String.valueOf(this.inputList.get(String.valueOf(item.id))), item.id);
                            textfieldWithID.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
                                inputLists.replace(String.valueOf(item.id), textfieldWithID.getText());
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
                        case Res.MIX_BUTTON: {
                            JPanel sub_panel = new JPanel();
                            sub_panel.setLayout(new BorderLayout());
                            JButtonWithID btnDone = new JButtonWithID("Done", item.id);

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
                checkBoxWithID.setSelected(Objects.equals(this.checkedList.get(String.valueOf(selectedMixData.id)), "checked"));
                sub_panel.add(checkBoxWithID);

                JButtonWithID btnDone = new JButtonWithID("Done", selectedMixData.id + 200);
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
        if ((JComponent) e.getSource() instanceof JButtonWithID) {

            if (((JButtonWithID) ((JComponent) e.getSource())).getId() == 100) {
                // next button
                System.out.println("Next");
            } else if (((JButtonWithID) ((JComponent) e.getSource())).getId() == 101) {
                // prev button
                System.out.println("Prev");
            } else {
                int id = ((JButtonWithID) ((JComponent) e.getSource())).getId();
                if (this.doneList.get(String.valueOf(id - 1)) == null || this.doneList.get(String.valueOf(id - 1)).equals("Done")) {
                    this.doneList.put(String.valueOf(((JButtonWithID) ((JComponent) e.getSource())).getId()), "Done");
                } else {
                    showMessageDialog(null, "Previous sections should be completed.");
                }
            }
        }
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

class JButtonWithID extends JButton {
    /* I use Integer but the id could be whatever you want, the concept is the same */
    private Integer _id;

    public JButtonWithID(String text, Integer id) {
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