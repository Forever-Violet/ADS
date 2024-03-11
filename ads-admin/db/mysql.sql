-- 系统用户
CREATE TABLE sys_user (
  id bigint NOT NULL COMMENT 'id',
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(100) COMMENT '密码',
  real_name varchar(50) COMMENT '姓名',
  head_url varchar(200) COMMENT '头像',
  gender tinyint unsigned COMMENT '性别   0：男   1：女    2：保密',
  email varchar(100) COMMENT '邮箱',
  mobile varchar(100) COMMENT '手机号',
  school_id bigint COMMENT '学校ID',
  super_admin tinyint unsigned COMMENT '超级管理员   0：否   1：是',
  status tinyint COMMENT '状态  0：停用   1：正常',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  primary key (id),
  unique key uk_username (username),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';


-- 学校表
CREATE TABLE sys_school (
    school_id BIGINT PRIMARY KEY COMMENT '学校ID',
    school_name VARCHAR(255) COMMENT '学校名称',
    alias VARCHAR(255) COMMENT '别名',
    full_name VARCHAR(255) COMMENT '全称',
    status tinyint COMMENT '状态 0正常 1禁用',
    education_bureau VARCHAR(255) COMMENT '所属教育局',
    create_date datetime COMMENT '创建时间',
    unit_type tinyint COMMENT '单位类型 0省级 1市级 2区县级',
    key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校管理';


-- 学校学期表
CREATE TABLE sys_school_semester (
      id bigint PRIMARY KEY COMMENT 'id',
      school_id BIGINT NOT NULL COMMENT '学校ID',
      semester_name VARCHAR(255) NOT NULL COMMENT '学期名称',
      start_date DATE NOT NULL COMMENT '开始日期',
      end_date DATE NOT NULL COMMENT '结束日期',
      remark varchar(100) COMMENT '备注',
      create_date datetime COMMENT '创建时间',
      key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学期管理';

-- 学校年级表
CREATE TABLE sys_school_grade (
    id bigint PRIMARY KEY COMMENT 'id',
    school_id BIGINT NOT NULL COMMENT '学校ID',
    grade_name VARCHAR(255) NOT NULL COMMENT '年级名称',
    remark varchar(100) COMMENT '备注',
    create_date datetime COMMENT '创建时间',
    key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年级管理';

-- 学校班级表
CREATE TABLE sys_school_class (
    id bigint PRIMARY KEY COMMENT 'id',
    school_id BIGINT NOT NULL COMMENT '学校ID',
    class_id BIGINT NOT NULL COMMENT '年级ID',
    class_name VARCHAR(255) NOT NULL COMMENT '班级名称',
    remark varchar(100) COMMENT '备注',
    create_date datetime COMMENT '创建时间',
    key idx_grade_id (grade_id),
    key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级管理';

-- 班级-用户表
CREATE TABLE sys_user_class (
    id bigint PRIMARY KEY COMMENT 'id',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    grade_id BIGINT NOT NULL COMMENT '班级ID',
    create_date datetime COMMENT '创建时间',
    key idx_grade_id (grade_id),
    key idx_user_id (user_id),
    key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户班级关系';


-- 五育-权重表 每个学校只有一个权重设置
CREATE TABLE wuyu_weight (
   id BIGINT PRIMARY KEY COMMENT '主键',
   school_id BIGINT unique NOT NULL COMMENT '学校ID',
   moral FLOAT(4, 2) NOT NULL COMMENT '德育',
   intellectual FLOAT(4, 2) NOT NULL COMMENT '智育',
   physical FLOAT(4, 2) NOT NULL COMMENT '体育',
   artistic FLOAT(4, 2) NOT NULL COMMENT '美育',
   labor FLOAT(4, 2) NOT NULL COMMENT '劳育',
   character_ethics FLOAT(4, 2) NOT NULL COMMENT '品德评定',
   rewards_punishments FLOAT(4, 2) NOT NULL COMMENT '奖惩记录',
   moral_education_courses FLOAT(4, 2) NOT NULL COMMENT '德育课程',
   practical_activities FLOAT(4, 2) NOT NULL COMMENT '实践活动',
   online_culture FLOAT(4, 2) NOT NULL COMMENT '网络文化',
   interpersonal_relationships FLOAT(4, 2) NOT NULL COMMENT '人际关系',
   prep_management FLOAT(4, 2) NOT NULL COMMENT '预习管理',
   plan_management FLOAT(4, 2) NOT NULL COMMENT '计划管理',
   classroom_behavior FLOAT(4, 2) NOT NULL COMMENT '课堂行为',
   classroom_attendance FLOAT(4, 2) NOT NULL COMMENT '课堂考勤',
   homework_management FLOAT(4, 2) NOT NULL COMMENT '作业管理',
   review_management FLOAT(4, 2) NOT NULL COMMENT '复习管理',
   personal_abilities FLOAT(4, 2) NOT NULL COMMENT '个人能力',
   academic_performance FLOAT(4, 2) NOT NULL COMMENT '学业成绩',
   experimental_competitions FLOAT(4, 2) NOT NULL COMMENT '实验竞赛',
   examination_metrics FLOAT(4, 2) NOT NULL COMMENT '体检指标',
   physical_fitness_scores FLOAT(4, 2) NOT NULL COMMENT '体能成绩',
   sporting_specialties FLOAT(4, 2) NOT NULL COMMENT '体育特长',
   healthy_living FLOAT(4, 2) NOT NULL COMMENT '健康生活',
   mental_qualities FLOAT(4, 2) NOT NULL COMMENT '心理素质',
   physical_education_courses FLOAT(4, 2) NOT NULL COMMENT '体育课程',
   arts_courses FLOAT(4, 2) NOT NULL COMMENT '美育课程',
   arts_achievements FLOAT(4, 2) NOT NULL COMMENT '美育成果',
   arts_activities FLOAT(4, 2) NOT NULL COMMENT '美育活动',
   labor_practices FLOAT(4, 2) NOT NULL COMMENT '劳动实践',
   labor_courses FLOAT(4, 2) NOT NULL COMMENT '劳动课程',
   create_date datetime COMMENT '创建时间',
   key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='五育设置（五育权重表）';


-- 五育分析 （因为该记录是有时效性的，即学生的年级班级会变，但记录保留，所以字段上加入学期、年级、班级，）
CREATE TABLE wuyu_score (
    id BIGINT PRIMARY KEY COMMENT '主键',
    school_id BIGINT NOT NULL COMMENT '学校ID',
    semester_id BIGINT NOT NULL COMMENT '学期ID',
    grade_id BIGINT NOT NULL COMMENT '年级ID',
    class_id BIGINT NOT NULL COMMENT '班级ID',
    report_id BIGINT NOT NULL COMMENT '报告ID',
    student_no varchar(50) NOT NULL COMMENT '学生学号',
    student_name varchar(100) NOT NULL COMMENT '学生姓名',
    character_ethics INT NOT NULL COMMENT '品德评定',
    rewards_punishments INT NOT NULL COMMENT '奖惩记录',
    moral_education_courses INT NOT NULL COMMENT '德育课程',
    practical_activities INT NOT NULL COMMENT '实践活动',
    online_culture INT NOT NULL COMMENT '网络文化',
    interpersonal_relationships INT NOT NULL COMMENT '人际关系',
    prep_management INT NOT NULL COMMENT '预习管理',
    plan_management INT NOT NULL COMMENT '计划管理',
    classroom_behavior INT NOT NULL COMMENT '课堂行为',
    classroom_attendance INT NOT NULL COMMENT '课堂考勤',
    homework_management INT NOT NULL COMMENT '作业管理',
    review_management INT NOT NULL COMMENT '复习管理',
    personal_abilities INT NOT NULL COMMENT '个人能力',
    academic_performance INT NOT NULL COMMENT '学业成绩',
    experimental_competitions INT NOT NULL COMMENT '实验竞赛',
    examination_metrics INT NOT NULL COMMENT '体检指标',
    physical_fitness_scores INT NOT NULL COMMENT '体能成绩',
    sporting_specialties INT NOT NULL COMMENT '体育特长',
    healthy_living INT NOT NULL COMMENT '健康生活',
    mental_qualities INT NOT NULL COMMENT '心理素质',
    physical_education_courses INT NOT NULL COMMENT '体育课程',
    arts_courses INT NOT NULL COMMENT '美育课程',
    arts_achievements INT NOT NULL COMMENT '美育成果',
    arts_activities INT NOT NULL COMMENT '美育活动',
    labor_practices INT NOT NULL COMMENT '劳动实践',
    labor_courses INT NOT NULL COMMENT '劳动课程',
    comprehensive_score INT NOT NULL COMMENT '五育综合成绩',
    comprehensive_level tinyint NOT NULL COMMENT '综合等级 0优, 1中, 2差',
    academic_level tinyint COMMENT '学业等级 0优, 1中, 2差',
    create_date datetime COMMENT '创建时间',
    INDEX idx_student_name (student_name),
    INDEX idx_student_no (student_no),
    INDEX idx_semester_id (semester_id),
    INDEX idx_grade_id(grade_id),
    INDEX idx_class_id (class_id),
    key idx_create_date (create_date),
    key idx_school_id (school_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='五育分析（五育成绩表）';

-- 五育分析 结果（报告）表
CREATE TABLE wuyu_analysis_result (
    id BIGINT PRIMARY KEY COMMENT '主键',
    semester_id BIGINT COMMENT '学期ID',
    class_id BIGINT COMMENT '班级ID',
    score_id BIGINT COMMENT '五育成绩ID',
    student_no varchar(50) COMMENT '学生学号',
    student_name varchar(100) COMMENT '学生姓名',
    response varchar(3000) COMMENT '诊断结果',

    create_date datetime COMMENT '创建时间',
    INDEX idx_student_name (student_name),
    INDEX idx_student_no (student_no),
    key idx_create_date (create_date),
    key idx_class_id (class_id),
    constraint class_id_and_semester_id unique (class_id, semester_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='五育分析 结果（报告）表';

-- 奖项设置
CREATE TABLE award_settings (
    id BIGINT PRIMARY KEY COMMENT '主键',
    school_id BIGINT NOT NULL COMMENT '学校ID',
    student_name varchar(50) NOT NULL COMMENT '学生姓名',
    topic tinyint NOT NULL COMMENT '所属五育 0德育, 1智育, 2体育, 3美育, 4劳育',
    subtopic tinyint NOT NULL COMMENT '所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践',
    level tinyint NOT NULL COMMENT '级别 0国家级, 1省级, 2市厅级, 3区级, 4校级',
    award_name VARCHAR(100) NOT NULL COMMENT '奖项名称',
    grade VARCHAR(100) NOT NULL COMMENT '等级, 自定义',
    remarks VARCHAR(255) COMMENT '备注',
    create_date datetime COMMENT '创建时间',
    key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='奖项设置';


-- 获奖记录
CREATE TABLE award_records (
   id BIGINT PRIMARY KEY COMMENT '主键',
   student_no varchar(50) NOT NULL COMMENT '学生学号',
   award_id BIGINT NOT NULL COMMENT '奖项id',
   grade VARCHAR(50) NOT NULL COMMENT '获奖等级',
   award_date DATE NOT NULL COMMENT '获奖日期',
   remarks VARCHAR(255) COMMENT '备注',
   create_date datetime COMMENT '创建时间',
   key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='获奖记录设置';


-- 角色管理
create table sys_role
(
  id                   bigint NOT NULL COMMENT 'id',
  name                 varchar(50) COMMENT '角色名称',
  remark               varchar(100) COMMENT '备注',
  school_id              bigint COMMENT '学校ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  key idx_school_id (school_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色管理';

-- 菜单管理
create table sys_menu
(
  id                   bigint NOT NULL COMMENT 'id',
  pid                  bigint COMMENT '上级ID，一级菜单为0',
  name                 varchar(200) COMMENT '名称',
  url                  varchar(200) COMMENT '菜单URL',
  permissions          varchar(500) COMMENT '授权(多个用逗号分隔，如：sys:user:list,sys:user:save)',
  menu_type            tinyint unsigned COMMENT '类型   0：菜单   1：按钮',
  icon                 varchar(50) COMMENT '菜单图标',
  sort                 int COMMENT '排序',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  key idx_pid (pid),
  key idx_sort (sort)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='菜单管理';

-- 角色用户关系
create table sys_role_user
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  user_id              bigint COMMENT '用户ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色用户关系';

-- 角色菜单关系
create table sys_role_menu
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  menu_id              bigint COMMENT '菜单ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_menu_id (menu_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色菜单关系';

-- 角色数据权限
create table sys_role_data_scope
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  school_id              bigint COMMENT '学校ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色数据权限';

-- 参数管理
create table sys_params
(
  id                   bigint NOT NULL COMMENT 'id',
  param_code           varchar(32) COMMENT '参数编码',
  param_value          varchar(2000) COMMENT '参数值',
  param_type           tinyint unsigned default 1 COMMENT '类型   0：系统参数   1：非系统参数',
  remark               varchar(200) COMMENT '备注',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  unique key uk_param_code (param_code),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='参数管理';

-- 字典类型
create table sys_dict_type
(
    id                   bigint NOT NULL COMMENT 'id',
    dict_type            varchar(100) NOT NULL COMMENT '字典类型',
    dict_name            varchar(255) NOT NULL COMMENT '字典名称',
    remark               varchar(255) COMMENT '备注',
    sort                 int unsigned COMMENT '排序',
    creator              bigint COMMENT '创建者',
    create_date          datetime COMMENT '创建时间',
    updater              bigint COMMENT '更新者',
    update_date          datetime COMMENT '更新时间',
    primary key (id),
    UNIQUE KEY(dict_type)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='字典类型';

-- 字典数据
create table sys_dict_data
(
    id                   bigint NOT NULL COMMENT 'id',
    dict_type_id         bigint NOT NULL COMMENT '字典类型ID',
    dict_label           varchar(255) NOT NULL COMMENT '字典标签',
    dict_value           varchar(255) COMMENT '字典值',
    remark               varchar(255) COMMENT '备注',
    sort                 int unsigned COMMENT '排序',
    creator              bigint COMMENT '创建者',
    create_date          datetime COMMENT '创建时间',
    updater              bigint COMMENT '更新者',
    update_date          datetime COMMENT '更新时间',
    primary key (id),
    unique key uk_dict_type_value (dict_type_id, dict_value),
    key idx_sort (sort)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='字典数据';

-- 登录日志
create table sys_log_login
(
  id                   bigint NOT NULL COMMENT 'id',
  operation            tinyint unsigned COMMENT '用户操作   0：用户登录   1：用户退出',
  status               tinyint unsigned NOT NULL COMMENT '状态  0：失败    1：成功    2：账号已锁定',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  creator_name         varchar(50) COMMENT '用户名',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_status (status),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='登录日志';

-- 操作日志
create table sys_log_operation
(
  id                   bigint NOT NULL COMMENT 'id',
  operation            varchar(50) COMMENT '用户操作',
  request_uri          varchar(200) COMMENT '请求URI',
  request_method       varchar(20) COMMENT '请求方式',
  request_params       text COMMENT '请求参数',
  request_time         int unsigned NOT NULL COMMENT '请求时长(毫秒)',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  status               tinyint unsigned NOT NULL COMMENT '状态  0：失败   1：成功',
  creator_name         varchar(50) COMMENT '用户名',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='操作日志';

-- 异常日志
create table sys_log_error
(
  id                   bigint NOT NULL COMMENT 'id',
  request_uri          varchar(200) COMMENT '请求URI',
  request_method       varchar(20) COMMENT '请求方式',
  request_params       text COMMENT '请求参数',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  error_info           text COMMENT '异常信息',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='异常日志';


-- 文件上传
CREATE TABLE sys_oss (
  id bigint NOT NULL COMMENT 'id',
  url varchar(200) COMMENT 'URL地址',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='文件上传';

-- 定时任务
CREATE TABLE schedule_job (
  id bigint NOT NULL COMMENT 'id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status tinyint unsigned COMMENT '任务状态  0：暂停  1：正常',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- 定时任务日志
CREATE TABLE schedule_job_log (
  id bigint NOT NULL COMMENT 'id',
  job_id bigint NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status tinyint unsigned NOT NULL COMMENT '任务状态    0：失败    1：成功',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int NOT NULL COMMENT '耗时(单位：毫秒)',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_job_id (job_id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- 系统用户Token
CREATE TABLE sys_user_token (
  id bigint NOT NULL COMMENT 'id',
  user_id bigint NOT NULL COMMENT '用户id',
  token varchar(100) NOT NULL COMMENT '用户token',
  expire_date datetime COMMENT '过期时间',
  update_date datetime COMMENT '更新时间',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY user_id (user_id),
  UNIQUE KEY token (token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- 初始数据
INSERT INTO sys_user(id, username, password, real_name, gender, email, mobile, status, school_id, super_admin, creator, create_date, updater, update_date) VALUES (1067246875800000001, 'admin', '$2a$10$012Kx2ba5jzqr9gLlG4MX.bnQJTD9UWqF57XDo2N3.fPtLne02u/m', '管理员', 0, 'root@renren.io', '13612345678', 1, null, 1, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000002, 0, '用户管理', null, '', 0, 'icon-safetycertificate', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-12-19 15:46:15');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000003, 1067246875800000055, '新增', null, 'sys:user:save,sys:role:list', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-11-11 15:32:04');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000004, 1067246875800000055, '修改', null, 'sys:user:update,sys:role:list', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-11-11 15:31:49');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000005, 1067246875800000055, '删除', null, 'sys:user:delete', 1, null, 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000006, 1067246875800000055, '导出', null, 'sys:user:export', 1, null, 4, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000007, 1067246875800000002, '角色管理', 'sys/role', null, 0, 'icon-team', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-22 19:22:57');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000008, 1067246875800000007, '查看', null, 'sys:role:page,sys:role:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000009, 1067246875800000007, '新增', null, 'sys:role:save,sys:menu:select,sys:school:list', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-11-11 17:05:17');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000010, 1067246875800000007, '修改', null, 'sys:role:update,sys:menu:select,sys:school:list', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-11-11 17:05:23');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000011, 1067246875800000007, '删除', null, 'sys:role:delete', 1, null, 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000025, 1067246875800000035, '菜单管理', 'sys/menu', null, 0, 'icon-unorderedlist', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000026, 1067246875800000025, '查看', null, 'sys:menu:list,sys:menu:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000027, 1067246875800000025, '新增', null, 'sys:menu:save', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000028, 1067246875800000025, '修改', null, 'sys:menu:update', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000029, 1067246875800000025, '删除', null, 'sys:menu:delete', 1, null, 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000030, 1067246875800000035, '定时任务', 'job/schedule', null, 0, 'icon-dashboard', 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000031, 1067246875800000030, '查看', null, 'sys:schedule:page,sys:schedule:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000032, 1067246875800000030, '新增', null, 'sys:schedule:save', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000033, 1067246875800000030, '修改', null, 'sys:schedule:update', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000034, 1067246875800000030, '删除', null, 'sys:schedule:delete', 1, null, 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000035, 0, '系统设置', null, null, 0, 'icon-setting', 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-12-20 18:10:36');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000036, 1067246875800000030, '暂停', null, 'sys:schedule:pause', 1, null, 4, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000037, 1067246875800000030, '恢复', null, 'sys:schedule:resume', 1, null, 5, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000038, 1067246875800000030, '立即执行', null, 'sys:schedule:run', 1, null, 6, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000039, 1067246875800000030, '日志列表', null, 'sys:schedule:log', 1, null, 7, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000040, 1067246875800000035, '参数管理', 'sys/params', '', 0, 'icon-fileprotect', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000041, 1067246875800000035, '字典管理', 'sys/dict-type', null, 0, 'icon-golden-fill', 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000042, 1067246875800000041, '查看', null, 'sys:dict:page,sys:dict:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000043, 1067246875800000041, '新增', null, 'sys:dict:save', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000044, 1067246875800000041, '修改', null, 'sys:dict:update', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000045, 1067246875800000041, '删除', null, 'sys:dict:delete', 1, null, 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000046, 0, '日志管理', null, null, 0, 'icon-container', 5, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-12-20 18:10:54');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000047, 1067246875800000035, '文件上传', 'oss/oss', 'sys:oss:all', 0, 'icon-upload', 4, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000048, 1067246875800000046, '登录日志', 'sys/log-login', 'sys:log:login', 0, 'icon-filedone', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000049, 1067246875800000046, '操作日志', 'sys/log-operation', 'sys:log:operation', 0, 'icon-solution', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000050, 1067246875800000046, '异常日志', 'sys/log-error', 'sys:log:error', 0, 'icon-file-exception', 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000051, 1067246875800000053, 'SQL监控', '{{ApiUrl}}/druid/sql.html', null, 0, 'icon-database', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000053, 0, '系统监控', null, null, 0, 'icon-desktop', 4, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-22 17:53:25');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000055, 1067246875800000002, '用户列表', 'sys/user', null, 0, 'icon-user', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-22 17:51:11');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000056, 1067246875800000055, '查看', null, 'sys:user:page,sys:user:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000057, 1067246875800000040, '新增', null, 'sys:params:save', 1, null, 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000058, 1067246875800000040, '导出', null, 'sys:params:export', 1, null, 4, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000059, 1067246875800000040, '查看', '', 'sys:params:page,sys:params:info', 1, null, 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000060, 1067246875800000040, '修改', null, 'sys:params:update', 1, null, 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000061, 1067246875800000040, '删除', '', 'sys:params:delete', 1, '', 3, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1156748733921165314, 1067246875800000053, '接口文档', '{{ApiUrl}}/doc.html', '', 0, 'icon-file-word', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1716029791874179074, 0, 'AI学习诊断', '', '', 0, 'icon-chrome', 0, 1067246875800000001, '2023-10-22 17:52:39', 1067246875800000001, '2023-10-22 17:52:39');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1716030861283287042, 1716029791874179074, '五育分析', 'analysis/wuyuscore', '', 0, 'icon-bulb-fill', 0, 1067246875800000001, '2023-10-22 17:56:54', 1067246875800000001, '2023-11-06 11:53:51');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1716031800874491906, 1716029791874179074, '五育设置', 'analysis/wuyuweight', '', 0, 'icon-setting', 1, 1067246875800000001, '2023-10-22 18:00:38', 1067246875800000001, '2023-11-06 11:54:03');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1716050759447339009, 1716029791874179074, '获奖记录', 'analysis/awardrecords', '', 0, 'icon-filesearch', 2, 1067246875800000001, '2023-10-22 19:15:58', 1067246875800000001, '2023-11-06 11:54:55');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1716050869086445569, 1716029791874179074, '奖项设置', 'analysis/awardsettings', '', 0, 'icon-setting', 3, 1067246875800000001, '2023-10-22 19:16:25', 1067246875800000001, '2023-11-06 11:54:30');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1718257406296743938, 1737415108751011842, '学校列表', 'sys/school', '', 0, 'icon-home', 0, 1067246875800000001, '2023-10-28 21:24:24', 1067246875800000001, '2023-12-20 18:12:06');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1718288185865383937, 1718257406296743938, '新增', '', 'sys:school:save', 1, '', 3, 1067246875800000001, '2023-10-28 23:26:42', 1067246875800000001, '2023-10-29 14:44:30');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1718517396983074818, 1718257406296743938, '删除', '', 'sys:school:delete', 1, '', 4, 1067246875800000001, '2023-10-29 14:37:31', 1067246875800000001, '2023-10-29 14:44:35');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1718519134716776450, 1718257406296743938, '查看', null, 'sys:school:page,sys:school:info,sys:school:list', 1, null, 0, 1067246875800000001, '2023-10-29 14:44:25', 1067246875800000001, '2023-11-11 17:05:45');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1718529633617530881, 1718257406296743938, '修改', '', 'sys:school:update', 1, '', 2, 1067246875800000001, '2023-10-29 15:26:08', 1067246875800000001, '2023-10-30 19:11:54');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721794446074327042, 1716050869086445569, '新增', '', 'analysis:awardsettings:save', 1, '', 0, 1067246875800000001, '2023-11-07 15:39:20', 1067246875800000001, '2023-11-07 15:39:20');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721794632909598722, 1716050869086445569, '查看', '', 'analysis:awardsettings:page,analysis:awardsettings:info', 1, '', 1, 1067246875800000001, '2023-11-07 15:40:05', 1067246875800000001, '2023-11-07 19:39:56');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721794847431471106, 1716050869086445569, '修改', '', 'analysis:awardsettings:update', 1, '', 2, 1067246875800000001, '2023-11-07 15:40:56', 1067246875800000001, '2023-11-07 15:40:56');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721795012393447425, 1716050869086445569, '删除', '', 'analysis:awardsettings:delete', 1, '', 3, 1067246875800000001, '2023-11-07 15:41:35', 1067246875800000001, '2023-11-07 15:41:35');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721795160565624833, 1716050759447339009, '查看', '', 'analysis:awardrecords:page,analysis:awardrecords:info', 1, '', 0, 1067246875800000001, '2023-11-07 15:42:10', 1067246875800000001, '2023-11-07 19:40:10');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721803433461166082, 1716050759447339009, '新增', '', 'analysis:awardrecords:save,analysis:awardsettings:list', 1, '', 1, 1067246875800000001, '2023-11-07 16:15:03', 1067246875800000001, '2023-11-12 23:57:19');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721803485017550849, 1716050759447339009, '修改', '', 'analysis:awardrecords:update', 1, '', 2, 1067246875800000001, '2023-11-07 16:15:15', 1067246875800000001, '2023-11-13 16:24:16');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1721803542664065026, 1716050759447339009, '删除', '', 'analysis:awardrecords:delete', 1, '', 3, 1067246875800000001, '2023-11-07 16:15:29', 1067246875800000001, '2023-11-07 16:15:29');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1724438772122198018, 1716030861283287042, '查看', '', 'analysis:wuyuscore:page,analysis:wuyuscore:info,sys:class:list,sys:grade:list,sys:semester:list', 1, '', 0, 1067246875800000001, '2023-11-14 22:46:56', 1067246875800000001, '2023-12-05 22:45:54');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1724438980369391617, 1716030861283287042, '新增', '', 'analysis:wuyuscore:save,analysis:wuyuscore:list', 1, '', 1, 1067246875800000001, '2023-11-14 22:47:46', 1067246875800000001, '2023-11-14 22:47:46');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1724439072388227073, 1716030861283287042, '修改', '', 'analysis:wuyuscore:update', 1, '', 2, 1067246875800000001, '2023-11-14 22:48:08', 1067246875800000001, '2023-11-14 22:48:08');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1724439143959830529, 1716030861283287042, '删除', '', 'analysis:wuyuscore:delete', 1, '', 3, 1067246875800000001, '2023-11-14 22:48:25', 1067246875800000001, '2023-11-14 22:48:25');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1725451968362606594, 1716031800874491906, '查看', '', 'analysis:wuyuweight:info,analysis:wuyuweight:page', 1, '', 0, 1067246875800000001, '2023-11-17 17:53:01', 1067246875800000001, '2023-11-17 17:53:17');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1725452186311225345, 1716031800874491906, '新增', '', 'analysis:wuyuweight:save,analysis:wuyuweight:list', 1, '', 1, 1067246875800000001, '2023-11-17 17:53:53', 1067246875800000001, '2023-11-17 17:53:53');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1725452286752223234, 1716031800874491906, '修改', '', 'analysis:wuyuweight:update', 1, '', 2, 1067246875800000001, '2023-11-17 17:54:17', 1067246875800000001, '2023-11-17 17:54:17');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1727256140055896065, 1716030861283287042, '个人诊断', '', 'analysis:wuyuscore:individual', 1, '', 5, 1067246875800000001, '2023-11-22 17:22:09', 1067246875800000001, '2023-11-22 17:22:09');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1727256280116289538, 1716030861283287042, '班级诊断', '', 'analysis:wuyuscore:class', 1, '', 4, 1067246875800000001, '2023-11-22 17:22:43', 1067246875800000001, '2023-11-22 17:22:43');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1727256747978317826, 1716030861283287042, '导出', '', 'analysis:wuyuscore:export', 1, '', 6, 1067246875800000001, '2023-11-22 17:24:34', 1067246875800000001, '2023-11-22 17:24:34');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1727904853822341122, 1716050869086445569, '导出', '', 'analysis:awardsettings:export', 1, '', 4, 1067246875800000001, '2023-11-24 12:19:55', 1067246875800000001, '2023-11-24 12:19:55');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1727904970004561922, 1716050759447339009, '导出', '', 'analysis:awardrecords:export', 1, '', 4, 1067246875800000001, '2023-11-24 12:20:22', 1067246875800000001, '2023-11-24 12:20:22');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729788012698861570, 1737415108751011842, '班级管理', 'sys/class', '', 0, 'icon-solution', 4, 1067246875800000001, '2023-11-29 17:02:55', 1067246875800000001, '2023-12-20 18:12:38');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729790202588745729, 1729788012698861570, '查看', '', 'sys:class:page,sys:class:info,sys:class:list', 1, '', 0, 1067246875800000001, '2023-11-29 17:11:37', 1067246875800000001, '2023-11-29 17:11:37');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729790287301103617, 1729788012698861570, '修改', '', 'sys:class:update', 1, '', 1, 1067246875800000001, '2023-11-29 17:11:57', 1067246875800000001, '2023-11-29 17:11:57');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729790349511020545, 1729788012698861570, '新增', '', 'sys:class:save', 1, '', 3, 1067246875800000001, '2023-11-29 17:12:12', 1067246875800000001, '2023-11-29 17:12:12');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729790387700158466, 1729788012698861570, '删除', '', 'sys:class:delete', 1, '', 2, 1067246875800000001, '2023-11-29 17:12:21', 1067246875800000001, '2023-12-01 21:03:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861203135942657, 1737415108751011842, '年级管理', 'sys/grade', '', 0, 'icon-group', 3, 1067246875800000001, '2023-11-29 21:53:45', 1067246875800000001, '2023-12-20 18:12:30');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861489346859009, 1729861203135942657, '查看', '', 'sys:grade:page,sys:grade:info,sys:grade:list', 1, '', 0, 1067246875800000001, '2023-11-29 21:54:53', 1067246875800000001, '2023-11-29 21:54:53');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861593319460865, 1729861203135942657, '修改', '', 'sys:grade:update', 1, '', 1, 1067246875800000001, '2023-11-29 21:55:18', 1067246875800000001, '2023-11-29 21:55:18');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861668514942978, 1729861203135942657, '新增', '', 'sys:grade:save', 1, '', 2, 1067246875800000001, '2023-11-29 21:55:36', 1067246875800000001, '2023-11-29 21:55:36');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861766653267970, 1729861203135942657, '删除', '', 'sys:grade:delete', 1, '', 3, 1067246875800000001, '2023-11-29 21:55:59', 1067246875800000001, '2023-11-29 21:55:59');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861824085872641, 1729861203135942657, '导出', '', 'sys:grade:export', 1, '', 4, 1067246875800000001, '2023-11-29 21:56:13', 1067246875800000001, '2023-11-29 21:56:13');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729861981519073281, 1718257406296743938, '导出', '', 'sys:school:export', 1, '', 5, 1067246875800000001, '2023-11-29 21:56:50', 1067246875800000001, '2023-11-29 21:56:58');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1729862096635940866, 1729788012698861570, '导出', '', 'sys:class:export', 1, '', 4, 1067246875800000001, '2023-11-29 21:57:18', 1067246875800000001, '2023-11-29 21:57:18');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573161583362049, 1737415108751011842, '学期管理', 'sys/semester', '', 0, 'icon-Field-time', 1, 1067246875800000001, '2023-12-01 21:02:49', 1067246875800000001, '2023-12-20 18:12:22');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573361634885634, 1730573161583362049, '查看', '', 'sys:semester:page,sys:semester:list,sys:semester:info', 1, '', 0, 1067246875800000001, '2023-12-01 21:03:37', 1067246875800000001, '2023-12-01 21:03:37');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573432468291585, 1730573161583362049, '新增', '', 'sys:semester:save', 1, '', 1, 1067246875800000001, '2023-12-01 21:03:53', 1067246875800000001, '2023-12-01 21:03:53');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573475539599361, 1730573161583362049, '修改', '', 'sys:semester:update', 1, '', 3, 1067246875800000001, '2023-12-01 21:04:04', 1067246875800000001, '2023-12-01 21:04:04');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573530870857729, 1730573161583362049, '删除', '', 'sys:semester:delete', 1, '', 2, 1067246875800000001, '2023-12-01 21:04:17', 1067246875800000001, '2023-12-01 21:04:17');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1730573607127498754, 1730573161583362049, '导出', '', 'sys:semester:export', 1, '', 4, 1067246875800000001, '2023-12-01 21:04:35', 1067246875800000001, '2023-12-01 21:04:35');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1732054581032026113, 1716030861283287042, '学生列表', '', 'sys:user:student', 1, '', 8, 1067246875800000001, '2023-12-05 23:09:27', 1067246875800000001, '2023-12-05 23:09:27');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1737415108751011842, 0, '学校管理', '', '', 0, 'icon-bank-fill', 2, 1067246875800000001, '2023-12-20 18:10:16', 1067246875800000001, '2023-12-20 18:10:44');

INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1160061077912858625, 'gender', '性别', '', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1225813644059140097, 'notice_type', '站内通知-类型', '', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1718520998321541121, 'schoolStatus', '学校状态', '', 3, 1067246875800000001, '2023-10-29 14:51:49', 1067246875800000001, '2023-10-31 11:01:38');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1719181886602891266, 'unitType', '单位类型', '', 4, 1067246875800000001, '2023-10-31 10:37:57', 1067246875800000001, '2023-10-31 10:37:57');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1721808445859966978, 'topic', '五育一级主题', '5个', 5, 1067246875800000001, '2023-11-07 16:34:58', 1067246875800000001, '2023-11-07 16:35:46');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1721808582929821697, 'subtopic', '奖项所属小类', '6个', 6, 1067246875800000001, '2023-11-07 16:35:30', 1067246875800000001, '2023-11-07 16:36:05');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1721808833577234433, 'award_level', '奖项级别', '', 7, 1067246875800000001, '2023-11-07 16:36:30', 1067246875800000001, '2023-11-07 16:36:30');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1727247807261499394, 'academicLevel', '学业等级', '', 7, 1067246875800000001, '2023-11-22 16:49:03', 1067246875800000001, '2023-11-22 16:49:21');
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1727613523248578562, 'comprehensiveLevel', '综合等级', '', 8, 1067246875800000001, '2023-11-23 17:02:16', 1067246875800000001, '2023-11-23 17:02:16');

INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061112075464705, 1160061077912858625, '男', '0', '', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061146967879681, 1160061077912858625, '女', '1', '', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061190127267841, 1160061077912858625, '保密', '2', '', 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814069634195457, 1225813644059140097, '公告', '0', '', 0, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814107559092225, 1225813644059140097, '会议', '1', '', 1, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814271879340034, 1225813644059140097, '其他', '2', '', 2, 1067246875800000001, '2023-10-20 17:26:05', 1067246875800000001, '2023-10-20 17:26:05');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1718521119461429249, 1718520998321541121, '正常', '0', '', 0, 1067246875800000001, '2023-10-29 14:52:18', 1067246875800000001, '2023-10-29 14:52:18');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1718521152621596674, 1718520998321541121, '停用', '1', '', 1, 1067246875800000001, '2023-10-29 14:52:26', 1067246875800000001, '2023-10-29 14:52:26');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1719181999836516354, 1719181886602891266, '省级', '0', '', 0, 1067246875800000001, '2023-10-31 10:38:24', 1067246875800000001, '2023-10-31 10:38:24');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1719182048276533250, 1719181886602891266, '市级', '1', '', 1, 1067246875800000001, '2023-10-31 10:38:36', 1067246875800000001, '2023-10-31 10:38:36');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1719182114135494657, 1719181886602891266, '区县级', '2', '', 2, 1067246875800000001, '2023-10-31 10:38:52', 1067246875800000001, '2023-10-31 10:38:52');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809139778203649, 1721808445859966978, '德育', '0', '', 0, 1067246875800000001, '2023-11-07 16:37:43', 1067246875800000001, '2023-11-07 16:37:43');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809197953200129, 1721808445859966978, '智育', '1', '', 1, 1067246875800000001, '2023-11-07 16:37:57', 1067246875800000001, '2023-11-07 16:37:57');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809261111029762, 1721808445859966978, '体育', '2', '', 2, 1067246875800000001, '2023-11-07 16:38:12', 1067246875800000001, '2023-11-07 16:38:12');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809301720281089, 1721808445859966978, '美育', '3', '', 3, 1067246875800000001, '2023-11-07 16:38:22', 1067246875800000001, '2023-11-07 16:38:22');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809346565779457, 1721808445859966978, '劳育', '4', '', 4, 1067246875800000001, '2023-11-07 16:38:33', 1067246875800000001, '2023-11-07 16:38:33');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809428908355586, 1721808582929821697, '奖惩记录', '0', '', 0, 1067246875800000001, '2023-11-07 16:38:52', 1067246875800000001, '2023-11-10 12:12:26');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809461825253378, 1721808582929821697, '实验与竞赛', '1', '', 1, 1067246875800000001, '2023-11-07 16:39:00', 1067246875800000001, '2023-11-10 12:12:34');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809487536336898, 1721808582929821697, '学业成绩', '2', '', 2, 1067246875800000001, '2023-11-07 16:39:06', 1067246875800000001, '2023-11-10 12:12:40');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809562127839233, 1721808582929821697, '体育特长', '3', '', 3, 1067246875800000001, '2023-11-07 16:39:24', 1067246875800000001, '2023-11-10 12:12:46');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1721809620298641410, 1721808582929821697, '美育成果', '4', '', 4, 1067246875800000001, '2023-11-07 16:39:38', 1067246875800000001, '2023-11-10 12:12:53');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722556771268354050, 1721808833577234433, '国家级', '0', '', 0, 1067246875800000001, '2023-11-09 18:08:32', 1067246875800000001, '2023-11-09 18:08:32');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722556811382677506, 1721808833577234433, '省级', '1', '', 1, 1067246875800000001, '2023-11-09 18:08:42', 1067246875800000001, '2023-11-09 18:08:42');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722556840889606146, 1721808833577234433, '市厅级', '2', '', 2, 1067246875800000001, '2023-11-09 18:08:49', 1067246875800000001, '2023-11-09 18:08:49');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722556865933795330, 1721808833577234433, '区级', '3', '', 3, 1067246875800000001, '2023-11-09 18:08:55', 1067246875800000001, '2023-11-09 18:08:55');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722556890520805378, 1721808833577234433, '校级', '4', '', 4, 1067246875800000001, '2023-11-09 18:09:01', 1067246875800000001, '2023-11-09 18:09:01');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1722829686377086978, 1721808582929821697, '劳动实践', '5', '', 5, 1067246875800000001, '2023-11-10 12:13:01', 1067246875800000001, '2023-11-10 12:13:01');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613594623049729, 1727247807261499394, '优', '0', '', 0, 1067246875800000001, '2023-11-23 17:02:33', 1067246875800000001, '2023-11-23 17:02:33');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613623823794177, 1727247807261499394, '中', '1', '', 1, 1067246875800000001, '2023-11-23 17:02:40', 1067246875800000001, '2023-11-23 17:02:40');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613654047948802, 1727247807261499394, '差', '2', '', 2, 1067246875800000001, '2023-11-23 17:02:47', 1067246875800000001, '2023-11-23 17:02:47');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613694426513409, 1727613523248578562, '优', '0', '', 0, 1067246875800000001, '2023-11-23 17:02:57', 1067246875800000001, '2023-11-23 17:02:57');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613731147644929, 1727613523248578562, '中', '1', '', 1, 1067246875800000001, '2023-11-23 17:03:06', 1067246875800000001, '2023-11-23 17:03:09');
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1727613767239630849, 1727613523248578562, '差', '2', '', 2, 1067246875800000001, '2023-11-23 17:03:14', 1067246875800000001, '2023-11-23 17:03:14');


