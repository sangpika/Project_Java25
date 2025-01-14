package Library;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {

  private JPanel panel1, panel2, panel3;

  private static JTabbedPane tabbedPane;

  private JButton buttonSearch, muon_button, tra_button, add_button, update_button, delete_button,
      register_button, booksChart_button, edit_button, delete_account;

  protected static JTextField nameField, seachField, authorField, idSach_MuonTra, idDocgiaField, idBook, accountid, ten, cccd, email;


  protected static DefaultTableModel tableModel = new DefaultTableModel(
      new Object[]{"ID Sách", "Tên Sách", "Tác Giả"}, 0) {
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  };
  protected static JTable tableBook = new JTable(tableModel);


  protected static DefaultTableModel tableModel2 = new DefaultTableModel(
      new Object[]{"ID Độc giả", "Tên", "CCCD", "Email"}, 0) {
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  };
  protected static JTable tableDG = new JTable(tableModel2);

  protected static DefaultTableModel tableMuonsach = new DefaultTableModel(
      new Object[]{"ID Độc giả", "ID Sách", "Ngày mượn", "Ngày trả", "Ngày trả dự kiến"}, 0) {
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  };
  JTable tableMuon = new JTable(tableMuonsach);


  public GUI() {
    JFrame frame = new JFrame("Quản Lí Thư Viện");
    frame.setSize(1280, 800);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout(2, 2));

    //font
    Font custumerFont = new Font(".VnClarendonNormal", Font.BOLD, 16);

    // panel tim kiem
    panel1 = new JPanel(new FlowLayout());
    panel1.setBorder(BorderFactory.createLineBorder(new Color(146, 143, 143, 243), 1));
    panel1.setBackground(new Color(215, 249, 250, 128));

    seachField = new JTextField(50);
    seachField.setPreferredSize(new Dimension(400, 25));

    buttonSearch = new JButton("Tìm kiếm");
    buttonSearch.setBackground(new Color(7, 87, 91, 255));
    buttonSearch.setForeground(new Color(229, 250, 229, 255));
    buttonSearch.setFont(custumerFont);

    register_button = new JButton("Đăng ký độc giả");
    register_button.setBackground(new Color(7, 87, 91, 255));
    register_button.setForeground(new Color(229, 250, 229, 255));
    register_button.setFont(custumerFont);

    booksChart_button = new JButton("Thống kê sách");
    booksChart_button.setBackground(new Color(7, 87, 91, 255));
    booksChart_button.setForeground(new Color(229, 250, 229, 255));
    booksChart_button.setFont(custumerFont);

    panel1.add(new JLabel("Tên sách, tác giả, id"));
    panel1.add(seachField);
    panel1.add(buttonSearch);
    panel1.add(register_button);
    panel1.add(booksChart_button);

    booksChart_button.addActionListener(e -> {
      SwingUtilities.invokeLater(() -> {
        BorrowedBooksChart chart = new BorrowedBooksChart();
        chart.setVisible(true);
      });
    });
    register_button.addActionListener(e -> new Register());

    // panel phía đông
    panel2 = new JPanel(new GridBagLayout());
    panel2.setBorder(BorderFactory.createLineBorder(new Color(146, 143, 143, 243), 1));
    panel2.setBackground(new Color(215, 249, 250, 128));

    nameField = new JTextField(30);
    authorField = new JTextField(30);
    idBook = new JTextField(30);
    idSach_MuonTra = new JTextField(30);
    idDocgiaField = new JTextField(30);

    nameField.setPreferredSize(new Dimension(30, 30));
    authorField.setPreferredSize(new Dimension(30, 30));
    idBook.setPreferredSize(new Dimension(30, 30));
    idSach_MuonTra.setPreferredSize(new Dimension(30, 30));
    idDocgiaField.setPreferredSize(new Dimension(30, 30));

    add_button = new JButton("Thêm sách");
    update_button = new JButton("Cập nhật");
    delete_button = new JButton("Xoá");
    muon_button = new JButton("Mượn sách");
    tra_button = new JButton("Trả Sách");
    JButton reload = new JButton("Reload");

    add_button.setBackground(new Color(7, 87, 91, 255));
    add_button.setForeground(new Color(229, 250, 229, 255));

    update_button.setBackground(new Color(7, 87, 91, 255));
    update_button.setForeground(new Color(229, 250, 229, 255));

    delete_button.setBackground(new Color(7, 87, 91, 255));
    delete_button.setForeground(new Color(229, 250, 229, 255));

    muon_button.setBackground(new Color(7, 87, 91, 255));
    muon_button.setForeground(new Color(229, 250, 229, 255));

    tra_button.setBackground(new Color(7, 87, 91, 255));
    tra_button.setForeground(new Color(229, 250, 229, 255));

    reload.setBackground(new Color(237, 246, 255, 255));
    reload.setForeground(new Color(2, 2, 2, 255));

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 5, 5, 5);
    c.anchor = GridBagConstraints.WEST;
    c.gridx = 0;
    c.gridy = 0;
    panel2.add(new JLabel("Tên sách"), c);
    c.gridy = 1;
    panel2.add(nameField, c);
    c.gridy = 2;
    panel2.add(new JLabel("ID Sách"), c);
    c.gridy = 3;
    panel2.add(idBook, c);
    c.gridy = 4;
    panel2.add(new JLabel("Tên tác giả"), c);
    c.gridy = 5;
    panel2.add(authorField, c);
    c.gridy = 6;

    JPanel temp = new JPanel();
    c.anchor = GridBagConstraints.EAST;
    temp.add(add_button);
    temp.add(update_button);
    temp.add(delete_button);
    panel2.add(temp, c);
    c.gridy = 7;
    c.anchor = GridBagConstraints.WEST;
    panel2.add(new JLabel("ID Sách muốn mượn hoặc trả"), c);
    c.gridy = 8;
    panel2.add(idSach_MuonTra, c);
    c.gridy = 9;
    panel2.add(new JLabel("ID Độc giả"), c);
    c.gridy = 10;
    panel2.add(idDocgiaField, c);
    c.gridy = 11;

    JPanel temp2 = new JPanel();
    c.anchor = GridBagConstraints.EAST;
    temp2.add(muon_button);
    temp2.add(tra_button);
    temp2.add(reload);
    panel2.add(temp2, c);
    c.gridy = 12;
    c.insets = new Insets(5, 5, 5, 10);
    panel2.add(reload, c);

    JPanel temp3 = new JPanel();
    c.gridy = 13;
    c.weighty = 1;
    panel2.add(temp3, c);

    // table sach
    custumerFont = new Font(".VnClarendonNormal", Font.PLAIN, 16);
    tableBook.setBackground(new Color(255, 255, 255, 255));
    tableBook.setFont(custumerFont);
    tableBook.setRowHeight(24);
    JScrollPane scrollPane = new JScrollPane(tableBook);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // table độc giả
    tableDG.setBackground(new Color(255, 255, 255, 255));
    custumerFont = new Font(".VnClarendonNormal", Font.PLAIN, 14);
    tableDG.setFont(custumerFont);
    tableDG.setRowHeight(24);
    JScrollPane scrollPane2 = new JScrollPane(tableDG);
    scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // table muợn sách
    tableMuon.setBackground(new Color(255, 255, 255, 255));
    tableMuon.setFont(custumerFont);
    tableMuon.setRowHeight(24);
    JScrollPane scrollPane3 = new JScrollPane(tableMuon);
    scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // tab
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBackground(new Color(7, 87, 91, 255));
    tabbedPane.setForeground(new Color(229, 250, 229, 255));

    tabbedPane.add("Sách", scrollPane);
    tabbedPane.add("Độc giả", scrollPane2);
    tabbedPane.add("Mượn trả sách", scrollPane3);

    // panel3 của tab độc giả
    panel3 = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    edit_button = new JButton("Cập nhật thông tin");
    delete_account = new JButton("Xoá độc giả");

    edit_button.setPreferredSize(new Dimension(200, 30));
    delete_account.setPreferredSize(new Dimension(200, 30));

    edit_button.setBackground(new Color(7, 87, 91, 255));
    edit_button.setForeground(new Color(229, 250, 229, 255));
    delete_account.setBackground(new Color(7, 87, 91, 255));
    delete_account.setForeground(new Color(229, 250, 229, 255));

    accountid = new JTextField(30);
    ten = new JTextField(30);
    cccd = new JTextField(30);
    email = new JTextField(30);

    accountid.setPreferredSize(new Dimension(30, 30));
    ten.setPreferredSize(new Dimension(30, 30));
    cccd.setPreferredSize(new Dimension(30, 30));
    email.setPreferredSize(new Dimension(30, 30));

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel3.add(new JLabel("ID"), gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    panel3.add(accountid, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel3.add(new JLabel("Tên"), gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel3.add(ten, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel3.add(new JLabel("CCCD"), gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    panel3.add(cccd, gbc);
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel3.add(new JLabel("E-mail"), gbc);
    gbc.gridx = 1;
    gbc.gridy = 3;
    panel3.add(email, gbc);
    gbc.gridx = 2;
    gbc.gridy = 0;
    panel3.add(edit_button, gbc);
    gbc.gridx = 2;
    gbc.gridy = 1;
    panel3.add(delete_account, gbc);

    // tìm kiếm
    buttonSearch.addActionListener(e -> {
      ActionButton.search();
    });

    // thêm sách
    add_button.addActionListener(e -> {
      ActionButton.addButton();
    });

    // cập nhật sách
    update_button.addActionListener(e -> {
      ActionButton.updateButton();
    });

    // cập nhật độc giả
    edit_button.addActionListener(e -> {
      ActionButton.updateButton2();
    });

    // xoá sách
    delete_button.addActionListener(e -> {
      ActionButton.deleteButton();
    });

    // xoá độc giả
    delete_account.addActionListener(e -> {
      ActionButton.deleteButton1();
    });

    // mượn sách
    muon_button.addActionListener(e -> {
      boolean can = ActionButton.check();
      if (can) {
        ActionButton.muonsach();
      } else {
        JOptionPane.showMessageDialog(null, " Độc giả này đã mượn quá 3 cuốn sách trong 1 tuần vừa qua");
      }
    });

    // trả sách
    tra_button.addActionListener(e -> {
      ActionButton.traSach();
    });

    // làm mới dữ liệu từ database
    reload.addActionListener(e -> {
      ActionButton.reloadBook();
    });

    // hành động với dữ lệu trong bảng sách
    tableBook.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        int rowSelect = tableBook.getSelectedRow();
        if (rowSelect != -1) {
          idBook.setText(tableModel.getValueAt(rowSelect, 0).toString());
          nameField.setText(tableModel.getValueAt(rowSelect, 1).toString());
          authorField.setText(tableModel.getValueAt(rowSelect, 2).toString());
        }
      }
    });

    // hành động với dữ liệu trong bảng độc giả
    tableDG.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        int rowSelect = tableDG.getSelectedRow();
        if (rowSelect != -1) {
          accountid.setText(tableModel2.getValueAt(rowSelect, 0).toString());
          ten.setText(tableModel2.getValueAt(rowSelect, 1).toString());
          cccd.setText(tableModel2.getValueAt(rowSelect, 2).toString());
          email.setText(tableModel2.getValueAt(rowSelect, 3).toString());
        }
      }
    });
    // hành động với tab
    tabbedPane.addChangeListener(e -> {
      int selectedIndex = tabbedPane.getSelectedIndex();
      if (selectedIndex >= 0) {
        switch (selectedIndex) {
          case 0 -> panel3.setVisible(false);
          case 1 -> panel3.setVisible(true);
          case 2 -> panel3.setVisible(false);
          default -> panel3.setVisible(false);
        }
      }
    });

    // add cac thanh phan vao frame
    frame.add(panel1, BorderLayout.NORTH);
    frame.add(panel2, BorderLayout.EAST);
    frame.add(panel3, BorderLayout.SOUTH);
    panel3.setVisible(false);
    frame.add(tabbedPane, BorderLayout.CENTER);
    ActionButton.reloadBook();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new GUI();
  }
}
