FROM postgres

ENV POSTGRES_PASSWORD Sr412Tqew!
ENV POSTGRES_USER twinkle_test_user
ENV POSTGRES_DB integration_tests_carrental

EXPOSE 5433

COPY ../scripts/postgresql/init_db.sql /docker-entrypoint-initdb.d/
COPY init_test_data.sql /docker-entrypoint-initdb.d/