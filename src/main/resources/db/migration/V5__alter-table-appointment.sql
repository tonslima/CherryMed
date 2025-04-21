ALTER TABLE appointment
ADD COLUMN status               VARCHAR(20)     DEFAULT 'SCHEDULED' NOT NULL,
ADD COLUMN cancellation_reason  VARCHAR(50)                         NULL;