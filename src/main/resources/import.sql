--INSERT INTO tb_roles (uid, name, description, enable, created_at, updated_at)
--VALUES (gen_random_uuid(), 'ROLE_GROWDEV', 'role destinada aos administrados da aplicação', true, NOW(), NOW());
--INSERT INTO tb_roles (uid, name, description, enable, created_at, updated_at)
--VALUES (gen_random_uuid(), 'ROLE_PARCEIRO', 'role das empresas parceiras da growdev', true, NOW(), NOW());
--INSERT INTO tb_roles (uid, name, description, enable, created_at, updated_at)
--VALUES (gen_random_uuid(), 'ROLE_CANDIDATO', 'role dos candidatos as vagas ofertadas', true, NOW(), NOW());
--
--INSERT INTO tb_companies (uid, name, created_at, updated_at)
--VALUES (gen_random_uuid(), 'AREZZO', NOW(), NOW());
--INSERT INTO tb_companies (uid, name, created_at, updated_at)
--VALUES (gen_random_uuid(), 'SIRROS', NOW(), NOW());
--
--INSERT INTO tb_data_profiles (uid, email, name, phone_number, created_at, updated_at, company_uid)
--values (gen_random_uuid(), 'muriloteste@gmail.com', 'murilo', '(65)996601675', now(), now(), 'a5fbd4c8-edc8-4d7c-b713-e4ab378aec0a');
--
---- Pega o uid do tb_data_profile e coloca no tb_users.
--INSERT INTO tb_users(uid, login, password, enable, created_at, updated_at, role_uid)
--VALUES (gen_random_uuid(), 'muriloteste@gmail.com', '$2a$12$l8HttUPhMc70lGHr2ep8eeoYBNpytr8lmVOdEkkOGRLR3BIcdzRHm', true, NOW(), NOW(), '99dce788-0ca4-4039-8148-78f3c412e493');