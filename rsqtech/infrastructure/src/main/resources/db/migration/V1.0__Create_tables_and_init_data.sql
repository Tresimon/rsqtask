CREATE TABLE public.organisation
(
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT organisation_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.organisation_seq
    INCREMENT 1
    START 3
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.doctor
(
    id bigint NOT NULL,
    major character varying(50) NOT NULL,
    name character varying(25) NOT NULL,
    surname character varying(25) NOT NULL,
    organisation_id bigint NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id),
    CONSTRAINT fklskdj475jgmxsldki837456k FOREIGN KEY (organisation_id)
        REFERENCES public.organisation (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE public.doctor_seq
    INCREMENT 1
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
    organisation_id bigint NOT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (id),
    CONSTRAINT fklskdj475jgmx30ssld94j56k FOREIGN KEY (organisation_id)
        REFERENCES public.organisation (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE public.patient_seq
    INCREMENT 1
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
    organisation_id bigint NOT NULL,
    CONSTRAINT visit_pkey PRIMARY KEY (id),
    CONSTRAINT fkc63541y8ppkvsovm00gumv90t FOREIGN KEY (doctor_id)
        REFERENCES public.doctor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrban5yeabnx30seqm69jw44e FOREIGN KEY (patient_id)
        REFERENCES public.patient (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fklskdj475jgmx30sem69jw44e FOREIGN KEY (organisation_id)
        REFERENCES public.organisation (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE public.visit_seq
    INCREMENT 1
    START 4
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

INSERT INTO public.organisation(id, name)
    VALUES (1, 'LuxMed'),
    	   (2, 'MediCover');

INSERT INTO public.doctor(id, major, name, surname, organisation_id)
	VALUES (1, 'Fizjoterapeuta', 'Piotr', 'Zieliński', 1),
	       (2, 'Dentysta', 'Robert', 'Lewandowski', 2),
	       (3, 'Urolog', 'Wojciech', 'Szczęsny', 1);

INSERT INTO public.patient(id, address, name, surname, organisation_id)
	VALUES (1, 'ul. Marsyliańska 9', 'Arkadiusz', 'Milik', 2),
	       (2, 'ul. Bułgarska 17', 'Jakub', 'Moder', 1),
	       (3, 'ul. Angielska', 'Jan', 'Bednarek', 1);

INSERT INTO public.visit(id, date, doctor_id, patient_id, organisation_id)
	VALUES (1, '2021-03-26 10:00', 1, 3, 1),
	       (2, '2021-03-30 12:00', 2, 1, 2),
	       (3, '2021-03-21 09:00', 3, 2, 1);