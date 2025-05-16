import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoveQuiz extends JFrame implements ActionListener {
    private String[] questions = {
        "What’s our favorite game to play mostly?",
        "When is our monthsary?",
        "What’s our comfort food?"
    };
    
    private Choice[][] choices = {
        {
            new Choice("Stardew Valley", true),
            new Choice("Minecraft", false),
            new Choice("Wild Rift", false),
            new Choice("Terraria", false)
        },
        {
            new Choice("Today", true),
            new Choice("June 23, 2023", false),
            new Choice("I forgot", false),
            new Choice("May 1, 2023", false)
        },
        {
            new Choice("Jollibee", false),
            new Choice("Samyang Buldak Carbonara with Seaweed", true),
            new Choice("Popcorn", false),
            new Choice("Street Food", false)
        }
    };    

    private int currentQuestion = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;
    private JPanel centerPanel;
    private Font poppinsFont;

    class Choice {
        String text;
        boolean isCorrect;
    
        Choice(String text, boolean isCorrect) {
            this.text = text;
            this.isCorrect = isCorrect;
        }
    }    

    public LoveQuiz() {
        setTitle("Made with Love by Isabelle :)");
        ImageIcon icon = new ImageIcon("icon\\loveicon.png");
        setIconImage(icon.getImage());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 240, 245));
        setLayout(new BorderLayout());

        // Load custom font or fallback
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Poppins-Regular.ttf")).deriveFont(Font.PLAIN, 16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (Exception e) {
            poppinsFont = new Font("SansSerif", Font.PLAIN, 16);
        }

        JLabel header = new JLabel("Welcome to Our Monthsary Quiz!", JLabel.CENTER);
        header.setFont(poppinsFont.deriveFont(Font.BOLD, 24f));
        header.setForeground(new Color(199, 21, 133));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(255, 240, 245));
        centerPanel.setLayout(new GridLayout(5, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        questionLabel = new JLabel("", JLabel.LEFT);
        questionLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 18f));
        centerPanel.add(questionLabel);

        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(poppinsFont);
            options[i].setBackground(new Color(255, 240, 245));
            group.add(options[i]);
            centerPanel.add(options[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setFont(poppinsFont.deriveFont(Font.BOLD, 16f));
        nextButton.setBackground(new Color(255, 182, 193));
        nextButton.setFocusPainted(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        loadQuestion(currentQuestion);
        setVisible(true);
    }

    private void loadQuestion(int index) {
        questionLabel.setText((index + 1) + ". " + questions[index]);
    
        // Clone and shuffle choices
        Choice[] currentChoices = choices[index].clone();
        java.util.List<Choice> choiceList = java.util.Arrays.asList(currentChoices);
        java.util.Collections.shuffle(choiceList);
    
        group.clearSelection(); // This clears the selected radio button
    
        for (int i = 0; i < 4; i++) {
            options[i].setText(choiceList.get(i).text);
            options[i].putClientProperty("isCorrect", choiceList.get(i).isCorrect);
        }
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Answeri loveee :( huhu");
            return;
        }

        if ((boolean) options[selected].getClientProperty("isCorrect")) {
            score++;
        }        

        currentQuestion++;
        if (currentQuestion < questions.length) {
            loadQuestion(currentQuestion);
        } else {
            showResult();
        }
    }

    private void showResult() {
        String message;
        if (score == 3) {
            message = "Perfect score, loveeee!!\nYou really know us so well <3 I love youuu mwaaaa!!";
        } else if (score == 2) {
            message = "So close, babyy! You still know us really well <3";
        } else {
            message = "Hihi you might've missed a few, but I still love you lots <3";
        }
    
        JOptionPane.showMessageDialog(this,
            message + "\nYou got " + score + " out of " + questions.length + " <3",
            "Your Result",
            JOptionPane.INFORMATION_MESSAGE);
    
        // Call the Love Letter Popup after the result
        showLoveLetter();
    }    

    private void showLoveLetter() {
        String letter = "My dearest Jerich,\n\n" +
        "Every moment with you is a treasure that I hold close to my heart. From the first time we met, I knew that my life was about to change. Every laugh, every little conversation, and every game we play together makes me fall deeper in love with you.\n\n" +
        "Happy Monthsary, my loveeeeloveeeee jerichhh!!! <333 I still can’t believe how fast time has flown, and how lucky I am to have you by my side. You make even the simplest moments feel so special and for that, I am so thankful.\n\n" +
        "Through the good days and the not-so-good ones, you are my constant source of happiness and comfort. I love how we can just be ourselves with each other, whether we're talking about anything or simply enjoying a quiet day together.\n\n" +
        "Here’s to many more months, more memories, and more love to share. I am looking forward to growing with you, laughing with you, and creating more beautiful moments, one step at a time. I love you more than words could ever express hehehe\n\n" +
        "With all my heart, Isabelle <333";
    
        JDialog dialog = new JDialog(this, "A Message From Me to You", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
    
        JTextArea textArea = new JTextArea(letter);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBackground(new Color(255, 240, 245));
    
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // If the dialog is closed, also close the main window
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the main JFrame
                System.exit(0); // Exit the application
            }
        });
    
        dialog.setVisible(true);
    }    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoveQuiz());
    }
}