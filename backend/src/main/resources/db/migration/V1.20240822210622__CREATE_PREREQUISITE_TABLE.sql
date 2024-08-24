CREATE TABLE "subject_prerequisite" (
  "subject_id" BIGINT NOT NULL,
  "prerequisite_id" BIGINT NOT NULL,
  CHECK ("subject_id" <> "prerequisite_id")
);

CREATE UNIQUE INDEX "prerequisite_unique" ON "subject_prerequisite" ("subject_id", "prerequisite_id");
ALTER TABLE "subject_prerequisite" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");
ALTER TABLE "subject_prerequisite" ADD FOREIGN KEY ("prerequisite_id") REFERENCES "subject" ("id");