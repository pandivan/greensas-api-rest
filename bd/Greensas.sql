

CREATE SCHEMA domicilios;
--CREATE SCHEMA analitica;


--drop table domicilios.aforo;
--drop table domicilios.producto_pedido;
--drop table domicilios.pedido;
--drop table domicilios.producto;
--drop table domicilios.cliente;
--drop table domicilios.barrio;
--drop table domicilios.sucursal;
--drop table domicilios.empresa;
--drop table domicilios.tiempo;
--drop table domicilios.estado;
--drop table domicilios.geografia;
--
--drop SCHEMA domicilios;



create table domicilios.estado
(
id_estado bigserial not null,
descripcion varchar(50) NULL,
primary key (id_estado)
);




create table domicilios.geografia
(
id_geografia bigserial not null,
id_pais varchar(2) not null,
pais varchar(50) not null,
departamento varchar(50) not null,
ciudad varchar(50) not null,
primary key (id_geografia)
);



create table domicilios.barrio
(
id_barrio bigserial not null,
id_geografia bigint not null,
nombre varchar(100) not null,
primary key (id_barrio)
);

ALTER TABLE domicilios.barrio ADD CONSTRAINT barrio_geografia_fk FOREIGN KEY (id_geografia) REFERENCES domicilios.geografia (id_geografia);



	

create table domicilios.empresa
(
id_empresa bigserial not null,
id_geografia bigint not null,
id_estado bigint not null,
id_tiempo_fecha_creacion integer DEFAULT cast(to_char(NOW()::timestamp, 'YYYYMMDD') as integer) not null,
nit varchar(50) not null,
nombre varchar(100) not null,
telefono varchar(50) not null,
direccion varchar(255) null,
email varchar(100) not null,
primary key (id_empresa)
);

ALTER TABLE domicilios.empresa ADD CONSTRAINT empresa_geografia_fk FOREIGN KEY (id_geografia) REFERENCES domicilios.geografia (id_geografia);
ALTER TABLE domicilios.empresa ADD CONSTRAINT empresa_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);





create table domicilios.sucursal
(
id_sucursal bigserial not null,
id_empresa bigint not null,
id_geografia bigint not null,
id_estado bigint not null,
id_tiempo_fecha_creacion integer DEFAULT cast(to_char(NOW()::timestamp, 'YYYYMMDD') as integer) not null,
nombre varchar(100) not null,
telefono varchar(50) not null,
direccion varchar(255) not null,
horario_apertura varchar(4) null,
horario_cierre varchar(4) null,
primary key (id_sucursal)
);

ALTER TABLE domicilios.sucursal ADD CONSTRAINT sucursal_empresa_fk FOREIGN KEY (id_empresa) REFERENCES domicilios.empresa (id_empresa);
ALTER TABLE domicilios.sucursal ADD CONSTRAINT sucursal_geografia_fk FOREIGN KEY (id_geografia) REFERENCES domicilios.geografia (id_geografia);
ALTER TABLE domicilios.sucursal ADD CONSTRAINT sucursal_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);




create table domicilios.cliente
(
id_cliente bigserial not null,
id_barrio bigint not null,
id_estado bigint default 1 not null,
id_tiempo_fecha_creacion integer DEFAULT cast(to_char(NOW()::timestamp, 'YYYYMMDD') as integer) not null,
cedula varchar(20) not null,
nombre varchar(100) not null,
telefono varchar(50) not null,
direccion1 varchar(255) not null,
direccion2 varchar(255) null,
direccion3 varchar(255) null,
email varchar(100) not null,
fecha_nacimiento date not null,
sexo varchar(1) not null,
primary key (id_cliente)
);

ALTER TABLE domicilios.cliente ADD CONSTRAINT cliente_barrio_fk FOREIGN KEY (id_barrio) REFERENCES domicilios.barrio (id_barrio);
ALTER TABLE domicilios.cliente ADD CONSTRAINT cliente_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);




create table domicilios.usuario
(
id_usuario bigserial not null,
id_entidad bigint not null,
id_estado bigint not null,
id_tiempo_fecha_creacion integer DEFAULT cast(to_char(NOW()::timestamp, 'YYYYMMDD') as integer) not null,
username varchar(100) not null,
password varchar(1000) not null,
tipo varchar(10) not null,
primary key (id_usuario)
);

