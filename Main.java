import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        int rows = 100, cols = 100; // Adjust the grid size as needed

        GameOfLife game = new GameOfLife(rows, cols);
        game.initializeGrid();

        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        JButton[][] buttons = new JButton[rows][cols];

        // Create buttons for each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(20, 20)); // Set smaller size for buttons
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Add gray border

                int row = i, col = j;
                button.addActionListener(e -> {
                    game.toggleCell(row, col);
                    button.setBackground(game.getGrid()[row][col] 
                        ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) 
                        : Color.WHITE);
                });

                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }

        // -=-=-=-= BUTTONS AND STUFF-=-=-=-
        // Add the grid panel to the frame
        frame.add(gridPanel, BorderLayout.CENTER);

        // Add a button to reset the simulation
        JButton resetButton = new JButton("Reset Simulation");
        resetButton.setPreferredSize(new Dimension(150, 30)); // Adjust size for reset button
        resetButton.addActionListener(e -> {
            game.initializeGrid();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        });
        frame.add(resetButton, BorderLayout.NORTH);
        final boolean[] running = {false};

        // Add a button to start/stop the simulation
        JButton startButton = new JButton("Start Simulation");
        startButton.setPreferredSize(new Dimension(150, 30)); // Adjust size for start/stop button
        frame.add(startButton, BorderLayout.SOUTH);

        // Add a text box to display the number of years passed
        JTextField yearTextBox = new JTextField("Year: 0");
        yearTextBox.setEditable(false);
        yearTextBox.setHorizontalAlignment(JTextField.CENTER);
        yearTextBox.setPreferredSize(new Dimension(150, 30));
        frame.add(yearTextBox, BorderLayout.WEST);

        final int[] yearCounter = {0}; // Counter to track the number of years

        // Modify the start/stop button action listener to update the year counter
        startButton.addActionListener(e -> {
            running[0] = !running[0];
            startButton.setText(running[0] ? "Stop Simulation" : "Start Simulation");

            if (running[0]) {
                new Thread(() -> {
                    while (running[0]) {
                        game.nextGeneration();
                        yearCounter[0]++; // Increment the year counter
                        SwingUtilities.invokeLater(() -> {
                            yearTextBox.setText("Year: " + yearCounter[0]); // Update the text box
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < cols; j++) {
                                    buttons[i][j].setBackground(game.getGrid()[i][j] 
                                        ? game.getColors()[i][j] 
                                        : Color.WHITE);
                                }
                            }
                        });
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // Reset the year counter when resetting the simulation
        resetButton.addActionListener(e -> {
            game.initializeGrid();
            yearCounter[0] = 0; // Reset the year counter
            yearTextBox.setText("Year: 0"); // Reset the text box
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        });

        // Randomizes the grid
        JButton randomizeButton = new JButton("Randomize Grid");
        randomizeButton.setPreferredSize(new Dimension(150, 30)); // Adjust size for randomize button
        randomizeButton.addActionListener(e -> {
            game.randomizeGrid();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    buttons[i][j].setBackground(game.getGrid()[i][j] 
                        ? game.getColors()[i][j] 
                        : Color.WHITE);
                }
            }
        });
        frame.add(randomizeButton, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
