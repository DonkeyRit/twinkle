BEGIN;

ALTER TABLE "car"
ALTER COLUMN "cost" TYPE double precision USING "cost"::double precision;

COMMIT;
