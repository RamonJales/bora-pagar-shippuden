DO $$
BEGIN
    IF EXISTS (
        SELECT *
        FROM information_schema.columns
        WHERE table_name = 'course'
        AND column_name = 'course_level'
    )
    THEN
        ALTER TABLE course
        DROP COLUMN course_level;
    END IF;
END $$;