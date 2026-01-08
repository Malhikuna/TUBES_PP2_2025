-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 08, 2026 at 03:34 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pp2`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama_lengkap` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`, `nama_lengkap`) VALUES
(1, 'admin', '123', 'Hikmal');

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id_anggota` varchar(10) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  `status_aktif` enum('Aktif','Tidak Aktif') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id_anggota`, `nama`, `no_telp`, `status_aktif`) VALUES
('A001', 'Arkananta Zayyan', '081234567890', 'Aktif'),
('A002', 'Bagas Kara Wibowo', '081234567891', 'Aktif'),
('A003', 'Callista Maheswari', '081234567892', 'Aktif'),
('A004', 'Danendra Praditya', '081234567893', 'Aktif'),
('A005', 'Elara Prameswari', '081234567894', 'Tidak Aktif'),
('A006', 'Farel Alghifari', '081234567895', 'Aktif'),
('A007', 'Giska Felicia', '081234567896', 'Aktif'),
('A008', 'Haikal Ramadhan', '081234567897', 'Aktif'),
('A009', 'Ivana Salsabila', '081234567898', 'Aktif'),
('A010', 'Julian Verlando', '081234567899', 'Aktif'),
('A011', 'Ariel', '081234567890', 'Aktif'),
('A012', 'Alya', '089876543210', 'Aktif'),
('A013', 'Tubagus', '081122334455', 'Aktif'),
('A014', 'Ikhsan', '081234567892', 'Aktif'),
('A015', 'Afumado Zaki', '081222348908', 'Aktif'),
('A016', 'Hikmal Maulana', '089663850026', 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id_buku` varchar(10) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `pengarang` varchar(255) DEFAULT NULL,
  `kategori` varchar(100) DEFAULT NULL,
  `stok` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id_buku`, `judul`, `pengarang`, `kategori`, `stok`) VALUES
('B001', 'Laskar Pelangi', 'Andrea Hirata', 'Novel', 3),
('B002', 'Bumi Manusia', 'Pramoedya Ananta Toer', 'Sastra', 0),
('B003', 'Filosofi Teras', 'Henry Manampiring', 'Psikologi', 10),
('B004', 'Dilan 1990', 'Pidi Baiq', 'Novel', 8),
('B005', 'Atomic Habits', 'James Clear', 'Self Improvement', 1),
('B006', 'Sapiens', 'Yuval Noah Harari', 'Sejarah', 2),
('B007', 'Harry Potter and the Sorcerers Stone', 'J.K. Rowling', 'Fantasi', 6),
('B008', 'Laut Bercerita', 'Leila S. Chudori', 'Novel', 5),
('B009', 'Rich Dad Poor Dad', 'Robert Kiyosaki', 'Keuangan', 9),
('B010', 'Negeri 5 Menara', 'A. Fuadi', 'Novel', 5),
('B011', 'Pemrograman Web dengan PHP', 'Budi Raharjo', 'Teknologi', 14),
('B012', 'Algoritma dan Struktur Data', 'Rinaldi Munir', 'Teknologi', 12),
('B013', 'Sistem Basis Data', 'Fathansyah', 'Teknologi', 10),
('B014', 'Belajar Machine Learning', 'Dicoding Team', 'Teknologi', 8),
('B015', 'Sebuah Seni untuk Bersikap Bodo Amat', 'Mark Manson', 'Self Improvement', 11),
('B016', 'Pulang', 'Tere Liye', 'Novel', 6),
('B017', 'Hujan', 'Tere Liye', 'Novel', 7),
('B018', 'Cantik Itu Luka', 'Eka Kurniawan', 'Sastra', 4),
('B019', 'Garis Waktu', 'Fiersa Besari', 'Novel', 8),
('B020', 'Psikologi Kematian', 'Komaruddin Hidayat', 'Psikologi', 5),
('B021', 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 'Novel', 4),
('B022', 'Belajar Java untuk Pemula', 'Abdul Kadir', 'Edukasi', 10),
('B023', 'Laskar Pelangi', 'Andrea Hirata', 'Novel', 0),
('B024', 'Sistem Basis Data', 'Fathansyah', 'Edukasi', 6),
('B025', 'Beberapa Gaya saat Di Ranjang', 'Hikmal Maulana', 'Buku Dewasa', 1),
('B026', 'Momo', 'Michael Ende', 'Fantasi', 5);

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_log` int NOT NULL,
  `id_anggota` varchar(10) DEFAULT NULL,
  `id_buku` varchar(10) DEFAULT NULL,
  `tgl_pinjam` date DEFAULT NULL,
  `tgl_kembali` date DEFAULT NULL,
  `denda` double NOT NULL,
  `durasi` int NOT NULL,
  `status` enum('Dipinjam','Kembali') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_log`, `id_anggota`, `id_buku`, `tgl_pinjam`, `tgl_kembali`, `denda`, `durasi`, `status`) VALUES
(1, 'A001', 'B001', '2025-12-01', '2025-12-04', 0, 3, 'Kembali'),
(2, 'A002', 'B011', '2025-12-01', '2025-12-10', 5000, 9, 'Kembali'),
(3, 'A003', 'B004', '2025-12-25', '2026-01-07', 22000, 2, 'Kembali'),
(4, 'A006', 'B005', '2025-12-26', '2026-01-07', 22000, 1, 'Kembali'),
(5, 'A010', 'B006', '2025-12-20', '2025-12-23', 0, 3, 'Kembali'),
(23, 'A014', 'B005', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(24, 'A007', 'B005', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(25, 'A011', 'B005', '2026-01-08', '2026-01-08', 0, 7, 'Kembali'),
(26, 'A013', 'B001', '2026-01-08', '2026-01-08', 0, 7, 'Kembali'),
(27, 'A005', 'B001', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(28, 'A004', 'B006', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(29, 'A009', 'B006', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(30, 'A008', 'B006', '2026-01-08', '2026-01-08', 0, 7, 'Kembali'),
(31, 'A006', 'B024', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(32, 'A014', 'B011', '2026-01-08', NULL, 0, 7, 'Dipinjam'),
(33, 'A016', 'B026', '2026-01-08', '2026-01-08', 0, 7, 'Kembali');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id_anggota`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_log`),
  ADD KEY `id_anggota` (`id_anggota`),
  ADD KEY `id_buku` (`id_buku`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id_log` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `anggota` (`id_anggota`),
  ADD CONSTRAINT `peminjaman_ibfk_2` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
