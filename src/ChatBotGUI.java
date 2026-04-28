import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChatBotGUI {

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatBotGUI() {
        frame = new JFrame("Mini AI Chatbot 🤖");
        chatArea = new JTextArea();
        inputField = new JTextField();
        sendButton = new JButton("Send");

        // Chat area settings
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // 🌙 Dark Theme
        chatArea.setBackground(Color.BLACK);
        chatArea.setForeground(Color.GREEN);
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        sendButton.setBackground(Color.GRAY);
        sendButton.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Bottom panel (input + button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        chatArea.append("Bot: Hello! I'm your chatbot 🤖\n\n");

        // 🔁 Action (Enter key)
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 🔁 Action (Send button)
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    // 💬 Handle sending message
    private void sendMessage() {
        String userInput = inputField.getText();

        if (userInput.trim().isEmpty()) return;

        chatArea.append("You: " + userInput + "\n");

        String response = getResponse(userInput.toLowerCase());
        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
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