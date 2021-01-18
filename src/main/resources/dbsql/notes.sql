
-- 客户信息上传标记0：未上传 1：已上传
alter table mchk add  upload_status tinyint not Null Default 0;

-- 料品信息上传标记0：未上传 1：已上传
alter table spkfk add  upload_status tinyint not Null Default 0;


-- 订单表(中间表)
create table sal_order(
out_order_code varchar (64) primary key,-- 我⽅订单编号
branch_id varchar (16) ,-- 站点id
danwbh varchar (64) ,-- 客户编号
create_time varchar (256) ,-- 下单时间
note varchar (256) ,-- 订单备注
is_online_pay tinyint -- 是否在线⽀付 1-是， 0-否
)


-- 订单行(中间表)
create table sal_order_line(
out_order_code varchar (64),-- 我⽅订单编号
prod_no varchar (64) ,-- 站点id
quantity int ,-- 客户编号
price decimal (12,4) -- 下单时间
)
