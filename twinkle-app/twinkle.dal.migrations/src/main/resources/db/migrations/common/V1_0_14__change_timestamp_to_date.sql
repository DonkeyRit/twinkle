BEGIN;

ALTER TABLE "car" ALTER COLUMN "model_year" TYPE date USING "model_year"::date;
ALTER TABLE "rent" ALTER COLUMN "start_date" TYPE date USING "start_date"::date;
ALTER TABLE "rent" ALTER COLUMN "plan_date" TYPE date USING "plan_date"::date;
ALTER TABLE "rent" ALTER COLUMN "end_date" TYPE date USING "end_date"::date;

COMMIT;
