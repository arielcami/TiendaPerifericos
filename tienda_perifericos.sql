
DROP DATABASE IF EXISTS tienda_perifericos;

CREATE DATABASE tienda_perifericos DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE tienda_perifericos;

-- Estructura para la tabla productos

DROP TABLE IF EXISTS productos;

CREATE TABLE productos (
    producto_id INT UNSIGNED NOT NULL,
    producto_nombre VARCHAR(100) NOT NULL,
    producto_desc VARCHAR(256) NOT NULL,
    marca VARCHAR(35) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    imagen VARCHAR(256) NOT NULL,
    stock SMALLINT UNSIGNED NOT NULL,
    estado TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (producto_id)
);

-- Productos

INSERT INTO productos (producto_id, producto_nombre, producto_desc, marca, precio, imagen, stock, estado) 
VALUES 
(1, 'Mouse Gamer Scorpion', 'Mouse para juegos, ergonómico 18,000 DPI bluetooth recargable. Batería interna fija.', 'Genius', 159.99, 'mouse.png', 37, 1), 
(2, 'Teclado mecánico Gamer', 'Teclado de alto rendimiento retroiluminado, modo bluetooth y cableado.', 'Logitech', 239.99, 'teclado.png', 22, 1), 
(3, 'Audífonos Bluetooth', 'Audífonos de alta fidelidad, Noise Cancelation, recargables, comandos de voz, batería interna.', 'Bose', 384.99, 'audifonos.png', 23, 1), 
(4, 'Cámara Full HD 1080p USB-C', 'Alta definición, 60FPS, micrófono integrado, conexión Thunderbolt USB-C', 'HP', 119.99, 'camara.png', 29, 1), 
(5, 'Mando PS5 DualSense Black', 'Control de PlayStation5 DualSense color negro.', 'Sony', 284.99, 'mando.png', 22, 1), 
(6, 'Monitor ROG Strix XG27AQMR', 'Pantalla LED IPS 27 pulg. 1ms, 1k @300Hz - 2k @120Hz - 4k @60Hz, 1ms latencia. NVIDIA G-SYNC', 'ASUS', 2299.99, 'monitor.png', 17, 1), 
(7, 'Silla Premium Gaming Negro-Rojo', 'Ergonómica, fibra premium, 360 grados.', 'Gambyte', 339.99, 'silla.png', 16, 1), 
(8, 'Micrófono Condensador USB ULRIC-001', 'Micrófono profesional cardioide por USB con pop filter.', 'Kuzler', 399.99, 'micro.png', 21, 1), 
(9, 'Parlantes 5.1 Z506 Hi-Fi Sorround', 'Parlantes conectividad USB sonido envolvente 5.1 calidad Blu-ray.', 'Logitech', 199.99, 'parlantes.png', 25, 1), 
(10, 'Teclado Mecánico F2067', 'Teclado Gamer mecánico, 16 teclas simultáneas, macros, recargable.', 'TKL', 239.99, 'teclado2.png', 19, 1), 
(11, 'Memoria USB-C 3.2 128GB', 'Memoria USB 3.2 5Gbps.', 'SanDisk', 49.99, 'memoria.png', 51, 1), 
(12, 'Mando Xbox Series X/PC BlueTooth Rojo', 'Microsoft Mando Xbox Series X/PC recargable conexion USB-C.', 'Microsoft', 419.99, 'mandoXbox.png', 16, 1), 
(13, 'Capturador de Video HDMI USB-C 3.1', 'Capturador USB-C 3.1 de video HDMI 1k 60fps, 2k 60fps, 4k 30fps. Compatible con todos los softwares de captura de video como OBS Studio.', 'NETCOM', 124.99, 'captura.png', 23, 1), 
(14, 'Micrófono Condensador El NEOM USB', 'Micrófono portátil repleto de funciones con características de precisión y una construcción elegante y contorneada que es perfecto para creativos y profesionales.', 'sE Electronics', 360.00, 'micro2.png', 23, 1), 
(15, 'Mando Wireless para PC Estilo SNES', 'Mando inalámbrico recargable por USB Micro, para PC, estilo Super Nintendo, hasta 15 metros de cobertura sin interferencias.', '8bitDo', 229.99, '8bitdo.png', 20, 1), 
(16, 'Pistola Infrarroja USB', 'Pistola Arcade 4 Led Sensor de posicionamiento infrarrojo accesorio retroceso baja latencia para USB PC.', 'Fire Phoenix', 119.99, 'pistola.png', 33, 1), 
(17, 'Bolígrafo Memoria USB 32GB', 'USB 2.0, color de tinta Azul, protector enroscable, cómodo, color plateado.', 'IceBluePhone', 44.99, 'lapiz.png', 21, 1), 
(18, 'Teclado USB controlador MIDI', 'Teclado de piano portátil Mini teclado controlador USB de 25 teclas Piano electrónico', 'VENEKA', 74.99, 'piano.png', 16, 1);

-- Estructura para usuarios

DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    usuario_id int UNSIGNED NOT NULL AUTO_INCREMENT,
    usuario_nombre VARCHAR(40) NOT NULL,
    usuario_pass VARCHAR(256) NOT NULL,
    usuario_correo VARCHAR(100) NOT NULL,
    intentos_login TINYINT UNSIGNED NOT NULL DEFAULT 0,
    estado TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (usuario_id),
    UNIQUE KEY usuario_nombre (usuario_nombre),
    UNIQUE KEY usuario_correo (usuario_correo)
);