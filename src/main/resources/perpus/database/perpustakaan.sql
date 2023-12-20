-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Des 2023 pada 06.32
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `nama_admin` varchar(50) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `kode_admin` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `buku`
--

CREATE TABLE `buku` (
  `id_buku` int(11) NOT NULL,
  `kode_buku` varchar(5) NOT NULL,
  `genre` varchar(50) NOT NULL,
  `judul` varchar(50) NOT NULL,
  `tahun_rilis` char(4) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `buku`
--

INSERT INTO `buku` (`id_buku`, `kode_buku`, `genre`, `judul`, `tahun_rilis`, `stok`) VALUES
(1, '1702', 'Sosial', 'Krisis Kebebasan', '2001', 2),
(2, '1101', 'Filsafat', 'Dunia Shopie', '1991', 2),
(3, '1102', 'Filsafat', 'Dunia Anna', '2014', 2),
(4, '1103', 'Filsafat', 'How To Die', '2020', 2),
(5, '1201', 'Biography', 'Karl Marx', '2002', 2),
(6, '1202', 'Biography', 'H.O.S Tjokroaminoto', '2015', 2),
(7, '1401', 'Horor', 'KKN di Desa Penari', '2019', 2),
(8, '1701', 'Sosial', 'Kelahiran yang dipersoalkan', '1990', 2),
(9, '1801', 'Fiksi Sejarah', 'Bumi Manusia', '1980', 2),
(10, '1601', 'Agama', 'Menuju Hidup Mulia', '1998', 2),
(11, '1901', 'Novel', 'Logika Cinta', '2016', 2),
(12, '1902', 'Novel', 'drama Mangir', '2000', 2),
(13, '1104', 'Filsafat', 'Filsafat ketuhanan', '1984', 2),
(14, '1602', 'Agama', 'Islam sebagai Hukum ', '2004', 2),
(15, '1105', 'Filsafat', 'Madilog Tan Malaka', '2019', 2),
(16, '1402', 'Horor', 'Sewu Dino', '2019', 1),
(17, '1301', 'Hukum', 'Hukum Adat Indonesia', '2020', 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama_user` varchar(50) NOT NULL,
  `jurusan_user` varchar(50) NOT NULL,
  `kode_user` int(11) NOT NULL,
  `passworrd` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `uniq` (`kode_admin`);

--
-- Indeks untuk tabel `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`),
  ADD UNIQUE KEY `uniq` (`kode_buku`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `uniq` (`kode_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `buku`
--
ALTER TABLE `buku`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
