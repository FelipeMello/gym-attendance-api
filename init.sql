-- init.sql

-- Grant privileges to the member
GRANT ALL PRIVILEGES ON DATABASE
mygym TO gymapi;

-- Drop constraints if they exist
ALTER TABLE IF EXISTS "attendance" DROP CONSTRAINT IF EXISTS fk46cuxphi3uh5quom51s6i2q8x;

-- Drop the "user" table if it exists
DROP TABLE IF EXISTS "member" CASCADE;

-- Create the "member" table
CREATE TABLE IF NOT EXISTS "member"
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
),
    email VARCHAR
(
    255
),
    password VARCHAR
(
    255
)
    );

-- Create the "attendance" table
CREATE TABLE IF NOT EXISTS "attendance"
(
    id
    SERIAL
    PRIMARY
    KEY,
    member_id
    INT,
    date
    DATE,
    FOREIGN
    KEY
(
    member_id
) REFERENCES "member"
(
    id
)
    );

