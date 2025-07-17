CREATE TABLE banner_entity
(
    id CHAR(36) NOT NULL,
    CONSTRAINT banner_entity_pkey PRIMARY KEY (id)
);

CREATE TABLE category_entity
(
    id   CHAR(36) NOT NULL,
    name VARCHAR(255),
    CONSTRAINT category_entity_pkey PRIMARY KEY (id)
);

CREATE TABLE image_entity
(
    id         CHAR(36)     NOT NULL,
    path_image VARCHAR(255) NOT NULL,
    type       VARCHAR(255) NOT NULL,
    banner_id  CHAR(36),
    product_id CHAR(36),
    user_id    CHAR(36),
    CONSTRAINT image_entity_pkey PRIMARY KEY (id)
);

CREATE TABLE product_entity
(
    id          CHAR(36)       NOT NULL,
    description VARCHAR(255),
    price       numeric(38, 2) NOT NULL,
    sku         VARCHAR(255),
    title       VARCHAR(255)   NOT NULL,
    category_id CHAR(36),
    CONSTRAINT product_entity_pkey PRIMARY KEY (id)
);

CREATE TABLE user_entity
(
    id                 CHAR(36) NOT NULL,
    email              VARCHAR(255),
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    login              VARCHAR(255),
    password           VARCHAR(255),
    phone              VARCHAR(255),
    refresh_token_hash VARCHAR(512),
    CONSTRAINT user_entity_pkey PRIMARY KEY (id)
);

ALTER TABLE image_entity
    ADD CONSTRAINT ukf9e23jl3xobqv868x4ey8macm UNIQUE (user_id);

ALTER TABLE product_entity
    ADD CONSTRAINT fk8kxxmqdokh3lthvw0t148w0bc FOREIGN KEY (category_id) REFERENCES category_entity (id) ON DELETE NO ACTION;

ALTER TABLE image_entity
    ADD CONSTRAINT fkkvc6o1s90ghwk1xvtqwk21228 FOREIGN KEY (banner_id) REFERENCES banner_entity (id) ON DELETE NO ACTION;

ALTER TABLE image_entity
    ADD CONSTRAINT fknnae9idlott335fbsnkejvtap FOREIGN KEY (user_id) REFERENCES user_entity (id) ON DELETE NO ACTION;

ALTER TABLE image_entity
    ADD CONSTRAINT fkrna9so4bjernm1yis2t27g99x FOREIGN KEY (product_id) REFERENCES product_entity (id) ON DELETE NO ACTION;