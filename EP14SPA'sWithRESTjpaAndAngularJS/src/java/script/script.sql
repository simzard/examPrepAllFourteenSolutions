drop table if exists restaurant;

create table restaurant (
  id int Auto_Increment primary key,
  name varchar(45) not null,
  address  varchar(100),
  phone varchar(14),
  url varchar (70),
  review int (2),
  restaurentType varchar(30),
  priceLevel varChar(15)
);
  
insert into restaurant values (null,'Lazio','Godthåbsvej 191, 2720 Vanløse','12345678','http://restaurant-lazio.dk/',4,'Italian','cheap');
insert into restaurant values (null,'Condesa','Ved Stranden 18, 1061 København K','12665678','http://www.condesa.dk/',3,'Mexican','average');
insert into restaurant values (null,'Chicos Cantina','Borgergade 2 1300 København K','126672378','http://www.chicos-cantina.dk/',2,'Mexican','average');
insert into restaurant values (null,'Noodle House','Abel Cathrines Gade 23 1654 København V','121172378','http://www.chicos-cantina.dk/',4,'Chineese','cheap');
insert into restaurant values (null,'Restaurant Kokkeriet','Abel Cathrines Gade 23 1654 København V','56562312','http://www.kokkeriet.dk/',5,'Danish','expensive');