ALTER TABLE domicilios.usuario ADD CONSTRAINT usuario_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);




create table domicilios.tiempo
(
id_tiempo integer not null,
fecha date not null,
año smallint not null,
semestre smallint not null,
trimestre smallint not null,
mes smallint not null,
mes_nombre varchar(10) null,
semana smallint not null,
dia_semana varchar(10) null,
fecha_inicio_año date not null,
fecha_fin_año date not null,
fecha_inicio_semestre date not null,
fecha_fin_semestre date not null,
fecha_inicio_trimestre date not null,
fecha_fin_trimestre date not null,
fecha_inicio_mes date not null,
fecha_fin_mes date not null,
fecha_inicio_semana date not null,
fecha_fin_semana date not null,
primary key (id_tiempo)
);





create table domicilios.producto
(
id_producto bigserial not null,
id_empresa bigint not null,
id_estado bigint not null,
categoria_nivel1 varchar(50) not null,
categoria_nivel2 varchar(50) null,
categoria_nivel3 varchar(50) null,
categoria_nivel4 varchar(50) null,
categoria_nivel5 varchar(50) null,
ean varchar(50) not null,
nombre varchar(100) not null,
nivel smallint not null,
valor numeric(8,2) null,
url_imagen_categoria varchar(100) null,
url_imagen_producto varchar(100) null,
primary key (id_producto)
);

ALTER TABLE domicilios.producto ADD CONSTRAINT producto_empresa_fk FOREIGN KEY (id_empresa) REFERENCES domicilios.empresa (id_empresa);
ALTER TABLE domicilios.producto ADD CONSTRAINT producto_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);







create table domicilios.pedido
(
id_pedido bigserial not null,
id_sucursal bigint null,
id_cliente bigint not null,
id_estado bigint not null,
fecha_pedido TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
primary key (id_pedido)
);

ALTER TABLE domicilios.pedido ADD CONSTRAINT pedido_sucursal_fk  FOREIGN KEY (id_sucursal) REFERENCES domicilios.sucursal (id_sucursal);
ALTER TABLE domicilios.pedido ADD CONSTRAINT pedido_cliente_fk FOREIGN KEY (id_cliente) REFERENCES domicilios.cliente (id_cliente);
ALTER TABLE domicilios.pedido ADD CONSTRAINT pedido_estado_fk FOREIGN KEY (id_estado) REFERENCES domicilios.estado (id_estado);






create table domicilios.producto_pedido
(
id_producto_pedido bigserial not null,
id_pedido bigint not null,
id_producto bigint not null,
cantidad smallint not null,
valor numeric(8,2) not null,
primary key (id_producto_pedido)
);

ALTER TABLE domicilios.producto_pedido ADD CONSTRAINT productopedido_pedido_fk FOREIGN KEY (id_pedido) REFERENCES domicilios.pedido (id_pedido);
ALTER TABLE domicilios.producto_pedido ADD CONSTRAINT productopedido_producto_fk FOREIGN KEY (id_producto) REFERENCES domicilios.producto (id_producto);







create table domicilios.aforo
(
id_aforo bigserial not null,
fecha_ingreso TIMESTAMP WITH TIME ZONE null,
fecha_salida TIMESTAMP WITH TIME ZONE null,
primary key (id_aforo)
);


--Ejecutar consulta en la maquina de distribuidores y exportar el restulado a csv
--SELECT [INT_PK_ID_TIEMPO] id_tiempo
--      ,[DTM_FECHA] fecha
--      ,[INT_A—O] aÒo
--      ,[INT_A—O_SEMESTRE] semestre
--      ,[INT_TRIMESTRE] trimestre
--      ,[INT_MES] mes
--      ,[VAR_MES_NOMBRE] mes_nombre
--      ,[VAR_NOMBRE_DIA_SEMANA] dia_semana
--      ,[INT_SEMANA] semana
--      ,[DTM_FECHA_INICIO_A—O] fecha_inicio_aÒo
--      ,[DTM_FECHA_FIN_A—O] fecha_fin_aÒo
--      ,[DTM_FECHA_INICIO_SEMESTRE] fecha_inicio_semestre
--      ,[DTM_FECHA_FIN_SEMESTRE] fecha_fin_semestre
--      ,[DTM_FECHA_INICIO_TRIMESTRE] fecha_inicio_trimestre
--      ,[DTM_FECHA_FIN_TRIMESTRE] fecha_fin_trimestre
--      ,[DTM_FECHA_INICIO_MES] fecha_inicio_mes
--      ,[DTM_FECHA_FIN_MES] fecha_fin_mes
--      ,[DTM_FECHA_INICIO_SEMANA] fecha_inicio_semana
--      ,[DTM_FECHA_FIN_SEMANA] fecha_fin_semana
--  FROM [DW_CENC_BI].[Dimensiones].[TIEMPO]
--  where INT_PK_ID_TIEMPO >= 20200101




