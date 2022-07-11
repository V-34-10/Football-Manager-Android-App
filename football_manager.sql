-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3307
-- Время создания: Май 24 2022 г., 00:27
-- Версия сервера: 10.3.13-MariaDB-log
-- Версия PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `football_manager`
--

-- --------------------------------------------------------

--
-- Структура таблицы `klub`
--

CREATE TABLE `klub` (
  `Id_klub` int(11) NOT NULL,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Pib_trener` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Liga` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Image` varchar(45) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `klub`
--

INSERT INTO `klub` (`Id_klub`, `Name`, `Pib_trener`, `Liga`, `Image`) VALUES
(1, 'ФК Динамо', 'Мірча Луческу', 'Українська ліга', 'Dinamo-1.jpg'),
(2, 'ФК Шахтар', 'Де Дзерби, Роберто', 'Українська ліга', 'FC_Shakhtar-1.jpg'),
(3, 'ФК Десна', 'Рябоконь Олександр', 'Українська ліга', 'Desna-1.jpg'),
(4, 'ФК Верес', 'Юрій Вірт', 'Українська ліга', 'Veres-1.jpg'),
(5, 'ФК Карпати', 'Андрій Тлумак', 'Українська ліга', 'Karpaty-1.jpg');

-- --------------------------------------------------------

--
-- Структура таблицы `list_player`
--

CREATE TABLE `list_player` (
  `id_player` int(11) NOT NULL,
  `id_plan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `list_player`
--

INSERT INTO `list_player` (`id_player`, `id_plan`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 3),
(5, 3),
(6, 3),
(7, 2),
(8, 2),
(9, 2),
(10, 4),
(11, 4),
(12, 4),
(13, 3),
(14, 3),
(15, 3),
(16, 3),
(17, 3),
(18, 3),
(19, 3),
(20, 3),
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 1),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2),
(31, 4),
(32, 4),
(33, 4),
(34, 4),
(35, 4),
(36, 5),
(37, 5),
(38, 5),
(39, 5),
(40, 5);

-- --------------------------------------------------------

--
-- Структура таблицы `login`
--

