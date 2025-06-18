--
-- PostgreSQL database dump
--

-- Dumped from database version 17rc1
-- Dumped by pg_dump version 17rc1

-- Started on 2025-06-09 23:03:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 38191)
-- Name: cinema; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA cinema;


--
-- TOC entry 249 (class 1255 OID 38377)
-- Name: log_review_changes(); Type: FUNCTION; Schema: cinema; Owner: -
--

CREATE FUNCTION cinema.log_review_changes() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        INSERT INTO review_audit_logs (review_id, action, old_value, new_value) 
        VALUES (OLD.review_id, 'UPDATE', to_jsonb(OLD), to_jsonb(NEW));
        RETURN NEW;

    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO review_audit_logs (review_id, action, old_value) 
        VALUES (OLD.review_id, 'DELETE', to_jsonb(OLD));
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$;


--
-- TOC entry 245 (class 1259 OID 38664)
-- Name: actor_sequence; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.actor_sequence
    START WITH 15
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 229 (class 1259 OID 38251)
-- Name: actors; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.actors (
    actor_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT actors_name_check CHECK (((length(TRIM(BOTH FROM name)) >= 3) AND (length(TRIM(BOTH FROM name)) <= 100)))
);


--
-- TOC entry 228 (class 1259 OID 38250)
-- Name: actors_actor_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.actors_actor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5016 (class 0 OID 0)
-- Dependencies: 228
-- Name: actors_actor_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.actors_actor_id_seq OWNED BY cinema.actors.actor_id;


--
-- TOC entry 246 (class 1259 OID 38666)
-- Name: actors_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.actors_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 235 (class 1259 OID 38303)
-- Name: cinemas; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.cinemas (
    cinema_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    phone character varying(11) NOT NULL,
    CONSTRAINT cinemas_phone_check CHECK ((length((phone)::text) = 11))
);


--
-- TOC entry 234 (class 1259 OID 38302)
-- Name: cinemas_cinema_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.cinemas_cinema_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5017 (class 0 OID 0)
-- Dependencies: 234
-- Name: cinemas_cinema_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.cinemas_cinema_id_seq OWNED BY cinema.cinemas.cinema_id;


--
-- TOC entry 248 (class 1259 OID 38668)
-- Name: director_sequence; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.director_sequence
    START WITH 20
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 226 (class 1259 OID 38228)
-- Name: directors; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.directors (
    director_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT directors_name_check CHECK (((length(TRIM(BOTH FROM name)) >= 3) AND (length(TRIM(BOTH FROM name)) <= 100)))
);


--
-- TOC entry 225 (class 1259 OID 38227)
-- Name: directors_director_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.directors_director_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 225
-- Name: directors_director_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.directors_director_id_seq OWNED BY cinema.directors.director_id;


--
-- TOC entry 247 (class 1259 OID 38667)
-- Name: directors_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.directors_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 221 (class 1259 OID 38193)
-- Name: films; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.films (
    film_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    release_date date NOT NULL,
    duration integer,
    CONSTRAINT films_duration_check CHECK ((duration > 0))
);


--
-- TOC entry 230 (class 1259 OID 38258)
-- Name: films_actors; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.films_actors (
    actor_id bigint NOT NULL,
    film_id bigint NOT NULL,
    actor bigint,
    film bigint
);


--
-- TOC entry 227 (class 1259 OID 38235)
-- Name: films_directors; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.films_directors (
    director_id bigint NOT NULL,
    film_id bigint NOT NULL
);


--
-- TOC entry 220 (class 1259 OID 38192)
-- Name: films_film_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.films_film_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 220
-- Name: films_film_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.films_film_id_seq OWNED BY cinema.films.film_id;


--
-- TOC entry 224 (class 1259 OID 38212)
-- Name: films_genres; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.films_genres (
    genre_id bigint NOT NULL,
    film_id bigint NOT NULL
);


