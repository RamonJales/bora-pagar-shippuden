ALTER TABLE users
DROP COLUMN deleted; 

ALTER TABLE users
ADD deleted_at TIMESTAMPTZ;

