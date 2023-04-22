package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay(){
        //TODO: implement skip hari
        cal.add(Calendar.DATE, 1);
        for (Nota nota : notaList){
            nota.toNextDay();
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota){
        //add nota yang sudah dibuat ke dalam notalist
        Nota[] newNotaList = new Nota[notaList.length+1];
        System.arraycopy(notaList, 0, newNotaList, 0, notaList.length);
        notaList = newNotaList;
        newNotaList[notaList.length-1] = nota;
    }

    /**
     * Method untuk melakukan perhitungan hari
     * menggunakan simple date format dan java calender
     */
    public static String countDays(String date1, int hari) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date1));
        c.add(Calendar.DATE, hari);  // number of days to add
        return sdf.format(c.getTime());
    }
}
