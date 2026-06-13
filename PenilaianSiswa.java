import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Kelas Utama GUI Penilaian Siswa.
 */
public class PenilaianSiswa extends JFrame {
    // Komponen GUI Panel Informasi
    private JTextField txtJumlahSiswa;
    private JTextField txtTahunAjaran;
    private JButton btnInputData;

    // Komponen GUI Panel Detail Daftar Siswa
    private JTextField txtNoDetail;
    private JTextField txtNamaDetail;
    private JTextField txtNilaiDetail;
    private JTextField txtGradeDetail;
    private JTextField txtStatusDetail;

    // Komponen JTable
    private JTable tabelSiswa;
    private DefaultTableModel tabelModel;

    // Komponen Ringkasan Analisis
    private JLabel lblRataRata;
    private JLabel lblNilaiTertinggi;

    // List Data Siswa
    private final List<Siswa> daftarSiswa = new ArrayList<>();

    // Konstanta Warna dan Font untuk Desain Premium
    private static final Color WARNA_BIRU = new Color(31, 56, 92);
    private static final Color WARNA_BG = new Color(245, 247, 250);
    private static final Color WARNA_BORDER = new Color(218, 224, 233);
    private static final Color WARNA_HIJAU_TEKS = new Color(40, 167, 69);
    private static final Color WARNA_ORANYE_TEKS = new Color(253, 126, 20);
    private static final Color WARNA_MERAH_TEKS = new Color(220, 53, 69);

    private static final Font FONT_BBIASA = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BTEBAL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_BTITEL = new Font("Segoe UI", Font.BOLD, 12);

    public PenilaianSiswa() {
        setTitle("Aplikasi Penilaian Siswa");
        setSize(950, 830);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(WARNA_BG);

        // Menggunakan BorderLayout sebagai layout utama frame
        setLayout(new BorderLayout(0, 0));

        // Menambahkan panel header dengan logo di bagian atas (NORTH)
        add(buatPanelHeader(), BorderLayout.NORTH);

        // Membuat padding luar kontainer utama untuk body aplikasi (CENTER)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(WARNA_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 0);

        // 1. Baris Pertama: Panel Informasi
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        mainPanel.add(buatPanelInformasi(), gbc);

        // 2. Baris Kedua: Panel Daftar Siswa (Kiri) dan Keterangan Grade (Kanan)
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.35;
        gbc.insets = new Insets(0, 0, 15, 15);
        mainPanel.add(buatPanelDaftarSiswa(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.45;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(buatPanelKeteranganGrade(), gbc);

        // 3. Baris Ketiga: Panel Tabel Nilai Siswa
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.60;
        mainPanel.add(buatPanelTabelNilaiSiswa(), gbc);

        // Event Listener untuk Tombol Input
        btnInputData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesInputDataSiswa();
            }
        });

