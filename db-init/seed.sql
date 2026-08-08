INSERT INTO utente (nickname, email, password, fotoProfilo, biografia, manager)
VALUES ('marcorossi', 'marco@test.com', 'password123', NULL, 'Insegnante di tecnologia', 1),
       ('giuliabianchi', 'giulia@test.com', 'password123', NULL, 'Appassionata di natura', 0),
       ('lucaverdi', 'luca@test.com', 'password123', NULL, 'Studente curioso', 0);

INSERT INTO categoria (nome)
VALUES ('tecnologia'),
       ('lavoro'),
       ('natura');

INSERT INTO tag (nome)
VALUES ('principiante'),
       ('avanzato'),
       ('online');

INSERT INTO corso (nome, prezzoBase, testo, copertina)
VALUES ('Introduzione a Java', 29.99, 'Corso base di programmazione Java per principianti.', NULL),
       ('Escursionismo in Montagna', 15.00, 'Guida pratica alle escursioni e alla sopravvivenza in natura.', NULL),
       ('Marketing Digitale', 49.99, 'Strategie di marketing per il mondo digitale.', NULL);

INSERT INTO utenteCreaCorso (nicknameUtente, idCorso)
VALUES ('marcorossi', 1),
       ('giuliabianchi', 2),
       ('marcorossi', 3);

INSERT INTO corsoCategoria (idCorso, nomeCategoria)
VALUES (1, 'tecnologia'),
       (2, 'natura'),
       (3, 'lavoro');

INSERT INTO corsoTag (idCorso, nomeTag)
VALUES (1, 'principiante'),
       (1, 'online'),
       (2, 'principiante'),
       (3, 'avanzato');

INSERT INTO utentePartecipaCorso (nicknameUtente, idCorso)
VALUES ('lucaverdi', 1);

INSERT INTO carrello (id)
VALUES (1),
       (2),
       (3);

INSERT INTO utenteCarrello (nicknameUtente, idCarrello)
VALUES ('marcorossi', 1),
       ('giuliabianchi', 2),
       ('lucaverdi', 3);