package Library;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Register {

  JLabel idLabel, nameLabel, emailLabel, cccdLabel;
  JTextField idTextField, nameTextField, emailTextField, cccdTextField;
  JButton registerButton;

  public Register() {
    JFrame registerFrame = new JFrame("Đăng ký độc giả ");
    registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    registerFrame.setSize(500, 600);
    registerFrame.setLocationRelativeTo(null);
    registerFrame.setLayout(new BorderLayout());

    Font custumerFont = new Font(".VnClarendonNormal", Font.BOLD, 17);

    ImageIcon icon = new ImageIcon("src/Library/platform.png");
    Image image = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
    JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
    imageLabel.setPreferredSize(new Dimension(500, 180));
    imageLabel.setIcon(new ImageIcon(image));

    idLabel = new JLabel("ID Độc giả");
    emailLabel = new JLabel("Email");
    cccdLabel = new JLabel("CCCD");
    nameLabel = new JLabel("Tên");

    idLabel.setFont(custumerFont);
    emailLabel.setFont(custumerFont);
    cccdLabel.setFont(custumerFont);
    nameLabel.setFont(custumerFont);

    idTextField = new JTextField(30);
    nameTextField = new JTextField(30);
    emailTextField = new JTextField(30);
    cccdTextField = new JTextField(30);

    idTextField.setPreferredSize(new Dimension(30, 30));
    nameTextField.setPreferredSize(new Dimension(30, 30));
    emailTextField.setPreferredSize(new Dimension(30, 30));
    cccdTextField.setPreferredSize(new Dimension(30, 30));

    registerButton = new JButton("Đăng ký");
    registerButton.setFont(custumerFont);
    registerButton.setBackground(new Color(7, 87, 91));
    registerButton.setForeground(new Color(229, 250, 229));
    registerButton.addActionListener(e -> {
      String id = idTextField.getText().trim();
      String name = nameTextField.getText().trim();
      String email = emailTextField.getText().trim();
      String cccd = cccdTextField.getText().trim();
      try {
        Connection con = DatabaseConnection.getConnection();
        String query = "INSERT INTO DOCGIA (IDDOCGIA, TEN, CCCD, EMAIL) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        if (!id.isEmpty() && !name.isEmpty() && !email.isEmpty() && !cccd.isEmpty()) {
          ps.setString(1, id);
          ps.setString(2, name);
          ps.setString(3, cccd);
          ps.setString(4, email);
          ps.executeUpdate();

          idTextField.setText("");
          nameTextField.setText("");
          emailTextField.setText("");
          cccdTextField.setText("");
          ActionButton.reloadBook();
          JOptionPane.showMessageDialog(null, "Tạo thành công");
        } else {
          JOptionPane.showMessageDialog(null, "Nhập thông tin đầy đủ");
        }
        ps.close();
        con.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    });

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setPreferredSize(new Dimension(500, 600));
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(10, 10, 10, 10);
    c.gridx = 0;
    c.gridy = 0;
    panel.add(idLabel, c);
    c.gridx = 1;
    panel.add(idTextField, c);
    c.gridx = 0;
    c.gridy = 1;
    panel.add(nameLabel, c);
    c.gridx = 1;
    panel.add(nameTextField, c);
    c.gridx = 0;
    c.gridy = 2;
    panel.add(emailLabel, c);
    c.gridx = 1;
    panel.add(emailTextField, c);
    c.gridx = 0;
    c.gridy = 3;
    panel.add(cccdLabel, c);
    c.gridx = 1;
    panel.add(cccdTextField, c);
    c.gridy = 4;
    c.anchor = GridBagConstraints.EAST;
    panel.add(registerButton, c);
    c.gridy = 5;
    JPanel tempPanel = new JPanel();
    c.weighty = 1;
    panel.add(tempPanel, c);

    registerFrame.add(panel, BorderLayout.CENTER);
    registerFrame.add(imageLabel, BorderLayout.NORTH);
    registerFrame.setVisible(true);
  }
}
