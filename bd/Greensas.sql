SELECT *
from dbo.grupo_articulos ga 
;







/******* ARTICULOS ******/

SELECT  
--distinct SUBSTRING(a.codigo, 0, CHARINDEX('T', a.codigo)+1) as codigo_articulo
--a.codigo, SUBSTRING(a.codigo, 0, CHARINDEX('T', a.codigo)+1) as nuevo_codigo, a.grupo, a.nombre, a.nombre as descripcion, 1 as cantidad, pa.precio, SUBSTRING(a.codigo, CHARINDEX('T', a.codigo)+1, 5) as talla, a.fecha 
a.codigo, SUBSTRING(a.codigo, 0, CHARINDEX('T', a.codigo)+1) as codigo_articulo, a.grupo, a.nombre, a.nombre as descripcion, 1 as cantidad, pa.precio, SUBSTRING(a.codigo, CHARINDEX('T', a.codigo)+1, 5) as talla 
from dbo.articulos a
inner join dbo.precios_articulos pa on pa.cod_articulo = a.codigo
where 1=1
and a.codigo in ('3002001001T04','3002001002T02','3002001003T08','3003001003T04','3003001005T04','3005004044T18','3005004045T10','3005004047T3','3007002001T12M','3007002001T18M','3007002001T24','3007002001T6-9M','3007002002T12M','3012004001T1','3012005002TL','3012008001TXL','3008007001T14','3004001054TS','3004001058TXL','3005006028T9','3005005086T24','3012008002TXL','3007002066T18','3007002066T12','3007002066T18','3007002066T24','3007002067T12','3007002002T18M','3007002002T24','3007002067T18','3007002067T24','3007002067T6-9','3012005002TM','3012005002TS','3012005002TXL','3012008001TL','3012008001TM','3012008001TS','3012008002TL','3012008002TM','3012008002TS','3004001054TM','3004001054TS','3004001058TL','3004001058TM','3004001058TS') 
--and a.codigo like '%3004001058T%'
and pa.cod_listaprecios = 01
--and pa.precio <> 0
--and a.grupo in ('DAMA')
--and a.nombre like '%BUSO HOMBRE AMAC0002%'
--and a.codigo like '%.%'
order by 1
;



SELECT *
FROM dbo.lista_precios lp 
;

SELECT * 
FROM dbo.precios_articulos pa 
where 1=1
--and cod_articulo in ('3002001002T12', '3002001002T14')
--and cod_listaprecios = 01
;

SELECT *
from dbo.articulos 
;




/******* CIUDADES ******/
SELECT (c.codigo_provincia+c.codigo_ciudad) as id, c.codigo_pais, c.codigo_provincia, CASE c.codigo_ciudad WHEN '' THEN c.codigo_ciudad ELSE (c.codigo_provincia + '-' + c.codigo_ciudad) END codigo_ciudad, c.nombre 
from ciudades c
where 1=1
and c.codigo_provincia in ('09')
--and (codigo_ciudad = '' or codigo_ciudad = '001')
order by c.codigo_provincia, c.codigo_ciudad, c.nombre 
;




/******* CLIENTES ******/

--POSTMAN
UPDATE dbo.clientes_registro 
set 
--correo = 'ivan.hernandez.coral11@gmail.com', 
--clave= '$2a$10$O3jiJVKkdk/cUOTNndkW9egKXd23hc/Sd64GRFYCpAKEu.EpjywGm',
direccion = 'dir',
--telefono = '22222',
--direccion_entrega = 'dir entrega',
--latitud = 'lt',
cedula = '13072207'
where cedula = '13072207';


http://localhost:3000/usuario/cuenta/datos-acceso
http://localhost:3000/usuario/informacion-personal
http://localhost:3000/ajustes
http://localhost:3000/transaccion


ALTER TABLE [dbo].[clientes_registro] ALTER COLUMN clave varchar(400);




TRUNCATE table dbo.clientes_registro ;

TRUNCATE table pedido_encabezado;
TRUNCATE table pedido_detalle;

--

SELECT *
from dbo.clientes_registro 
;


Consultores expertos en las tecnologías líderes

Diseño y ejecución del proyecto de implementación

Construcción de tableros aplicando las mejores prácticas de la industria

Transferencia de conocimiento



/******* PEDIDOS ******/
SELECT * 
from dbo.pedido_encabezado p
inner join pedido_detalle pd on pd.nro_pedido = p.nro_pedido
where 1=1
and p.cliente = '13072207'
;

SELECT *
from pedido_encabezado p
--where p.nro_pedido = '1010'
;


SELECT * 
from pedido_detalle pd
;

