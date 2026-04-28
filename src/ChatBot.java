import java.util.*;

public class ChatBot {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("🤖 Mini AI Chatbot Started!");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Bot: Goodbye! 👋");
                break;
            }

            System.out.println("Bot: " + getResponse(input));
        }
    }

    public static String getResponse(String input) {

        if (input.contains("hello") || input.contains("hi")) {
            return "Hey there! 😊";
        }

        if (input.contains("how are you")) {
            return "I'm just code, but I'm doing great!";
        }

        if (input.contains("name")) {
            return "I'm your Mini AI Bot 🤖";
        }

        if (input.contains("time")) {
            return new Date().toString();
        }

        if (input.contains("bye")) {
            return "See you soon!";
        }

        return "Hmm... I don't understand that yet 🤔";
    }
}