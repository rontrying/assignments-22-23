package assignments.assignment2;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class MainMenu {
    // definisi variabel static
    private static final Scanner input = new Scanner(System.in);
    private static final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static ArrayList<Member> memberList = new ArrayList<Member>();

    public static void main(String[] args){
        // menu utama
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    /*
     * fungsi handle generate user 
     * validasi no hp
     * validasi id member sudah ada atau belum
     */
    private static void handleGenerateUser() {
        //handle generate user
        String namaDepan, nomorHandphone;
        System.out.println("Masukkan nama Anda: ");
        namaDepan = input.nextLine();
        System.out.println("Masukkan nomor handphone Anda: ");

        nomorHandphone = input.nextLine();
        nomorHandphone = validateNoHp(nomorHandphone);

        // while loop meminta input sampai nomorhp sesuai
        while (nomorHandphone.equals("-1")){
            System.out.println("Field nomor hp hanya menerima digit");
            nomorHandphone = input.nextLine();
            nomorHandphone = validateNoHp(nomorHandphone);
        }

        Member member = new Member(namaDepan, nomorHandphone);

        if (validateIdMember(member.getId())){
            memberList.add(member);
            System.out.println("Berhasil membuat member dengan ID "+member.getId()+"!");
        } else {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n",namaDepan,nomorHandphone);
        }
    }

    /*
     * fungsi untuk mengvalidasi id member 
     * return false jika sudah ada id membernya
     * return true jika belum ada
     */
    private static boolean validateIdMember(String id){
        for (int i =0; i < memberList.size(); i++){
            if (memberList.get(i).getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method untuk mengvalidasi nomor handphone.
     * mengembalikan -1 jika string kosong 
     * mengembalikan -1 jika bukan digit
     * mengembalikan nomor handphone jika format sesuai
     */
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

    /*
     * fungsi handle generate nota dengan validasi paket, berat dan id
     */
    private static void handleGenerateNota() {
        //handle ambil cucian
        boolean isada = false;
        System.out.println("Masukan ID member:");
        String id = input.nextLine();
        if (memberList.size() == 0){
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",id);
        }
        for (int i = 0; i < memberList.size(); i++){
            if (memberList.get(i).getId().equals(id)){
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                System.out.println("Masukkan paket laundry:");
                String paket = input.nextLine();
                String istrue = validatePaket(paket);
            
                // while loop meminta paket sampai sesuai
                while (istrue.equals("-1") || istrue.equals("1")){
                    if (istrue.equals("-1")){
                        System.out.printf("Paket %s tidak diketahui\n",paket);
                        System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                    } else if (istrue.equals("1")){
                        showPaket();
                    }
                    System.out.println("Masukkan paket laundry:");
                    paket = input.nextLine();
                    istrue = validatePaket(paket);
                }

                System.out.println("Masukkan berat cucian Anda [Kg]:");
                int berat = validateBerat();

                // while loop meminta berat sampai sesuai
                while (berat == -1){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    berat = validateBerat();
                }

                if (berat < 2){
                    System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                    berat++;
                }

                Nota nota = new Nota(memberList.get(i), paket, berat, fmt.format(cal.getTime()));
                notaList.add(nota);
                System.out.println("Berhasil menambahkan nota!");
                System.out.println(nota.generateNota());
                isada = true;
                break;
            }
        }
        if (!isada){
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",id);
        }
        
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
    private static int validateBerat(){
        int berat;
        final Scanner sc = new Scanner(System.in);
        try{
            berat = sc.nextInt();
            if (berat <= 0){
                return -1;
            }
            return berat;
        } catch (Exception e){
            return -1;
        }
    }

    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /*
     * memeriksa apakah setiap nota cucian sudah bisa diambil atau tidak
     */
    private static void handleListNota() {
        //handle list semua nota pada sistem
        if (notaList.size() == 0){
            System.out.println("Terdaftar 0 nota dalam sistem.");
        } else {
            System.out.printf("Terdaftar %d nota dalam sistem.\n",notaList.size());
            for (int i = 0; i < notaList.size(); i++){
                if (notaList.get(i).GetIsReady()){
                    System.out.printf("- [%d] Status : Sudah dapat diambil\n",notaList.get(i).getId());
                } else {
                    System.out.printf("- [%d] Status : Belum bisa diambil :(\n",notaList.get(i).getId());
                }
            }
        }
    }

    private static void handleListUser() {
        //handle list semua user pada sistem
        if (memberList.size() == 0){
            System.out.println("Terdaftar 0 member dalam sistem.");
        } else {
            System.out.printf("Terdaftar %d member dalam sistem.\n",memberList.size());
            for (int i = 0; i < memberList.size(); i++){
                System.out.printf("- %s : %s\n",memberList.get(i).getId(),memberList.get(i).getNama());
            }
        }
    }

    private static void handleAmbilCucian() {
        //handle ambil cucian, menghilangkan nota jika cucian sudah diambil
        // mengvalidasi id dan apakah ada atau tidak dan berhasil diambil atau tidak
        boolean istrue = false;
        System.out.println("Masukan ID nota yang akan diambil:");
        String idString = input.nextLine();
        idString = validateNoHp(idString);

        // while loop meminta input sampai sesuai
        while (idString.equals("-1")){
            System.out.println("Nota id harus berupa angka!");
            idString = input.nextLine();
            idString = validateNoHp(idString);
        }

        int id = Integer.parseInt(idString);

        if(notaList.size() == 0){
            System.out.printf("Nota dengan ID %s tidak ditemukan\n",idString);
        } else{
            for (int i = 0; i < notaList.size(); i++){
                if (notaList.get(i).GetIsReady() && (notaList.get(i).getId() == id)){
                    System.out.printf("Nota dengan ID %s berhasil diambil!\n",idString);
                    notaList.remove(i);
                    istrue = true;
                    break;
                } else if (!notaList.get(i).GetIsReady() && (notaList.get(i).getId() == id)){
                    System.out.printf("Nota dengan ID %s gagal diambil!\n",idString);
                    istrue = true;
                    break;
                }
            }
            if (!istrue){
                System.out.printf("Nota dengan ID %s tidak ditemukan!\n",idString);
            }
        }
    }


    private static void handleNextDay() {
        //handle ganti hari dan mengecek laundry yang sudah bisa diambil
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DATE, 1);
        for (int i = 0; i < notaList.size();i++){
            notaList.get(i).setSisaHariPengerjaan();
            if (notaList.get(i).GetIsReady()){
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n",notaList.get(i).getId());
            }
        }
        printNextDay();
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    private static void printNextDay(){
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }
}