--DELETE from pedido_encabezado where nro_pedido = '000000980000002';
--DELETE from pedido_detalle where nro_pedido = '000000980000002';


--CAMISETA DE NIÑO MAURICIO T.04

UPDATE dbo.articulos set 
detalle = detalle+'-Producto elaborado con amor y con alta calidad, tela fria, absorve el sudor y dura mil lavadas-3002001001T-3002001002T-3002001003T'
--detalle = SUBSTRING(detalle, 0, CHARINDEX('-', detalle))
where codigo in ('3002001001T04','3002001002T02','3002001003T08','3003001003T04','3003001005T04','3005004044T18','3005004045T10','3005004047T3','3007002001T12M','3007002001T18M','3007002001T24','3007002001T6-9M','3007002002T12M','3012004001T1','3012005002TL','3012008001TXL','3008007001T14','3004001054TS','3004001058TXL','3005006028T9','3005005086T24','3012008002TXL','3007002066T18','3007002066T12','3007002066T18','3007002066T24','3007002067T12','3007002002T18M','3007002002T24','3007002067T18','3007002067T24','3007002067T6-9','3012005002TM','3012005002TS','3012005002TXL','3012008001TL','3012008001TM','3012008001TS','3012008002TL','3012008002TM','3012008002TS','3004001054TM','3004001054TS','3004001058TL','3004001058TM','3004001058TS') 
--and subgrupo = 'PIJAMAS DE HOMBRE'
;





SELECT a.detalle, 
---detalle+'-Descripción del producto '+detalle+'-Producto elaborado con amor y con alta calidad, tela fria, absorve el sudor y dura mil lavadas' nuevo, 
a.*
from dbo.articulos a 
--where a.codigo like '%3002001003T08%'
where a.codigo in ('3002001001T04','3002001002T02','3002001003T08','3003001003T04','3003001005T04','3005004044T18','3005004045T10','3005004047T3','3007002001T12M','3007002001T18M','3007002001T24','3007002001T6-9M','3007002002T12M','3012004001T1','3012005002TL','3012008001TXL','3008007001T14','3004001054TS','3004001058TXL','3005006028T9','3005005086T24','3012008002TXL','3007002066T18','3007002066T12','3007002066T18','3007002066T24','3007002067T12','3007002002T18M','3007002002T24','3007002067T18','3007002067T24','3007002067T6-9','3012005002TM','3012005002TS','3012005002TXL','3012008001TL','3012008001TM','3012008001TS','3012008002TL','3012008002TM','3012008002TS','3004001054TM','3004001054TS','3004001058TL','3004001058TM','3004001058TS') 
--and subgrupo = 'CAMISETAS DE NIÑO'
order by subgrupo 
;
https://www.zara.com/co/es/pantalón-masculino-full-length-p07385169.html?v1=94353190&v2=1712675
https://www.zara.com/co/es/blazer-recta-bolsillos-p02010708.html?v1=100624056&v2=1712675

SELECT pa.cod_articulo, pa.precio, (pa.precio * 3) precio_web, ROUND(pa.precio, 2) redondeo_ori, (ROUND(pa.precio, 2) * 3) redondeo
from dbo.precios_articulos pa 
where pa.cod_articulo in (SELECT pd.cod_articulo from pedido_detalle pd)
and pa.cod_listaprecios ='01'
;



SELECT a.codigo, SUBSTRING(a.codigo, 0, CHARINDEX('T', a.codigo)+1) as codigo_articulo, a.grupo, a.detalle, 1 as cantidad, ROUND(pa.precio, 2) precio, SUBSTRING(a.codigo, CHARINDEX('T', a.codigo)+1, 5) as talla 
, SUBSTRING(a.detalle, 0, CHARINDEX('-', a.detalle))
from dbo.articulos a 
inner join dbo.precios_articulos pa on pa.cod_articulo = a.codigo 
where a.codigo in ('3002001001T04','3002001002T02','3002001003T08','3003001003T04','3003001005T04','3005004044T18','3005004045T10','3005004047T3','3007002001T12M','3007002001T18M','3007002001T24','3007002001T6-9M','3007002002T12M','3012004001T1','3012005002TL','3012008001TXL','3008007001T14','3004001054TS','3004001058TXL','3005006028T9','3005005086T24','3012008002TXL','3007002066T18','3007002066T12','3007002066T18','3007002066T24','3007002067T12','3007002002T18M','3007002002T24','3007002067T18','3007002067T24','3007002067T6-9','3012005002TM','3012005002TS','3012005002TXL','3012008001TL','3012008001TM','3012008001TS','3012008002TL','3012008002TM','3012008002TS','3004001054TM','3004001054TS','3004001058TL','3004001058TM','3004001058TS') 
and pa.cod_listaprecios = 01
;





