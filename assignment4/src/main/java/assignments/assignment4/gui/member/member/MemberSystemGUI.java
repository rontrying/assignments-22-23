package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
                new JButton("Saya ingin laundry"),
                new JButton("Lihat detail nota saya")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        String text = "";
        Nota[] arrayNota = loggedInMember.getNotaList();
        for (Nota nota : arrayNota){
            text+=nota.toString()+"\n\n\n";
        }
        JTextArea textArea = new JTextArea(20, 45);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        if (text.equals("")){
            textArea.setText("Belum pernah laundry di CuciCuci, hiks :'(");
        } else {
            textArea.setText(text);
        }

        // Membungkus JTextArea dengan JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Membuat JOptionPane dengan JScrollPane sebagai isi pesan
        JOptionPane.showMessageDialog(this, scrollPane, "Informasi", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

}
