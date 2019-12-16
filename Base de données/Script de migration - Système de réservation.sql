CREATE SEQUENCE public.reservation_id_seq;

CREATE TABLE public.Reservation (
                id INTEGER NOT NULL DEFAULT nextval('public.reservation_id_seq'),
                date_demande_de_resa DATE NOT NULL,
                num_position_resa INTEGER NOT NULL,
                ouvrage_id INTEGER NOT NULL,
                compte_id INTEGER NOT NULL,
                CONSTRAINT reservation_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.reservation_id_seq OWNED BY public.Reservation.id;

ALTER TABLE public.Reservation ADD CONSTRAINT ouvrage_reservation_fk
FOREIGN KEY (ouvrage_id)
REFERENCES public.ouvrage (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Reservation ADD CONSTRAINT compte_reservation_fk
FOREIGN KEY (compte_id)
REFERENCES public.compte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;