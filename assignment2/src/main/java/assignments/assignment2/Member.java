package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    //tambahkan attributes yang diperlukan untuk class ini
    private String nama, noHp, id;
    private int bonusCounter;
    public Member(String nama, String noHp) {
        //buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = generateId(this.nama, this.noHp);
        this.bonusCounter=0;
    }

    public String getId(){
        return this.id;
    }

    public String getNama(){
        return this.nama;
    }

    public int getBonusCounter(){
        return this.bonusCounter;
    }

    public void setBonusCounter(){
        this.bonusCounter+=1;
    }

    public void resetBonusCounter(){
        this.bonusCounter = 0;
    }
    //tambahkan methods yang diperlukan untuk class ini
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
                hasil = "0" + hasil;
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
}
