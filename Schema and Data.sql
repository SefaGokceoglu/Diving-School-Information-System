--DROP TABLE uyeler;
CREATE TABLE  uyeler (
	fname varchar(15) not null,
	lname varchar(15) not null,
	kayıtid char(9) not null,
	boy smallint not null,
	bdate date not null,
	sid char(4),
	srapor boolean not null,
	primary key (kayıtid)
	--foreign key (sid) references sınıflar(sınıfid)
);
	
	
--DROP TABLE sınıflar;
CREATE TABLE  sınıflar (
	sınıfid char(4),
	kapasite int,
	yıldız varchar(12),
	dalısegid char(9),
	primary key (sınıfid)
	--foreign key (dalısegid) references ogretmenler(personalid)
);
ALTER TABLE uyeler ADD CONSTRAINT foreign_key_const0
  foreign key (sid) references sınıflar(sınıfid);

--DROP TABLE ogretmenler;
CREATE TABLE  ogretmenler (
	fname varchar(15) not null,
	lname varchar(15) not null,
	personalid char(9),
	ogrsayisi int,
	oyıldız varchar(12) not null,
	havuzkey varchar(4),
	primary key (personalid)
	--foreign key (havuzkey) references havuzlar(havuzid)
);
ALTER TABLE sınıflar ADD CONSTRAINT foreign_key_const1
  foreign key (dalısegid) references ogretmenler(personalid);

--DROP TABLE dalısyeri;
CREATE TABLE  dalısyeri (
		dname varchar(25) not null,
		havuzid varchar(4) not null,
		derinlik int not null,
		çeşitlilik varchar(6),
		hyıldız varchar(12),
		primary key (havuzid),
		unique (dname)
		--foreign key (hyıldız) references sınıflar(yıldız)
);
ALTER TABLE ogretmenler ADD CONSTRAINT foreign_key_const3
  foreign key (havuzkey) references dalısyeri(havuzid);
  







		
		
insert into dalısyeri values
('Samandağ','701', 20,'Çok','İleri');
insert into dalısyeri values
('Saros','702', 28,'Orta', 'Orta');
insert into dalısyeri values
('Karaburun','703', 15,'Az','Başlangıç');
insert into dalısyeri values
('Sivriada','704', 18,'Az','Başlangıç');
insert into dalısyeri values
('Fethiye','705', 21,'Çok','Orta');
insert into dalısyeri values
('Delikli Mağara','706', 15,'Orta','Başlangıç');
insert into dalısyeri values
('Bodrum ','707', 12,'Az','Başlangıç');
insert into dalısyeri values
('İzmir','708', 17,'Orta','Orta');
insert into dalısyeri values
('Tekirova','709',19,'Çok','İleri');
insert into dalısyeri values
('Gökova Kocadağ','710',22,'Orta','İleri');

insert into ogretmenler values
('İsmail','Kara','500000011', 7 ,'Başlangıç',703);
insert into ogretmenler values
('İbrahim','Kaya','500000021', 2 ,'Orta',702);
insert into ogretmenler values
('Meryem','Uşak','500000022', 3 ,'Orta',705);
insert into ogretmenler values
('Eyşan','Dehşet','500000012', 3 ,'Başlangıç',704);
insert into ogretmenler values
('Kuzey','Tekinoğlu','500000031', 6 ,'İleri',709);
insert into ogretmenler values
('Ramiz','Karaeski','500000032', 0 ,'İleri',701);
insert into ogretmenler values
('Ahmet','Karaeski','500000038', 0 ,'İleri',709);
insert into ogretmenler values
('Mehmet','Karaeski','500000042', 0,'Orta',708);
insert into ogretmenler values
('Ali','Okka','500000048', 0,'İleri',709);
insert into ogretmenler values
('Yusuf','Serçe','500000049', 0,'İleri',710);



insert into sınıflar values
('1001', 7,'Başlangıç','500000011');
insert into sınıflar values
('1002', 7,'Orta','500000021');
insert into sınıflar values
('1003', 7,'İleri','500000031');
insert into sınıflar values
('1004', 7,'Orta','500000022');
insert into sınıflar values
('1005', 7,'İleri','500000031');
insert into sınıflar values
('1006', 7,'Başlangıç','500000012');
insert into sınıflar values
('1007', 7,'Başlangıç',null);
insert into sınıflar values
('1008', 7,'Orta',null);
insert into sınıflar values
('1009', 7,'İleri',null);
insert into sınıflar values
('1010', 7,'Başlangıç',null);
insert into sınıflar values
('1011', 7,'Orta',null);