INSERT INTO sys_params(id, param_code, param_value, param_type, remark, creator, create_date, updater, update_date) VALUES (1067246875800000073, 'CLOUD_STORAGE_CONFIG_KEY', '{"type":1,"qiniuDomain":"http://test.oss.renren.io","qiniuPrefix":"upload","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","qiniuBucketName":"renren-oss","aliyunDomain":"","aliyunPrefix":"","aliyunEndPoint":"","aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qcloudBucketName":""}', '0', '云存储配置信息', 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO schedule_job (id, bean_name, params, cron_expression, status, remark, creator, create_date, updater, update_date) VALUES (1067246875800000076, 'testTask', 'renren', '0 0/30 * * * ?', 0, '有参测试，多个参数使用json', 1067246875800000001, now(), 1067246875800000001, now());


--  quartz自带表结构
CREATE TABLE QRTZ_JOB_DETAILS(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME VARCHAR(250) NOT NULL,
  IS_DURABLE VARCHAR(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT(13) NULL,
  PREV_FIRE_TIME BIGINT(13) NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT(13) NOT NULL,
  END_TIME BIGINT(13) NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT(2) NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT(7) NOT NULL,
  REPEAT_INTERVAL BIGINT(12) NOT NULL,
  TIMES_TRIGGERED BIGINT(10) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR(1) NULL,
  BOOL_PROP_2 VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT(13) NOT NULL,
  SCHED_TIME BIGINT(13) NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT VARCHAR(1) NULL,
  REQUESTS_RECOVERY VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
  CHECKIN_INTERVAL BIGINT(13) NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