SET timezone TO 'America/Bogota';



commit;

insert into domicilios.geografia(id_pais,pais,departamento,ciudad) values('CO','Colombia','Valle del Cauca','Cali');
insert into domicilios.geografia(id_pais,pais,departamento,ciudad) values('CO','Colombia','Nariño','Pasto');

insert into domicilios.barrio(id_geografia, nombre) values(1, 'Valle de Lili');

insert into domicilios.estado(id_estado, descripcion) values(100, 'PENDIENTE');
insert into domicilios.estado(id_estado, descripcion) values(101, 'ACEPTADO');
insert into domicilios.estado(id_estado, descripcion) values(102, 'CANCELADO');
insert into domicilios.estado(id_estado, descripcion) values(103, 'ACTIVO');
insert into domicilios.estado(id_estado, descripcion) values(104, 'INACTIVO');
insert into domicilios.estado(id_estado, descripcion) values(105, 'DESPACHADO');




--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(100, 101, 100, 'American', 'ean-Beef Grill', 'Beef Grill', 1, 24, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/beef-grill.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(101, 101, 100, 'American', 'ean-Chicken Picatta', 'Chicken Picatta', 1, 20, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-piccata.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(102, 101, 100, 'American', 'ean-Chicken Romesco', 'Chicken Romesco', 1, 21, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-romesco.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(103, 101, 100, 'American', 'ean-Chicken Grill', 'Chicken Grill', 1, 22, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-grill.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(104, 101, 100, 'American', 'ean-Salmon Grill', 'Salmon Grill', 1, 26, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/salmon-grill.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(105, 101, 100, 'American', 'ean-Salmon Romesco', 'Salmon Romesco', 1, 25, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/salmon-romesco', 103);
--
--
--
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(106, 101, 100, 'Burger', 'ean-Burger Home', 'Burger Home', 1, 16, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-1.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(107, 101, 100, 'Burger', 'ean-MegaBurger', 'MegaBurger', 1, 16, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-2.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(108, 101, 100, 'Burger', 'ean-Burger Special', 'Burger Special', 1, 17, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-3.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(109, 101, 100, 'Burger', 'ean-Burger Cheese', 'Burger Cheese', 1, 18, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-4.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(110, 101, 100, 'Burger', 'ean-Burger Farmer', 'Burger Farmer', 1, 19, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-5.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(111, 101, 100, 'Burger', 'ean-Mini Burger', 'Mini Burger', 1, 10, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-6.png', 103);
--
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(112, 101, 100, 'Pizza', 'ean-Pizza Vegan', 'Pizza Vegan', 1, 14, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-1.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(113, 101, 100, 'Pizza', 'ean-Pizza Bufalisimo', 'Pizza Bufalisimo', 1, 15, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-2.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(114, 101, 100, 'Pizza', 'ean-Pizza Haiwaiana', 'Pizza Haiwaiana', 1, 16, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-3.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(115, 101, 100, 'Pizza', 'ean-Pizza Peperoni', 'Pizza Peperoni', 1, 17, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-4.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(116, 101, 100, 'Pizza', 'ean-Pizza Vegan 2', 'Pizza Vegan 2', 1, 33, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-5.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(117, 101, 100, 'Pizza', 'ean-Pizza Napolitana', 'Pizza Napolitana', 1, 30, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-6.png', 103);
--
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(118, 101, 100, 'Drink', 'ean-Coca cola', 'Coca cola', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/cocacola.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(119, 101, 100, 'Drink', 'ean-Pepsi', 'Pepsi', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/pepsi.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(120, 101, 100, 'Drink', 'ean-Quatro', 'Quatro', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/quatro.png', 103);
--
--insert into domicilios.producto(id_producto, id_empresa, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(121, 101, 100, 'Drink', 'ean-Sprite', 'Sprite', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/sprite.png', 103);


--insert into domicilios.aforo(fecha_ingreso) values(TIMESTAMP '2011-05-16 15:36:38');
--insert into domicilios.aforo(fecha_salida) values(TIMESTAMP '2011-05-16 16:36:38');

commit;














/********************************************************************************************************************************
 * Consultas de DBA
 *********************************************************************************************************************************/

--SELECT * --table_name AS "Tabla", ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Tamaño (MB)"
--FROM information_schema.TABLES
----WHERE table_schema in ("domicilios", "domicilios")
----ORDER BY (data_length + index_length) DESC
--;  



/********************************************************************************************************************************
 * Otorgar Privilegios
 *********************************************************************************************************************************/

--GRANT ALL PRIVILEGES ON DATABASE dbg8dhdal4qvec TO vrhygmcmqapxkw;
--
--SELECT
--pg_database.datname,
--pg_size_pretty(pg_database_size(pg_database.datname)) AS size
--FROM pg_database;

--nextval('dimeid_clientension.cliente_id_cliente_seq'::regclass)




/********************************************************************************************************************************
 * Listar secuencias
 *********************************************************************************************************************************/
--select nextval('domicilios.cliente_id_cliente_seq');
--
--SELECT * FROM pg_class c WHERE c.relkind = 'S';





/********************************************************************************************************************************
 * Soporte Aforo
 *********************************************************************************************************************************/
--create table domicilios.tiempo2 as (select * from domicilios.tiempo);

--select  
--min(fecha_ingreso ), max(fecha_ingreso ), CURRENT_DATE
----count(1) 
----delete
--from domicilios.aforo
----where fecha_ingreso < CURRENT_DATE --or fecha_salida < CURRENT_DATE
----order by 1 desc
--;
--
--
--
--select ingreso, salida, (ingreso - salida) aforo, (ingreso + salida) total
--from 
--(
--	select 
--	(
--		select count(1)
--		from domicilios.aforo
--		where fecha_ingreso is not null and fecha_ingreso >= CURRENT_DATE
--	) ingreso,
--	(
--		select count(1)
--		from domicilios.aforo
--		where fecha_salida is not null and fecha_salida >= CURRENT_DATE
--	) salida
--) tabla
--;





/********************************************************************************************************************************
 * Soporte Mi Barrio
 *********************************************************************************************************************************/


--select * from domicilios.empresa;

--select * from domicilios.geografia;
--
--select * from domicilios.tiempo;

--delete from domicilios.tiempo;
--
--select * from domicilios.barrio;
--
--select * from domicilios.estado;
--
--select * from domicilios.producto;
--
--select * from domicilios.cliente order by 1 desc;

--delete from domicilios.cliente;




--select  * from domicilios.aforo 
----order by 1 desc
--;

--update domicilios.pedido set id_tienda = null, id_estado = 100 where id_pedido in (1,2,3,4,5,6,7);

--select * from domicilios.pedido;
--
--select * from domicilios.producto_pedido;
--
--
--truncate table domicilios.producto_pedido;
--delete from domicilios.pedido;



--delete from domicilios.cliente where id_cliente > 4;
--
--select p.id_pedido, t.nombre tienda, pro.nombre producto, pp.cantidad, pp.valor, c.nombre cliente, p.fecha_pedido, e.descripcion estado, e.id_estado 
--from domicilios.pedido p 
--inner join domicilios.producto_pedido pp on pp.id_pedido = p.id_pedido
--inner join domicilios.producto pro on pro.id_producto = pp.id_producto
--inner join domicilios.cliente c on c.id_cliente = p.id_cliente
--left outer join domicilios.cliente t on t.id_cliente = p.id_tienda
--inner join domicilios.estado e on e.id_estado = p.id_estado
--where 1=1
----and p.id_tienda is null
----and p.id_pedido = 14
--;
