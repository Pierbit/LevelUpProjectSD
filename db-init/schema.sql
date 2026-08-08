CREATE TABLE utente
(
    nickname    VARCHAR(50) PRIMARY KEY,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    fotoProfilo VARCHAR(255),
    biografia   TEXT,
    manager     TINYINT(1) DEFAULT 0
);

CREATE TABLE tag
(
    nome VARCHAR(50) PRIMARY KEY
);

CREATE TABLE categoria
(
    nome VARCHAR(50) PRIMARY KEY
);

CREATE TABLE oggetto
(
    id     INT PRIMARY KEY,
    prezzo DECIMAL(10, 2) NOT NULL
);

CREATE TABLE corso
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(150)   NOT NULL,
    prezzoBase DECIMAL(10, 2) NOT NULL,
    testo      TEXT,
    copertina  VARCHAR(255)
);

CREATE TABLE ordine
(
    id         INT PRIMARY KEY,
    dataOrdine DATE
);

CREATE TABLE carrello
(
    id INT PRIMARY KEY
);

-- Junction tables

CREATE TABLE utenteTag
(
    nicknameUtente VARCHAR(50),
    nomeTag        VARCHAR(50),
    PRIMARY KEY (nicknameUtente, nomeTag),
    FOREIGN KEY (nicknameUtente) REFERENCES utente (nickname),
    FOREIGN KEY (nomeTag) REFERENCES tag (nome)
);

CREATE TABLE utentePartecipaCorso
(
    nicknameUtente VARCHAR(50),
    idCorso        INT,
    PRIMARY KEY (nicknameUtente, idCorso),
    FOREIGN KEY (nicknameUtente) REFERENCES utente (nickname),
    FOREIGN KEY (idCorso) REFERENCES corso (id)
);

CREATE TABLE utenteCreaCorso
(
    nicknameUtente VARCHAR(50),
    idCorso        INT,
    PRIMARY KEY (nicknameUtente, idCorso),
    FOREIGN KEY (nicknameUtente) REFERENCES utente (nickname),
    FOREIGN KEY (idCorso) REFERENCES corso (id)
);

CREATE TABLE utenteOrdine
(
    nicknameUtente VARCHAR(50),
    idOrdine       INT,
    PRIMARY KEY (nicknameUtente, idOrdine),
    FOREIGN KEY (nicknameUtente) REFERENCES utente (nickname),
    FOREIGN KEY (idOrdine) REFERENCES ordine (id)
);

CREATE TABLE utenteCarrello
(
    nicknameUtente VARCHAR(50),
    idCarrello     INT,
    PRIMARY KEY (nicknameUtente, idCarrello),
    FOREIGN KEY (nicknameUtente) REFERENCES utente (nickname),
    FOREIGN KEY (idCarrello) REFERENCES carrello (id)
);

CREATE TABLE corsoTag
(
    idCorso INT,
    nomeTag VARCHAR(50),
    PRIMARY KEY (idCorso, nomeTag),
    FOREIGN KEY (idCorso) REFERENCES corso (id),
    FOREIGN KEY (nomeTag) REFERENCES tag (nome)
);

CREATE TABLE corsoCategoria
(
    idCorso       INT,
    nomeCategoria VARCHAR(50),
    PRIMARY KEY (idCorso, nomeCategoria),
    FOREIGN KEY (idCorso) REFERENCES corso (id),
    FOREIGN KEY (nomeCategoria) REFERENCES categoria (nome)
);

CREATE TABLE corsoOggetto
(
    idCorso   INT,
    idOggetto INT,
    PRIMARY KEY (idCorso, idOggetto),
    FOREIGN KEY (idCorso) REFERENCES corso (id),
    FOREIGN KEY (idOggetto) REFERENCES oggetto (id)
);

CREATE TABLE oggettoCarrello
(
    idOggetto  INT,
    idCarrello INT,
    PRIMARY KEY (idOggetto, idCarrello),
    FOREIGN KEY (idOggetto) REFERENCES oggetto (id),
    FOREIGN KEY (idCarrello) REFERENCES carrello (id)
);

CREATE TABLE carrelloOrdine
(
    idCarrello INT,
    idOrdine   INT,
    PRIMARY KEY (idCarrello, idOrdine),
    FOREIGN KEY (idCarrello) REFERENCES carrello (id),
    FOREIGN KEY (idOrdine) REFERENCES ordine (id)
);