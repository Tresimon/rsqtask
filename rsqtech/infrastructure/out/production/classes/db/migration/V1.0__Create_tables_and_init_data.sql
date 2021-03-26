CREATE TABLE public.doctor
(
    id bigint NOT NULL,
    major character varying(50) NOT NULL,
    name character varying(25) NOT NULL,
    surname character varying(25) NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.doctor_seq
    INCREMENT 50
    START 4
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.patient
(
    id bigint NOT NULL,
    address character varying(50) NOT NULL,
    name character varying(25) NOT NULL,
    surname character varying(25) NOT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.patient_seq
    INCREMENT 50
    START 4
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.visit
(
    id bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    doctor_id bigint NOT NULL,
    patient_id bigint NOT NULL,
    CONSTRAINT visit_pkey PRIMARY KEY (id),
    CONSTRAINT fkc63541y8ppkvsovm00gumv90t FOREIGN KEY (doctor_id)
        REFERENCES public.doctor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrban5yeabnx30seqm69jw44e FOREIGN KEY (patient_id)
        REFERENCES public.patient (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE public.visit_seq
    INCREMENT 50
    START 4
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

INSERT INTO public.doctor(id, major, name, surname)
	VALUES (1, 'Fizjoterapeuta', 'Piotr', 'Zieliński'),
	       (2, 'Dentysta', 'Robert', 'Lewandowski'),
	       (3, 'Urolog', 'Wojciech', 'Szczęsny');

INSERT INTO public.patient(id, address, name, surname)
	VALUES (1, 'ul. Marsyliańska 9', 'Arkadiusz', 'Milik'),
	       (2, 'ul. Bułgarska 17', 'Jakub', 'Moder'),
	       (3, 'ul. Angielska', 'Jan', 'Bednarek');

INSERT INTO public.visit(id, date, doctor_id, patient_id)
	VALUES (1, '2021-03-26 10:00', 1, 2),
	       (2, '2021-03-30 12:00', 2, 1),
	       (3, '2021-03-21 09:00', 3, 2);