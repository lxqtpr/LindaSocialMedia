create table artist_entity (
     id bigserial not null,
     birthdate timestamp(6),
     deathdate timestamp(6),
     country_id bigint,
     first_name varchar(255),
     last_name varchar(255),
     portrait varchar(255),
     primary key (id)
);
create table country_entity (
    id bigserial not null,
    image varchar(255),
     name varchar(255),
     primary key (id)
);
create table picture_entity (
    artist_id bigint,
    id bigserial not null,
     image varchar(255),
      primary key (id)
);
create table post_files (
    post_entity_id bigint not null,
     files varchar(255)
);
create table comment_entity (
    author_id bigint,
    id bigserial not null,
     post_id bigint,
     text varchar(255),
      primary key (id)
);
create table post_likes (
    post_id bigint not null,
    user_id bigint not null,
    primary key (post_id, user_id)
);
create table post_entity (
    author_id bigint,
    id bigserial not null,
    text varchar(255),
    primary key (id)
);
create table role_entity (
    id bigserial not null,
    role varchar(255) check (role in ('ROlE_ADMIN','ROLE_USER')),
    primary key (id)
);
create table user_roles (
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id)
);
create table user_entity (
    is_verified boolean,
    id bigserial not null,
    avatar varchar(255),
    city varchar(255),
    email varchar(255),
    name varchar(255),
    page_cover varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table if exists artist_entity add constraint FK90mbchb9pw1741n5ywt0i6fk3 foreign key (country_id) references country_entity;
alter table if exists comment_entity add constraint FKexrg7rmory7ijwhf6ha5ke2rn foreign key (author_id) references user_entity;
alter table if exists comment_entity add constraint FK5q5av5arkm3of9b5n493p992p foreign key (post_id) references post_entity;
alter table if exists picture_entity add constraint FKlo2gpu1w754tpo4p3ce2kn6mm foreign key (artist_id) references artist_entity;
alter table if exists post_files add constraint FKasl51uyd5vql9p8tj5n7e0bll foreign key (post_entity_id) references post_entity;
alter table if exists post_likes add constraint FKil13p0yg5qqulf999aeh24fmh foreign key (post_id) references user_entity;
alter table if exists post_likes add constraint FK5ophadhiahhoi5cjvbmnsscfc foreign key (user_id) references comment_entity;
alter table if exists post_entity add constraint FKmqyrpi535ad5my31g1sn0bfrg foreign key (author_id) references user_entity;
alter table if exists user_roles add constraint FKh83ux1f9i6ch6gie5xtj5mqnt foreign key (role_id) references role_entity;
alter table if exists user_roles add constraint FK6y02653x6ebhsu2plf21ard62 foreign key (user_id) references user_entity;