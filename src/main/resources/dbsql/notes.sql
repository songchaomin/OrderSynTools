
-- 客户信息上传标记0：未上传 1：已上传
alter table mchk add  upload_status tinyint  Default 0;

-- 料品信息上传标记0：未上传 1：已上传
alter table spkfk add  upload_status tinyint  Default 0;


-- 订单表(中间表)
create table sal_order(
out_order_code varchar (64) primary key,-- 我⽅订单编号
branch_id varchar (16) ,-- 站点id
danw_bh varchar (64) ,-- 客户编号
create_time varchar (256) ,-- 下单时间
note varchar (256) ,-- 订单备注
is_online_pay tinyint, -- 是否在线⽀付 1-是， 0-否
is_zx varchar(4) -- ERP订单生成标记
)
-- 订单上传标记0：未上传 1：已上传
alter table sal_order add  upload_status tinyint  Default 0;

-- 订单行(中间表)
create table sal_order_line(
out_order_code varchar (64),-- 我⽅订单编号
prod_no varchar (64) ,-- 站点id
quantity int ,-- 客户编号
price decimal (12,4), -- 下单时间
is_zx varchar(4) -- ERP销售出库标记
)


-- 日志记录表
create table operator_log(
    id int identity (1,1) primary key not null,
    type varchar (64) ,-- 类型 1：商品上传 2：客户上传 3：库存上传 4：订单下载 5：订单状态上传
    type_name varchar (32), -- 类型名称
    code varchar (64),-- 事务代码
    msg text  ,-- 消息
    create_time date ,-- 创建时间
)