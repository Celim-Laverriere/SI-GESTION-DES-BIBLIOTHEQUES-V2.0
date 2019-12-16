
CREATE SEQUENCE public.ouvrage_id_seq;

CREATE TABLE public.ouvrage (
                id INTEGER NOT NULL DEFAULT nextval('public.ouvrage_id_seq'),
                titre VARCHAR NOT NULL,
                genre VARCHAR NOT NULL,
                resumer VARCHAR NOT NULL,
                auteur VARCHAR NOT NULL,
                editeur VARCHAR NOT NULL,
                ref VARCHAR NOT NULL,
                CONSTRAINT ouvrage_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.ouvrage_id_seq OWNED BY public.ouvrage.id;

CREATE SEQUENCE public.photo_id_seq;

CREATE TABLE public.photo (
                id INTEGER NOT NULL DEFAULT nextval('public.photo_id_seq'),
                url_photo VARCHAR NOT NULL,
                nom_photo VARCHAR NOT NULL,
                ouvrage_id INTEGER NOT NULL,
                CONSTRAINT photo_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.photo_id_seq OWNED BY public.photo.id;

CREATE SEQUENCE public.livre_id_seq;

CREATE TABLE public.livre (
                id INTEGER NOT NULL DEFAULT nextval('public.livre_id_seq'),
                ref_bibliotheque VARCHAR NOT NULL,
                statut VARCHAR NOT NULL,
                ouvrage_id INTEGER NOT NULL,
                CONSTRAINT livre_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.livre_id_seq OWNED BY public.livre.id;

CREATE UNIQUE INDEX livre_idx
 ON public.livre
 ( ref_bibliotheque );

CREATE SEQUENCE public.compte_id_seq;

CREATE TABLE public.compte (
                id INTEGER NOT NULL DEFAULT nextval('public.compte_id_seq'),
                nom VARCHAR NOT NULL,
                prenom VARCHAR NOT NULL,
                adresse VARCHAR NOT NULL,
                code_postal INTEGER NOT NULL,
                ville VARCHAR NOT NULL,
                num_portable INTEGER,
                num_domicile INTEGER,
                num_carte_bibliotheque INTEGER NOT NULL,
                mail VARCHAR NOT NULL,
                mot_de_passe VARCHAR NOT NULL,
                CONSTRAINT compte_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.compte_id_seq OWNED BY public.compte.id;

CREATE UNIQUE INDEX compte_idx
 ON public.compte
 ( num_carte_bibliotheque, mot_de_passe );

CREATE UNIQUE INDEX compte_idx1
 ON public.compte
 ( mail );

CREATE SEQUENCE public.emprunt_id_seq;

CREATE TABLE public.emprunt (
                id INTEGER NOT NULL DEFAULT nextval('public.emprunt_id_seq'),
                date_debut DATE NOT NULL,
                date_fin DATE NOT NULL,
                prolongation BOOLEAN,
                livre_id INTEGER NOT NULL,
                compte_id INTEGER NOT NULL,
                CONSTRAINT emprunt_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.emprunt_id_seq OWNED BY public.emprunt.id;

ALTER TABLE public.livre ADD CONSTRAINT ouvrage_livre_fk
FOREIGN KEY (ouvrage_id)
REFERENCES public.ouvrage (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.photo ADD CONSTRAINT ouvrage_photo_fk
FOREIGN KEY (ouvrage_id)
REFERENCES public.ouvrage (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emprunt ADD CONSTRAINT livre_emprunt_fk
FOREIGN KEY (livre_id)
REFERENCES public.livre (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emprunt ADD CONSTRAINT compte_emprunt_fk
FOREIGN KEY (compte_id)
REFERENCES public.compte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
