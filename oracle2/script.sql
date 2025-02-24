drop type direccion force;
/
drop type persona force;
/
DROP TYPE GRUPOS_T FORCE;
/
DROP TABLE GRUPOS2;
/
CREATE OR REPLACE TYPE DIRECCION AS OBJECT 
( 
CALLE VARCHAR2(25), 
CIUDAD VARCHAR2(20), 
CODIGO_POST NUMBER(5) 
);
/ 
CREATE OR REPLACE TYPE PERSONA AS OBJECT 
( 
CODIGO NUMBER, 
NOMBRE VARCHAR2(35), 
DIREC DIRECCION, 
FECHA_NAC DATE 
); 
/
CREATE TYPE GRUPOS_T AS TABLE OF PERSONA; 
/ 
CREATE TABLE GRUPOS2(
    ID integer primary key,
    NOMBRE varchar(15),
    FECHA_CREACION date,
    PERSONA_ANIDADA GRUPOS_T
)NESTED TABLE PERSONA_ANIDADA STORE AS PERSONA_ANIDADA_TABLE;
/
INSERT INTO GRUPOS2 VALUES(1,'INFORMÁTICA','21/01/2020',GRUPOS_T(PERSONA(1, 'Juan', Direccion('c/ Conde','Málaga',12001),'17/02/1990'),PERSONA(2, 'María', Direccion('c/ Amiel','Córdoba',16001),'21/10/1991'),PERSONA(3, 'Sofía', Direccion('c/ Castillo','Cádiz',15001),'1/09/1992'),PERSONA(4, 'Carla', Direccion('c/ Sin nombre','Cádiz',11001),'14/08/1990'),PERSONA(5, 'Manuel', Direccion('c/ Cuba','Málaga',10001),'27/02/1989')));

INSERT INTO GRUPOS2 VALUES(2,'ADMINISTRACIÓN','2/02/2020',GRUPOS_T(PERSONA(6, 'Lucas', DirecciOn('c/ España','Granada',11001),'29/01/1988'),PERSONA(7, 'Marta', DirecciOn('c/ Conde','Málaga',12001),'30/07/1986'),PERSONA(8, 'Carmen', DirecciOn('c/ Mimbre','Granada',11001),'1/04/1990'),PERSONA(9, 'Milagros', DirecciOn('c/ Segura','Sevilla',16001),'7/01/1994')));
INSERT INTO GRUPOS2 VALUES(3,'DIRECCIÓN','10/03/2020',GRUPOS_T(PERSONA(10, 'José Miguel', DirecciOn('c/ Volga','Sevilla',15001),'5/12/1979'),PERSONA(11, 'Antonia', DirecciOn('c/ Tajo','Málaga',15001),'17/05/1980')));
delete from grupos2;
COMMIT;
select * from grupos2;

SELECT * FROM GRUPOS2 GR, TABLE(PERSONA_ANIDADA) P WHERE P.DIREC.CIUDAD='Málaga';
