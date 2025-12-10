
CREATE DATABASE product_management_db;

CREATE USER product_manager_user WITH PASSWORD '123456';

GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;

\c product_management_db;

GRANT CREATE ON SCHEMA public TO product_manager_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO product_manager_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO product_manager_user;

GRANT USAGE ON SCHEMA public TO product_manager_user;
