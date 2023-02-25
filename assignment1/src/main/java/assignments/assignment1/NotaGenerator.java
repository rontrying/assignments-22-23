package assignments.assignment1;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // Implement interface menu utama
        int perintah = -2;
        String namaDepan, nomorHandphone;

        // while loop menjalankan perintah sampai input user bernilai 0 akan berhenti
        while (perintah != 0){
            printMenu();
            System.out.print("Pilihan : ");
            perintah = validatePilihan();
            if (perintah == -1){
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
            System.out.println("================================");

            // menu generate id dengan validasi no hp
            if (perintah == 1){
                System.out.println("Masukkan nama Anda: ");
                namaDepan = input.nextLine();
                System.out.println("Masukkan nomor handphone Anda: ");

                nomorHandphone = input.nextLine();
                nomorHandphone = validateNoHp(nomorHandphone);

                // while loop meminta input sampai nomorhp sesuai
                while (nomorHandphone.equals("-1")){
                    System.out.println("Nomor hp hanya menerima digit");
                    nomorHandphone = input.nextLine();
                    nomorHandphone = validateNoHp(nomorHandphone);
                }

                System.out.println("ID Anda : "+generateId(namaDepan, nomorHandphone));
            } // menu perintah 2 generate nota dengan validasi no hp, validasi paket, validasi berat, tanggal sudah dijamin inputnya
            else if (perintah == 2){
                System.out.println("Masukkan nama Anda: ");
                namaDepan = input.nextLine();
                System.out.println("Masukkan nomor handphone Anda: ");

                nomorHandphone = input.nextLine();
                nomorHandphone = validateNoHp(nomorHandphone);

                // while loop meminta nomor hp sampai sesuai 
                while (nomorHandphone.equals("-1")){
                    System.out.println("Nomor hp hanya menerima digit");
                    nomorHandphone = input.nextLine();
                    nomorHandphone = validateNoHp(nomorHandphone);
                }

                System.out.println("Masukkan tanggal terima: ");
                String tanggalTerima = input.nextLine();
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

                System.out.println("Nota Laundry");
                System.out.println(generateNota(generateId(namaDepan, nomorHandphone), paket, berat, tanggalTerima));
            }
            // exit program
            if (perintah == 0){
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
            }
        }
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
     * Method untuk mengvalidasi pilihan.
     * mengembalikan -1 jika tidak sesuai format pilihan 
     * mengembalikan -1 jika bukan diantara 0 dan 2
     * mengembalikan perintah jika format sesuai
     */
    private static int validatePilihan(){
        int perintah;
        final Scanner sc = new Scanner(System.in);
        try{
            perintah = sc.nextInt();
            if (perintah < 0 || perintah > 2){
                return -1;
            }
            return perintah;
        } catch (Exception e){
            return -1;
        }
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

    /**
     * Method untuk melakukan checksum dan mengembalikan 2 angka digit terakhir sebagai id.
     * menggunakan proses rekursif mengurangi huruf 1 demi demi 1 hinga base case.
     */
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
            String hasil = String.format("%s",total);
            if (hasil.length() <= 1){
                hasil+="0";
            }
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
     * Method untuk melakukan perhitungan hari
     * menggunakan simple date format dan java calender
     */
    private static String countDays(String date1, int hari) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date1));
        c.add(Calendar.DATE, hari);  // number of days to add
        return sdf.format(c.getTime());
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
        String output;
        //Implement generate nota sesuai soal.
        long harga, hargaPerKilo;
        int waktu;
        harga = 0;
        if (paket.equalsIgnoreCase("express")){
            hargaPerKilo = 12000;
            harga+=berat*hargaPerKilo;
            waktu = 1;
        } else if (paket.equalsIgnoreCase("fast")){
            hargaPerKilo = 10000;
            harga+=berat*hargaPerKilo;
            waktu = 2;
        } else {
            hargaPerKilo = 7000;
            harga+=berat*hargaPerKilo;
            waktu = 3;
        }

        output = "ID    : "+ id + "\n" + "Paket : "+ paket+"\n"+ "Harga :\n"+Integer.toString(berat);
        output+=" kg x "+Long.toString(hargaPerKilo)+" = "+Long.toString(harga);
        output+="\nTanggal Terima  : "+tanggalTerima+"\nTanggal Selesai : ";
        try{
            output+=countDays(tanggalTerima, waktu);
        } catch (Exception e){
            output+="date error";
        }
        return output;
    }
}
