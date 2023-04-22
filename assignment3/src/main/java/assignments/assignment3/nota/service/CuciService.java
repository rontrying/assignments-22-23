package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    boolean isDone;
    @Override
    public String doWork() {
        // untuk menampilkan pesan bagi pekerja
        this.isDone = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        // untuk sebagai tanda apakah cucian sudah mulai dicuci atau belum
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // return 0 karena sudah termasuk harga paket
        return 0L;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