--
-- TOC entry 244 (class 1259 OID 38663)
-- Name: genre_sequence; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.genre_sequence
    START WITH 8
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 223 (class 1259 OID 38203)
-- Name: genres; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.genres (
    genre_id bigint NOT NULL,
    genre character varying(255) NOT NULL,
    CONSTRAINT genres_genre_check CHECK (((genre)::text <> ''::text))
);


--
-- TOC entry 222 (class 1259 OID 38202)
-- Name: genres_genre_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.genres_genre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 222
-- Name: genres_genre_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.genres_genre_id_seq OWNED BY cinema.genres.genre_id;


--
-- TOC entry 243 (class 1259 OID 38662)
-- Name: genres_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.genres_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 236 (class 1259 OID 38314)
-- Name: halls; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.halls (
    cinema_id bigint NOT NULL,
    hall_number smallint NOT NULL,
    capacity integer NOT NULL,
    CONSTRAINT halls_capacity_check CHECK ((capacity > 0))
);


--
-- TOC entry 242 (class 1259 OID 38363)
-- Name: review_audit_logs; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.review_audit_logs (
    log_id integer NOT NULL,
    review_id integer NOT NULL,
    action character varying(50) NOT NULL,
    old_value jsonb,
    new_value jsonb,
    changed_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- TOC entry 241 (class 1259 OID 38362)
-- Name: review_audit_logs_log_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.review_audit_logs_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 241
-- Name: review_audit_logs_log_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.review_audit_logs_log_id_seq OWNED BY cinema.review_audit_logs.log_id;


--
-- TOC entry 233 (class 1259 OID 38283)
-- Name: reviews; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.reviews (
    review_id bigint NOT NULL,
    film_id bigint NOT NULL,
    user_id character varying(255) NOT NULL,
    rating smallint,
    comment character varying(255),
    CONSTRAINT reviews_rating_check CHECK (((rating >= 0) AND (rating <= 5)))
);


--
-- TOC entry 232 (class 1259 OID 38282)
-- Name: reviews_review_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.reviews_review_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 232
-- Name: reviews_review_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.reviews_review_id_seq OWNED BY cinema.reviews.review_id;


--
-- TOC entry 238 (class 1259 OID 38326)
-- Name: sessions; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.sessions (
    session_id bigint NOT NULL,
    cinema_id bigint NOT NULL,
    film_id bigint NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    hall_number smallint NOT NULL,
    ticket_price double precision NOT NULL,
    CONSTRAINT sessions_check CHECK ((end_time > start_time))
);


--
-- TOC entry 237 (class 1259 OID 38325)
-- Name: sessions_session_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.sessions_session_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5023 (class 0 OID 0)
-- Dependencies: 237
-- Name: sessions_session_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.sessions_session_id_seq OWNED BY cinema.sessions.session_id;


--
-- TOC entry 240 (class 1259 OID 38344)
-- Name: tickets; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.tickets (
    ticket_id bigint NOT NULL,
    session_id bigint NOT NULL,
    seat character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL
);


--
-- TOC entry 239 (class 1259 OID 38343)
-- Name: tickets_ticket_id_seq; Type: SEQUENCE; Schema: cinema; Owner: -
--

CREATE SEQUENCE cinema.tickets_ticket_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5024 (class 0 OID 0)
-- Dependencies: 239
-- Name: tickets_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: -
--

ALTER SEQUENCE cinema.tickets_ticket_id_seq OWNED BY cinema.tickets.ticket_id;


--
-- TOC entry 231 (class 1259 OID 38273)
-- Name: users; Type: TABLE; Schema: cinema; Owner: -
--

CREATE TABLE cinema.users (
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    CONSTRAINT users_email_check CHECK (((email)::text ~~ '%_@__%.__%'::text)),
    CONSTRAINT users_name_check CHECK (((length(TRIM(BOTH FROM name)) >= 3) AND (length(TRIM(BOTH FROM name)) <= 100)))
);


--
-- TOC entry 4770 (class 2604 OID 38414)
-- Name: actors actor_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.actors ALTER COLUMN actor_id SET DEFAULT nextval('cinema.actors_actor_id_seq'::regclass);


--
-- TOC entry 4772 (class 2604 OID 38427)
-- Name: cinemas cinema_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.cinemas ALTER COLUMN cinema_id SET DEFAULT nextval('cinema.cinemas_cinema_id_seq'::regclass);


--
-- TOC entry 4769 (class 2604 OID 38452)
-- Name: directors director_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.directors ALTER COLUMN director_id SET DEFAULT nextval('cinema.directors_director_id_seq'::regclass);


--
-- TOC entry 4767 (class 2604 OID 38380)
-- Name: films film_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films ALTER COLUMN film_id SET DEFAULT nextval('cinema.films_film_id_seq'::regclass);


--
-- TOC entry 4768 (class 2604 OID 38531)
-- Name: genres genre_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.genres ALTER COLUMN genre_id SET DEFAULT nextval('cinema.genres_genre_id_seq'::regclass);


--
-- TOC entry 4775 (class 2604 OID 38366)
-- Name: review_audit_logs log_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.review_audit_logs ALTER COLUMN log_id SET DEFAULT nextval('cinema.review_audit_logs_log_id_seq'::regclass);


--
-- TOC entry 4771 (class 2604 OID 38563)
-- Name: reviews review_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.reviews ALTER COLUMN review_id SET DEFAULT nextval('cinema.reviews_review_id_seq'::regclass);


--
-- TOC entry 4773 (class 2604 OID 38594)
-- Name: sessions session_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.sessions ALTER COLUMN session_id SET DEFAULT nextval('cinema.sessions_session_id_seq'::regclass);


--
-- TOC entry 4774 (class 2604 OID 38628)
-- Name: tickets ticket_id; Type: DEFAULT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.tickets ALTER COLUMN ticket_id SET DEFAULT nextval('cinema.tickets_ticket_id_seq'::regclass);


--
-- TOC entry 4991 (class 0 OID 38251)
-- Dependencies: 229
-- Data for Name: actors; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.actors VALUES (8, 'Тим Роббинс');
INSERT INTO cinema.actors VALUES (12, 'Райан Рейнольд');
INSERT INTO cinema.actors VALUES (302, 'Джеки Чан');
INSERT INTO cinema.actors VALUES (6, 'Мэтью МакКона');
INSERT INTO cinema.actors VALUES (353, 'Джонни Депп');


--
-- TOC entry 4997 (class 0 OID 38303)
-- Dependencies: 235
-- Data for Name: cinemas; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 4988 (class 0 OID 38228)
-- Dependencies: 226
-- Data for Name: directors; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.directors VALUES (4, 'Фрэнк Дарабон');
INSERT INTO cinema.directors VALUES (302, 'Гор Вербински');


--
-- TOC entry 4983 (class 0 OID 38193)
-- Dependencies: 221
-- Data for Name: films; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.films VALUES (59, 'новый', 'описание', '2025-01-09', 12);
INSERT INTO cinema.films VALUES (45, 'Дэдпул', 'Уэйд Уилсон — наёмник. Будучи побочным продуктом программы вооружённых сил под названием «Оружие X», Уилсон приобрёл невероятную силу, проворство и способность к исцелению. Но страшной ценой: его клеточная структура постоянно меняется, а здравомыслие сомнительно. Всё, чего хочет Уилсон, — держаться на плаву в социальной выгребной яме. Но течение в ней слишком быстрое.', '2024-09-06', 110);
INSERT INTO cinema.films VALUES (48, 'Пираты Карибского моря', 'Капитан Джек Воробей, пират с обаянием и хитростью, пытается вернуть свой корабль "Черная жемчужина", который был захвачен его бывшим первым помощником Гектором Барбоссой. В то же время он сталкивается с Уиллом Тёрнером и Элизабет Суон, которые вовлечены в конфликт между пиратами и британскими властями.', '2003-07-09', 143);


--
-- TOC entry 4992 (class 0 OID 38258)
-- Dependencies: 230
-- Data for Name: films_actors; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.films_actors VALUES (12, 45, NULL, NULL);
INSERT INTO cinema.films_actors VALUES (6, 45, NULL, NULL);
INSERT INTO cinema.films_actors VALUES (12, 59, NULL, NULL);
INSERT INTO cinema.films_actors VALUES (353, 48, NULL, NULL);


--
-- TOC entry 4989 (class 0 OID 38235)
-- Dependencies: 227
-- Data for Name: films_directors; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.films_directors VALUES (4, 45);
INSERT INTO cinema.films_directors VALUES (4, 59);
INSERT INTO cinema.films_directors VALUES (302, 48);


--
-- TOC entry 4986 (class 0 OID 38212)
-- Dependencies: 224
-- Data for Name: films_genres; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.films_genres VALUES (5, 45);
INSERT INTO cinema.films_genres VALUES (5, 59);
INSERT INTO cinema.films_genres VALUES (1052, 59);
INSERT INTO cinema.films_genres VALUES (504, 48);
INSERT INTO cinema.films_genres VALUES (505, 48);


--
-- TOC entry 4985 (class 0 OID 38203)
-- Dependencies: 223
-- Data for Name: genres; Type: TABLE DATA; Schema: cinema; Owner: -
--

INSERT INTO cinema.genres VALUES (5, 'комедия');
INSERT INTO cinema.genres VALUES (504, 'приключения');
INSERT INTO cinema.genres VALUES (505, 'экшн');
INSERT INTO cinema.genres VALUES (604, 'романтика');
INSERT INTO cinema.genres VALUES (1052, 'новый жанр');


--
-- TOC entry 4998 (class 0 OID 38314)
-- Dependencies: 236
-- Data for Name: halls; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 5004 (class 0 OID 38363)
-- Dependencies: 242
-- Data for Name: review_audit_logs; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 4995 (class 0 OID 38283)
-- Dependencies: 233
-- Data for Name: reviews; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 5000 (class 0 OID 38326)
-- Dependencies: 238
-- Data for Name: sessions; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 5002 (class 0 OID 38344)
-- Dependencies: 240
-- Data for Name: tickets; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 4993 (class 0 OID 38273)
-- Dependencies: 231
-- Data for Name: users; Type: TABLE DATA; Schema: cinema; Owner: -
--



--
-- TOC entry 5025 (class 0 OID 0)
-- Dependencies: 245
-- Name: actor_sequence; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.actor_sequence', 15, false);


--
-- TOC entry 5026 (class 0 OID 0)
-- Dependencies: 228
-- Name: actors_actor_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.actors_actor_id_seq', 1, true);


--
-- TOC entry 5027 (class 0 OID 0)
-- Dependencies: 246
-- Name: actors_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.actors_seq', 951, true);


--
-- TOC entry 5028 (class 0 OID 0)
-- Dependencies: 234
-- Name: cinemas_cinema_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.cinemas_cinema_id_seq', 1, false);


--
-- TOC entry 5029 (class 0 OID 0)
-- Dependencies: 248
-- Name: director_sequence; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.director_sequence', 20, false);


--
-- TOC entry 5030 (class 0 OID 0)
-- Dependencies: 225
-- Name: directors_director_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.directors_director_id_seq', 1, false);


--
-- TOC entry 5031 (class 0 OID 0)
-- Dependencies: 247
-- Name: directors_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.directors_seq', 801, true);


--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 220
-- Name: films_film_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.films_film_id_seq', 59, true);


--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 244
-- Name: genre_sequence; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.genre_sequence', 8, false);


--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 222
-- Name: genres_genre_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.genres_genre_id_seq', 2, true);


--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 243
-- Name: genres_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.genres_seq', 1101, true);


--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 241
-- Name: review_audit_logs_log_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.review_audit_logs_log_id_seq', 1, false);


--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 232
-- Name: reviews_review_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.reviews_review_id_seq', 1, false);


--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 237
-- Name: sessions_session_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.sessions_session_id_seq', 1, false);


--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 239
-- Name: tickets_ticket_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: -
--

SELECT pg_catalog.setval('cinema.tickets_ticket_id_seq', 1, false);


--
-- TOC entry 4800 (class 2606 OID 38416)
-- Name: actors actors_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.actors
    ADD CONSTRAINT actors_pkey PRIMARY KEY (actor_id);


--
-- TOC entry 4808 (class 2606 OID 38443)
-- Name: cinemas cinemas_phone_key; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.cinemas
    ADD CONSTRAINT cinemas_phone_key UNIQUE (phone);


--
-- TOC entry 4810 (class 2606 OID 38429)
-- Name: cinemas cinemas_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.cinemas
    ADD CONSTRAINT cinemas_pkey PRIMARY KEY (cinema_id);


--
-- TOC entry 4796 (class 2606 OID 38454)
-- Name: directors directors_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.directors
    ADD CONSTRAINT directors_pkey PRIMARY KEY (director_id);


--
-- TOC entry 4802 (class 2606 OID 38477)
-- Name: films_actors films_actors_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_actors
    ADD CONSTRAINT films_actors_pkey PRIMARY KEY (actor_id, film_id);


--
-- TOC entry 4798 (class 2606 OID 38499)
-- Name: films_directors films_directors_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_directors
    ADD CONSTRAINT films_directors_pkey PRIMARY KEY (director_id, film_id);


--
-- TOC entry 4794 (class 2606 OID 38521)
-- Name: films_genres films_genres_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_genres
    ADD CONSTRAINT films_genres_pkey PRIMARY KEY (genre_id, film_id);


--
-- TOC entry 4788 (class 2606 OID 38382)
-- Name: films films_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films
    ADD CONSTRAINT films_pkey PRIMARY KEY (film_id);


--
-- TOC entry 4790 (class 2606 OID 38545)
-- Name: genres genres_genre_key; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.genres
    ADD CONSTRAINT genres_genre_key UNIQUE (genre);


--
-- TOC entry 4792 (class 2606 OID 38533)
-- Name: genres genres_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (genre_id);


--
-- TOC entry 4820 (class 2606 OID 38371)
-- Name: review_audit_logs review_audit_logs_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.review_audit_logs
    ADD CONSTRAINT review_audit_logs_pkey PRIMARY KEY (log_id);


--
-- TOC entry 4806 (class 2606 OID 38565)
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (review_id);


--
-- TOC entry 4814 (class 2606 OID 38596)
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (session_id);


--
-- TOC entry 4816 (class 2606 OID 38630)
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (ticket_id);


--
-- TOC entry 4812 (class 2606 OID 38548)
-- Name: halls unique_hall_cinema; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.halls
    ADD CONSTRAINT unique_hall_cinema PRIMARY KEY (cinema_id, hall_number);


--
-- TOC entry 4818 (class 2606 OID 38641)
-- Name: tickets unique_seat_session; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT unique_seat_session UNIQUE (session_id, seat);


--
-- TOC entry 4804 (class 2606 OID 38281)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (email);


--
-- TOC entry 4836 (class 2620 OID 38378)
-- Name: reviews review_changes; Type: TRIGGER; Schema: cinema; Owner: -
--

CREATE TRIGGER review_changes AFTER DELETE OR UPDATE ON cinema.reviews FOR EACH ROW EXECUTE FUNCTION cinema.log_review_changes();


--
-- TOC entry 4825 (class 2606 OID 38467)
-- Name: films_actors films_actors_actor_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_actors
    ADD CONSTRAINT films_actors_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES cinema.actors(actor_id) ON DELETE CASCADE;


--
-- TOC entry 4826 (class 2606 OID 38478)
-- Name: films_actors films_actors_film_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_actors
    ADD CONSTRAINT films_actors_film_id_fkey FOREIGN KEY (film_id) REFERENCES cinema.films(film_id) ON DELETE CASCADE;


--
-- TOC entry 4823 (class 2606 OID 38489)
-- Name: films_directors films_directors_director_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_directors
    ADD CONSTRAINT films_directors_director_id_fkey FOREIGN KEY (director_id) REFERENCES cinema.directors(director_id) ON DELETE CASCADE;


--
-- TOC entry 4824 (class 2606 OID 38500)
-- Name: films_directors films_directors_film_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_directors
    ADD CONSTRAINT films_directors_film_id_fkey FOREIGN KEY (film_id) REFERENCES cinema.films(film_id) ON DELETE CASCADE;


--
-- TOC entry 4821 (class 2606 OID 38522)
-- Name: films_genres films_genres_film_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_genres
    ADD CONSTRAINT films_genres_film_id_fkey FOREIGN KEY (film_id) REFERENCES cinema.films(film_id) ON DELETE CASCADE;


--
-- TOC entry 4822 (class 2606 OID 38534)
-- Name: films_genres films_genres_genre_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.films_genres
    ADD CONSTRAINT films_genres_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES cinema.genres(genre_id) ON DELETE CASCADE;


--
-- TOC entry 4830 (class 2606 OID 38655)
-- Name: sessions fkqc6kayaexjuwfa8ukiute2riq; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.sessions
    ADD CONSTRAINT fkqc6kayaexjuwfa8ukiute2riq FOREIGN KEY (cinema_id) REFERENCES cinema.cinemas(cinema_id);


--
-- TOC entry 4829 (class 2606 OID 38549)
-- Name: halls halls_cinema_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.halls
    ADD CONSTRAINT halls_cinema_id_fkey FOREIGN KEY (cinema_id) REFERENCES cinema.cinemas(cinema_id) ON DELETE CASCADE;


--
-- TOC entry 4835 (class 2606 OID 38566)
-- Name: review_audit_logs review_audit_logs_review_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.review_audit_logs
    ADD CONSTRAINT review_audit_logs_review_id_fkey FOREIGN KEY (review_id) REFERENCES cinema.reviews(review_id) ON DELETE CASCADE;


--
-- TOC entry 4827 (class 2606 OID 38583)
-- Name: reviews reviews_film_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.reviews
    ADD CONSTRAINT reviews_film_id_fkey FOREIGN KEY (film_id) REFERENCES cinema.films(film_id);


--
-- TOC entry 4828 (class 2606 OID 38297)
-- Name: reviews reviews_user_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.reviews
    ADD CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES cinema.users(email);


--
-- TOC entry 4831 (class 2606 OID 38610)
-- Name: sessions sessions_cinema_id_hall_number_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.sessions
    ADD CONSTRAINT sessions_cinema_id_hall_number_fkey FOREIGN KEY (cinema_id, hall_number) REFERENCES cinema.halls(cinema_id, hall_number);


--
-- TOC entry 4832 (class 2606 OID 38619)
-- Name: sessions sessions_film_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.sessions
    ADD CONSTRAINT sessions_film_id_fkey FOREIGN KEY (film_id) REFERENCES cinema.films(film_id);


--
-- TOC entry 4833 (class 2606 OID 38642)
-- Name: tickets tickets_session_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_session_id_fkey FOREIGN KEY (session_id) REFERENCES cinema.sessions(session_id);


--
-- TOC entry 4834 (class 2606 OID 38357)
-- Name: tickets tickets_user_id_fkey; Type: FK CONSTRAINT; Schema: cinema; Owner: -
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_user_id_fkey FOREIGN KEY (user_id) REFERENCES cinema.users(email);


-- Completed on 2025-06-09 23:03:40

--
-- PostgreSQL database dump complete
--

