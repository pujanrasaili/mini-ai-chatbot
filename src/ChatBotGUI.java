import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChatBotGUI {

    private JFrame frame;
    private JPanel chatPanel;
    private JTextField inputField;
    private JScrollPane scrollPane;

    public ChatBotGUI() {
        frame = new JFrame("Mini AI Chatbot 🤖");

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.BLACK);

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(null);

        inputField = new JTextField();

        JButton sendButton = new JButton("Send");

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Dark theme
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        sendButton.setBackground(Color.GRAY);
        sendButton.setForeground(Color.WHITE);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addBotMessage("Hello! I'm your chatbot 🤖");

        // Enter key
        inputField.addActionListener(e -> sendMessage());

        // Button click
        sendButton.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        addUserMessage(text);

        String response = getResponse(text.toLowerCase());
        addBotMessage(response);

        inputField.setText("");
    }

    // 💬 USER bubble (right side)
    private void addUserMessage(String message) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.BLACK);

        JLabel label = new JLabel("<html><p style='padding:5px;'>" + message + "</p></html>");
        label.setOpaque(true);
        label.setBackground(Color.GREEN);
        label.setForeground(Color.BLACK);

        panel.add(label);
        chatPanel.add(panel);
        refreshChat();
    }

    // 🤖 BOT bubble (left side)
    private void addBotMessage(String message) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.BLACK);

        JLabel label = new JLabel("<html><p style='padding:5px;'>" + message + "</p></html>");
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setForeground(Color.WHITE);

        panel.add(label);
        chatPanel.add(panel);
        refreshChat();
    }

    private void refreshChat() {
        chatPanel.revalidate();
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    // 🤖 Bot logic
    private String getResponse(String input) {
        if (input.contains("hello") || input.contains("hi")) {
            return "Hey there! 😊";
        }
        if (input.contains("how are you")) {
            return "I'm doing great!";
        }
        if (input.contains("name")) {
            return "I'm your GUI Chatbot 🤖";
        }
        if (input.contains("time")) {
            return new Date().toString();
        }
        if (input.contains("bye")) {
            return "Goodbye! 👋";
        }

        String[] replies = {
                "Interesting 🤔",
                "Tell me more...",
                "I see 👀",
                "Hmm..."
        };
        return replies[new Random().nextInt(replies.length)];
    }

    public static void main(String[] args) {
        new ChatBotGUI();
    }
}