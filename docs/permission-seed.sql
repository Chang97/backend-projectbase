-- Seed permissions for existing REST APIs.
-- Run with: psql -f permission-seed.sql (adjust schema prefix if needed).

CREATE SCHEMA IF NOT EXISTS common;

CREATE SEQUENCE IF NOT EXISTS common.permission_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE IF EXISTS common.permission
    ALTER COLUMN permission_id SET DEFAULT nextval('common.permission_permission_id_seq');

ALTER SEQUENCE common.permission_permission_id_seq OWNED BY common.permission.permission_id;

COMMENT ON COLUMN common.permission.permission_id IS '권한 PK';

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'USER_LIST', '사용자 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'USER_READ', '사용자 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'USER_CREATE', '사용자 등록', true),
    (nextval('common.permission_permission_id_seq'), 'USER_UPDATE', '사용자 수정', true),
    (nextval('common.permission_permission_id_seq'), 'USER_DELETE', '사용자 삭제', true),
    (nextval('common.permission_permission_id_seq'), 'USER_CHANGE_PASSWORD', '사용자 비밀번호 변경', true),
    (nextval('common.permission_permission_id_seq'), 'USER_CHECK_LOGIN_ID', '로그인 ID 중복 확인', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'ROLE_LIST', '역할 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ROLE_READ', '역할 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ROLE_CREATE', '역할 등록', true),
    (nextval('common.permission_permission_id_seq'), 'ROLE_UPDATE', '역할 수정', true),
    (nextval('common.permission_permission_id_seq'), 'ROLE_DELETE', '역할 삭제', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'PERMISSION_LIST', '권한 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'PERMISSION_READ', '권한 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'PERMISSION_CREATE', '권한 등록', true),
    (nextval('common.permission_permission_id_seq'), 'PERMISSION_UPDATE', '권한 수정', true),
    (nextval('common.permission_permission_id_seq'), 'PERMISSION_DELETE', '권한 삭제', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'MENU_LIST', '메뉴 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'MENU_READ', '메뉴 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'MENU_CREATE', '메뉴 등록', true),
    (nextval('common.permission_permission_id_seq'), 'MENU_UPDATE', '메뉴 수정', true),
    (nextval('common.permission_permission_id_seq'), 'MENU_DELETE', '메뉴 삭제', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'ORG_LIST', '조직 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_READ', '조직 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_CREATE', '조직 등록', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_UPDATE', '조직 수정', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_DELETE', '조직 삭제', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_CHILDREN_LIST', '하위 조직 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ORG_CHILDREN_BY_CODE', '코드 기반 하위 조직 조회', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'CODE_LIST', '공통코드 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_READ', '공통코드 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_CREATE', '공통코드 등록', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_UPDATE', '공통코드 수정', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_DELETE', '공통코드 삭제', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_GROUP_BY_ID', '상위코드 기준 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'CODE_GROUP_BY_CODE', '상위코드값 기준 목록 조회', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_LIST', '첨부파일 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_READ', '첨부파일 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_CREATE', '첨부파일 등록', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_UPDATE', '첨부파일 수정', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_DELETE', '첨부파일 삭제', true)
ON CONFLICT (permission_code) DO NOTHING;

INSERT INTO permission (permission_id, permission_code, permission_name, use_yn)
VALUES
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_ITEM_LIST', '첨부파일 항목 목록 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_ITEM_READ', '첨부파일 항목 단건 조회', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_ITEM_CREATE', '첨부파일 항목 등록', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_ITEM_UPDATE', '첨부파일 항목 수정', true),
    (nextval('common.permission_permission_id_seq'), 'ATCH_FILE_ITEM_DELETE', '첨부파일 항목 삭제', true)
ON CONFLICT (permission_code) DO NOTHING;
