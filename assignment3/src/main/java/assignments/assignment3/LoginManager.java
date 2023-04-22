package assignments.assignment3;

import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;

public class LoginManager {
    private final EmployeeSystem employeeSystem;
    private final MemberSystem memberSystem;

    public LoginManager(EmployeeSystem employeeSystem, MemberSystem memberSystem) {
        this.employeeSystem = employeeSystem;
        this.memberSystem = memberSystem;
    }

    /**
     * Method mapping dari ke SystemCLI yang sesuai.
     *
     * @param id -> ID dari user yang akan menggunakan SystemCLI
     * @return SystemCLI object yang sesuai dengan ID, null if  ID tidak ditemukan.
     */
    public SystemCLI getSystem(String id){
        if(memberSystem.isMemberExist(id)){
            return memberSystem;
        }
        if(employeeSystem.isMemberExist(id)){
            return employeeSystem;
        }
        return null;
    }

    /**
     * Mendaftarkan member baru dengan informasi yang diberikan.
     *
     * @param nama -> Nama member.
     * @param noHp -> Nomor handphone member.
     * @param password -> Password akun member.
     * @return Member object yang berhasil mendaftar, return null jika gagal mendaftar.
     */
    public Member register(String nama, String noHp, String password) {
        // membuat member
        String namaDepan = nama.split(" ")[0].toUpperCase();
        String digitChecksum = checksum(namaDepan + "-" + noHp, 0);
        String id = namaDepan + "-" + noHp + "-"+ digitChecksum;
        Member member = new Member(nama, id, password);
        if (memberSystem.isMemberExist(id) || validateNoHp(noHp).equals("-1")){
            return null;
        }
        memberSystem.addMember(member);
        return member;
    }

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
            hasil = hasil.substring(hasil.length()-2);
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
        return checksum(kalimat.substring(1),total);
    }

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
}