CREATE TABLE "user_planning" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "user_semester_id" BIGINT NOT NULL,
  "subject_id" BIGINT NOT NULL,
  "completed" BOOLEAN DEFAULT false,
  "created_at" TIMESTAMPTZ,
  "updated_at" TIMESTAMPTZ
);

CREATE UNIQUE INDEX "subject_unique_for_user" ON "user_planning" ("user_id", "subject_id");
ALTER TABLE "user_planning" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
ALTER TABLE "user_planning" ADD FOREIGN KEY ("user_semester_id") REFERENCES "user_semester" ("id");
ALTER TABLE "user_planning" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");