package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    boolean isDone;
    @Override
    public String doWork() {
        this.isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
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
