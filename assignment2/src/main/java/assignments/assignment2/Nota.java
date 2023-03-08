package assignments.assignment2;

import assignments.assignment1.NotaGenerator;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class Nota {
    //tambahkan attributes yang diperlukan untuk class ini
    private int idBon, berat, sisaHariPengerjaan;
    private static int idNota = -1;
    private String paket, tanggalMasuk;
    private Member member;
    private boolean isReady;
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        //buat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.isReady = false;
        this.tanggalMasuk = tanggalMasuk;
        idNota++;
        this.idBon = idNota;
    }

    //tambahkan methods yang diperlukan untuk class ini
    // getter

    public int getId(){
        return this.idBon;
    }
    public int getBerat(){
        return this.berat;
    }

    public int getSisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }

    public String getPaket(){
        return this.paket;
    }

    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }

    public Member getMember(){
        return this.member;
    }

    public boolean GetIsReady(){
        return this.isReady;
    }

    public void setSisaHariPengerjaan(){
        if (this.sisaHariPengerjaan > 1){
            this.sisaHariPengerjaan-=1;
        } else {
            isReady = true;
        }
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
     *         <p>Status          : Belum bisa diambil :(
     */

    public String generateNota(){
        this.member.setBonusCounter();
        String output;
        //Implement generate nota sesuai soal.
        long harga, hargaPerKilo, hargaDiskon;
        harga = 0;
        if (paket.equalsIgnoreCase("express")){
            hargaPerKilo = 12000;
            harga+=berat*hargaPerKilo;
            this.sisaHariPengerjaan = 1;
        } else if (paket.equalsIgnoreCase("fast")){
            hargaPerKilo = 10000;
            harga+=berat*hargaPerKilo;
            this.sisaHariPengerjaan = 2;
        } else {
            hargaPerKilo = 7000;
            harga+=berat*hargaPerKilo;
            this.sisaHariPengerjaan = 3;
        }

        output ="[ID Nota = "+this.idBon+"]\n"+"ID    : "+ this.getMember().getId() + "\n" + "Paket : "+ this.getPaket()+"\n"+ "Harga :\n"+Integer.toString(this.getBerat());
        output+=" kg x "+Long.toString(hargaPerKilo)+" = "+Long.toString(harga);

        if (this.member.getBonusCounter() == 3){
            hargaDiskon=(harga/2);
            this.member.setBonusCounter();
            output+=" = "+Long.toString(hargaDiskon)+" (Discount member 50%!!!)";
            
        }
        output+="\nTanggal Terima  : "+this.getTanggalMasuk()+"\nTanggal Selesai : ";
        try{
            output+=countDays(this.getTanggalMasuk(), this.getSisaHariPengerjaan());
        } catch (Exception e){
            output+="date error";
        }
        output+="\nStatus          : "+"Belum bisa diambil :(";
        return output;
    }

    private static String countDays(String date1, int hari) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date1));
        c.add(Calendar.DATE, hari);  // number of days to add
        return sdf.format(c.getTime());
    }
}