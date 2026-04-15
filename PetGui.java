
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PetGui {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public PetGui() {
        frame = new JFrame("Pet Adoption System - Professional Edition");
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 242, 245)); // Светло-серый фон

        // 1. Заголовок
        JLabel headerLabel = new JLabel("Shelter Management Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(headerLabel, BorderLayout.NORTH);

        // 2. Стилизация таблицы
        String[] columns = {"ID", "Pet Type", "Name", "Age", "Additional Info"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Настройка внешнего вида таблицы
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(173, 216, 230)); // Голубой при выборе

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180)); // Стальной синий
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // 3. Панель кнопок (Side or Bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnAddDog = createStyledButton("Add New Dog", new Color(46, 204, 113));
        JButton btnAddCat = createStyledButton("Add New Cat", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("Remove Pet", new Color(231, 76, 60));

        buttonPanel.add(btnAddDog);
        buttonPanel.add(btnAddCat);
        buttonPanel.add(btnDelete);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Логика кнопок (оставляем ту же, что была)
        btnAddDog.addActionListener(e -> addPetAction("Dog", "Breed"));
        btnAddCat.addActionListener(e -> addPetAction("Cat", "Indoor (true/false)"));
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) tableModel.removeRow(row);
            else JOptionPane.showMessageDialog(frame, "Please select a row first!");
        });

        frame.setLocationRelativeTo(null); // Окно по центру экрана
        frame.setVisible(true);
    }

    // Вспомогательный метод для красивых кнопок
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));

        // --- ВАЖНЫЕ ИСПРАВЛЕНИЯ ТУТ ---
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);               // Делаем кнопку непрозрачной
        btn.setContentAreaFilled(true);    // Принудительно заполняем цветом
        btn.setBorderPainted(false);       // Убираем стандартную рамку
        btn.setFocusPainted(false);        // Убираем рамку фокуса вокруг текста
        // ------------------------------

        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Эффект при наведении (чтобы кнопка чуть темнела)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    private void addPetAction(String type, String extraLabel) {
        String name = JOptionPane.showInputDialog(frame, "Enter " + type + " Name:");
        if (name == null || name.isEmpty()) return;
        String age = JOptionPane.showInputDialog(frame, "Enter " + type + " Age:");
        String extra = JOptionPane.showInputDialog(frame, "Enter " + extraLabel + ":");
        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, type, name, age, extra});
    }

    public static void main(String[] args) {
        try {
            // Устанавливаем системный стиль окон
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new PetGui());
    }
}


