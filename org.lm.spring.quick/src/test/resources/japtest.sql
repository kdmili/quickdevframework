insert into users (username,password) values ('test','123456');
insert into users (username,password) values ('admin','123456');
insert into roles (role_name) values ('admin');
insert into roles (role_name) values ('test');
insert into user_roles (username,role_name) values ('admin','admin');
insert into user_roles (username,role_name) values ('test','test');
insert into roles_permissions (permission,role_name) values ('user:*','admin');
insert into roles_permissions (permission,role_name) values ('user:all:view:*','test');