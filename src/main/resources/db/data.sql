insert into user_tb(username,password,fullname, email, created_at) values ('ssar','1234','홍길동', 'ssar@nate.com', now());
insert into user_tb(username,password,fullname, email, created_at) values ('cos','1234','임꺽정','cos@nate.com', now());
insert into user_tb(username,password,fullname, email, created_at) values ('love','1234','이순신','love@nate.com', now());

insert into account_tb(user_id,number,password,balance,status,created_at) values (1,1111,'1234',600,true,now());
insert into account_tb(user_id,number,password,balance,status,created_at) values (2,2222,'1234',1000,true,now());
insert into account_tb(user_id,number,password,balance,status,created_at) values (3,3333,'1234',1300,true,now());
insert into account_tb(user_id,number,password,balance,status,created_at) values (1,4444,'1234',1000,true,now());
insert into account_tb(user_id,number,password,balance,status,created_at) values (2,5555,'1234',1100,true,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (1,2,100,900,1100,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (1,3,100,800,1100,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (1,4,100,700,1100,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (1,5,100,600,1100,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (2,3,100,1000,1200,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (4,3,100,1000,1300,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (1,3,100,500,1400,now());

insert into history_tb(sender_id,receiver_id,amount, sender_balance,receiver_balance,created_at)
values (3,1,100,1300,600,now());