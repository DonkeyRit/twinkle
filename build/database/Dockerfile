FROM postgres

ENV POSTGRES_PASSWORD Sr412Tqew!
ENV POSTGRES_USER twinkle_user
ENV POSTGRES_DB carrental

EXPOSE 5432

COPY scripts/postgresql/init_db.sql /docker-entrypoint-initdb.d/
COPY scripts/postgresql/init_test_data.sql /docker-entrypoint-initdb.d/