        // Event Listener untuk Seleksi Baris Tabel
        tabelSiswa.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tampilkanDetailSiswaTerpilih();
            }
        });

        // Inisialisasi 5 data awal bawaan aplikasi
        inisialisasiDataAwal();
    }

    /**
     * Membuat Titled Border kustom berwarna biru dengan font tebal
     */
    private TitledBorder buatTitledBorder(String judul) {
        Border lineBorder = BorderFactory.createLineBorder(WARNA_BORDER, 1, true);
        return BorderFactory.createTitledBorder(
                lineBorder,
                judul,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_BTITEL,
                WARNA_BIRU
        );
    }

    /**
     * Membuat Panel Informasi (Baris Atas)
     */
    private JPanel buatPanelInformasi() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WARNA_BG);
        panel.setBorder(buatTitledBorder("INFORMASI"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Jumlah Siswa
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        JLabel lblJumlahSiswa = new JLabel("Jumlah Siswa");
        lblJumlahSiswa.setFont(FONT_BTEBAL);
        panel.add(lblJumlahSiswa, gbc);

        // Text Field Jumlah Siswa (Non-editable karena mewakili data aktual yang sudah ada)
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        txtJumlahSiswa = new JTextField("5");
        txtJumlahSiswa.setEditable(false);
        txtJumlahSiswa.setBackground(new Color(240, 242, 245));
        txtJumlahSiswa.setFont(FONT_BBIASA);
        txtJumlahSiswa.setPreferredSize(new Dimension(80, 28));
        panel.add(txtJumlahSiswa, gbc);

        // Label Tahun Ajaran
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        JLabel lblTahunAjaran = new JLabel("Tahun Ajaran");
        lblTahunAjaran.setFont(FONT_BTEBAL);
        panel.add(lblTahunAjaran, gbc);

        // Text Field Tahun Ajaran
        gbc.gridx = 3;
        gbc.weightx = 0.25;
        txtTahunAjaran = new JTextField("2024/2025");
        txtTahunAjaran.setFont(FONT_BBIASA);
        txtTahunAjaran.setPreferredSize(new Dimension(150, 28));
        panel.add(txtTahunAjaran, gbc);

        // Tombol Tambah Siswa (Input Data) dengan background biru yang kompatibel di Windows L&F
        gbc.gridx = 4;
        gbc.weightx = 0.4;
        btnInputData = new JButton("Tambah Siswa") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(WARNA_BIRU.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(50, 100, 240));
                } else {
                    g2.setColor(WARNA_BIRU);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnInputData.setFont(FONT_BTEBAL);
        btnInputData.setForeground(Color.WHITE);
        btnInputData.setContentAreaFilled(false);
        btnInputData.setFocusPainted(false);
        btnInputData.setBorderPainted(false);
        btnInputData.setOpaque(false);
        btnInputData.setPreferredSize(new Dimension(180, 30));
        panel.add(btnInputData, gbc);

        return panel;
    }

    /**
     * Membuat Panel Form Detail Daftar Siswa (Kiri Tengah)
     */
    private JPanel buatPanelDaftarSiswa() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WARNA_BG);
        panel.setBorder(buatTitledBorder("DAFTAR SISWA"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labelTexts = {"NO", "NAMA SISWA", "NILAI", "GRADE", "STATUS"};
        JTextField[] fields = {
                txtNoDetail = new JTextField("-"),
                txtNamaDetail = new JTextField("-"),
                txtNilaiDetail = new JTextField("-"),
                txtGradeDetail = new JTextField("-"),
                txtStatusDetail = new JTextField("-")
        };

        for (int i = 0; i < labelTexts.length; i++) {
            gbc.gridy = i;

            // Label Kiri
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            JLabel lbl = new JLabel(labelTexts[i]);
            lbl.setFont(FONT_BTEBAL);
            panel.add(lbl, gbc);

            // Titik Dua ":"
            gbc.gridx = 1;
            gbc.weightx = 0.05;
            JLabel lblColon = new JLabel(":");
            lblColon.setFont(FONT_BTEBAL);
            panel.add(lblColon, gbc);

            // TextField Detail (semuanya diset non-editable karena ini adalah panel view detail)
            gbc.gridx = 2;
            gbc.weightx = 0.65;
            fields[i].setEditable(false);
            fields[i].setFont(FONT_BBIASA);
            fields[i].setBackground(new Color(240, 242, 245));
            fields[i].setPreferredSize(new Dimension(200, 26));
            panel.add(fields[i], gbc);
        }

        return panel;
    }

    /**
     * Membuat Panel Keterangan Grade (Kanan Tengah)
     */
    private JPanel buatPanelKeteranganGrade() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WARNA_BG);
        panel.setBorder(buatTitledBorder("KETERANGAN GRADE"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kriteria Grade sesuai spesifikasi UTS
        String[][] gradeCriteria = {
                {"A", " :  85 - 100", "(LULUS)"},
                {"B", " :  75 - 84", "(LULUS)"},
                {"C", " :  65 - 74", "(TIDAK LULUS)"},
                {"D", " :  50 - 64", "(TIDAK LULUS)"},
                {"E", " :  0 - 49", "(TIDAK LULUS)"}
        };

        for (int i = 0; i < gradeCriteria.length; i++) {
            gbc.gridy = i;

            // Grade Huruf (A, B, C, dsb)
            gbc.gridx = 0;
            gbc.weightx = 0.1;
            JLabel lblGrade = new JLabel(gradeCriteria[i][0]);
            lblGrade.setFont(FONT_BTEBAL);
            panel.add(lblGrade, gbc);

            // Rentang Nilai
            gbc.gridx = 1;
            gbc.weightx = 0.4;
            JLabel lblRange = new JLabel(gradeCriteria[i][1]);
            lblRange.setFont(FONT_BBIASA);
            panel.add(lblRange, gbc);

            // Status Kelulusan
            gbc.gridx = 2;
            gbc.weightx = 0.5;
            JLabel lblStatus = new JLabel(gradeCriteria[i][2]);
            lblStatus.setFont(FONT_BBIASA);
            // Memberikan warna teks sesuai status
            if (gradeCriteria[i][2].contains("LULUS") && !gradeCriteria[i][2].contains("TIDAK")) {
                lblStatus.setForeground(WARNA_HIJAU_TEKS);
            } else {
                lblStatus.setForeground(WARNA_MERAH_TEKS);
            }
            panel.add(lblStatus, gbc);
        }

        return panel;
    }

    /**
     * Membuat Panel Tabel Nilai Siswa (Baris Bawah)
     */
    private JPanel buatPanelTabelNilaiSiswa() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(WARNA_BG);
        panel.setBorder(buatTitledBorder("TABEL NILAI SISWA"));

        // Definisi Kolom Tabel
        String[] columnNames = {"NO", "NAMA", "NILAI", "GRADE", "STATUS"};
        tabelModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel dibuat read-only karena diinput via Dialog For
            }
        };

        tabelSiswa = new JTable(tabelModel);
        tabelSiswa.setFont(FONT_BBIASA);
        tabelSiswa.setRowHeight(32);
        tabelSiswa.setGridColor(WARNA_BORDER);
        tabelSiswa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Mengatur Layout Header Tabel dengan Renderer Kustom agar warna biru tampil di semua Look and Feel
        JTableHeader header = tabelSiswa.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(WARNA_BIRU);
                label.setForeground(Color.WHITE);
                label.setFont(FONT_BTEBAL);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(255, 255, 255, 50)));
                return label;
            }
        });

        // Mengatur Perataan Kolom & Pewarnaan Kustom
        tabelSiswa.getColumnModel().getColumn(0).setPreferredWidth(50);   // NO
        tabelSiswa.getColumnModel().getColumn(1).setPreferredWidth(250);  // NAMA
        tabelSiswa.getColumnModel().getColumn(2).setPreferredWidth(120);  // NILAI
        tabelSiswa.getColumnModel().getColumn(3).setPreferredWidth(120);  // GRADE
        tabelSiswa.getColumnModel().getColumn(4).setPreferredWidth(150);  // STATUS

        // Renderer Kolom
        tabelSiswa.getColumnModel().getColumn(0).setCellRenderer(buatRendererTengah());
        tabelSiswa.getColumnModel().getColumn(1).setCellRenderer(buatRendererKiri());
        tabelSiswa.getColumnModel().getColumn(2).setCellRenderer(buatRendererTengah());
        tabelSiswa.getColumnModel().getColumn(3).setCellRenderer(buatRendererWarnaKustom(3));
        tabelSiswa.getColumnModel().getColumn(4).setCellRenderer(buatRendererWarnaKustom(4));

        JScrollPane scrollPane = new JScrollPane(tabelSiswa);
        scrollPane.setBorder(BorderFactory.createLineBorder(WARNA_BORDER));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Membuat Panel Analisis Ringkasan (Rata-rata & Nilai Tertinggi)
        JPanel panelAnalisis = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 8));
        panelAnalisis.setBackground(WARNA_BG);
        panelAnalisis.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblRataTitle = new JLabel("Rata-rata Kelas :");
        lblRataTitle.setFont(FONT_BBIASA);
        lblRataRata = new JLabel("-");
        lblRataRata.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRataRata.setForeground(WARNA_BIRU);

        JLabel lblTinggiTitle = new JLabel("Nilai Tertinggi :");
        lblTinggiTitle.setFont(FONT_BBIASA);
        lblNilaiTertinggi = new JLabel("-");
        lblNilaiTertinggi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNilaiTertinggi.setForeground(WARNA_BIRU);

        panelAnalisis.add(lblRataTitle);
        panelAnalisis.add(lblRataRata);
        panelAnalisis.add(new JLabel("  |  ")); // Pembatas visual
        panelAnalisis.add(lblTinggiTitle);
        panelAnalisis.add(lblNilaiTertinggi);

        panel.add(panelAnalisis, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Membuat Cell Renderer dengan perataan tengah
     */
    private DefaultTableCellRenderer buatRendererTengah() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(225, 238, 255));
                    c.setForeground(table.getForeground());
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 253));
                    c.setForeground(table.getForeground());
                }
                return c;
            }
        };
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        return renderer;
    }

    /**
     * Membuat Cell Renderer dengan perataan kiri untuk Nama
     */
    private DefaultTableCellRenderer buatRendererKiri() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(225, 238, 255));
                    c.setForeground(table.getForeground());
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 253));
                    c.setForeground(table.getForeground());
                }
                return c;
            }
        };
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        // Tambahkan sedikit indentasi kiri agar teks Nama tidak menempel garis border
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return renderer;
    }

    /**
     * Membuat Cell Renderer kustom untuk warna teks Grade dan Status kelulusan
     */
    private DefaultTableCellRenderer buatRendererWarnaKustom(int kolomTipe) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(FONT_BTEBAL);

                if (isSelected) {
                    cell.setBackground(new Color(225, 238, 255));
                } else {
                    cell.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 253));
                }

                if (value != null) {
                    String valStr = value.toString();
                    if (kolomTipe == 3) { // Kolom Grade
                        switch (valStr) {
                            case "A":
                                cell.setForeground(WARNA_HIJAU_TEKS);
                                break;
                            case "B":
                            case "C":
                            case "D":
                                cell.setForeground(WARNA_ORANYE_TEKS);
                                break;
                            case "E":
                                cell.setForeground(WARNA_MERAH_TEKS);
                                break;
                            default:
                                cell.setForeground(table.getForeground());
                        }
                    } else if (kolomTipe == 4) { // Kolom Status
                        if (valStr.equals("LULUS")) {
                            cell.setForeground(WARNA_HIJAU_TEKS);
                        } else {
                            cell.setForeground(WARNA_MERAH_TEKS);
                        }
                    }
                }
                return cell;
            }
        };
        return renderer;
    }

    /**
     * Menginisialisasi 5 data siswa awal bawaan aplikasi (Fauziah, Tsarwan, Lilis, Fauzi, Marcel)
     */
    private void inisialisasiDataAwal() {
        daftarSiswa.add(new Siswa(1, "Fauzi", 90.0));
        daftarSiswa.add(new Siswa(2, "Dika", 70.0));
        daftarSiswa.add(new Siswa(3, "Tuti", 40.0));
        daftarSiswa.add(new Siswa(4, "Danias", 50.0));
        daftarSiswa.add(new Siswa(5, "Tania", 95.0));

        for (Siswa siswa : daftarSiswa) {
            tabelModel.addRow(new Object[]{
                    siswa.getNo(),
                    siswa.getNama(),
                    siswa.getNilai(),
                    siswa.getGrade(),
                    siswa.getStatus()
            });
        }

        txtJumlahSiswa.setText(String.valueOf(daftarSiswa.size()));
        hitungDanTampilkanAnalisis();
    }

    /**
     * Membuat Panel Header di bagian paling atas (NORTH) berisi logo.png dan nama aplikasi.
     */
    private JPanel buatPanelHeader() {
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, WARNA_BORDER),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;

        // Memuat logo.png
        ImageIcon logoIcon = null;
        try {
            ImageIcon rawIcon = new ImageIcon("logo.png");
            if (rawIcon.getIconWidth() > 0) {
                Image img = rawIcon.getImage();
                int h = 50;
                int w = (int) (((double) rawIcon.getIconWidth() / rawIcon.getIconHeight()) * h);
                Image scaledImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(scaledImg);
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat logo.png: " + e.getMessage());
        }

        int col = 0;
        if (logoIcon != null) {
            gbc.gridx = col++;
            gbc.weightx = 0.0;
            gbc.insets = new Insets(0, 0, 0, 15);
            JLabel lblLogo = new JLabel(logoIcon);
            headerPanel.add(lblLogo, gbc);
        }

        // Teks Judul dan Subjudul
        gbc.gridx = col;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        JPanel panelTeks = new JPanel(new GridLayout(2, 1, 2, 2));
        panelTeks.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("APLIKASI PENILAIAN SISWA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(WARNA_BIRU);

        JLabel lblSubtitle = new JLabel("Oleh Supardi Akhiyat | 230101010026");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSubtitle.setForeground(new Color(110, 120, 135));

        panelTeks.add(lblTitle);
        panelTeks.add(lblSubtitle);

        headerPanel.add(panelTeks, gbc);

        return headerPanel;
    }

    /**
     * Proses Tambah Data Siswa Satu per Satu.
     * Langsung memunculkan satu dialog kustom untuk menginput Nama dan Nilai siswa berikutnya.
     */
    private void prosesInputDataSiswa() {
        int nextNo = daftarSiswa.size() + 1;

        // Membuat panel input kustom yang rapi
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Label & Field untuk NO
        c.gridx = 0; c.gridy = 0; c.weightx = 0.3;
        JLabel lblNo = new JLabel("No Siswa");
        lblNo.setFont(FONT_BTEBAL);
        inputPanel.add(lblNo, c);

        c.gridx = 1; c.weightx = 0.7;
        JTextField txtNo = new JTextField(String.valueOf(nextNo));
        txtNo.setEditable(false);
        txtNo.setFont(FONT_BBIASA);
        txtNo.setBackground(new Color(240, 242, 245));
        txtNo.setPreferredSize(new Dimension(220, 28));
        inputPanel.add(txtNo, c);

        // Label & Field untuk Nama
        c.gridx = 0; c.gridy = 1; c.weightx = 0.3;
        JLabel lblNama = new JLabel("Nama Siswa");
        lblNama.setFont(FONT_BTEBAL);
        inputPanel.add(lblNama, c);

        c.gridx = 1; c.weightx = 0.7;
        JTextField txtNama = new JTextField();
        txtNama.setFont(FONT_BBIASA);
        txtNama.setPreferredSize(new Dimension(220, 28));
        inputPanel.add(txtNama, c);

        // Label & Field untuk Nilai
        c.gridx = 0; c.gridy = 2; c.weightx = 0.3;
        JLabel lblNilai = new JLabel("Nilai Siswa");
        lblNilai.setFont(FONT_BTEBAL);
        inputPanel.add(lblNilai, c);

        c.gridx = 1; c.weightx = 0.7;
        JTextField txtNilaiInput = new JTextField();
        txtNilaiInput.setFont(FONT_BBIASA);
        txtNilaiInput.setPreferredSize(new Dimension(220, 28));
        inputPanel.add(txtNilaiInput, c);

        // Set focus otomatis ke text field Nama saat dialog terbuka
        SwingUtilities.invokeLater(() -> txtNama.requestFocusInWindow());

        // Tampilkan dialog konfirmasi dengan panel kustom
        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "Tambah Siswa Baru (No. " + nextNo + ")",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return; // Batalkan jika user tidak klik OK
        }

        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama siswa tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nilaiStr = txtNilaiInput.getText().trim();
        if (nilaiStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nilai tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double nilai = 0;
        try {
            nilai = Double.parseDouble(nilaiStr);
            if (nilai < 0 || nilai > 100) {
                JOptionPane.showMessageDialog(this, "Nilai harus di rentang 0 sampai 100!", "Error Nilai", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format nilai salah! Harap masukkan angka desimal/bulat (contoh: 80.5)", "Error Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Instansiasi Object Siswa baru (OOP)
        Siswa siswa = new Siswa(nextNo, nama, nilai);
        daftarSiswa.add(siswa);

        // Tambahkan data ke tabel
        tabelModel.addRow(new Object[]{
                siswa.getNo(),
                siswa.getNama(),
                siswa.getNilai(),
                siswa.getGrade(),
                siswa.getStatus()
        });

        // Update jumlah siswa dan statistik
        txtJumlahSiswa.setText(String.valueOf(daftarSiswa.size()));
        hitungDanTampilkanAnalisis();
        JOptionPane.showMessageDialog(this, "Berhasil menambahkan siswa: " + nama + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menghitung nilai rata-rata kelas dan mencari nilai tertinggi siswa
     */
    private void hitungDanTampilkanAnalisis() {
        if (daftarSiswa.isEmpty()) {
            lblRataRata.setText("-");
            lblNilaiTertinggi.setText("-");
            return;
        }

        double total = 0;
        double tertinggi = -1;

        for (Siswa s : daftarSiswa) {
            total += s.getNilai();
            if (s.getNilai() > tertinggi) {
                tertinggi = s.getNilai();
            }
        }

        double rataRata = total / daftarSiswa.size();

        // Format angka ke format desimal lokal
        DecimalFormat df = new DecimalFormat("#.##");

        lblRataRata.setText(df.format(rataRata));
        lblNilaiTertinggi.setText(df.format(tertinggi));
    }

    /**
     * Menampilkan detail data dari siswa yang baris tabelnya sedang diseleksi
     */
    private void tampilkanDetailSiswaTerpilih() {
        int selectedRow = tabelSiswa.getSelectedRow();
        if (selectedRow != -1) {
            txtNoDetail.setText(tabelSiswa.getValueAt(selectedRow, 0).toString());
            txtNamaDetail.setText(tabelSiswa.getValueAt(selectedRow, 1).toString());
            txtNilaiDetail.setText(tabelSiswa.getValueAt(selectedRow, 2).toString());
            txtGradeDetail.setText(tabelSiswa.getValueAt(selectedRow, 3).toString());
            txtStatusDetail.setText(tabelSiswa.getValueAt(selectedRow, 4).toString());
        } else {
            clearDetailFields();
        }
    }

    /**
     * Mengosongkan form detail daftar siswa di sebelah kiri
     */
    private void clearDetailFields() {
        txtNoDetail.setText("-");
        txtNamaDetail.setText("-");
        txtNilaiDetail.setText("-");
        txtGradeDetail.setText("-");
        txtStatusDetail.setText("-");
    }

    /**
     * Method main untuk menjalankan aplikasi
     */
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
}
