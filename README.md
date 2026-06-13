# Analisis Kode Program: Aplikasi Penilaian Siswa

Aplikasi **Penilaian Siswa** ini adalah program berbasis Desktop (GUI) menggunakan Java Swing. Program ini menerapkan prinsip Pemrograman Berbasis Objek (OOP) untuk mengelola data nilai siswa, menentukan *grade*, serta status kelulusan.

Berikut adalah analisis dan penjabaran dari komponen utama dalam kode program:

## 1. Konsep OOP: Class `Siswa`
Class `Siswa` digunakan sebagai Model data untuk merepresentasikan satu entitas siswa. Pendekatan ini menunjukkan prinsip enkapsulasi di mana atribut penting disembunyikan menggunakan _access modifier_ `private`, dan diakses melalui _getter_.

```java
class Siswa {
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
    // ... getter methods ...
}
```

## 2. Logika Penentuan Grade & Status Kelulusan
Di dalam *constructor* `Siswa`, dipanggil sebuah *method private* `tentukanGradeDanStatus()` yang bertugas menghitung Grade (A, B, C, D, E) dan Status (LULUS / TIDAK LULUS) berdasarkan struktur kontrol `if-else`.

```java
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
```

## 3. Desain Antarmuka (GUI) Utama
Class `PenilaianSiswa` bertindak sebagai *View* sekaligus *Controller* yang mewarisi `JFrame`. Di dalamnya terdapat banyak komponen antarmuka seperti `JTextField`, `JLabel`, `JButton`, dan `JTable`. Layout dibagi secara rapi menggunakan kombinasi `BorderLayout` dan `GridBagLayout`.

```java
public class PenilaianSiswa extends JFrame {
    // Komponen GUI Panel Informasi
    private JTextField txtJumlahSiswa;
    private JTextField txtTahunAjaran;
    private JButton btnInputData;

    // List Data Siswa yang menyimpan objek Siswa
    private final List<Siswa> daftarSiswa = new ArrayList<>();
    // ... inisialisasi layout GUI
}
```

## 4. Analisis Rata-rata dan Nilai Tertinggi
Selain menyimpan data, program ini memiliki fungsi perulangan untuk menghitung nilai rata-rata kelas dan menemukan nilai tertinggi dari kumpulan objek siswa yang tersimpan di dalam `ArrayList`.

```java
private void hitungDanTampilkanAnalisis() {
    double total = 0;
    double tertinggi = -1;

    for (Siswa s : daftarSiswa) {
        total += s.getNilai();
        if (s.getNilai() > tertinggi) {
            tertinggi = s.getNilai();
        }
    }

    double rataRata = total / daftarSiswa.size();
    // ... menampilkannya di Label
}
```

## 5. Kustomisasi Tampilan Modern dengan FlatLaf
Untuk menghindari tampilan Java Swing bawaan yang terkesan usang, kode program dimodifikasi di bagian method `main` agar memuat *Look and Feel* modern dari pustaka **FlatLaf** (`FlatLightLaf`).

```java
import com.formdev.flatlaf.FlatLightLaf;

public static void main(String[] args) {
    // Menggunakan FlatLaf untuk tampilan modern
    try {
        FlatLightLaf.setup();
    } catch (Exception ignored) {
    }

    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new PenilaianSiswa().setVisible(true);
        }
    });
}
```

## Kesimpulan
Kode ini terstruktur dengan sangat baik memisahkan bagian representasi *Data* (`Siswa`) dan *User Interface* (`PenilaianSiswa`). Penggunaan **List/ArrayList** memungkinkan penyimpanan data secara dinamis, sedangkan integrasi **FlatLaf** memastikan aplikasi memiliki UI yang segar dan elegan.
