create database ids;


create table ids.frames
(
    frame_id        int auto_increment comment '框架ID，主键'
        primary key,
    frame_name      varchar(52)  not null comment '框架名称',
    frame_describe  varchar(100) not null comment '框架描述',
    frame_character text         null comment '框架特性'
)
    comment '框架信息表' collate = utf8mb4_unicode_ci;

create index idx_frame_name
    on ids.frames (frame_name);

create table ids.identify_traffic
(
    id                  int auto_increment
        primary key,
    protocol            int                  null,
    source_ip           varchar(45)          null,
    source_port         bigint               null,
    destination_ip      varchar(45)          null,
    destination_port    bigint               null,
    timestamp           timestamp            null,
    traffic_description text                 null,
    suggestion          text                 null,
    label               int                  null,
    is_correct          tinyint(1) default 0 null
)
    collate = utf8mb4_unicode_ci;

create index idx_destination_ip
    on ids.identify_traffic (destination_ip);

create index idx_label
    on ids.identify_traffic (label);

create index idx_source_ip
    on ids.identify_traffic (source_ip);

create index idx_timestamp
    on ids.identify_traffic (timestamp);

create table ids.models
(
    model_id      int                     not null comment '模型唯一标识ID'
        primary key,
    model_version varchar(32)             not null comment '模型版本号(格式：v1.0.0)',
    model_name    varchar(128)            not null comment '模型名称',
    loss          decimal(10, 6)          null comment '损失率(保留6位小数)',
    accuracy      decimal(5, 4)           null comment '准确率(0-1之间，保留4位小数)',
    create_date   datetime                not null comment '创建时间',
    update_time   datetime                not null comment '更新时间',
    model_path    varchar(512)            not null comment '模型文件存储路径',
    is_apply      tinyint(1)  default 0   not null comment '是否启用',
    frame_name    varchar(52) default '0' not null,
    constraint uk_model_version
        unique (model_id, model_version) comment '模型ID和版本唯一索引'
)
    comment 'AI模型信息表' collate = utf8mb4_unicode_ci;

create index idx_create_date
    on ids.models (create_date)
    comment '创建时间索引';

create table ids.network_flow
(
    id                       int auto_increment
        primary key,
    protocol                 varchar(20)    null,
    source_ip                varchar(45)    null,
    source_port              int            null,
    destination_ip           varchar(45)    null,
    destination_port         int            null,
    timestamp                datetime       null,
    flow_duration            bigint         null,
    total_fwd_packets        int            null,
    total_backward_packets   int            null,
    total_length_fwd_packets bigint         null,
    total_length_bwd_packets bigint         null,
    fwd_packet_length_max    int            null,
    fwd_packet_length_min    int            null,
    fwd_packet_length_mean   decimal(10, 2) null,
    fwd_packet_length_std    decimal(10, 2) null,
    bwd_packet_length_max    int            null,
    bwd_packet_length_min    int            null,
    bwd_packet_length_mean   decimal(10, 2) null,
    bwd_packet_length_std    decimal(10, 2) null,
    flow_bytes_per_sec       decimal(15, 2) null,
    flow_packets_per_sec     decimal(15, 2) null,
    flow_iat_mean            decimal(15, 2) null,
    flow_iat_std             decimal(15, 2) null,
    flow_iat_max             decimal(15, 2) null,
    flow_iat_min             decimal(15, 2) null,
    fwd_iat_total            decimal(15, 2) null,
    fwd_iat_mean             decimal(15, 2) null,
    fwd_iat_std              decimal(15, 2) null,
    fwd_iat_max              decimal(15, 2) null,
    fwd_iat_min              decimal(15, 2) null,
    bwd_iat_total            decimal(15, 2) null,
    bwd_iat_mean             decimal(15, 2) null,
    bwd_iat_std              decimal(15, 2) null,
    bwd_iat_max              decimal(15, 2) null,
    bwd_iat_min              decimal(15, 2) null,
    fwd_psh_flags            int            null,
    bwd_psh_flags            int            null,
    fwd_urg_flags            int            null,
    bwd_urg_flags            int            null,
    fwd_header_length        int            null,
    bwd_header_length        int            null,
    fwd_packets_per_sec      decimal(15, 2) null,
    bwd_packets_per_sec      decimal(15, 2) null,
    min_packet_length        int            null,
    max_packet_length        int            null,
    packet_length_mean       decimal(10, 2) null,
    packet_length_std        decimal(10, 2) null,
    packet_length_variance   decimal(15, 2) null,
    fin_flag_count           int            null,
    syn_flag_count           int            null,
    rst_flag_count           int            null,
    psh_flag_count           int            null,
    ack_flag_count           int            null,
    urg_flag_count           int            null,
    cwe_flag_count           int            null,
    ece_flag_count           int            null,
    down_up_ratio            decimal(10, 2) null,
    average_packet_size      decimal(10, 2) null,
    avg_fwd_segment_size     decimal(10, 2) null,
    avg_bwd_segment_size     decimal(10, 2) null,
    fwd_header_length_end    int            null,
    fwd_avg_bytes_bulk       decimal(10, 2) null,
    fwd_avg_packets_bulk     decimal(10, 2) null,
    fwd_avg_bulk_rate        decimal(15, 2) null,
    bwd_avg_bytes_bulk       decimal(10, 2) null,
    bwd_avg_packets_bulk     decimal(10, 2) null,
    bwd_avg_bulk_rate        decimal(15, 2) null,
    subflow_fwd_packets      int            null,
    subflow_fwd_bytes        bigint         null,
    subflow_bwd_packets      int            null,
    subflow_bwd_bytes        bigint         null,
    init_win_bytes_forward   int            null,
    init_win_bytes_backward  int            null,
    act_data_pkt_fwd         int            null,
    min_seg_size_forward     int            null,
    active_mean              decimal(15, 2) null,
    active_std               decimal(15, 2) null,
    active_max               decimal(15, 2) null,
    active_min               decimal(15, 2) null,
    idle_mean                decimal(15, 2) null,
    idle_std                 decimal(15, 2) null,
    idle_max                 decimal(15, 2) null,
    idle_min                 decimal(15, 2) null,
    similar_http             tinyint(1)     null,
    inbound                  tinyint(1)     null,
    label                    varchar(50)    null
);

create index idx_destination
    on ids.network_flow (destination_ip, destination_port);

create index idx_protocol
    on ids.network_flow (protocol);

create index idx_source
    on ids.network_flow (source_ip, source_port);

create index idx_timestamp
    on ids.network_flow (timestamp);

create table ids.system_menu_table
(
    menu_id        int auto_increment comment 'Primary Key'
        primary key,
    menu_name      varchar(50)           not null,
    menu_path      varchar(100)          not null,
    menu_component varchar(100)          null,
    menu_title     varchar(50)           not null,
    parent_id      int        default -1 not null,
    is_directory   tinyint(1) default 0  not null,
    menu_icon      varchar(45)           null
);

create table ids.user
(
    user_id     varchar(52)                        not null
        primary key,
    user_name   varchar(32)                        not null,
    password    varchar(64)                        not null,
    phone       varchar(11)                        not null,
    role        tinyint  default 1                 not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    avatar      varchar(255)                       null comment '用户头像',
    constraint idx_phone
        unique (phone),
    constraint idx_username
        unique (user_name)
);

create index idx_role
    on ids.user (role);