CREATE TABLE `login` (
  `id_login` int(11) NOT NULL,
  `Id_klub` int(11) NOT NULL,
  `user_login` varchar(15) NOT NULL,
  `user_password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `login`
--

INSERT INTO `login` (`id_login`, `Id_klub`, `user_login`, `user_password`) VALUES
(1, 1, 'luchesku', 'mircha'),
(2, 2, 'roberto', 'dedzerbe'),
(3, 3, 'olexsandr', 'ryabokon'),
(4, 4, 'virtovuy', 'yuriy'),
(5, 5, 'tlumack', 'andriy'),
(6, 0, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Структура таблицы `match`
--

CREATE TABLE `match` (
  `id_match` int(11) NOT NULL,
  `id_klub1` int(11) NOT NULL,
  `klub2` varchar(25) CHARACTER SET utf8 NOT NULL,
  `date_match` date NOT NULL,
  `id_plan` int(11) NOT NULL,
  `kol_ball` int(11) NOT NULL,
  `kol_gears` int(11) NOT NULL,
  `result` varchar(3) CHARACTER SET utf8 NOT NULL,
  `sezon` varchar(15) CHARACTER SET utf8 NOT NULL,
  `image` varchar(45) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `match`
--

INSERT INTO `match` (`id_match`, `id_klub1`, `klub2`, `date_match`, `id_plan`, `kol_ball`, `kol_gears`, `result`, `sezon`, `image`) VALUES
(1, 1, 'ФК Шахтар', '2021-10-02', 1, 2, 2, '0:2', '2021-2022', 'Dinamo-Shahtar.png'),
(2, 2, 'ФК Верес', '2021-09-24', 2, 6, 3, '6:0', '2021-2022', 'Shahtar-Veres.png'),
(3, 4, 'ФК Десна', '2021-12-05', 3, 2, 1, '1:1', '2021-2022', 'Veres-Desna.png'),
(4, 3, 'ФК Динамо', '2021-11-21', 4, 4, 3, '0:4', '2021-2022', 'Desna-Dinamo.png'),
(5, 2, 'ФК Десна', '2021-10-29', 1, 4, 2, '4:0', '2021-2022', 'Shahtar-Desna.png'),
(7, 1, 'ФК Десна', '2021-08-21', 1, 4, 4, '4:0', '2021-2022', 'Dinamo-Desna.png'),
(8, 3, 'ФК Верес', '2021-08-29', 2, 4, 1, '0:4', '2021-2022', 'Desna-Veres.png'),
(9, 2, 'ФК Десна', '2021-10-30', 3, 5, 4, '4:1', '2021-2022', 'Shahtar-Desna.png'),
(10, 2, 'ФК Динамо', '2021-09-21', 3, 3, 1, '3:0', '2021-2022', 'Shahtar-Dinamo.png'),
(30, 3, 'ФК Карпати', '2022-05-07', 4, 4, 3, '2:2', '2022-2023', 'Desna-Karpaty.png'),
(31, 4, 'ФК Десна', '2022-05-04', 5, 6, 4, '3:3', '2022-2023', 'Veres-Desna.png'),
(32, 5, 'ФК Динамо', '2022-04-29', 1, 8, 5, '3:5', '2022-2023', 'Karpaty-Dinamo.png'),
(33, 4, 'ФК Карпати', '2022-05-08', 4, 5, 4, '3:2', '2022-2023', 'Veres-Karpaty.png');

-- --------------------------------------------------------

--
-- Структура таблицы `plan`
--

CREATE TABLE `plan` (
  `id_plan` int(11) NOT NULL,
  `name_plan` varchar(15) CHARACTER SET utf8 NOT NULL,
  `imagePlan` varchar(45) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `plan`
--

INSERT INTO `plan` (`id_plan`, `name_plan`, `imagePlan`) VALUES
(1, '4-3-3', '4-3-3.png'),
(2, '4-1-2-1-2', '4-1-2-1-2.png'),
(3, '4-3-2-1', '4-3-2-1.png'),
(4, '4-4-2', '4-4-2.png'),
(5, '4-5-1', '4-5-1.png');

-- --------------------------------------------------------

--
-- Структура таблицы `player`
--

CREATE TABLE `player` (
  `id_player` int(11) NOT NULL,
  `Id_klub` int(11) NOT NULL,
  `pib` varchar(45) CHARACTER SET utf8 NOT NULL,
  `date` date NOT NULL,
  `position` varchar(5) CHARACTER SET utf8 NOT NULL,
  `number` varchar(2) CHARACTER SET utf8 NOT NULL,
  `image` varchar(45) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `player`
--

INSERT INTO `player` (`id_player`, `Id_klub`, `pib`, `date`, `position`, `number`, `image`) VALUES
(1, 1, 'Микола Шапаренко', '1998-10-04', 'АПЗ', '10', 'Shaparenko.png'),
(2, 1, 'Георгій Бущан', '1994-05-31', 'ВР', '1', 'Bushchan.png'),
(3, 1, 'Сергій Сидорчук', '1991-05-02', 'ЦПЗ', '5', 'Sydorchuk.png'),
(4, 2, 'Марлос Ромеро', '1988-06-07', 'АПЗ', '11', 'Marlos.png'),
(5, 2, 'Євген Коноплянка', '1989-09-29', 'ЛПЗ', '70', 'Konoplyanka.png'),
(6, 2, 'Андрій П\'ятов', '1984-06-28', 'ВР', '30', 'Pyatov.png'),
(7, 3, 'Олексій Ковтун', '1995-02-05', 'ЦЗ', '6', 'Kovtun.png'),
(8, 3, 'Дмитро Сидоренко', '2002-11-12', 'ВР', '1', 'Sudorenko.png'),
(9, 3, 'Денис Безбородько', '1994-05-31', 'НП', '20', 'Bezborodyko.png'),
(10, 4, 'Роберт Гегедош', '1993-05-02', 'НП', '88', 'Gegedosh.png'),
(11, 4, 'Віталій Дахновський', '1999-02-10', 'ЦПЗ', '11', 'Dahnovskiy.png'),
(12, 4, 'Богдан Когут', '1987-10-10', 'ВР', '47', 'Kogut.png'),
(13, 2, 'Крістіан Бако', '2003-01-07', 'НП', '0', 'Bako.png'),
(14, 2, 'Гонсалвес Ісмаїлі', '1990-01-11', 'ЦЗ', '31', 'Ismaili.png'),
(15, 2, 'Майкон', '1997-07-15', 'АПЗ', '7', 'Maikon.png'),
(16, 2, 'Дентіньо', '1989-01-19', 'АПЗ', '9', 'Dentinio.png'),
(17, 2, 'Степаненко Тарас', '1989-08-08', 'ЦПЗ', '6', 'Stepanenko.png'),
(18, 2, 'Алан Патрік', '1991-05-13', 'ППЗ', '21', 'Patrik.png'),
(19, 2, 'Мораєс', '1987-04-04', 'НП', '10', 'Moraes.png'),
(20, 2, 'Траоре', '2001-01-12', 'НП', '23', 'Traore.png'),
(21, 1, 'Бурда Микита', '1995-03-21', 'ЦЗ', '6', 'Burda.png'),
(22, 1, 'Тимчик Олександр', '1997-01-20', 'ЛЗ', '24', 'Timchuk.png'),
(23, 1, 'Гармаш Денис', '1990-04-19', 'ППЗ', '19', 'Garmash.png'),
(24, 1, 'Де Пена Карлос', '1992-03-11', 'ЛПЗ', '14', 'DePena.png'),
(25, 1, 'Бесєдін Артем', '1996-03-31', 'НП', '41', 'Besedin.png'),
(26, 3, 'Жук Вадим', '1991-04-15', 'ЛЗ', '2', 'Juk.png'),
(27, 3, 'Арвеладзе Леван', '1993-04-06', 'ЦПЗ', '9', 'Arveladze.png'),
(28, 3, 'Болбат Сергій', '1993-06-13', 'ППЗ', '7', 'Bolbat.png'),
(29, 3, 'Волошин Вікентій', '2001-04-17', 'ЛПЗ', '15', 'Voloshun.png'),
(30, 3, 'Білич Євген      ', '2001-01-10', 'ПЗ', '10', 'Biluch.png'),
(31, 4, 'Солдат Ігор', '1991-03-10', 'ЦЗ', '4', 'Soldat.png'),
(32, 4, 'Махнєв Дмитро', '1996-03-02', 'ПЗ', '14', 'Makhnyev.png'),
(33, 4, 'Петько Сергій', '1994-01-23', 'ППЗ', '00', 'Petko.png'),
(34, 4, 'Гєчов Михайло', '1997-11-05', 'АПЗ', '97', 'Hyechov.png'),
(35, 4, 'Напівлях Микита', '1993-03-15', 'ЛПЗ', '77', 'Napivlyakh.png'),
(36, 5, 'Максим Кучинський', '1988-06-28', 'ВР', '1', 'Kuchinskiy.png'),
(37, 5, 'Володимир Заставний', '1990-09-02', 'ЦЗ', '90', 'Zastavniy.png'),
(38, 5, 'Ілля Повалій', '1998-06-08', 'ППЗ', '18', 'Povaliy.png'),
(39, 5, 'Олександр Єрмаченко', '1993-01-29', 'ЛПЗ', '10', 'Iermachenko.png'),
(40, 5, 'Євген Буднік', '1990-09-04', 'НП', '99', 'Budnik.png'),
(41, 1, 'Буяльський Віталій', '1993-01-06', 'АПЗ', '29', 'Buyalskiy.png'),
(42, 1, 'Кедзьора Томаш', '1994-06-11', 'ПЗ', '94', 'Kedjora.png'),
(43, 1, 'Сирота Олександр', '2000-06-11', 'ЦЗ', '34', 'Sirota.png'),
(44, 3, 'Пусь Даниїл', '2003-01-02', 'ЦЗ', '23', 'Pus.png'),
(45, 3, 'Завійський Тарас', '1995-04-12', 'АПЗ', '7', 'Zavoyskiy.png'),
(46, 3, 'Домбровський Андрій', '1995-08-12', 'ОПЗ', '8', 'Dombrovskiy.png'),
(47, 4, 'Гончаренко Роман', '1993-11-16', 'ЛЗ', '8', 'Goncharenko.png'),
(48, 4, 'Гук Ігор', '2002-06-11', 'ЦЗ', '24', 'Guk.png'),
(49, 4, 'Панасенко Сергій', '1992-03-09', 'ОПЗ', '21', 'Panasenko.png'),
(50, 5, 'Дударенко Олександр', '1995-04-10', 'ЦЗ', '21', 'Dudarenko.png'),
(51, 5, 'Маткобожик Олександр', '1998-01-03', 'ЛЗ', '15', 'Matkobaznik.png'),
(52, 5, 'Лобай Іван', '1996-05-21', 'ПЗ', '5', 'Lobaj.png'),
(53, 5, 'Чачуа Амбросій', '1994-04-02', 'ЦПЗ', '8', 'Chachua.png'),
(54, 5, 'Тітунін Анатолій', '1993-03-12', 'ОПЗ', '17', 'Titunin.png'),
(55, 5, 'Гуменюк Максим', '1999-02-01', 'АПЗ', '74', 'Gumenyuk.png');

-- --------------------------------------------------------

--
-- Структура таблицы `sezon`
--

CREATE TABLE `sezon` (
  `name` varchar(15) CHARACTER SET utf8 NOT NULL,
  `date1` date NOT NULL,
  `date2` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `sezon`
--

INSERT INTO `sezon` (`name`, `date1`, `date2`) VALUES
('2016-2017', '2016-01-01', '2017-12-31'),
('2017-2018', '2017-01-01', '2018-12-31'),
('2018-2019', '2018-01-01', '2019-12-31'),
('2019-2020', '2019-01-01', '2020-12-31'),
('2020-2021', '2020-01-01', '2021-12-31'),
('2021-2022', '2021-01-01', '2022-12-31'),
('2022-2023', '2022-01-01', '2023-12-31'),
('2023-2024', '2023-01-01', '2024-12-31'),
('2024-2025', '2024-01-01', '2025-12-31'),
('2025-2026', '2025-01-01', '2026-12-31');

-- --------------------------------------------------------

--
-- Структура таблицы `transfer`
--

CREATE TABLE `transfer` (
  `id_transfer` int(11) NOT NULL,
  `term` int(11) NOT NULL,
  `date_1` date NOT NULL,
  `date_2` date NOT NULL,
  `price` int(11) NOT NULL,
  `id_player` int(11) NOT NULL,
  `imagetransfer` varchar(45) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `transfer`
--

INSERT INTO `transfer` (`id_transfer`, `term`, `date_1`, `date_2`, `price`, `id_player`, `imagetransfer`) VALUES
(1, 24, '2021-01-01', '2023-01-01', 25, 1, 'Shaparenko.png'),
(2, 36, '2021-01-01', '2024-01-01', 30, 2, 'Bushchan.png'),
(3, 48, '2021-01-01', '2025-01-01', 35, 4, 'Marlos.png'),
(4, 24, '2019-01-01', '2021-01-01', 25, 25, 'Besedin.png'),
(5, 36, '2020-01-01', '2023-01-01', 18, 22, 'Timchuk.png'),
(6, 60, '2020-01-01', '2025-01-01', 40, 19, 'Moraes.png'),
(7, 12, '2020-01-01', '2021-01-01', 21, 13, 'Bako.png'),
(8, 24, '2020-01-01', '2022-01-01', 29, 5, 'Konoplyanka.png'),
(9, 36, '2019-01-01', '2022-01-01', 35, 6, 'Pyatov.png'),
(10, 12, '2020-01-01', '2021-01-01', 15, 9, 'Bezborodyko.png'),
(11, 24, '2022-05-12', '2024-05-12', 19, 31, 'Soldat.png'),
(12, 22, '2022-05-14', '2024-02-01', 11, 40, 'Budnik.png'),
(13, 24, '2024-05-19', '2026-05-19', 27, 25, 'Besedin.png'),
(14, 48, '2023-05-19', '2027-05-19', 35, 6, 'Pyatov.png'),
(15, 48, '2022-06-01', '2026-06-01', 36, 8, 'Sudorenko.png'),
(16, 36, '2022-07-10', '2025-07-10', 29, 29, 'Voloshun.png'),
(17, 12, '2023-01-01', '2024-01-01', 24, 32, 'Makhnyev.png'),
(18, 24, '2023-02-01', '2025-02-01', 19, 37, 'Zastavniy.png'),
(19, 24, '2022-09-01', '2024-09-01', 26, 11, 'Dahnovskiy.png'),
(20, 12, '2022-11-01', '2023-11-01', 33, 39, 'Iermachenko.png');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `klub`
--
ALTER TABLE `klub`
  ADD PRIMARY KEY (`Id_klub`);

--
-- Индексы таблицы `list_player`
--
ALTER TABLE `list_player`
  ADD PRIMARY KEY (`id_player`);

--
-- Индексы таблицы `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id_login`);

--
-- Индексы таблицы `match`
--
ALTER TABLE `match`
  ADD PRIMARY KEY (`id_match`);

--
-- Индексы таблицы `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id_plan`);

--
-- Индексы таблицы `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id_player`);

--
-- Индексы таблицы `sezon`
--
ALTER TABLE `sezon`
  ADD PRIMARY KEY (`name`);

--
-- Индексы таблицы `transfer`
--
ALTER TABLE `transfer`
  ADD PRIMARY KEY (`id_transfer`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `klub`
--
ALTER TABLE `klub`
  MODIFY `Id_klub` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `login`
--
ALTER TABLE `login`
  MODIFY `id_login` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `match`
--
ALTER TABLE `match`
  MODIFY `id_match` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT для таблицы `plan`
--
ALTER TABLE `plan`
  MODIFY `id_plan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT для таблицы `player`
--
ALTER TABLE `player`
  MODIFY `id_player` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
