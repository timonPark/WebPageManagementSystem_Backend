create table users
(
    user_no     serial,
    name        varchar(40)              not null,
    email       varchar(100),
    password    varchar(100),
    is_social   varchar(1) default 'N'::character varying,
    social_id   varchar(50),
    social_type varchar(20),
    picture     varchar(200),
    is_use      varchar(1) default 'U'::character varying,
    created_at  timestamp  default now() not null,
    updated_at  timestamp
);

comment on table users is '계정';

comment on column users.user_no is '계정식별값';

comment on column users.name is '이름';

comment on column users.email is '이메일';

comment on column users.password is '비밀번호';

comment on column users.is_social is '소설로그인여부';

comment on column users.social_id is '소셜 아이디';

comment on column users.social_type is '소셜로그인종류';

comment on column users.picture is '프로필사진';

comment on column users.is_use is '사용여부';

comment on column users.created_at is '생성시간';

comment on column users.updated_at is '수정시간';

alter table users
    owner to wpms;

create table menu
(
    menu_no         serial,
    name            varchar(40)              not null,
    url             varchar(255),
    parent_menu_no  serial,
    order_no          int,
    is_manager      varchar(1) default 'N'::character varying,
    is_use          varchar(1) default 'U'::character varying,
    created_no      serial,
    created_at      timestamp  default now() not null,
    updated_no      serial,
    updated_at      timestamp
);

comment on table menu is '메뉴';

comment on column menu.menu_no is '메뉴식별값';

comment on column menu.name is '메뉴명';

comment on column menu.url is 'url';

comment on column menu.parent_menu_no is '상위 메뉴 식별값';

comment on column menu.order_no is '순서';

comment on column menu.is_manager is '관리자여부';

comment on column menu.is_use is '사용여부';

comment on column menu.created_no is '생성자';

comment on column menu.created_at is '생성시간';

comment on column menu.updated_no is '수정자';

comment on column menu.updated_at is '수정시간';

alter table menu
    owner to wpms;

