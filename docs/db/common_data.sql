SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET search_path = common, public;

COPY common.atch_file (atch_file_id,file_grp_code_id,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
atch_file_id,file_grp_code_id,created_id,created_dt,updated_id,updated_dt
\.

COPY common.atch_file_item (atch_file_item_id,atch_file_id,path,file_name,file_size,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
atch_file_item_id,atch_file_id,path,file_name,file_size,created_id,created_dt,updated_id,updated_dt
\.

COPY common.code (code_id,upper_code_id,code,code_name,description,srt,etc1,etc2,etc3,etc4,use_yn,created_id,created_dt,updated_id,updated_dt,order_path) FROM stdin WITH CSV HEADER;
code_id,upper_code_id,code,code_name,description,srt,etc1,etc2,etc3,etc4,use_yn,created_id,created_dt,updated_id,updated_dt,order_path
11,10,PROFILE_IMG,프로필 이미지,,1,,,,,t,0,2025-09-27 09:44:01.28567+09,,2025-09-27 09:44:01.28567+09,
12,10,ATTACH_DOC,첨부 문서,,2,,,,,t,0,2025-09-27 09:44:01.28567+09,,2025-09-27 09:44:01.28567+09,
13,,YN,여부,여부,3,,,,,t,,2025-10-15 15:47:17.349069+09,,2025-10-15 15:47:17.349069+09,
14,13,Y,Y,,1,,,,,t,,2025-10-15 15:47:43.491725+09,,2025-10-15 15:47:43.491725+09,
15,13,N,N,,2,,,,,t,,2025-10-15 15:47:43.493353+09,,2025-10-15 15:47:43.493353+09,
10,,FILE_GRP,파일 그룹 코드,,2,,,,,t,0,2025-09-27 09:44:01.28567+09,,2025-09-27 09:44:01.28567+09,
2,1,ACTIVE,활성,,1,,,,,t,0,2025-09-27 09:44:01.283969+09,,2025-09-27 09:44:01.283969+09,000001:USER_STATUS>000001:ACTIVE
3,1,INACTIVE,비활성,test,2,,,,,t,0,2025-09-27 09:44:01.283969+09,,2025-09-27 09:44:01.283969+09,000001:USER_STATUS>000002:INACTIVE
4,1,LOCKED,잠김,,3,,,,,t,0,2025-09-27 09:44:01.283969+09,,2025-09-27 09:44:01.283969+09,000001:USER_STATUS>000003:LOCKED
1,,USER_STATUS,사용자 상태 그룹,사용자 상태 코드 그룹,1,,,,,t,0,2025-09-27 09:44:01.280302+09,,2025-09-27 09:44:01.280302+09,000001:USER_STATUS
\.

COPY common.menu (menu_id,menu_code,upper_menu_id,menu_name,menu_cn,url,srt,use_yn,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
menu_id,menu_code,upper_menu_id,menu_name,menu_cn,url,srt,use_yn,created_id,created_dt,updated_id,updated_dt
2,USER_MGMT,1,사용자 관리,사용자 등록/조회,/main/manage/users,2,t,0,2025-09-27 09:44:01.293711+09,,2025-09-27 09:44:01.293711+09
3,ROLE_MGMT,1,역할 관리,역할 등록/조회,/main/manage/roles,3,t,0,2025-09-27 09:44:01.293711+09,,2025-09-27 09:44:01.293711+09
4,MENU_MGMT,1,메뉴 관리,메뉴 등록/조회,/main/manage/menus,4,t,0,2025-09-27 09:44:01.293711+09,,2025-09-27 09:44:01.293711+09
1,COMMON_MGMT,,시스템 관리,메인 화면,/main/manage,1,t,0,2025-09-27 09:44:01.293711+09,,2025-09-27 09:44:01.293711+09
5,CODE_MGMT,1,공통코드 관리,코드 등록/조회,/main/manage/codes,5,t,0,2025-09-27 09:44:01.293711+09,,2025-09-27 09:44:01.293711+09
6,PERMISSION_MGMT,1,권한 관리,권한 관리,/main/manage/permissions,1,t,,2025-10-21 14:52:13.648633+09,,2025-10-21 14:52:13.648634+09
\.

COPY common.menu_permission_map (menu_id,permission_id) FROM stdin WITH CSV HEADER;
menu_id,permission_id
2,1
2,2
2,3
2,4
2,5
2,6
2,7
3,8
3,9
3,10
3,11
3,12
4,18
4,19
4,20
4,21
4,22
5,30
5,31
5,32
5,33
5,34
5,35
5,36
6,13
6,14
6,15
6,16
6,17
1,50
\.

COPY common.org (org_id,upper_org_id,org_name,srt,use_yn,created_id,created_dt,updated_id,updated_dt,org_code) FROM stdin WITH CSV HEADER;
org_id,upper_org_id,org_name,srt,use_yn,created_id,created_dt,updated_id,updated_dt,org_code
1,,대표부서,1,t,0,2025-09-27 09:44:01.274578+09,,2025-09-27 09:44:01.274578+09,본사
2,1,부서001,1,t,0,2025-09-27 09:44:01.274578+09,,2025-09-27 09:44:01.274578+09,개발팀
3,1,부서002,2,t,0,2025-09-27 09:44:01.274578+09,,2025-09-27 09:44:01.274578+09,영업팀
4,1,부서003,3,t,0,2025-09-27 09:44:01.274578+09,,2025-09-27 09:44:01.274578+09,인사팀
\.

COPY common.permission (permission_id,permission_code,permission_name,use_yn,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
permission_id,permission_code,permission_name,use_yn,created_id,created_dt,updated_id,updated_dt
2,USER_READ,사용자 단건 조회,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
3,USER_CREATE,사용자 등록,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
4,USER_UPDATE,사용자 수정,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
5,USER_DELETE,사용자 삭제,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
6,USER_CHANGE_PASSWORD,사용자 비밀번호 변경,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
7,USER_CHECK_LOGIN_ID,로그인 ID 중복 확인,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
8,ROLE_LIST,역할 목록 조회,t,,2025-10-19 01:31:11.566815+09,,2025-10-19 01:31:11.566815+09
9,ROLE_READ,역할 단건 조회,t,,2025-10-19 01:31:11.566815+09,,2025-10-19 01:31:11.566815+09
10,ROLE_CREATE,역할 등록,t,,2025-10-19 01:31:11.566815+09,,2025-10-19 01:31:11.566815+09
11,ROLE_UPDATE,역할 수정,t,,2025-10-19 01:31:11.566815+09,,2025-10-19 01:31:11.566815+09
12,ROLE_DELETE,역할 삭제,t,,2025-10-19 01:31:11.566815+09,,2025-10-19 01:31:11.566815+09
13,PERMISSION_LIST,권한 목록 조회,t,,2025-10-19 01:31:11.569155+09,,2025-10-19 01:31:11.569155+09
14,PERMISSION_READ,권한 단건 조회,t,,2025-10-19 01:31:11.569155+09,,2025-10-19 01:31:11.569155+09
15,PERMISSION_CREATE,권한 등록,t,,2025-10-19 01:31:11.569155+09,,2025-10-19 01:31:11.569155+09
16,PERMISSION_UPDATE,권한 수정,t,,2025-10-19 01:31:11.569155+09,,2025-10-19 01:31:11.569155+09
17,PERMISSION_DELETE,권한 삭제,t,,2025-10-19 01:31:11.569155+09,,2025-10-19 01:31:11.569155+09
18,MENU_LIST,메뉴 목록 조회,t,,2025-10-19 01:31:11.57167+09,,2025-10-19 01:31:11.57167+09
19,MENU_READ,메뉴 단건 조회,t,,2025-10-19 01:31:11.57167+09,,2025-10-19 01:31:11.57167+09
20,MENU_CREATE,메뉴 등록,t,,2025-10-19 01:31:11.57167+09,,2025-10-19 01:31:11.57167+09
21,MENU_UPDATE,메뉴 수정,t,,2025-10-19 01:31:11.57167+09,,2025-10-19 01:31:11.57167+09
22,MENU_DELETE,메뉴 삭제,t,,2025-10-19 01:31:11.57167+09,,2025-10-19 01:31:11.57167+09
23,ORG_LIST,조직 목록 조회,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
24,ORG_READ,조직 단건 조회,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
25,ORG_CREATE,조직 등록,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
26,ORG_UPDATE,조직 수정,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
27,ORG_DELETE,조직 삭제,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
28,ORG_CHILDREN_LIST,하위 조직 조회,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
29,ORG_CHILDREN_BY_CODE,코드 기반 하위 조직 조회,t,,2025-10-19 01:31:11.574179+09,,2025-10-19 01:31:11.574179+09
30,CODE_LIST,공통코드 목록 조회,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
31,CODE_READ,공통코드 단건 조회,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
32,CODE_CREATE,공통코드 등록,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
33,CODE_UPDATE,공통코드 수정,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
34,CODE_DELETE,공통코드 삭제,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
35,CODE_GROUP_BY_ID,상위코드 기준 목록 조회,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
37,ATCH_FILE_LIST,첨부파일 목록 조회,t,,2025-10-19 01:31:11.579062+09,,2025-10-19 01:31:11.579062+09
38,ATCH_FILE_READ,첨부파일 단건 조회,t,,2025-10-19 01:31:11.579062+09,,2025-10-19 01:31:11.579062+09
39,ATCH_FILE_CREATE,첨부파일 등록,t,,2025-10-19 01:31:11.579062+09,,2025-10-19 01:31:11.579062+09
40,ATCH_FILE_UPDATE,첨부파일 수정,t,,2025-10-19 01:31:11.579062+09,,2025-10-19 01:31:11.579062+09
41,ATCH_FILE_DELETE,첨부파일 삭제,t,,2025-10-19 01:31:11.579062+09,,2025-10-19 01:31:11.579062+09
42,ATCH_FILE_ITEM_LIST,첨부파일 항목 목록 조회,t,,2025-10-19 01:31:11.580702+09,,2025-10-19 01:31:11.580702+09
43,ATCH_FILE_ITEM_READ,첨부파일 항목 단건 조회,t,,2025-10-19 01:31:11.580702+09,,2025-10-19 01:31:11.580702+09
44,ATCH_FILE_ITEM_CREATE,첨부파일 항목 등록,t,,2025-10-19 01:31:11.580702+09,,2025-10-19 01:31:11.580702+09
45,ATCH_FILE_ITEM_UPDATE,첨부파일 항목 수정,t,,2025-10-19 01:31:11.580702+09,,2025-10-19 01:31:11.580702+09
46,ATCH_FILE_ITEM_DELETE,첨부파일 항목 삭제,t,,2025-10-19 01:31:11.580702+09,,2025-10-19 01:31:11.580702+09
1,USER_LIST,사용자 목록 조회,t,,2025-10-19 01:31:11.562768+09,,2025-10-19 01:31:11.562768+09
36,CODE_GROUP_BY_CODE,상위코드값 기준 목록 조회,t,,2025-10-19 01:31:11.577179+09,,2025-10-19 01:31:11.577179+09
50,UPPER_MENU_ACCESS,상위 메뉴 접근 권한,t,,2025-11-07 00:23:16.30989+09,,2025-11-07 00:23:16.30989+09
\.

COPY common.role (role_id,role_name,use_yn,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
role_id,role_name,use_yn,created_id,created_dt,updated_id,updated_dt
2,MANAGER,t,0,2025-09-27 09:44:01.287056+09,,2025-09-27 09:44:01.287056+09
3,USER,t,0,2025-09-27 09:44:01.287056+09,,2025-09-27 09:44:01.287056+09
1,ADMIN,t,0,2025-09-27 09:44:01.287056+09,,2025-09-27 09:44:01.287056+09
\.

COPY common.role_permission_map (role_id,permission_id) FROM stdin WITH CSV HEADER;
role_id,permission_id
1,1
1,2
1,3
1,4
1,5
1,6
1,7
1,8
1,9
1,10
1,11
1,12
1,13
1,14
1,15
1,16
1,17
1,18
1,19
1,20
1,21
1,22
1,23
1,24
1,25
1,26
1,27
1,28
1,29
1,30
1,31
1,32
1,33
1,34
1,35
1,36
1,37
1,38
1,39
1,40
1,41
1,42
1,43
1,44
1,45
1,46
2,8
2,9
2,13
2,14
2,18
2,19
2,23
2,24
2,28
2,29
2,30
2,31
2,32
2,33
2,34
2,35
2,36
2,37
2,38
2,39
2,40
2,41
2,42
2,43
2,44
2,45
2,46
1,50
2,50
\.

COPY common.user_role_map (user_id,role_id) FROM stdin WITH CSV HEADER;
user_id,role_id
1,1
2,2
3,3
4,3
\.

COPY common.users (user_id,email,login_id,user_password,user_name,org_id,emp_no,pstn_name,tel,user_status_code_id,user_password_update_dt,user_password_fail_cnt,old1_user_password,use_yn,created_id,created_dt,updated_id,updated_dt) FROM stdin WITH CSV HEADER;
user_id,email,login_id,user_password,user_name,org_id,emp_no,pstn_name,tel,user_status_code_id,user_password_update_dt,user_password_fail_cnt,old1_user_password,use_yn,created_id,created_dt,updated_id,updated_dt
1,admin@test.com,admin,"$argon2id$v=19$m=16384,t=2,p=1$rypco9GCyN7Z+69koxJG2w$jqFwHBi74S/eraRcoLQQd3hIKXTycAVfhzTDHroeim8",관리자,,1001,팀장,010-1111-11111,2,2025-10-18 21:59:11.606318+09,0,1234,t,0,2025-09-27 09:44:01.298069+09,,2025-09-27 09:44:01.298069+09
2,manager@test.com,manager,"$argon2id$v=19$m=16384,t=2,p=1$SWQASQ+0FqnEfR9+3abRUw$dFqF7P1igqY0ylNhP2JJ0C29POTaCQpa612DjSRhy2s",매니저,,1002,매니저,010-2222-2222,2,2025-10-18 21:59:16.288211+09,0,1234,t,0,2025-09-27 09:44:01.298069+09,,2025-09-27 09:44:01.298069+09
3,user1@test.com,user1,"$argon2id$v=19$m=16384,t=2,p=1$XFxIUbwlWAyvAsSkcEJWmg$C0gqRKyJtFSlN2EX6eFiEgBuLkjbMZfp2i8sKrHyyjs",일반사용자1,,1003,사원,010-3333-3333,2,2025-10-18 21:59:20.436572+09,0,1234,t,0,2025-09-27 09:44:01.298069+09,,2025-09-27 09:44:01.298069+09
4,user2@test.com,user2,"$argon2id$v=19$m=16384,t=2,p=1$8NOYA/XbaAZjkGAUPSqRbw$KmSksiImzs0I7n2DHgWnjW5HcvPy2/zoTj9kPBtM+TI",일반사용자2,,1004,사원,010-4444-4444,3,2025-10-18 21:59:24.034666+09,0,1234,t,0,2025-09-27 09:44:01.298069+09,,2025-09-27 09:44:01.298069+09
\.

-- 재시작 값 조정 (IDENTITY 컬럼)
ALTER TABLE common.users ALTER COLUMN user_id RESTART WITH COALESCE((SELECT MAX(user_id) FROM common.users), 0) + 1;
ALTER TABLE common.role ALTER COLUMN role_id RESTART WITH COALESCE((SELECT MAX(role_id) FROM common.role), 0) + 1;
ALTER TABLE common.menu ALTER COLUMN menu_id RESTART WITH COALESCE((SELECT MAX(menu_id) FROM common.menu), 0) + 1;
ALTER TABLE common.code ALTER COLUMN code_id RESTART WITH COALESCE((SELECT MAX(code_id) FROM common.code), 0) + 1;
ALTER TABLE common.org ALTER COLUMN org_id RESTART WITH COALESCE((SELECT MAX(org_id) FROM common.org), 0) + 1;
ALTER TABLE common.atch_file ALTER COLUMN atch_file_id RESTART WITH COALESCE((SELECT MAX(atch_file_id) FROM common.atch_file), 0) + 1;
ALTER TABLE common.atch_file_item ALTER COLUMN atch_file_item_id RESTART WITH COALESCE((SELECT MAX(atch_file_item_id) FROM common.atch_file_item), 0) + 1;

-- 시퀀스 값 조정
SELECT setval('common.permission_permission_id_seq', COALESCE(MAX(permission_id), 0) + 1, false) FROM common.permission;
