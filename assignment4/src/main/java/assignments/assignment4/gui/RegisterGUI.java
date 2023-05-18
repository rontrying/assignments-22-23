package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel implements ActionListener {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

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

        nameLabel = new JLabel("Masukan ID Anda:");
        gridManager.gridy = 0;
        mainPanel.add(nameLabel, gridManager);

        nameTextField = new JTextField();
        gridManager.gridy = 1;
        mainPanel.add(nameTextField, gridManager);

        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        gridManager.gridy = 2;
        mainPanel.add(phoneLabel, gridManager);

        phoneTextField = new JTextField();
        gridManager.gridy = 3;
        mainPanel.add(phoneTextField, gridManager);

        passwordLabel = new JLabel("Masukan password Anda:");
        gridManager.gridy = 4;
        mainPanel.add(passwordLabel, gridManager);

        passwordField = new JPasswordField();
        gridManager.gridy = 5;
        mainPanel.add(passwordField, gridManager);

        gridManager.fill = GridBagConstraints.RELATIVE;
        registerButton = new JButton("Register");
        gridManager.gridy = 6;
        registerButton.addActionListener(this);
        mainPanel.add(registerButton, gridManager);

        backButton = new JButton("Kembali");
        gridManager.gridy = 7;
        backButton.addActionListener(this);
        mainPanel.add(backButton, gridManager);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        String nama = nameTextField.getText();
        String noHp = phoneTextField.getText();
        String password = passwordField.getText();
        if (validateNoHp(noHp).equals("-1")){
            JOptionPane.showMessageDialog(mainPanel, "Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
            return;
        }
        if (nama.equals("") || noHp.equals("") || password.equals("")){
            JOptionPane.showMessageDialog(mainPanel, "Ada Field yang kosong", "Field Kosong", JOptionPane.ERROR_MESSAGE);
            nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
            return;
        }
        Member registeredMember = loginManager.register(nama, noHp, password);
        if(registeredMember == null){
            JOptionPane.showMessageDialog(mainPanel, "User dengan nama "+nama+" dan nomor hp "+noHp+" sudah ada!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
            handleBack();
            return;
        }
        JTextField textField = new JTextField("Berhasil membuat user dengan ID "+registeredMember.getId() +"!");
        textField.setEditable(false); // Membuat teks tidak dapat diedit
        JOptionPane.showMessageDialog(mainPanel, textField, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
        handleBack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton){
            handleRegister();
        } else if (e.getSource() == backButton){
            handleBack();
        }
    }

    // untuk mengvalidasi nomor hp return -1 jika string kosong atau bukan digit
    private static String validateNoHp(String nomorHp){
        if (nomorHp.equals("")){
            return "-1";
        }
        for (int i =0; i < nomorHp.length(); i++){
            if (!Character.isDigit(nomorHp.charAt(i))) {
                return "-1";
            }
        }
        return nomorHp;
    }
}
