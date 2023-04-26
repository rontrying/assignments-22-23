package assignments.assignment3.nota;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;
    private long harga;
    private String tanggalSelesai;
    private int hariTerlambat;
    private int counterCalculateHarga;

    public Nota(Member member, int berat, String paket, String tanggal) {
        //construct harga awal dan isi service cuci
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggal;
        this.id = totalNota++;
        this.harga = calculateHarga();
        CuciService cuciService = new CuciService();
        this.addService(cuciService);
    }
    public void addService(LaundryService service){
        //memasukan objek service cuci, antar, setrika
        if (this.services == null){
            LaundryService[] newServices = new LaundryService[1];
            services = newServices;
            newServices[services.length-1] = service;
        }
        else {
            LaundryService[] newServices = new LaundryService[services.length+1];
            System.arraycopy(services, 0, newServices, 0, services.length);
            services = newServices;
            newServices[services.length-1] = service;
            harga+=service.getHarga(this.berat);
        }
    }

    public String kerjakan(){
        // perintah menampilkan pekerjaan yang dilakukan employee
        String output = "";
        if (services == null || services[services.length-1].isDone()){
            return "Nota " + this.id + " : Sudah selesai.";
        }
        else if (services != null){
            for (LaundryService laundryService : services){
                if (!(laundryService.isDone())){
                    output = laundryService.doWork();
                    if (services[services.length-1].isDone()){
                        this.isDone = true;
                    }
                    return "Nota " + this.id + " : " +output;
                }
            }
        }
        return "";
    }
    public void toNextDay() {
        // buat menghitung hari esok bagi nota
        this.sisaHariPengerjaan-=1;
        if (this.sisaHariPengerjaan < 0 && !(this.isDone)){
            hariTerlambat++;
            if ((harga - 2000L) > 0) {
                harga -= 2000L;
            } else {
                harga = 0L;
            }
        }

    }

    public long calculateHarga(){
        long harga = this.harga;
        if (this.counterCalculateHarga == 0){
            // buat hitung harga cucian saat minta detail nota
            if (paket.equalsIgnoreCase("express")){
                baseHarga = 12000L;
                harga+=berat*baseHarga;
                this.sisaHariPengerjaan = 1;
            } else if (paket.equalsIgnoreCase("fast")){
                baseHarga = 10000L;
                harga+=berat*baseHarga;
                this.sisaHariPengerjaan = 2;
            } else {
                baseHarga = 7000L;
                harga+=berat*baseHarga;
                this.sisaHariPengerjaan = 3;
            }

            try{
                this.tanggalSelesai = NotaManager.countDays(this.tanggalMasuk, this.sisaHariPengerjaan);
            } catch (Exception ignored){

            }
            this.counterCalculateHarga++;
        }

        return harga;
    }

    public String getNotaStatus(){
        // perintah lihat status nota bagi employee (sudah selesai/belum)
        if (services == null){
            return "";
        }
        if (services[services.length-1].isDone()){
            this.isDone = true;
        }

        if (this.isDone){
            return "Nota " + this.id + " : Sudah selesai.";
        } else {
            return "Nota " + this.id + " : Belum selesai.";
        }
    }

    @Override
    public String toString(){
        // print keterangan pelanggan ttg nota
        String output = "[ID Nota = "+this.id+"]\n" +
                "ID    : "+this.member.getId()+"\n"
                + "Paket : " + this.paket + "\n"
                + "Harga :\n"
                + berat + " kg x " + this.baseHarga + " = "+(this.baseHarga*berat)+"\n";
        output+="tanggal terima  : "+this.tanggalMasuk+"\ntanggal selesai : ";
        try{
            output+=this.tanggalSelesai;
        } catch (Exception e){
            output+="date error";
        }
        output+="\n--- SERVICE LIST ---\n" +
                "-Cuci @ Rp.0\n";

        if (!(hitungBiayaService("Setrika") == -1L)){
            output+="-Setrika @ Rp." + hitungBiayaService("Setrika")+"\n";
        }

        if (!(hitungBiayaService("Antar") == -1L)){
            output+="-Antar @ Rp." + hitungBiayaService("Antar")+"\n";
        }

        if (hariTerlambat != 0){
            output+="Harga Akhir: "+ harga + " Ada kompensasi keterlambatan " + hariTerlambat + " * 2000 hari\n";
        } else{
            output+="Harga Akhir: "+ harga+"\n";
        }
        return output;
    }
    
    
    private Long hitungBiayaService(String perintah) {
        // untuk mengeluarkan output harga layanan
        if (services != null) {
            for (LaundryService laundryService : services) {
                if (perintah.equals("Antar")) {
                    if (laundryService instanceof AntarService a) {
                        return a.getHarga(this.berat);
                    }
                } else if (perintah.equals("Setrika")) {
                    if (laundryService instanceof SetrikaService s) {
                        return s.getHarga(this.berat);
                    }
                }
            }
        }
        return -1L;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
}
