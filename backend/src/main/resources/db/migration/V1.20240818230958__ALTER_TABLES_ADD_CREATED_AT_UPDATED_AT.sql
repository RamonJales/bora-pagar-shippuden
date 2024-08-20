ALTER TABLE users
  ADD created_at TIMESTAMPTZ,
  ADD updated_at TIMESTAMPTZ;

ALTER TABLE course
  ADD created_at TIMESTAMPTZ,
  ADD updated_at TIMESTAMPTZ;

ALTER TABLE subject
  ADD created_at TIMESTAMPTZ,
  ADD updated_at TIMESTAMPTZ;

ALTER TABLE classroom
  ADD created_at TIMESTAMPTZ,
  ADD updated_at TIMESTAMPTZ;
