package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    boolean isDone;
    @Override
    public String doWork() {
        // untuk menampilkan pesan bagi pekerja
        this.isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        // untuk sebagai tanda apakah anter sudah mulai atau belum
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // harga per kilo 500 dengan minimal 2000
        long harga = berat * 500L;
        if (harga < 2000){
            harga = 2000;
        }
        return harga;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
