CREATE TABLE Ligue(
   Id_L SMALLINT,
   Nom_L VARCHAR(30),
   PRIMARY KEY(Id_L)
);

CREATE TABLE Employer(
   ID_E SMALLINT,
   Nom_E VARCHAR(20),
   Prenom VARCHAR(20),
   Mail VARCHAR(50),
   MDP VARCHAR(50),
   Status VARCHAR(20),
   Date_Dép DATE,
   Date_Arri DATE,
   Id_L SMALLINT,
   PRIMARY KEY(ID_E),
   FOREIGN KEY(Id_L) REFERENCES Ligue(Id_L)
);