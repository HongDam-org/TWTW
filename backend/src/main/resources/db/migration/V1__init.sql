create table `group`
(
    created_at  datetime(6),
    deleted_at  datetime(6),
    updated_at  datetime(6),
    id          BINARY(16) not null,
    leader_id   binary(16),
    group_image varchar(255),
    name        varchar(255),
    primary key (id)
);

create table `member`
(
    created_at      datetime(6),
    deleted_at      datetime(6),
    updated_at      datetime(6),
    device_token_id BINARY(16),
    id              BINARY(16) not null,
    auth_type       enum ('APPLE','KAKAO'),
    nickname        varchar(255) not null,
    profile_image   varchar(255),
    role            enum ('ROLE_ADMIN','ROLE_USER'),
    client_id       tinytext     not null,
    primary key (id)
);
create table device_token
(
    created_at   datetime(6),
    deleted_at   datetime(6),
    updated_at   datetime(6),
    id           BINARY(16) not null,
    device_token varchar(255),
    primary key (id)
);
create table friend
(
    created_at       datetime(6),
    deleted_at       datetime(6),
    updated_at       datetime(6),
    `from_member_id` BINARY(16),
    id               BINARY(16) not null,
    `to_member_id`   BINARY(16),
    friend_status    enum ('ACCEPTED','BLOCKED','DENIED','EXPIRED','REQUESTED'),
    primary key (id)
);
create table group_member
(
    is_share          bit,
    is_shared_once    bit,
    latitude          float(53),
    longitude         float(53),
    created_at        datetime(6),
    deleted_at        datetime(6),
    updated_at        datetime(6),
    group_id          BINARY(16),
    id                BINARY(16) not null,
    member_id         BINARY(16),
    group_invite_code enum ('ACCEPTED','BLOCKED','DENIED','EXPIRED','REQUESTED'),
    primary key (id)
);
create table place
(
    latitude          float(53),
    longitude         float(53),
    created_at        datetime(6),
    deleted_at        datetime(6),
    updated_at        datetime(6),
    id                BINARY(16) not null,
    place_name        varchar(255) not null,
    place_url         varchar(255),
    road_address_name varchar(255),
    primary key (id)
);
create table plan
(
    created_at datetime(6),
    deleted_at datetime(6),
    plan_day   datetime(6),
    updated_at datetime(6),
    group_id   BINARY(16),
    id         BINARY(16) not null,
    place_id   BINARY(16),
    name       varchar(255) not null,
    primary key (id)
);
create table plan_member
(
    is_plan_maker    bit,
    created_at       datetime(6),
    deleted_at       datetime(6),
    updated_at       datetime(6),
    id               BINARY(16) not null,
    `member_id`      BINARY(16),
    plan_id          BINARY(16),
    plan_invite_code enum ('ACCEPTED','DENIED','EXPIRED','REQUESTED'),
    primary key (id)
);
create table refresh_token
(
    id          BINARY(16) not null,
    token_key   varchar(255),
    token_value varchar(255),
    primary key (id)
);
alter table `member`
    add constraint unique_device_token_id unique (device_token_id);
alter table `member`
    add constraint unique_member_nickname unique (nickname);
alter table plan
    add constraint unique_plan_place_id unique (place_id);
alter table `member`
    add constraint fk_member_device_token_id foreign key (device_token_id) references device_token (id);
alter table friend
    add constraint fk_friend_from_member_id foreign key (`from_member_id`) references `member` (id);
alter table friend
    add constraint fk_friend_to_member_id foreign key (`to_member_id`) references `member` (id);
alter table group_member
    add constraint fk_group_member_group_id foreign key (group_id) references `group` (id);
alter table group_member
    add constraint fk_group_member_member_id foreign key (member_id) references `member` (id);
alter table plan
    add constraint fk_plan_group_id foreign key (group_id) references `group` (id);
alter table plan
    add constraint fk_plan_place_id foreign key (place_id) references place (id);
alter table plan_member
    add constraint fk_plan_member_member_id foreign key (`member_id`) references `member` (id);
alter table plan_member
    add constraint fk_plan_member_plan_id foreign key (plan_id) references plan (id);
