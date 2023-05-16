package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
                new JButton("It's nyuci time"),
                new JButton("Display List Nota")
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        if (notaList.length == 0){
            JOptionPane.showMessageDialog(this, "Belum ada nota", "List Nota", JOptionPane.ERROR_MESSAGE);
        } else {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            for (Nota nota : notaList){
                JLabel label = new JLabel(nota.getNotaStatus());
                panel.add(label);
            }

            JOptionPane.showMessageDialog(this, panel, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        JOptionPane.showMessageDialog(this, "Stand back! " + loggedInMember.getNama() +" beginning to nyuci!", "Nyuci time", JOptionPane.INFORMATION_MESSAGE);
        if (notaList.length == 0){
            JOptionPane.showMessageDialog(this, "nothing to cuci here", "Nyuci Results", JOptionPane.ERROR_MESSAGE);
        } else {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            for (Nota nota : notaList){
                JLabel label = new JLabel(nota.kerjakan());
                panel.add(label);
            }
            JOptionPane.showMessageDialog(this, panel, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
