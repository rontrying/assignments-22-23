package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import static assignments.assignment3.nota.NotaManager.*;

public class HomeGUI extends JPanel implements ActionListener {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints gridManager = new GridBagConstraints();
        gridManager.fill = GridBagConstraints.RELATIVE;
        gridManager.weightx=1.0;
        gridManager.weighty=1.0;

        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        gridManager.gridy = 0;
        gridManager.gridx = 0;
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        gridManager.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel,gridManager);

        loginButton = new JButton("Login");
        gridManager.gridy = 1;
        gridManager.insets = new Insets(0,0,30,0);
        loginButton.addActionListener(this);
        mainPanel.add(loginButton,gridManager);

        registerButton = new JButton("Register");
        gridManager.gridy = 2;
        gridManager.anchor = GridBagConstraints.CENTER;
        registerButton.addActionListener(this);
        mainPanel.add(registerButton,gridManager);

        toNextDayButton = new JButton("Next Day");
        gridManager.gridy = 3;
        gridManager.anchor = GridBagConstraints.SOUTH;
        toNextDayButton.addActionListener(this);
        mainPanel.add(toNextDayButton,gridManager);

        dateLabel = new JLabel("Hari ini: "+fmt.format(cal.getTime()));
        gridManager.gridy = 4;
        gridManager.insets = new Insets(0,0,0,0);
        mainPanel.add(dateLabel, gridManager);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        cal.add(Calendar.DATE, 1);
        NotaManager.toNextDay();
        JOptionPane.showMessageDialog(mainPanel, "Kamu tidur hari ini... zzz...", "Info", JOptionPane.INFORMATION_MESSAGE);
        dateLabel.setText("Hari ini: "+fmt.format(cal.getTime()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            handleToLogin();
        } else if (e.getSource() == registerButton){
            handleToRegister();
        } else if (e.getSource() == toNextDayButton){
            handleNextDay();
        }
    }
}
