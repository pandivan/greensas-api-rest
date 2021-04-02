

CREATE SCHEMA referencial;
--CREATE SCHEMA dimension;
CREATE SCHEMA hechos;


--drop SCHEMA referencial;
--drop SCHEMA dimension;
--drop SCHEMA hechos; 





--drop table hechos.aforo;
--drop table dimension.producto_pedido;
--drop table hechos.pedido;
--drop table dimension.producto;
--drop table dimension.cliente;
--drop table dimension.barrio;
--drop table dimension.empresa;
--drop table dimension.tiempo;
--drop table dimension.estado;
--drop table dimension.geografia;




create table dimension.estado
(
id_estado bigserial not null,
descripcion varchar(255) NULL,
primary key (id_estado)
);






create table dimension.geografia
(
id_geografia bigserial not null,
id_pais varchar(2) not null,
pais varchar(50) not null,
departamento varchar(50) not null,
ciudad varchar(50) not null,
primary key (id_geografia)
);





create table dimension.empresa
(
id_empresa bigserial not null,
id_geografia bigint not null,
tipo varchar(1) not null,
nit varchar(50) null,
nombre varchar(255) not null,
id_estado bigint not null,
primary key (id_empresa)
);

ALTER TABLE dimension.empresa ADD CONSTRAINT empresa_geografia_fk FOREIGN KEY (id_geografia) REFERENCES dimension.geografia (id_geografia);
ALTER TABLE dimension.empresa ADD CONSTRAINT empresa_estado_fk FOREIGN KEY (id_estado) REFERENCES dimension.estado (id_estado);




--create table dimension.tiempo
--(
--id_tiempo integer not null,
--fecha date not null,
--año smallint not null,
--semestre smallint not null,
--trimestre smallint not null,
--mes smallint not null,
--mes_nombre varchar(10) null,
--semana smallint not null,
--dia_semana varchar(10) null,
--fecha_inicio_año date not null,
--fecha_fin_año date not null,
--fecha_inicio_semestre date not null,
--fecha_fin_semestre date not null,
--fecha_inicio_trimestre date not null,
--fecha_fin_trimestre date not null,
--fecha_inicio_mes date not null,
--fecha_fin_mes date not null,
--fecha_inicio_semana date not null,
--fecha_fin_semana date not null,
--primary key (id_tiempo)
--);




create table dimension.barrio
(
id_barrio bigserial not null,
nombre varchar(255) not null,
primary key (id_barrio)
);




create table dimension.cliente
(
id_cliente bigserial not null,
id_geografia bigint null,
id_barrio bigint null,
id_tiempo_fecha_creacion integer DEFAULT cast(to_char(NOW()::timestamp, 'YYYYMMDD') as integer) not null,
cedula varchar(20) null,
nombre varchar(255) not null,
telefono varchar(50) not null,
direccion varchar(255) null,
email varchar(100) null,
fecha_nacimiento date null,
sexo varchar(1) not null,
tipo_cliente varchar(10) not null,
password varchar(255) null,
barrio varchar(255) null,
estado smallint default 1 not null,
primary key (id_cliente)
);

ALTER TABLE dimension.cliente ADD CONSTRAINT cliente_barrio_fk FOREIGN KEY (id_barrio) REFERENCES dimension.barrio (id_barrio);
ALTER TABLE dimension.cliente ADD CONSTRAINT cliente_tiempo_fk FOREIGN KEY (id_tiempo_fecha_creacion) REFERENCES dimension.tiempo (id_tiempo);
ALTER TABLE dimension.cliente ADD CONSTRAINT cliente_geografia_fk FOREIGN KEY (id_geografia) REFERENCES dimension.geografia (id_geografia);




create table dimension.producto
(
id_producto bigserial not null,
id_empresa bigint not null,
id_catalogo smallint not null,
id_estado bigint not null,
categoria_nivel1 varchar(100) not null,
categoria_nivel2 varchar(100) null,
categoria_nivel3 varchar(100) null,
categoria_nivel4 varchar(100) null,
categoria_nivel5 varchar(100) null,
ean varchar(100) not null,
nombre varchar(100) not null,
nivel smallint not null,
valor numeric(8,2) null,
url_imagen_categoria varchar(4000) null,
url_imagen_producto varchar(4000) null,
primary key (id_producto)
);

ALTER TABLE dimension.producto ADD CONSTRAINT producto_empresa_fk FOREIGN KEY (id_empresa) REFERENCES dimension.empresa (id_empresa);
ALTER TABLE dimension.producto ADD CONSTRAINT producto_estado_fk FOREIGN KEY (id_estado) REFERENCES dimension.estado (id_estado);





create table hechos.pedido
(
id_pedido bigserial not null,
id_tienda bigint null,
id_cliente bigint not null,
id_estado bigint not null,
fecha_pedido TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
id_tiempo integer DEFAULT cast(to_char(now(), 'YYYYMMDD') as integer) not null,
primary key (id_pedido)
);

