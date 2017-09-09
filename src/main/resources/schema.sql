CREATE TABLE public.department
(
    id SERIAL PRIMARY KEY NOT NULL,
    dep_code VARCHAR(20) NOT NULL,
    dep_job VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX department_id_uindex ON public.department (id);