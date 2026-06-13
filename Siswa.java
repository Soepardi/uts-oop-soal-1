/**
 * Kelas Model Siswa untuk merepresentasikan data siswa secara OOP.
 */
public class Siswa {
    private final int no;
    private final String nama;
    private final double nilai;
    private String grade;
    private String status;

    public Siswa(int no, String nama, double nilai) {
        this.no = no;
        this.nama = nama;
        this.nilai = nilai;
        tentukanGradeDanStatus();
    }

    /**
     * Menentukan grade dan status kelulusan berdasarkan nilai siswa.
     * Logika IF sesuai ketentuan UTS:
     * - Nilai >= 85 -> A
     * - Nilai >= 75 -> B
     * - Nilai >= 65 -> C
     * - Nilai >= 50 -> D
     * - Nilai < 50  -> E
     *
     * Ketentuan kelulusan:
     * - Nilai >= 75 -> LULUS
     * - Nilai < 75  -> TIDAK LULUS
     */
    private void tentukanGradeDanStatus() {
        // Logika IF untuk Grade
        if (this.nilai >= 85) {
            this.grade = "A";
        } else if (this.nilai >= 75) {
            this.grade = "B";
        } else if (this.nilai >= 65) {
            this.grade = "C";
        } else if (this.nilai >= 50) {
            this.grade = "D";
        } else {
            this.grade = "E";
        }

        // Logika IF untuk Status Kelulusan
        if (this.nilai >= 75) {
            this.status = "LULUS";
        } else {
            this.status = "TIDAK LULUS";
        }
    }

    public int getNo() { return no; }
    public String getNama() { return nama; }
    public double getNilai() { return nilai; }
    public String getGrade() { return grade; }
    public String getStatus() { return status; }
}
