package Library;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class ActionButton {

  // tìm kiếm
  public static void search() {
    String seach =
        "SELECT * FROM ListBooks WHERE NAME LIKE ?" + " OR IDBOOK LIKE ?" + "OR AUTHOR LIKE ?";
    String sText = GUI.seachField.getText().trim();
    try {
      if (sText.isEmpty()) {
        return;
      }
      GUI.tableModel.setRowCount(0);
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(seach);
      ps.setString(1, "%" + sText + "%");
      ps.setString(2, "%" + sText + "%");
      ps.setString(3, "%" + sText + "%");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String bookId = rs.getString("IDBOOK");
        String bookName = rs.getString("NAME");
        String author = rs.getString("AUTHOR");
        GUI.tableModel.addRow(new Object[]{bookId, bookName, author});
      }
      rs.close();
      ps.close();
      con.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Lỗi kết nối" + e.getMessage());
    }
  }

  // thêm sách
  public static void addButton() {
    String idText = GUI.idBook.getText().trim();
    String nameText = GUI.nameField.getText().trim();
    String authorText = GUI.authorField.getText().trim();
    GUI.idBook.setText("");
    GUI.nameField.setText("");
    GUI.authorField.setText("");
    if (!nameText.isEmpty() && !authorText.isEmpty() && !idText.isEmpty()) {
      try {
        Connection con = DatabaseConnection.getConnection();
        String check = "SELECT COUNT(*) FROM ListBooks WHERE IDBOOK = ?";
        PreparedStatement ps = con.prepareStatement(check);
        ps.setString(1, idText);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count > 0) {
          JOptionPane.showMessageDialog(null, "ID Sách trùng lặp!");
        } else {
          String query = "INSERT INTO ListBooks (IDBOOK, NAME, AUTHOR) VALUES (?, ?, ?)";
          ps = con.prepareStatement(query);
          ps.setString(1, idText);
          ps.setString(2, nameText);
          ps.setString(3, authorText);
          // Thực thi câu lệnh
          int rowsInserted = ps.executeUpdate();
          if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Thêm sách thành công");
          }
          ps.close();
          con.close();
          ActionButton.reloadBook();
        }
      } catch (SQLException ex) {
        System.err.printf("Lỗi kết nối không thành công " + ex.getErrorCode());
      }
    } else {
      JOptionPane.showMessageDialog(null, "Thông tin chưa đầy đủ");
    }
  }

  // xoá sách
  public static void deleteButton() {
    String idText = GUI.idBook.getText().trim();
    String idDelete = null;
    if (!idText.isEmpty()) {
      idDelete = idText;
    }
    int rowSelect = GUI.tableBook.getSelectedRow();
    if (rowSelect >= 0) {
      idDelete = (String) GUI.tableBook.getValueAt(rowSelect, 0);
    }
    if (idDelete == null || idDelete.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Không có ID sách hợp lệ để xoá");
      return;
    }
    String query = "DELETE FROM ListBooks WHERE IDBOOK = ?";
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(query);
      ps.setString(1, idDelete);
      int rowsDeleted = ps.executeUpdate();
      if (rowsDeleted > 0) {
        JOptionPane.showMessageDialog(null, "Xoá sách thành công");
      }
      ps.close();
      con.close();
      ActionButton.reloadBook();
      GUI.idBook.setText("");
      GUI.nameField.setText("");
      GUI.authorField.setText("");
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  // xoá độc giả
  public static void deleteButton1() {
    int rowSelect = GUI.tableDG.getSelectedRow();
    String idDelete = null;
    if (rowSelect >= 0) {
      idDelete = (String) GUI.tableDG.getValueAt(rowSelect, 0);
    } else {
      return;
    }
    String query = "DELETE FROM DOCGIA WHERE IDDOCGIA = ?";
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(query);
      ps.setString(1, idDelete);
      int rowsDeleted = ps.executeUpdate();
      if (rowsDeleted > 0) {
        JOptionPane.showMessageDialog(null, "Xoá độc giả thành công!");
        ActionButton.reloadBook();
      } else {
        JOptionPane.showMessageDialog(null,
            "Chưa chọn độc giả cần xoá hoặc độc giả không tồn tại! ");
      }
      ps.close();
      con.close();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Lỗi" + ex.getMessage());
    }
  }

  // cập nhật sách
  public static void updateButton() {
    int rowSelect = GUI.tableBook.getSelectedRow();
    if (rowSelect < 0) {
      JOptionPane.showMessageDialog(null, "Chọn lại hàng cần thay đổi");
      return;
    }
    String oldID = (String) GUI.tableBook.getValueAt(rowSelect, 0);
    String newIdText = GUI.idBook.getText().trim();
    String newNameText = GUI.nameField.getText().trim();
    String newAuthorText = GUI.authorField.getText().trim();
    if (newIdText.isEmpty() || newNameText.isEmpty() || newAuthorText.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Nhập đầy đủ thông tin");
      return;
    }
    try {
      Connection con = DatabaseConnection.getConnection();
      if (!newIdText.equals(oldID)) {
        String check = "SELECT COUNT(*) FROM ListBooks WHERE IDBOOK = ?";
        PreparedStatement ps = con.prepareStatement(check);
        ps.setString(1, newIdText);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0) {
          JOptionPane.showMessageDialog(null, "ID sách tồn tại");
          rs.close();
          ps.close();
          return;
        }
        rs.close();
        ps.close();
      }

      String updateQuery = "UPDATE ListBooks SET IDBOOK = ?, NAME = ?, AUTHOR = ? WHERE IDBOOK = ?";
      PreparedStatement ps = con.prepareStatement(updateQuery);
      ps.setString(1, newIdText);
      ps.setString(2, newNameText);
      ps.setString(3, newAuthorText);
      ps.setString(4, oldID);
      int rowsUpdated = ps.executeUpdate();
      if (rowsUpdated > 0) {
        JOptionPane.showMessageDialog(null,
            "Cập nhật thành công" + "Nhớ cập nhật sách bên tab mượn trả sách nhé!");
      }
      GUI.nameField.setText("");
      GUI.authorField.setText("");
      GUI.idBook.setText("");
      ps.close();
      con.close();
      ActionButton.reloadBook();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Lỗi rồi bạn ơi!");
    }

  }

  // chỉnh sửa độc giả
  public static void updateButton2() {
    int rowSelect = GUI.tableDG.getSelectedRow();
    String newid = GUI.accountid.getText().trim();
    String oldid = GUI.tableDG.getValueAt(rowSelect, 0).toString();
    String ten = GUI.ten.getText().trim();
    String cccd = GUI.cccd.getText().trim();
    String email = GUI.email.getText().trim();
    String update = "UPDATE DOCGIA SET IDDOCGIA = ?, TEN = ?, CCCD = ?, EMAIL = ? WHERE IDDOCGIA = ?";
    if (newid.isEmpty() || ten.isEmpty() || cccd.isEmpty() || email.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
      return;
    }
    try {
      Connection con = DatabaseConnection.getConnection();
      if (!newid.equals(oldid)) {
        String check = "SELECT COUNT(*) FROM DOCGIA WHERE IDDOCGIA = ?";
        PreparedStatement ps = con.prepareStatement(check);
        ps.setString(1, newid);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0) {
          JOptionPane.showMessageDialog(null, "ID mới trùng lặp");
          rs.close();
          ps.close();
          return;
        }
      }
      PreparedStatement ps = con.prepareStatement(update);
      ps.setString(1, newid);
      ps.setString(2, ten);
      ps.setString(3, cccd);
      ps.setString(4, email);
      ps.setString(5, oldid);
      int rowupdate = ps.executeUpdate();
      if (rowupdate > 0) {
        JOptionPane.showMessageDialog(null, "Cập nhật thành công");
      }
      GUI.accountid.setText("");
      GUI.ten.setText("");
      GUI.cccd.setText("");
      GUI.email.setText("");
      ps.close();
      con.close();
      ActionButton.reloadBook();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
    }
  }

  // Mượn sách
  protected static void muonsach() {
    String id = GUI.idSach_MuonTra.getText().trim();
    String iddg = GUI.idDocgiaField.getText().trim();
    if (iddg.isEmpty() || id.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Nhập đầy đủ thông tin");
      return;
    }
    String checkbook = "SELECT COUNT(*) FROM ListBooks WHERE IDBOOK = ?";
    String checkdg = "SELECT COUNT(*) FROM DOCGIA WHERE IDDOCGIA = ?";
    String query = "INSERT INTO MUON (IDDOCGIA, IDBOOK, NGAYMUON, NGAYTRADUKIEN) VALUES (?, ?, ?, ?)";
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(checkbook);
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int result = rs.getInt(1);
        if (result <= 0) {
          JOptionPane.showMessageDialog(null, "ID Sách không tồn tại");
          return;
        }
      }
      ps = con.prepareStatement(checkdg);
      ps.setString(1, iddg);
      rs = ps.executeQuery();
      if (rs.next()) {
        int result = rs.getInt(1);
        if (result <= 0) {
          JOptionPane.showMessageDialog(null, "ID Độc giả không tồn tại");
          return;
        }
      }
      ps = con.prepareStatement(query);
      ps.setString(1, iddg);
      ps.setString(2, id);
      ps.setDate(3, Date.valueOf(LocalDate.now()));
      ps.setDate(4, Date.valueOf(LocalDate.now().plusDays(7)));
      int rowsUpdated = ps.executeUpdate();
      if (rowsUpdated > 0) {
        JOptionPane.showMessageDialog(null, "Mượn sách thành công ♡");
      }
      rs.close();
      ps.close();
      con.close();
      ActionButton.reloadBook();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Kết nối không thành công" + e.getMessage());
    }
  }

  // trả sách
  protected static void traSach() {
    String id = GUI.idSach_MuonTra.getText().trim();
    String iddg = GUI.idDocgiaField.getText().trim();
    if (iddg.isEmpty() || id.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin sách và độc giả.");
      return;
    }
    String checkBookAndReader = "SELECT COUNT(*) FROM MUON WHERE IDDOCGIA = ? AND IDBOOK = ?";
    String update = "UPDATE MUON SET NGAYTRA = ? WHERE IDBOOK = ? AND IDDOCGIA = ?";
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(checkBookAndReader);
      ps.setString(1, iddg);
      ps.setString(2, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next() && rs.getInt(1) <= 0) {
        JOptionPane.showMessageDialog(null,
            "Thông tin không hợp lệ. Sách chưa được mượn bởi độc giả này.");
        return;
      }
      PreparedStatement updatePs = con.prepareStatement(update);
      updatePs.setDate(1, Date.valueOf(LocalDate.now()));
      updatePs.setString(2, id);
      updatePs.setString(3, iddg);
      int rowsUpdate = updatePs.executeUpdate();
      if (rowsUpdate > 0) {
        JOptionPane.showMessageDialog(null, "Trả sách thành công!");
        ActionButton.reloadBook();
      } else {
        JOptionPane.showMessageDialog(null, "Không thể cập nhật thông tin trả sách");

      }
      ps.close();
      updatePs.close();
      rs.close();
      con.close();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Đã xảy ra một số lỗi trong quá trình trả sách!");
    }

  }

  // CHECK số lần mượn sách trong 1 tuần
  public static boolean check() {
    String check = "SELECT COUNT(*) AS CHECK_COUNT FROM MUON " + "WHERE IDDOCGIA = ? "
        + "AND NGAYMUON BETWEEN CAST(DATEADD(DAY, -7, GETDATE()) AS DATE) "
        + "AND CAST(GETDATE() AS DATE)";
    String newid = GUI.idDocgiaField.getText().trim();
    if (newid.isEmpty()) {
      return false;
    }
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement ps = con.prepareStatement(check);
      ps.setString(1, newid);
      ResultSet rs = ps.executeQuery();
      int count = 0;
      if (rs.next()) {
        count = rs.getInt("CHECK_COUNT");
      }
      return count < 3;
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
    }
    return false;
  }

  // làm mới dữ liệu bảng
  public static void reloadBook() {
    String query = "SELECT * FROM ListBooks ORDER BY NAME";
    String query2 = "SELECT * FROM DOCGIA ORDER BY TEN";
    String query3 = "SELECT * FROM MUON ORDER BY IDBOOK";
    GUI.tableModel.setRowCount(0);
    GUI.tableModel2.setRowCount(0);
    GUI.tableMuonsach.setRowCount(0);
    try {
      Connection con = DatabaseConnection.getConnection();
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String idBook = rs.getString("IDBOOK");
        String name = rs.getString("NAME");
        String author = rs.getString("AUTHOR");
        GUI.tableModel.addRow(new Object[]{idBook, name, author});
      }

      stmt = con.prepareStatement(query2);
      rs = stmt.executeQuery();
      while (rs.next()) {
        String iddocgia = rs.getString("IDDOCGIA");
        String ten = rs.getString("TEN");
        String cccd = rs.getString("CCCD");
        String email = rs.getString("EMAIL");
        GUI.tableModel2.addRow(new Object[]{iddocgia, ten, cccd, email});
      }

      stmt = con.prepareStatement(query3);
      rs = stmt.executeQuery();
      while (rs.next()) {
        String iddocgia = rs.getString("IDDOCGIA");
        String idSachMuon = rs.getString("IDBOOK");
        String time = rs.getString("NGAYMUON");
        String time1 = rs.getString("NGAYTRA");
        String time2 = rs.getString("NGAYTRADUKIEN");
        GUI.tableMuonsach.addRow(new Object[]{iddocgia, idSachMuon, time, time1, time2});
      }
      rs.close();
      stmt.close();
      con.close();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog (null, "Lỗi kết nối không thành công " + ex.getMessage());
    }
  }
}