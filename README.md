# Aplikasi Penilaian Siswa (Java Swing)


Proyek ini dibuat untuk memenuhi tugas **Ujian Tengah Semester (UTS)**.

---

## Identitas Mahasiswa

*   **Nama:** Supardi Akhiyat
*   **NIM:** 230101010026

---

## Prasyarat Sistem

Sebelum menjalankan proyek ini, pastikan Anda telah menginstal:
*   **Java Development Kit (JDK)** versi 8 atau yang lebih baru.
*   Pustaka **FlatLaf** (sudah tersedia di dalam direktori proyek sebagai file `flatlaf-3.7.1.jar`).

---

## Cara Instalasi & Menjalankan Proyek

Ikuti langkah-langkah berikut untuk mengompilasi dan menjalankan program menggunakan Terminal / Command Prompt / PowerShell:

### 1. Unduh atau Klon Repositori
Pastikan semua file proyek berikut berada dalam satu direktori yang sama:
*   `Siswa.java`
*   `PenilaianSiswa.java`
*   `flatlaf-3.7.1.jar`
*   `logo.png`

### 2. Kompilasi Kode Program
Buka terminal/command prompt pada direktori proyek tersebut, lalu jalankan perintah kompilasi berikut:

*   **Windows / macOS / Linux:**
    ```bash
    javac -cp "flatlaf-3.7.1.jar" Siswa.java PenilaianSiswa.java
    ```

### 3. Jalankan Aplikasi
Setelah proses kompilasi selesai tanpa error, jalankan aplikasi menggunakan perintah sesuai sistem operasi Anda:

*   **Windows (Command Prompt / PowerShell):**
    ```powershell
    java -cp "flatlaf-3.7.1.jar;." PenilaianSiswa
    ```
*   **macOS / Linux:**
    ```bash
    java -cp "flatlaf-3.7.1.jar:." PenilaianSiswa
    ```



