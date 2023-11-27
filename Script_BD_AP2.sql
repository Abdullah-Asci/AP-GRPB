/*------------------------Tables-------------------------*/
drop table if exists Ligue;
drop table if exists Employer;

CREATE TABLE Ligue(
   Id_L SMALLINT,
   Nom_L VARCHAR(30),
   PRIMARY KEY(Id_L)
)ENGINE = INNODB;

CREATE TABLE Employer(
   ID_E SMALLINT,
   Nom_E VARCHAR(20),
   Prenom VARCHAR(20),
   Mail VARCHAR(50),
   MDP VARCHAR(50),
   Status VARCHAR(20),
   Date_Dep Date,
   Date_Arri Date,
   Id_L SMALLINT NOT NULL,
   PRIMARY KEY(ID_E),
   FOREIGN KEY(Id_L) REFERENCES Ligue(Id_L)
)ENGINE = INNODB;

/*------------------------Test Enregistrement-------------------------*/

/*Delete from Ligue;
INSERT INTO Ligue (Id_L, Nom_L)
VALUES
   (1,"?");
select * from Ligue;

Delete from Employer;
INSERT INTO Employer (ID_E, Nom_E, Prenom, Mail, MDP, Status, Date_Dep, Date_Arri, Id_L) 
VALUES
   (1,"?","?","?","?","?","2000-00-00","2000-00-00",1);
select * from Employer;*/
