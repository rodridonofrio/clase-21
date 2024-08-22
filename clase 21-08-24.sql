SELECT * FROM central.celular;
select max(precio) from central.celular;
select celular.* from celular where idcompania=1;
select celular.*, compania.nombre from celular inner join compania on celular.idcompania=compania.idcompania where compania.nombre="liberado";
select celular.*, compania.nombre from celular inner join compania on celular.idcompania=compania.idcompania where compania.idcompania=1;
select c.idcelular, c.idsistemaop, c.memoria, c.precio, co.nombre, m.nombre from celular as c inner join compania as co on c.idcompania=co.idcompania inner join modelo as m on c.idmodelo=m.idmodelo where c.idcompania=1;
select cel.idcelular,ma.nombre, mo.nombre, so.nombre, comp.nombre, cel.memoria, cel.precio from celular as cel inner join modelo as mo on cel.idmodelo=mo.idmodelo inner join compania as comp on cel.idcompania=comp.idcompania inner join sistemaoperativo as so on cel.idsistemaop=so.idsistemaop inner join marca as ma on mo.idmarca=ma.idmarca;