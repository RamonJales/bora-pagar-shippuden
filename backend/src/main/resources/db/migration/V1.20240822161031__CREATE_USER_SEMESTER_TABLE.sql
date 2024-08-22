CREATE TABLE "user_semester" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "semester_year" integer NOT NULL,
  "period" integer NOT NULL,
  "created_at" TIMESTAMPTZ,
  "updated_at" TIMESTAMPTZ,
  "deleted_at" TIMESTAMPTZ
);

CREATE UNIQUE INDEX "user_id_year_period_unique" ON "user_semester" ("user_id", "semester_year", "period");
ALTER TABLE "user_semester" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");