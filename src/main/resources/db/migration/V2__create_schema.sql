INSERT INTO MASTER_ROLE (role_code, role_name, description)
VALUES 
  ('SUPER ADMIN', 'Administrator', 'Full access to system'),
  ('ADMIN', 'Admin role', 'Manage To role'),
  ('USER', 'Standard User', 'Limited access user');

-- Tambahkan data user (saya gunakan password plain dulu: 'password123' -> nanti kita hash kalau sudah pakai BCrypt)
INSERT INTO MASTER_USER (username, email, password, full_name, phone, unit_id, role_id, created_by, updated_by)
VALUES 
  ('john.doe', 'john.doe@example.com', '$2a$10$P1Vyd6smYExCFkydTpxyMOfsoRIqC6ubBSXT3e4nnjRo/bDsnKz72', 'John Doe', '081234567890', 1, 2, 1, 1),
  ('admin', 'admin@example.com', '$2a$10$P1Vyd6smYExCFkydTpxyMOfsoRIqC6ubBSXT3e4nnjRo/bDsnKz72', 'Admin User', '089876543210', 1, 1, 1, 1);

-- Keterangan password:
-- Password 'password123' di-hash menggunakan BCrypt:
-- $2a$10$P1Vyd6smYExCFkydTpxyMOfsoRIqC6ubBSXT3e4nnjRo/bDsnKz72

-- Tambahkan dummy menu
INSERT INTO MASTER_MENU (menu_code, menu_name, menu_icon, menu_url, menu_order, created_by, updated_by)
VALUES 
  ('DASHBOARD', 'Dashboard', 'dashboard_icon', '/dashboard', 1, 1, 1),
  ('PROFILE', 'Profile', 'profile_icon', '/profile', 2, 1, 1),
  ('SETTINGS', 'Settings', 'settings_icon', '/settings', 3, 1, 1);

-- Tambahkan mapping role-menu
INSERT INTO ROLE_MENU (role_id, menu_id, created_by, updated_by)
VALUES
  (1, 1), -- ADMIN punya akses ke semua menu
  (1, 2),
  (1, 3),
  (2, 1), -- USER hanya akses dashboard
  (2, 2);
