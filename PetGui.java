import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PetGui {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel headerLabel;
    private JButton btnAddDog, btnAddCat, btnDelete;
    private String currentRole = "User";

    public PetGui() {
        // 1. Сначала окно входа
        if (!showLoginDialog()) {
            System.exit(0);
        }

        // 2. Создание основного окна
        frame = new JFrame("Pet Adoption System - Professional Edition");
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // --- ЗАГОЛОВОК ---
        headerLabel = new JLabel("Shelter Management Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerLabel.setForeground(new Color(44, 62, 80));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(headerLabel, BorderLayout.NORTH);

        // --- ТАБЛИЦА ---
        String[] columns = {"ID", "Pet Type", "Name", "Age", "Special Info"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        for (Object[] row : DatabaseManager.loadAllPets()) {
            // Проверяем: если это кот, то форматируем Special Info (row[4])
            if ("Cat".equalsIgnoreCase(row[1].toString())) {
                String info = row[4].toString();
                if (info.equals("1") || info.equalsIgnoreCase("true")) {
                    row[4] = "Indoors";
                } else if (info.equals("0") || info.equalsIgnoreCase("false")) {
                    row[4] = "Outdoors";
                }
            }
            tableModel.addRow(row);
        }
        // Стилизация строк и текста
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));

        // СТИЛИЗАЦИЯ ШАПКИ (Чтобы не была белой)
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(100, 40));
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(52, 73, 94)); // Темно-синий
        header.setForeground(Color.WHITE); // Белый текст
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        frame.add(scrollPane, BorderLayout.CENTER);

        // --- ПАНЕЛЬ КНОПОК ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
        buttonPanel.setBackground(new Color(245, 245, 245));

        btnAddDog = createStyledButton("Add New Dog", new Color(46, 204, 113)); // Зеленый
        btnAddCat = createStyledButton("Add New Cat", new Color(52, 152, 219)); // Синий
        btnDelete = createStyledButton("Remove Pet", new Color(231, 76, 60));  // Красный

        buttonPanel.add(btnAddDog);
        buttonPanel.add(btnAddCat);
        buttonPanel.add(btnDelete);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // --- ПРОВЕРКА РОЛИ ---
        if (!currentRole.equals("Admin")) {
            btnAddDog.setEnabled(false);
            btnAddCat.setEnabled(false);
            btnDelete.setEnabled(false);
            headerLabel.setText("Shelter Dashboard (Guest Mode)");
            headerLabel.setForeground(Color.GRAY);
        }

        // --- ЛОГИКА КНОПОК ---
        btnAddDog.addActionListener(e -> addPetAction("Dog", "Breed"));
        btnAddCat.addActionListener(e -> addPetAction("Cat", "Indoor (true/false)"));
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0); // Получаем ID из первой колонки
                DatabaseManager.deletePet(id);               // Удаляем из базы
                tableModel.removeRow(row);                    // Удаляем из таблицы
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // МЕТОД ДЛЯ КРАСИВЫХ КНОПОК
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        // Жесткая установка цвета
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        // Эффект при наведении
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(color.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(color);
            }
        });
        return btn;
    }

    // МЕТОД ДЛЯ ВХОДА
    private boolean showLoginDialog() {
        String[] options = {"Admin Login", "Guest View"};
        int res = JOptionPane.showOptionDialog(null, "Choose access level:", "Security Check",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (res == 0) {
            JPasswordField pf = new JPasswordField();
            int ok = JOptionPane.showConfirmDialog(null, pf, "Admin Password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (ok == JOptionPane.OK_OPTION) {
                String pass = new String(pf.getPassword());
                if (pass.equals("123")) {
                    currentRole = "Admin";
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password!");
                    return false;
                }
            }
            return false;
        } else if (res == 1) {
            currentRole = "User";
            return true;
        }
        return false;
    }

    private void addPetAction(String type, String extra) {
        String name = JOptionPane.showInputDialog(frame, "Enter " + type + " Name:");
        if (name == null || name.isEmpty()) return;
        String age = JOptionPane.showInputDialog(frame, "Enter " + type + " Age:");
        String info = JOptionPane.showInputDialog(frame, "Enter " + extra + ":");

        // Сохраняем в базу оригинал (0/1 или true/false)
        DatabaseManager.savePet(type, name, age, info);

        // Подготавливаем текст для отображения в таблице
        Object displayInfo = info;
        if (type.equalsIgnoreCase("Cat")) {
            if (info.equals("1") || info.equalsIgnoreCase("true")) {
                displayInfo = "Indoors";
            } else {
                displayInfo = "Outdoors";
            }
        }

        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, type, name, age, displayInfo});
    }

    public static void main(String[] args) {
        DatabaseManager.initialize(); // Создаем базу перед запуском окна
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(PetGui::new);
    }
}

