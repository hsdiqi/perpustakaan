SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE `admin` (
                         `username` varchar(25) NOT NULL,
                         `nama` varchar(50) NOT NULL,
                         `password` varchar(15) NOT NULL,
                         `id_admin` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `admin` (`username`, `nama`, `password`, `id_admin`) VALUES
                                                                     ('admin', 'Admin', 'admin123', 1),
                                                                     ('operator', 'Operator', 'operator123', 2);

CREATE TABLE `buku` (
                        `id_buku` int(11) NOT NULL,
                        `kode_buku` varchar(5) NOT NULL,
                        `genre` varchar(50) NOT NULL,
                        `judul` varchar(50) NOT NULL,
                        `tahun_rilis` int(4) NOT NULL,
                        `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `buku` (`id_buku`, `kode_buku`, `genre`, `judul`, `tahun_rilis`, `stok`) VALUES
                                                                                         (1, '1702', 'Sosial', 'Krisis Kebebasan', 2001, 2),
                                                                                         (2, '1101', 'Filsafat', 'Dunia Shopie', 1991, 2),
                                                                                         (3, '1102', 'Filsafat', 'Dunia Anna', 2014, 2),
                                                                                         (4, '1103', 'Filsafat', 'How To Die', 2020, 2),
                                                                                         (5, '1201', 'Biography', 'Karl Marx', 2002, 2),
                                                                                         (6, '1202', 'Biography', 'H.O.S Tjokroaminoto', 2015, 2),
                                                                                         (7, '1401', 'Horor', 'KKN di Desa Penari', 2019, 2),
                                                                                         (8, '1701', 'Sosial', 'Kelahiran yang dipersoalkan', 1990, 2),
                                                                                         (9, '1801', 'Fiksi Sejarah', 'Bumi Manusia', 1980, 2),
                                                                                         (10, '1601', 'Agama', 'Menuju Hidup Mulia', 1998, 2),
                                                                                         (11, '1901', 'Novel', 'Logika Cinta', 2016, 2),
                                                                                         (12, '1902', 'Novel', 'drama Mangir', 2000, 2),
                                                                                         (13, '1104', 'Filsafat', 'Filsafat ketuhanan', 1984, 2),
                                                                                         (14, '1602', 'Agama', 'Islam sebagai Hukum ', 2004, 2),
                                                                                         (15, '1105', 'Filsafat', 'Madilog Tan Malaka', 2019, 2),
                                                                                         (16, '1402', 'Horor', 'Sewu Dino', 2019, 2),
                                                                                         (17, '1301', 'Hukum', 'Hukum Adat Indonesia', 2020, 2);

CREATE TABLE `dipinjam` (
                            `id_buku` int(11) NOT NULL,
                            `judul` varchar(50) NOT NULL,
                            `genre` varchar(50) NOT NULL,
                            `tahun_rilis` int(4) NOT NULL,
                            `tanggal_pinjam` date NOT NULL DEFAULT current_timestamp(),
                            `peminjamId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `dipinjam` (`id_buku`, `judul`, `genre`, `tahun_rilis`, `tanggal_pinjam`, `peminjamId`) VALUES
                                                                                                        (1, 'Krisis Kebebasan', 'Sosial', 2001, '2023-12-25', 0),
                                                                                                        (6, 'H.O.S Tjokroaminoto', 'Biography', 2015, '2023-12-25', 0),
                                                                                                        (7, 'KKN di Desa Penari', 'Horor', 2019, '2023-12-25', 0),
                                                                                                        (8, 'Kelahiran yang dipersoalkan', 'Sosial', 1990, '2023-12-25', 0),
                                                                                                        (10, 'Menuju Hidup Mulia', 'Agama', 1998, '2023-12-25', 0),
                                                                                                        (11, 'Logika Cinta', 'Novel', 2016, '2023-12-25', 0),
                                                                                                        (12, 'drama Mangir', 'Novel', 2000, '2023-12-25', 0),
                                                                                                        (15, 'Madilog Tan Malaka', 'Filsafat', 2019, '2023-12-25', 0),
                                                                                                        (17, 'Hukum Adat Indonesia', 'Hukum', 2020, '2023-12-25', 0);

CREATE TABLE `users` (
                         `id_user` int(11) NOT NULL,
                         `nama` varchar(50) NOT NULL,
                         `username` varchar(50) NOT NULL,
                         `email` varchar(50) NOT NULL,
                         `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id_user`, `nama`, `username`, `email`, `password`) VALUES
                                                                             (1, 'bro', 'brody', 'wakanda@gmail.com', 'brody123'),
                                                                             (2, 'b', 'b', 'b@gmail.com', 'b');

CREATE TABLE `wishlist` (
                            `id_buku` int(11) NOT NULL,
                            `judul` varchar(50) NOT NULL,
                            `genre` varchar(50) NOT NULL,
                            `tahun_rilis` int(4) NOT NULL,
                            `stok` int(25) NOT NULL,
                            `peminjamId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE `admin`
    ADD PRIMARY KEY (`id_admin`);

ALTER TABLE `buku`
    ADD PRIMARY KEY (`id_buku`),
    ADD UNIQUE KEY `uniq` (`kode_buku`);

ALTER TABLE `dipinjam`
    ADD PRIMARY KEY (`id_buku`),
    ADD UNIQUE KEY `id_buku` (`id_buku`);

ALTER TABLE `users`
    ADD PRIMARY KEY (`id_user`);

ALTER TABLE `wishlist`
    ADD PRIMARY KEY (`id_buku`);


ALTER TABLE `admin`
    MODIFY `id_admin` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `buku`
    MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

ALTER TABLE `users`
    MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
