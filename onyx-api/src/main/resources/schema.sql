create table player_statistic
(
    first_name                  text                not null,
    last_name                   text                not null,
    season                      integer             not null,
    dob                         date,
    points_per_game             float               not null,
    rebounds_per_game           float               not null,
    assists_per_game            float               not null,
    created_by_uuid             text                not null,
    created_timestamp           timestamp(6)        not null,
    updated_by_guid             text                not null,
    updated_timestamp           timestamp(6)        not null,
    primary key (first_name, last_name, season)
);