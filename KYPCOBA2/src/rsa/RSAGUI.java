package rsa;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigInteger;
import java.util.List;

public class RSAGUI extends JFrame {

    private RSAKeyPair currentKeys;
    private List<BigInteger> lastCipher;

    private JTextArea inputArea;
    private JTextArea outputArea;

    private final Color SIDEBAR = new Color(24, 28, 36);
    private final Color BACKGROUND = new Color(36, 40, 50);
    private final Color PANEL = new Color(43, 48, 60);

    private final Color ACCENT = new Color(0, 170, 255);

    private final Color TEXT = new Color(235, 235, 235);

    public RSAGUI() {

        setTitle("RSA Шифрування");

        setSize(1200, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());

        root.setBackground(BACKGROUND);

        JPanel sidebar = createSidebar();

        JPanel centerPanel = createCenterPanel();

        root.add(sidebar, BorderLayout.WEST);

        root.add(centerPanel, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setBackground(SIDEBAR);

        sidebar.setPreferredSize(new Dimension(250, 0));

        sidebar.setLayout(
                new BoxLayout(sidebar, BoxLayout.Y_AXIS)
        );

        sidebar.setBorder(
                new EmptyBorder(25, 20, 25, 20)
        );

        JLabel title = new JLabel("RSA Студія");

        title.setForeground(TEXT);

        title.setFont(
                new Font("Segoe UI Variable", Font.BOLD, 28)
        );

        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(title);

        sidebar.add(Box.createVerticalStrut(30));

        JButton key512 = createButton("Ключ 512 біт");

        JButton key1024 = createButton("Ключ 1024 біт");

        JButton key2048 = createButton("Ключ 2048 біт");

        JButton encrypt = createButton("Зашифрувати");

        JButton decrypt = createButton("Розшифрувати");

        JButton swap = createButton("Поміняти місцями");

        JButton tests = createButton("Запустити тести");

        key512.addActionListener(e -> generateKeys(512));

        key1024.addActionListener(e -> generateKeys(1024));

        key2048.addActionListener(e -> generateKeys(2048));

        encrypt.addActionListener(e -> encryptText());

        decrypt.addActionListener(e -> decryptText());

        swap.addActionListener(e -> swapText());

        tests.addActionListener(e -> runTests());

        sidebar.add(key512);

        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(key1024);

        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(key2048);

        sidebar.add(Box.createVerticalStrut(30));

        sidebar.add(encrypt);

        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(decrypt);

        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(swap);

        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(tests);

        return sidebar;
    }

    private JPanel createCenterPanel() {

        JPanel center = new JPanel(
                new BorderLayout(20, 20)
        );

        center.setBackground(BACKGROUND);

        center.setBorder(
                new EmptyBorder(25, 25, 25, 25)
        );

        JLabel header = new JLabel(
                "Сучасний RSA застосунок"
        );

        header.setForeground(TEXT);

        header.setFont(
                new Font("Segoe UI Variable", Font.BOLD, 30)
        );

        center.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(
                new GridLayout(2, 1, 20, 20)
        );

        content.setBackground(BACKGROUND);

        inputArea = createTextArea();

        outputArea = createTextArea();

        JPanel inputPanel = createCard(
                "Введений текст",
                inputArea
        );

        JPanel outputPanel = createCard(
                "Результат",
                outputArea
        );

        content.add(inputPanel);

        content.add(outputPanel);

        center.add(content, BorderLayout.CENTER);

        return center;
    }

    private JPanel createCard(
            String title,
            JTextArea area
    ) {

        JPanel card = new JPanel(
                new BorderLayout(10, 10)
        );

        card.setBackground(PANEL);

        card.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel label = new JLabel(title);

        label.setForeground(TEXT);

        label.setFont(
                new Font("Segoe UI Variable", Font.BOLD, 20)
        );

        JScrollPane scroll = new JScrollPane(area);

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        card.add(label, BorderLayout.NORTH);

        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    private JTextArea createTextArea() {

        JTextArea area = new JTextArea();

        area.setBackground(
                new Color(55, 60, 75)
        );

        area.setForeground(TEXT);

        area.setCaretColor(TEXT);

        area.setFont(
                new Font("Segoe UI Variable", Font.PLAIN, 18)
        );

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBorder(
                new EmptyBorder(15, 15, 15, 15)
        );

        return area;
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);

        button.setBackground(ACCENT);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font("Segoe UI Variable", Font.BOLD, 16)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        16,
                        12,
                        16
                )
        );

        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        return button;
    }

    private void generateKeys(int bits) {

        long start = System.currentTimeMillis();

        currentKeys = RSAEngine.generateKeys(bits);

        long end = System.currentTimeMillis();

        outputArea.setText(
                "RSA ключі успішно згенеровані\n\n" +
                        "Розмір ключа: " + bits + " біт\n" +
                        "Час генерації: " + (end - start) + " мс"
        );
    }

    private void encryptText() {

        if (currentKeys == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Спочатку згенеруйте ключі."
            );

            return;
        }

        String text = inputArea.getText();

        if (text.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Введіть текст."
            );

            return;
        }

        lastCipher = RSAEngine.encrypt(
                text,
                currentKeys.getPublicExponent(),
                currentKeys.getModulus()
        );

        outputArea.setText(
                "Зашифрований текст:\n\n" +
                        RSAEngine.cipherToString(lastCipher)
        );
    }

    private void decryptText() {

        if (lastCipher == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Немає зашифрованих даних."
            );

            return;
        }

        String decrypted = RSAEngine.decrypt(
                lastCipher,
                currentKeys.getPrivateExponent(),
                currentKeys.getModulus()
        );

        outputArea.setText(
                "Розшифрований текст:\n\n" +
                        decrypted
        );
    }

    private void swapText() {

        String bottomText = outputArea.getText();

        inputArea.setText(bottomText);

        outputArea.setText("");
    }

    private void runTests() {

        RSAKeyPair keys = RSAEngine.generateKeys(512);

        String text = "Тест RSA";

        List<BigInteger> cipher = RSAEngine.encrypt(
                text,
                keys.getPublicExponent(),
                keys.getModulus()
        );

        String decrypted = RSAEngine.decrypt(
                cipher,
                keys.getPrivateExponent(),
                keys.getModulus()
        );

        if (text.equals(decrypted)) {

            outputArea.setText(
                    "Тест RSA успішно пройдено."
            );

        } else {

            outputArea.setText(
                    "Помилка тестування RSA."
            );
        }
    }

    public static void main(String[] args) {

        FlatDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {

            RSAGUI gui = new RSAGUI();

            gui.setVisible(true);
        });
    }
}