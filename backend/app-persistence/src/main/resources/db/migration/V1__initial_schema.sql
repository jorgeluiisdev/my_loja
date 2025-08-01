CREATE TABLE banner_entity
(
    id UUID NOT NULL,
    CONSTRAINT pk_bannerentity PRIMARY KEY (id)
);

CREATE TABLE category_entity
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_categoryentity PRIMARY KEY (id)
);

CREATE TABLE image_entity
(
    id            UUID         NOT NULL,
    type          VARCHAR(255) NOT NULL,
    path_image    VARCHAR(255) NOT NULL,
    product_id    UUID,
    banner_id     UUID,
    user_image_id UUID,
    CONSTRAINT pk_imageentity PRIMARY KEY (id)
);

CREATE TABLE product_entity
(
    id                 UUID         NOT NULL,
    title              VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    price              DECIMAL      NOT NULL,
    sku                VARCHAR(255),
    category_id        UUID,
    refresh_token_hash VARCHAR(512),
    CONSTRAINT pk_productentity PRIMARY KEY (id)
);

CREATE TABLE user_entity
(
    id                 UUID         NOT NULL,
    login              VARCHAR(255) NOT NULL,
    password           VARCHAR(255),
    role               SMALLINT     NOT NULL,
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    email              VARCHAR(255),
    phone              VARCHAR(255),
    refresh_token_hash VARCHAR(512),
    user_image_id      UUID,
    CONSTRAINT pk_userentity PRIMARY KEY (id)
);

ALTER TABLE user_entity
    ADD CONSTRAINT uc_userentity_login UNIQUE (login);

ALTER TABLE image_entity
    ADD CONSTRAINT FK_IMAGEENTITY_ON_BANNER FOREIGN KEY (banner_id) REFERENCES banner_entity (id);

ALTER TABLE image_entity
    ADD CONSTRAINT FK_IMAGEENTITY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product_entity (id);

ALTER TABLE product_entity
    ADD CONSTRAINT FK_PRODUCTENTITY_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category_entity (id);