--DESCRIPCIÓN = NOMBRE ARTICULO|DESCRIPCION ARTICULO|DETALLE ARTICULO

--Producto al cual le colocamos especificaciones y rayas para separar nombre de la descripcion
--3002002001T02







 /********************************************************************************************************************
 * 
 * PENDIENTES
 * 
 ********************************************************************************************************************/
cedula); --ruc
nombre);
provincia);
ciudad);
direccion);
correo); 
telefono); 
password);
fechaNacimiento); --fecha registro ?
direccion); --direccion entrega

  
--Por implementar en la tabla dbo.clientes_registro
sexo = F-M
politicas = 1-0
pais = 
fechaNacimiento = ''


	cedula
	nombres
	codprovincia
	codciudad
	direccion
	correo
	telefono
	clave
fecha
	direccion_entrega
latitud
longitud
estado


/* Validar bien si no tengo q mirar las tablas para nada
SELECT *
FROM dbo.clientes c 
WHERE 1=1
and c.codigo > 2468
;

SELECT *
FROM dbo.direcciones d 
where 1=1
and codigo in (2500, 2503)
;
*/







  
  
  
 /********************************************************************************************************************
 * 
 * DIMENSION CIUDADES
 * 
 ********************************************************************************************************************/

SELECT 
(c.codigo_provincia+c.codigo_ciudad) as ciudades, 
prov.nombre as provincia,
c.nombre as ciudad
from dbo.ciudades c 
inner join dbo.ciudades prov on prov.codigo_provincia = c.codigo_provincia and prov.codigo_ciudad = ''
where (c.codigo_provincia+c.codigo_ciudad) = '10002'
order by c.codigo_provincia, c.codigo_ciudad, c.nombre
;





 /********************************************************************************************************************
 * 
 * DIMENSION CLIENTES
 * 
 ********************************************************************************************************************/

SELECT 
CAST(cedula AS numeric) as clientes,
cedula,
nombres as cliente,
direccion,
correo,
telefono,
direccion_entrega
from dbo.clientes_registro
where cedula = '1'
;




  


/********************************************************************************************************************
 * 
 * DIMENSION ARTICULOS
 * 
 ********************************************************************************************************************/

SELECT a.codigo as articulos, 
a.grupo, 
a.subgrupo,
a.nombre as articulo,  
pa.precio, 
SUBSTRING(a.codigo, CHARINDEX('T', a.codigo)+1, 5) as talla,
a.stock 
from dbo.articulos a 
inner join dbo.precios_articulos pa on pa.cod_articulo = a.codigo 
where 1=1
--and a.codigo in ('3002001001T04','3002001002T02','3002001003T08','3003001003T04','3003001005T04','3005004044T18','3005004045T10','3005004047T3','3007002001T12M','3007002001T18M','3007002001T24','3007002001T6-9M','3007002002T12M','3012004001T1','3012005002TL','3012008001TXL','3008007001T14','3004001054TS','3004001058TXL','3005006028T9','3005005086T24','3012008002TXL','3007002066T18','3007002066T12','3007002066T18','3007002066T24','3007002067T12','3007002002T18M','3007002002T24','3007002067T18','3007002067T24','3007002067T6-9','3012005002TM','3012005002TS','3012005002TXL','3012008001TL','3012008001TM','3012008001TS','3012008002TL','3012008002TM','3012008002TS','3004001054TM','3004001054TS','3004001058TL','3004001058TM','3004001058TS') 
and pa.cod_listaprecios = 01
and a.codigo = '3007002067T6-9'
;

UPDATE dbo.articulos set stock = 18 where codigo = '3007002002T18M';






 /********************************************************************************************************************
 * 
 * HECHOS PEDIDOS
 * 
 ********************************************************************************************************************/
SELECT p.nro_pedido, p.estado, p.cliente 
from pedido_encabezado p
where 1=1
--and cliente = '13072207'
and nro_pedido = '1010'
order by p.cliente ;


SELECT * 
from pedido_detalle pd
;

UPDATE pedido_encabezado set codprovincia = '17', codciudad = '001'
--fecha = getdate()-7 
where 1=1
--and nro_pedido = '1000'
and cliente = '12121212'
;

select getdate();



SELECT 
p.nro_pedido as pedidos,
p.cliente as clientes,
pd.cod_articulo as articulos,
(p.codprovincia + p.codciudad) as ciudades,
p.hora,
pd.cantidad,
pd.precio_venta as valor,
p.fecha as fecha_pedido,
p.fechaDespacho as fecha_despacho,
p.estado
from dbo.pedido_encabezado p
inner join dbo.pedido_detalle pd on pd.nro_pedido = p.nro_pedido
;



















