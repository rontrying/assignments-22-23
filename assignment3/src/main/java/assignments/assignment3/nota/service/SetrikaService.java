package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    boolean isDone;
    @Override
    public String doWork() {
        // untuk menampilkan pesan bagi pekerja
        this.isDone = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        // untuk sebagai tanda apakah setrika sudah mulai dilakukan atau belum
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        // harga per kilo setrika adalah 1000
        return berat * 1000L;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
