package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel implements ActionListener {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

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
        gridManager.fill = GridBagConstraints.HORIZONTAL;
        gridManager.weightx=1.0;
        gridManager.weighty=1.0;
        gridManager.gridx = 0;

        idLabel = new JLabel("Masukan ID Anda:");
        gridManager.gridy = 0;
        mainPanel.add(idLabel, gridManager);

        idTextField = new JTextField();
        gridManager.gridy = 1;
        mainPanel.add(idTextField, gridManager);

        passwordLabel = new JLabel("Masukan password Anda:");
        gridManager.gridy = 2;
        mainPanel.add(passwordLabel, gridManager);

        passwordField = new JPasswordField();
        gridManager.gridy = 3;
        mainPanel.add(passwordField, gridManager);

        gridManager.fill = GridBagConstraints.RELATIVE;
        loginButton = new JButton("Login");
        gridManager.gridy = 4;
        loginButton.addActionListener(this);
        mainPanel.add(loginButton, gridManager);

        backButton = new JButton("Kembali");
        gridManager.gridy = 5;
        backButton.addActionListener(this);
        mainPanel.add(backButton, gridManager);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame mainFrame = MainFrame.getInstance();
        idTextField.setText("");passwordField.setText("");
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        String inputId = idTextField.getText();
        String inputPassword = passwordField.getText();
        SystemCLI systemCLI = loginManager.getSystem(inputId);
        idTextField.setText("");
        passwordField.setText("");
        if(systemCLI == null){
            JOptionPane.showMessageDialog(mainPanel, "Invalid ID or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!MainFrame.getInstance().login(inputId, inputPassword)){
            JOptionPane.showMessageDialog(mainPanel, "Invalid ID or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            handleBack();
        } else if (e.getSource() == loginButton){
            handleLogin();
        }
    }
}

