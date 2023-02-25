package assignments.assignment1;

import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // Implement interface menu utama
        int perintah = -1;
        String namaDepan, nomorHandphone;

        while (perintah != 0){
            printMenu();
            System.out.print("Pilihan : ");
            perintah = validatePilihan();
            if (perintah == -1){
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
            System.out.println("================================");
            if (perintah == 1){
                System.out.println("Masukkan nama Anda: ");
                namaDepan = input.nextLine();
                System.out.println("Masukkan nomor handphone Anda: ");

                nomorHandphone = input.nextLine();
                nomorHandphone = validateNoHp(nomorHandphone);

                while (nomorHandphone.equals("-1")){
                    System.out.println("Nomor hp hanya menerima digit");
                    nomorHandphone = input.nextLine();
                    nomorHandphone = validateNoHp(nomorHandphone);
                }

                System.out.println("ID Anda : "+generateId(namaDepan, nomorHandphone));
            }

            if (perintah == 0){
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
            }
        }
    }

    private static int validatePilihan(){
        int perintah;
        Scanner sc = new Scanner(System.in);
        try{
            perintah = sc.nextInt();
            return perintah;
        } catch (Exception e){
            return -1;
        }
    }

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

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        //Implement generate ID sesuai soal.
        nama = nama.split(" ")[0].toUpperCase();
        String digitChecksum = checksum(nama + "-" + nomorHP, 0);
        String id = nama + "-" + nomorHP + "-"+ digitChecksum;
        return id;
    }

    private static String checksum(String kalimat, int total){
        // base case
        // huruf uppercase dari 65 - 90
        // angka dari 48 - 57
        int indexHuruf = kalimat.charAt(0);
        if (kalimat.length() == 1){
            if ( (indexHuruf >= 65 && indexHuruf <= 90)){
                total += (indexHuruf-(int)'A'+1);
            } else if ((indexHuruf >= 48 && indexHuruf <= 57)){
                total += (indexHuruf-(int)'0');
            } else {
                total+= 7;
            }
            String hasil = String.format("%02d",total);
            
            hasil = hasil.substring(hasil.length()-2,hasil.length());
            return hasil;
        }

        if ( (indexHuruf >= 65 && indexHuruf <= 90)){
            total += (indexHuruf-(int)'A'+1);
        } else if ((indexHuruf >= 48 && indexHuruf <= 57)){
            total += (indexHuruf-(int)'0');
        } else {
            total+= 7;
        }
        // proses rekursif
        return checksum(kalimat.substring(1, kalimat.length()),total);
    }
    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // TODO: Implement generate nota sesuai soal.
        return null;
    }
}
