ALTER TABLE users
DROP COLUMN deleted; 

ALTER TABLE users
ADD deleted_at TIMESTAMPTZ;

ALTER TABLE subject
DROP COLUMN deleted; 

ALTER TABLE subject
ADD deleted_at TIMESTAMPTZ;

ALTER TABLE course
DROP COLUMN deleted; 

ALTER TABLE course
ADD deleted_at TIMESTAMPTZ;

ALTER TABLE classroom
DROP COLUMN deleted;

ALTER TABLE classroom
ADD deleted_at TIMESTAMPTZ;
