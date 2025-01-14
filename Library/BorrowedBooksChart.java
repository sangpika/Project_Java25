package Library;

import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BorrowedBooksChart extends JFrame {

  public BorrowedBooksChart() {
    setTitle("Thống kê sách được mượn");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);


    DefaultCategoryDataset dataset = createDataset();

    // Tạo biểu đồ cột
    JFreeChart barChart = ChartFactory.createBarChart(
        "Thống kê sách được mượn",
        "Tên sách",
        "Số lần mượn",
        dataset);

    barChart.setBackgroundPaint(Color.white);
    CategoryPlot plot = barChart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setDrawBarOutline(true);
    renderer.setSeriesPaint(0, new Color(243, 186, 220, 230));
    plot.setBackgroundPaint(new Color(158, 218, 232, 176));
    plot.setOutlinePaint(new Color(229, 250, 229));
    plot.setRangeGridlinePaint(new Color(155, 153, 153, 213));


    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
  }


  private DefaultCategoryDataset createDataset() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String query = "SELECT NAME, COUNT(*) AS BorrowCount " +
        "FROM MUON JOIN ListBooks ON MUON.IDBOOK = ListBooks.IDBOOK " +
        "GROUP BY NAME";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        String bookName = rs.getString("NAME");
        int borrowCount = rs.getInt("BorrowCount");
        dataset.addValue(borrowCount, "Số lần mượn", bookName);
      }

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
    }
    return dataset;
  }

}
