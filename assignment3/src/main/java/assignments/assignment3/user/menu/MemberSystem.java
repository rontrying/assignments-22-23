package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

import java.util.Scanner;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        if (choice == 1){
            System.out.println("Masukan paket laundry:");
            showPaket();
            String paket = in.nextLine();
            String isTrue = validatePaket(paket);
            // while loop meminta paket sampai sesuai
            while (isTrue.equals("-1") || isTrue.equals("1")){
                if (isTrue.equals("-1")){
                    System.out.printf("Paket %s tidak diketahui\n",paket);
                    System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                } else {
                    showPaket();
                }
                System.out.println("Masukkan paket laundry:");
                paket = in.nextLine();
                isTrue = validatePaket(paket);
            }

            System.out.println("Masukkan berat cucian Anda [Kg]:");
            int berat = validateBerat();

            // while loop meminta berat sampai sesuai
            while (berat == -1){
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                berat = validateBerat();
            }

            if (berat < 2){
                berat++;
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            }
            // buat nota
            Nota nota = new Nota(loginMember, berat, paket, fmt.format(cal.getTime()));

            System.out.print("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\n" +
                    "Hanya tambah 1000 / kg :0\n" +
                    "[Ketik x untuk tidak mau]: ");

            String perintahSetrika = in.next();

            if (!perintahSetrika.equals("x")){
                SetrikaService setrikaService = new SetrikaService();
                nota.addService(setrikaService); //isi service setrika
            }
            System.out.print("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\n" +
                    "Cuma 2000 / 4kg, kemudian 500 / kg\n" +
                    "[Ketik x untuk tidak mau]: ");

            String perintahAntar = in.next();

            if (!perintahAntar.equals("x")){
                AntarService antarService = new AntarService();
                nota.addService(antarService); // service antar
            }
            loginMember.addNota(nota);
            NotaManager.addNota(nota);

            System.out.println("Nota berhasil dibuat!");

        } else if (choice == 2){ //detail nota untuk pelanggan
            Nota[] arrayNota = loginMember.getNotaList();
            for (Nota nota : arrayNota){
                System.out.println(nota);
            }
        }
        else if (choice == 3){
            logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        // meletakan objek member pada memberlist
        Member[] newMemberList = new Member[memberList.length+1];
        System.arraycopy(memberList, 0, newMemberList, 0, memberList.length);
        memberList = newMemberList;
        newMemberList[memberList.length-1] = member;

    }

    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk mengvalidasi paket.
     * mengembalikan 1 jika user meminta data paket
     * mengembalikan -1 jika paket tidak ada
     */
    private static String validatePaket(String paket){
        if (paket.equalsIgnoreCase("express") || paket.equalsIgnoreCase("fast") || paket.equalsIgnoreCase("reguler")){
            return paket;
        } else if (paket.equals("?")){
            return "1";
        }
        return "-1";
    }

    /**
     * Method untuk mengvalidasi berat menggunakan try catch.
     * mengembalikan berat jika sesuai ketentuan
     * mengembalikan -1 jika bukan angka
     * mengembalikan -1 jika berat bukan bilangan bulat positif
     */
    private int validateBerat(){
        int berat1;
        final Scanner sc = new Scanner(System.in);
        try{
            berat1 = sc.nextInt();
            sc.nextLine();
            if (berat1 <= 0){
                return -1;
            }
            return berat1;
        } catch (Exception e){
            return -1;
        }
    }

}