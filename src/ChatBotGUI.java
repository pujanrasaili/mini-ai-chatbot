import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ChatBotGUI {

    // ── Colors ─────────────────────────────────────────
    private final Color BG_DARK      = new Color(10, 10, 15);
    private final Color BG_PANEL     = new Color(16, 16, 24);
    private final Color HEADER_BG    = new Color(18, 18, 28);
    private final Color INPUT_BG     = new Color(22, 22, 34);
    private final Color USER_BUBBLE  = new Color(16, 163, 127);
    private final Color BOT_BUBBLE   = new Color(28, 28, 42);
    private final Color ACCENT       = new Color(16, 163, 127);
    private final Color TEXT_PRIMARY = new Color(236, 236, 241);
    private final Color TEXT_MUTED   = new Color(100, 100, 130);
    private final Color SEND_BTN     = new Color(16, 163, 127);

    private JFrame frame;
    private JPanel chatPanel;
    private JTextField inputField;
    private JScrollPane scrollPane;
    private JLabel statusDot;

    public ChatBotGUI() {
        // ── Frame setup ────────────────────────────────
        frame = new JFrame("✦ NexusAI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 680);
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG_DARK);
        frame.setLayout(new BorderLayout(0, 0));

        // ── Header ─────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(0, 64));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 60)));

        // Left: avatar + name
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        headerLeft.setBackground(HEADER_BG);
        headerLeft.setBorder(BorderFactory.createEmptyBorder(12, 4, 0, 0));

        JLabel avatar = new JLabel("✦") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth("✦")) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString("✦", x, y);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(40, 40));

        JPanel nameGroup = new JPanel();
        nameGroup.setLayout(new BoxLayout(nameGroup, BoxLayout.Y_AXIS));
        nameGroup.setBackground(HEADER_BG);

        JLabel nameLabel = new JLabel("NexusAI");
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        statusRow.setBackground(HEADER_BG);

        statusDot = new JLabel("●");
        statusDot.setForeground(ACCENT);
        statusDot.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        JLabel statusLabel = new JLabel("Online");
        statusLabel.setForeground(TEXT_MUTED);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        statusRow.add(statusDot);
        statusRow.add(statusLabel);
        nameGroup.add(nameLabel);
        nameGroup.add(statusRow);

        headerLeft.add(avatar);
        headerLeft.add(nameGroup);

        // Right: model tag
        JLabel modelTag = new JLabel("  AI v1.0  ");
        modelTag.setForeground(ACCENT);
        modelTag.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        modelTag.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(16, 163, 127, 60), 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 20));
        headerRight.setBackground(HEADER_BG);
        headerRight.add(modelTag);

        header.add(headerLeft, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        // ── Chat panel ─────────────────────────────────
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(BG_PANEL);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BG_PANEL);
        scrollPane.getViewport().setBackground(BG_PANEL);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(BG_PANEL);

        // ── Input area ─────────────────────────────────
        JPanel inputArea = new JPanel(new BorderLayout(10, 0));
        inputArea.setBackground(BG_DARK);
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(40, 40, 60)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        inputField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INPUT_BG);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        inputField.setOpaque(false);
        inputField.setBackground(INPUT_BG);
        inputField.setForeground(TEXT_PRIMARY);
        inputField.setCaretColor(ACCENT);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // Placeholder
        inputField.setText("Type a message...");
        inputField.setForeground(TEXT_MUTED);
        inputField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Type a message...")) {
                    inputField.setText("");
                    inputField.setForeground(TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Type a message...");
                    inputField.setForeground(TEXT_MUTED);
                }
            }
        });

        // Send button
        JButton sendBtn = new JButton("Send") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? USER_BUBBLE.darker() : SEND_BTN);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24));
                g2.setColor(new Color(10, 10, 15));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth("Send")) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString("Send", x, y);
                g2.dispose();
            }
        };
        sendBtn.setPreferredSize(new Dimension(80, 44));
        sendBtn.setOpaque(false);
        sendBtn.setContentAreaFilled(false);
        sendBtn.setBorderPainted(false);
        sendBtn.setFocusPainted(false);
        sendBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        inputArea.add(inputField, BorderLayout.CENTER);
        inputArea.add(sendBtn, BorderLayout.EAST);

        // ── Assemble frame ─────────────────────────────
        frame.add(header, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputArea, BorderLayout.SOUTH);
        frame.setVisible(true);

        // ── Welcome message ────────────────────────────
        addBotMessage("Hello! I'm NexusAI 👋 How can I help you today?");

        // ── Actions ────────────────────────────────────
        inputField.addActionListener(e -> sendMessage());
        sendBtn.addActionListener(e -> sendMessage());
    }

    // ── Send message ───────────────────────────────────
    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty() || text.equals("Type a message...")) return;

        addUserMessage(text);
        inputField.setText("");

        statusDot.setForeground(Color.ORANGE);
        showTyping();

        String userInput = text.toLowerCase();
        new javax.swing.Timer(1000, e -> {
            removeTyping();
            addBotMessage(getResponse(userInput));
            statusDot.setForeground(ACCENT);
            ((javax.swing.Timer) e.getSource()).stop();
        }).start();
    }

    // ── Bubble factory ─────────────────────────────────
    private void addUserMessage(String msg) {
        chatPanel.add(createBubble(msg, USER_BUBBLE, new Color(10,10,15), true));
        chatPanel.add(Box.createVerticalStrut(6));
        refresh();
    }

    private void addBotMessage(String msg) {
        chatPanel.add(createBubble(msg, BOT_BUBBLE, TEXT_PRIMARY, false));
        chatPanel.add(Box.createVerticalStrut(6));
        refresh();
    }

    private JPanel typingPanel;

    private void showTyping() {
        typingPanel = createBubble("● ● ●", BOT_BUBBLE, TEXT_MUTED, false);
        chatPanel.add(typingPanel);
        refresh();
    }

    private void removeTyping() {
        if (typingPanel != null) {
            chatPanel.remove(typingPanel);
            refresh();
        }
    }

    private JPanel createBubble(String text, Color bg, Color fg, boolean isUser) {
        JPanel wrapper = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT, 16, 2));
        wrapper.setBackground(BG_PANEL);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Rounded bubble
        JPanel bubble = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 18, 18));
                g2.dispose();
            }
        };
        bubble.setOpaque(false);
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel msgLabel = new JLabel("<html><body style='width:220px'>" + text + "</body></html>");
        msgLabel.setForeground(fg);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel timeLabel = new JLabel(getTime());
        timeLabel.setForeground(isUser ? new Color(10,10,15,160) : TEXT_MUTED);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        bubble.add(msgLabel, BorderLayout.CENTER);
        bubble.add(timeLabel, BorderLayout.SOUTH);

        // Avatar
        JLabel avatarLabel = new JLabel(isUser ? "👤" : "🤖");
        avatarLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        avatarLabel.setForeground(ACCENT);

        if (isUser) {
            wrapper.add(bubble);
            wrapper.add(avatarLabel);
        } else {
            wrapper.add(avatarLabel);
            wrapper.add(bubble);
        }

        return wrapper;
    }

    private String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    private void refresh() {
        chatPanel.revalidate();
        chatPanel.repaint();
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    // ── Responses ──────────────────────────────────────
    private String getResponse(String input) {
        if (input.equals("hello") || input.equals("hi") || input.equals("hey")
                || input.startsWith("hello ") || input.startsWith("hi ") || input.startsWith("hey "))
            return "Hey there! Great to see you 😊";
        if (input.contains("how are you") || input.contains("how you doing") || input.contains("how r u"))
            return "I'm running at full capacity! How about you? 🚀";
        if (input.contains("what to think") || input.contains("what do you think"))
            return "I think every question is worth exploring! What's on your mind? 🤔";
        if (input.contains("feeling") || input.contains("feel"))
            return "I'm just code, but I feel great when I help someone! 😄";
        if (input.contains("your name") || input.contains("what is your name") || input.contains("who are you"))
            return "I'm NexusAI — your intelligent assistant ✦";
        if (input.contains("time"))
            return "Current time: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
        if (input.contains("bye") || input.contains("goodbye") || input.contains("see you"))
            return "Goodbye! Come back anytime 👋";
        if (input.contains("help"))
            return "I can chat, tell you the time, and answer basic questions. Try me! 💡";
        if (input.contains("thank"))
            return "You're welcome! Happy to help 😊";
        if (input.contains("what can you do") || input.contains("your features"))
            return "I can have conversations, tell you the time, and respond to keywords. More coming soon! 🔥";
        if (input.contains("good morning"))
            return "Good morning! Hope you have an amazing day ☀️";
        if (input.contains("good night"))
            return "Good night! Sleep well 🌙";
        if (input.contains("joke"))
            return "Why do programmers prefer dark mode? Because light attracts bugs! 😂";
        if (input.contains("age") || input.contains("old are you"))
            return "I was just born! But I'm learning fast 🚀";

        String[] replies = {
                "That's interesting! Can you tell me more? 🤔",
                "I'm not sure I understand. Could you rephrase that?",
                "Hmm, I'm still learning about that topic 📚",
                "Great point! What else is on your mind? 💡",
                "I find that fascinating! Tell me more 👀"
        };
        return replies[new Random().nextInt(replies.length)];
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(ChatBotGUI::new);
    }
}