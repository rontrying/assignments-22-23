package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    boolean isDone;
    @Override
    public String doWork() {
        this.isDone = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        return berat * 1000L;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
