create table courses (
                         id          INT NOT NULL,
                         name        VARCHAR(255),
                         category    VARCHAR(255),
                         rating      int NOT NULL,
                         description VARCHAR(255),
                         PRIMARY KEY (id)
);

-- create table ct_users(
--                          ID	BIGINT(19)	NOT NULL,
--                          EMAIL	VARCHAR(255)	NOT NULL,
--                          FIRST_NAME	VARCHAR(255) NOT NULL,
--                          LAST_NAME	VARCHAR(255) NOT NULL,
--                          PASSWORD	VARCHAR(255) NOT NULL,
--                          USERNAME	VARCHAR(255) NOT NULL,
--                          VERIFIED	BOOLEAN(1) NOT NULL,
--                          LOCKED BOOLEAN(1) NOT NULL,
--                          ACC_CRED_EXPIRED BOOLEAN(1) NOT NULL,
--                          PRIMARY KEY (ID)
-- );