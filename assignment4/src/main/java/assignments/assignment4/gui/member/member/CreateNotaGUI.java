package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class CreateNotaGUI extends JPanel implements ActionListener {
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new GridBagLayout());
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        GridBagConstraints gridManager = new GridBagConstraints();
        gridManager.weightx=1.0;
        gridManager.weighty=1.0;
        gridManager.fill = GridBagConstraints.HORIZONTAL;

        paketLabel = new JLabel("Paket Laundry:");
        gridManager.gridy = 0;
        add(paketLabel, gridManager);

        String[] comboBoxItems = {"Fast", "Reguler", "Express"};
        paketComboBox = new JComboBox<>(comboBoxItems);
        gridManager.anchor = GridBagConstraints.EAST;
        gridManager.insets = new Insets(0,10, 0, 10);
        add(paketComboBox, gridManager);

        showPaketButton = new JButton("Show Paket");
        gridManager.anchor = GridBagConstraints.EAST;
        showPaketButton.addActionListener(this);
        add(showPaketButton, gridManager);

        beratLabel = new JLabel("Berat Cucian(Kg):");
        gridManager.anchor = GridBagConstraints.WEST;
        gridManager.insets = new Insets(0,0, 0, 0);
        gridManager.gridy = 1;
        add(beratLabel, gridManager);

        beratTextField = new JTextField();
        gridManager.anchor = GridBagConstraints.EAST;
        gridManager.insets = new Insets(0,10, 0, 10);
        add(beratTextField, gridManager);

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        gridManager.gridy = 2;
        gridManager.anchor = GridBagConstraints.WEST;
        gridManager.insets = new Insets(0,0, 0, 0);
        add(setrikaCheckBox, gridManager);

        antarCheckBox = new JCheckBox("Tambah Antar Service(2000/4kg pertama, kemudian 500/kg");
        gridManager.gridy = 3;
        add(antarCheckBox, gridManager);

        createNotaButton = new JButton("Buat Nota");
        gridManager.gridy = 4;
        gridManager.gridwidth = 3;
        createNotaButton.addActionListener(this);
        add(createNotaButton, gridManager);

        backButton = new JButton("Kembali");
        backButton.addActionListener(this);
        gridManager.gridy = 5;
        add(backButton, gridManager);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        int berat = -1;
        boolean perintahSetrika = setrikaCheckBox.isSelected();
        String paket = (String) paketComboBox.getSelectedItem();
        boolean perintahAntar = antarCheckBox.isSelected();

        try {
            berat = Integer.parseInt(beratTextField.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Berat Cucian harus berisi angka", "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            setrikaCheckBox.setSelected(false);
            antarCheckBox.setSelected(false);
            return;
        }
        if (berat <= 0){
            JOptionPane.showMessageDialog(this, "Masukan berat cucian dalam bilangan positif", "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            setrikaCheckBox.setSelected(false);
            antarCheckBox.setSelected(false);
            return;
        }

        if (berat < 2){
            berat++;
            JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        Nota nota = new Nota(memberSystemGUI.getLoggedInMember(), berat, paket, fmt.format(cal.getTime()));


        if (perintahSetrika){
            SetrikaService setrikaService = new SetrikaService();
            nota.addService(setrikaService);
        }

        if (perintahAntar){
            AntarService antarService = new AntarService();
            nota.addService(antarService);
        }
        memberSystemGUI.getLoggedInMember().addNota(nota);
        NotaManager.addNota(nota);

        JOptionPane.showMessageDialog(this, "Nota berhasil dibuat", "Success", JOptionPane.INFORMATION_MESSAGE);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            handleBack();
        } else if (e.getSource() == createNotaButton){
            createNota();
        } else if (e.getSource() == showPaketButton){
            showPaket();
        }
    }


}
