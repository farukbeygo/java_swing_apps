import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroApp extends JFrame {
    private JLabel timerLabel;
    private JButton startPauseButton;
    private JButton resetButton;
    private Timer pomodoroTimer;
    private int workTimeInSeconds = 25 * 60; // 25 minutes in seconds
    private int breakTimeInSeconds = 5 * 60; // 5 minutes in seconds
    private int remainingTimeInSeconds;

    public PomodoroApp() {
        setTitle("Pomodoro App");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pomodoroTimer.isRunning()) {
                    pausePomodoroTimer();
                } else {
                    startPomodoroTimer();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPomodoroTimer();
            }
        });

        pomodoroTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTimeInSeconds--;
                if (remainingTimeInSeconds <= 0) {
                    pomodoroTimer.stop();
                    remainingTimeInSeconds = breakTimeInSeconds;
                    JOptionPane.showMessageDialog(PomodoroApp.this, "Take a break!");
                }
                updateTimerLabel();
            }
        });
    }

    private void initComponents() {
        setLayout(new GridLayout(2, 1));

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        updateTimerLabel();
        add(timerLabel);

        JPanel buttonPanel = new JPanel();
        startPauseButton = new JButton("Start");
        resetButton = new JButton("Reset");
        buttonPanel.add(startPauseButton);
        buttonPanel.add(resetButton);
        add(buttonPanel);
    }

    private void updateTimerLabel() {
        int minutes = remainingTimeInSeconds / 60;
        int seconds = remainingTimeInSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void startPomodoroTimer() {
        if (!pomodoroTimer.isRunning()) {
            pomodoroTimer.start();
            startPauseButton.setText("Pause");
        }
    }

    private void pausePomodoroTimer() {
        if (pomodoroTimer.isRunning()) {
            pomodoroTimer.stop();
            startPauseButton.setText("Resume");
        }
    }

    private void resetPomodoroTimer() {
        if (pomodoroTimer.isRunning()) {
            pomodoroTimer.stop();
        }
        remainingTimeInSeconds = workTimeInSeconds;
        updateTimerLabel();
        startPauseButton.setText("Start");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PomodoroApp().setVisible(true);
        });
    }
}

