// BookstoreGUI.java (Modern Stylish UI Upgrade + Phase 2 Features)
package gui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BookstoreGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField titleField, authorField, priceField, quantityField, searchField;
    private JComboBox<String> categoryDropdown;
    private JButton addButton, updateButton, deleteButton, clearButton, searchButton;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO = new BookDAO();

    public BookstoreGUI() {
        setTitle("📚 Bookstore Manager");
        setSize(1000, 630);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(100, 149, 237);
                Color c2 = new Color(65, 105, 225);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("📚 Bookstore Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        headerPanel.setOpaque(false);
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("📖 Book Details"));
        formPanel.setBackground(Color.WHITE);

        titleField = new JTextField();
        authorField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();
        searchField = new JTextField();
        categoryDropdown = new JComboBox<>(new String[]{"Fiction", "Technology", "Education", "Comics", "Biography"});

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryDropdown);
        formPanel.add(new JLabel("🔍 Search Title:"));
        formPanel.add(searchField);

        add(formPanel, BorderLayout.WEST);

        String[] columns = {"ID", "Title", "Author", "Price", "Quantity", "Category"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);

        JTableHeader header = bookTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.white);
        bookTable.setRowHeight(26);
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Book List"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        addButton = new JButton("➕ Add");
        updateButton = new JButton("✏️ Update");
        deleteButton = new JButton("🗑️ Delete");
        clearButton = new JButton("🧹 Clear");
        searchButton = new JButton("🔍 Search");

        JButton[] buttons = {addButton, updateButton, deleteButton, clearButton, searchButton};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Tahoma", Font.BOLD, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        for (JButton btn : buttons) buttonPanel.add(btn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadBooks();

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(e -> searchBooks());

        bookTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = bookTable.getSelectedRow();
                titleField.setText(tableModel.getValueAt(row, 1).toString());
                authorField.setText(tableModel.getValueAt(row, 2).toString());
                priceField.setText(tableModel.getValueAt(row, 3).toString());
                quantityField.setText(tableModel.getValueAt(row, 4).toString());
                categoryDropdown.setSelectedItem(tableModel.getValueAt(row, 5).toString());
            }
        });

        setVisible(true);
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getQuantity(), b.getCategory()});
        }
    }

    private void addBook() {
        try {
            String title = titleField.getText();
            String author = authorField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryDropdown.getSelectedItem().toString();

            Book book = new Book(title, author, price, quantity, category);
            bookDAO.insertBook(book);
            JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
            loadBooks();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Please enter valid book information.");
        }
    }

    private void updateBook() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a book to update.");
            return;
        }
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            String title = titleField.getText();
            String author = authorField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryDropdown.getSelectedItem().toString();

            Book book = new Book(id, title, author, price, quantity, category);
            bookDAO.updateBook(book);
            JOptionPane.showMessageDialog(this, "✏️ Book updated successfully!");
            loadBooks();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Update failed! Check input values.");
        }
    }

    private void deleteBook() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a book to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bookDAO.deleteBook(id);
            JOptionPane.showMessageDialog(this, "🗑️ Book deleted successfully!");
            loadBooks();
            clearFields();
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        priceField.setText("");
        quantityField.setText("");
        searchField.setText("");
        categoryDropdown.setSelectedIndex(0);
    }

    private void searchBooks() {
        String keyword = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword) || b.getAuthor().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getQuantity(), b.getCategory()});
            }
        }
    }

    public static void main(String[] args) {
        new BookstoreGUI();
    }
}