ALTER TABLE hechos.pedido ADD CONSTRAINT pedido_tienda_fk  FOREIGN KEY (id_tienda) REFERENCES dimension.cliente (id_cliente);
ALTER TABLE hechos.pedido ADD CONSTRAINT pedido_cliente_fk FOREIGN KEY (id_cliente) REFERENCES dimension.cliente (id_cliente);
ALTER TABLE hechos.pedido ADD CONSTRAINT pedido_estado_fk FOREIGN KEY (id_estado) REFERENCES dimension.estado (id_estado);
ALTER TABLE hechos.pedido ADD CONSTRAINT pedido_tiempo_fk FOREIGN KEY (id_tiempo) REFERENCES dimension.tiempo (id_tiempo);




create table dimension.producto_pedido
(
id_producto_pedido bigserial not null,
id_pedido bigint not null,
id_producto bigint not null,
cantidad smallint not null,
valor numeric(8,2) not null,
primary key (id_producto_pedido)
);

ALTER TABLE dimension.producto_pedido ADD CONSTRAINT productopedido_pedido_fk FOREIGN KEY (id_pedido) REFERENCES hechos.pedido (id_pedido);
ALTER TABLE dimension.producto_pedido ADD CONSTRAINT productopedido_producto_fk FOREIGN KEY (id_producto) REFERENCES dimension.producto (id_producto);





create table hechos.aforo
(
id_aforo bigserial not null,
fecha_ingreso TIMESTAMP WITH TIME ZONE null,
fecha_salida TIMESTAMP WITH TIME ZONE null,
primary key (id_aforo)
);







SET timezone TO 'America/Bogota';



commit;

insert into dimension.geografia(id_pais,pais,departamento,ciudad) values('CO','Colombia','Valle del Cauca','Cali');
insert into dimension.geografia(id_pais,pais,departamento,ciudad) values('CO','Colombia','Nariño','Pasto');
insert into dimension.barrio(nombre) values('Valle de Lili');
insert into dimension.cliente (id_geografia,id_tiempo_fecha_creacion,cedula,nombre,password,direccion,telefono, email, tipo_cliente, sexo, id_barrio, barrio ) values(1,20200702,'13072207','Ivan Hernandez','1234','Calle 45 # 99 39','3014317636', 'ivan.hernandez.coral@gmail.com', 'cliente', 'M', 1, 'Valle de Lili');
insert into dimension.estado(id_estado, descripcion) values(100, 'PENDIENTE');
insert into dimension.estado(id_estado, descripcion) values(101, 'ACEPTADO');
insert into dimension.estado(id_estado, descripcion) values(102, 'CANCELADO');
insert into dimension.estado(id_estado, descripcion) values(103, 'ACTIVO');
insert into dimension.estado(id_estado, descripcion) values(104, 'INACTIVO');
insert into dimension.estado(id_estado, descripcion) values(105, 'DESPACHADO');
insert into dimension.empresa(id_empresa, id_geografia, tipo, nit, nombre, id_estado) values(100, 1, 'F', '1010', 'Plazoleta Lili', 103);
insert into dimension.empresa(id_empresa, id_geografia, tipo, nit, nombre, id_estado) values(101, 1, 'F', '1010', 'Corner Burger', 103);
insert into dimension.empresa(id_empresa, id_geografia, tipo, nit, nombre, id_estado) values(102, 1, 'F', '1010', 'Cheers', 103);
insert into dimension.empresa(id_empresa, id_geografia, tipo, nit, nombre, id_estado) values(103, 1, 'F', '1010', 'Pollo Sorpresa', 103);
commit;


insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(100, 101, 100, 'American', 'ean-Beef Grill', 'Beef Grill', 1, 24, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/beef-grill.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(101, 101, 100, 'American', 'ean-Chicken Picatta', 'Chicken Picatta', 1, 20, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-piccata.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(102, 101, 100, 'American', 'ean-Chicken Romesco', 'Chicken Romesco', 1, 21, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-romesco.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(103, 101, 100, 'American', 'ean-Chicken Grill', 'Chicken Grill', 1, 22, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/chicken-grill.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(104, 101, 100, 'American', 'ean-Salmon Grill', 'Salmon Grill', 1, 26, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/salmon-grill.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(105, 101, 100, 'American', 'ean-Salmon Romesco', 'Salmon Romesco', 1, 25, 'http://tutofox.com/foodapp//categories/american.png', 'http://tutofox.com/foodapp//food/american/salmon-romesco', 103);




insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(106, 101, 100, 'Burger', 'ean-Burger Home', 'Burger Home', 1, 16, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-1.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(107, 101, 100, 'Burger', 'ean-MegaBurger', 'MegaBurger', 1, 16, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-2.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(108, 101, 100, 'Burger', 'ean-Burger Special', 'Burger Special', 1, 17, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-3.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(109, 101, 100, 'Burger', 'ean-Burger Cheese', 'Burger Cheese', 1, 18, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-4.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(110, 101, 100, 'Burger', 'ean-Burger Farmer', 'Burger Farmer', 1, 19, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-5.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(111, 101, 100, 'Burger', 'ean-Mini Burger', 'Mini Burger', 1, 10, 'http://tutofox.com/foodapp//categories/burger.png', 'http://tutofox.com/foodapp//food/burger/burger-6.png', 103);


insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(112, 101, 100, 'Pizza', 'ean-Pizza Vegan', 'Pizza Vegan', 1, 14, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-1.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(113, 101, 100, 'Pizza', 'ean-Pizza Bufalisimo', 'Pizza Bufalisimo', 1, 15, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-2.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(114, 101, 100, 'Pizza', 'ean-Pizza Haiwaiana', 'Pizza Haiwaiana', 1, 16, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-3.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(115, 101, 100, 'Pizza', 'ean-Pizza Peperoni', 'Pizza Peperoni', 1, 17, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-4.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(116, 101, 100, 'Pizza', 'ean-Pizza Vegan 2', 'Pizza Vegan 2', 1, 33, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-5.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(117, 101, 100, 'Pizza', 'ean-Pizza Napolitana', 'Pizza Napolitana', 1, 30, 'http://tutofox.com/foodapp//categories/pizza.png', 'http://tutofox.com/foodapp//food/pizza/pizza-6.png', 103);


insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(118, 101, 100, 'Drink', 'ean-Coca cola', 'Coca cola', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/cocacola.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(119, 101, 100, 'Drink', 'ean-Pepsi', 'Pepsi', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/pepsi.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(120, 101, 100, 'Drink', 'ean-Quatro', 'Quatro', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/quatro.png', 103);

insert into dimension.producto(id_producto, id_empresa, id_catalogo, categoria_nivel1, ean, nombre, nivel, valor, url_imagen_categoria, url_imagen_producto, id_estado) values(121, 101, 100, 'Drink', 'ean-Sprite', 'Sprite', 1, 30, 'http://tutofox.com/foodapp//categories/drink.png', 'http://tutofox.com/foodapp//food/drink/sprite.png', 103);


--insert into dimension.aforo(fecha_ingreso) values(TIMESTAMP '2011-05-16 15:36:38');
--insert into dimension.aforo(fecha_salida) values(TIMESTAMP '2011-05-16 16:36:38');

commit;














/********************************************************************************************************************************
 * Consultas de DBA
 *********************************************************************************************************************************/

--SELECT * --table_name AS "Tabla", ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Tamaño (MB)"
--FROM information_schema.TABLES
----WHERE table_schema in ("dimension", "hechos")
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
--select nextval('dimension.cliente_id_cliente_seq');
--
--SELECT * FROM pg_class c WHERE c.relkind = 'S';





/********************************************************************************************************************************
 * Soporte Aforo
 *********************************************************************************************************************************/
--create table dimension.tiempo2 as (select * from dimension.tiempo);

--select  
--min(fecha_ingreso ), max(fecha_ingreso ), CURRENT_DATE
----count(1) 
----delete
--from dimension.aforo
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
--		from dimension.aforo
--		where fecha_ingreso is not null and fecha_ingreso >= CURRENT_DATE
--	) ingreso,
--	(
--		select count(1)
--		from dimension.aforo
--		where fecha_salida is not null and fecha_salida >= CURRENT_DATE
--	) salida
--) tabla
--;





/********************************************************************************************************************************
 * Soporte Mi Barrio
 *********************************************************************************************************************************/

--select count(1), cedula, id_tiempo_fecha_creacion from dimension.cliente 
--group by cedula,id_tiempo_fecha_creacion having count(1)>1 
--order by 3 desc
--;
--
--
--
--
--select * from dimension.empresa;
--
--select * from dimension.geografia;
--
--select min(id_tiempo ) from dimension.tiempo;
--
--select * from dimension.barrio;
--
--select * from dimension.estado;
--
--select * from dimension.producto;
--
--select * from dimension.cliente order by 1 desc;
--
--select  * from dimension.aforo 
----order by 1 desc
--;
--
--
--update hechos.pedido set id_tienda = null, id_estado = 100 where id_pedido in (1,2,3,4,5,6,7);
--
--
--
--select * from hechos.pedido;
--
--select * from dimension.producto_pedido;
--
--truncate table hechos.pedido;
--
--delete from dimension.cliente where id_cliente > 4;
--
--select p.id_pedido, t.nombre tienda, pro.nombre producto, pp.cantidad, pp.valor, c.nombre cliente, p.fecha_pedido, e.descripcion estado, e.id_estado 
--from hechos.pedido p 
--inner join dimension.producto_pedido pp on pp.id_pedido = p.id_pedido
--inner join dimension.producto pro on pro.id_producto = pp.id_producto
--inner join dimension.cliente c on c.id_cliente = p.id_cliente
--left outer join dimension.cliente t on t.id_cliente = p.id_tienda
--inner join dimension.estado e on e.id_estado = p.id_estado
--where 1=1
----and p.id_tienda is null
----and p.id_pedido = 14
--;
