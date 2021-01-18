
-- 客户信息上传标记0：未上传 1：已上传
alter table mchk add  upload_status tinyint not Null Default 0;

-- 料品信息上传标记0：未上传 1：已上传
alter table spkfk add  upload_status tinyint not Null Default 0;