insert into UYELER values
('Bob', 'Marley', '100000001', 188,'16-JAN-1958', null , '1');--'101A'
insert into UYELER values
('Aslıhan', 'Gürkaş', '100000002', 170,'05-MAY-2001', null, '1');--'101B'
insert into UYELER values
('Zübeyde', 'Seçen', '100000003', 172,'28-JUN-2000', null, '1');--'101B'
insert into UYELER values
('Pınar', 'Akalan', '100000004', 178,'07-DEC-1989', null, '1');--'101A'
insert into UYELER values
('Melikşah', 'Köle', '100000005', 175,'18-OCT-2000', null, '1');--'101A'
insert into UYELER values
('Melih', 'Kırık', '100000011', 180,'15-NOV-1998', null, '1');--'101B'
insert into UYELER values
('Haydar', 'Inna', '100000018', 177,'21-APR-1995', null, '1');--'101A'
insert into UYELER values
('İsmail', 'Akacak', '100000105', 184,'03-APR-1997', null, '1');--'101C'
insert into UYELER values
('Memati', 'Baş', '100000111', 180,'27-OCT-1999', null, '1');--'201A'
insert into UYELER values
('Polat', 'Alemdar', '100000121', 178,'11-JUN-1981', null, '1');--'201A'
insert into UYELER values
('Abdulhey', 'Çoban', '100000131', 189,'19-AUG-1985', null, '1');--'201A'
insert into UYELER values
('Elif', 'Eylül', '100000141', 168,'22-MAR-1986', null, '1');--201A
insert into UYELER values
('Süleyman', 'Çakır', '100000151', 176,'07-MAR-1974', null, '1');--'201A'
insert into UYELER values
('Erhan', 'Güllü', '100000176', 174,'25-NOV-1990', null, '1');--'201A'
insert into UYELER values
('Safiye', 'Karahanlı', '100000204', 165,'06-JUN-1980', null, '1');--'201B'
insert into UYELER values
('Derya', 'Çakır', '100000209', 170,'23-OCT-1978', null, '1');--'201B'
insert into UYELER values
('Nesrin', 'Çakır', '100000213', 169,'18-JAN-1979', null, '1');--'201B'
insert into UYELER values
('Aslan', 'Akbey', '100000301', 177,'05-NOV-1971', null, '1');--'101C'
insert into UYELER values
('Pusat', 'Çakır', '100000302', 184,'01-APR-1993', null, '1');--'101C'
insert into UYELER values
('Hikmet', 'Deli', '100000327', 172,'23-MAR-1989', null, '1');--'101C'
insert into UYELER values
('Zehra', 'Atik', '100000339', 175,'03-JUN-1997', null, '0');--'null'
insert into UYELER values
('Ayşe','Yiğit', '100000351', 178,'12-JAN-1996', null, '1');--'201C'
insert into UYELER values
('Büşra', 'Koç', '100000377', 182,'16-MAR-1999', null, '0');--null

UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000001';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000002';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000003';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000004';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000011';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000018';
UPDATE UYELER SET sid = '1001' WHERE kayıtid = '100000105';
UPDATE UYELER SET sid = '1002' WHERE kayıtid = '100000111';
UPDATE UYELER SET sid = '1002' WHERE kayıtid = '100000121';
UPDATE UYELER SET sid = '1003' WHERE kayıtid = '100000131';
UPDATE UYELER SET sid = '1003' WHERE kayıtid = '100000141';
UPDATE UYELER SET sid = '1003' WHERE kayıtid = '100000151';
UPDATE UYELER SET sid = '1003' WHERE kayıtid = '100000176';
UPDATE UYELER SET sid = '1004' WHERE kayıtid = '100000204';
UPDATE UYELER SET sid = '1004' WHERE kayıtid = '100000209';
UPDATE UYELER SET sid = '1004' WHERE kayıtid = '100000213';
UPDATE UYELER SET sid = '1005' WHERE kayıtid = '100000301';
UPDATE UYELER SET sid = '1005' WHERE kayıtid = '100000302';
UPDATE UYELER SET sid = '1006' WHERE kayıtid = '100000327';
UPDATE UYELER SET sid = '1006' WHERE kayıtid = '100000351';
UPDATE UYELER SET sid = '1006' WHERE kayıtid = '100000005';