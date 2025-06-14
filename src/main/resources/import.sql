-- ENUM type harus dibuat dulu
CREATE TYPE status_enum AS ENUM ('Active', 'Inactive');
CREATE TYPE session_status_enum AS ENUM ('Active', 'Logout');

CREATE TABLE MASTER_ROLE (
    role_id SERIAL PRIMARY KEY,
    role_code VARCHAR(50) UNIQUE NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status status_enum DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE MASTER_USER (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    unit_id INT,
    role_id INT,
    status status_enum DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    FOREIGN KEY (role_id) REFERENCES MASTER_ROLE(role_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE MASTER_MENU (
    menu_id SERIAL PRIMARY KEY,
    parent_id INT,
    menu_code VARCHAR(50) UNIQUE NOT NULL,
    menu_name VARCHAR(100) NOT NULL,
    menu_icon VARCHAR(100),
    menu_url VARCHAR(255),
    menu_order INT DEFAULT 0,
    is_active status_enum DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    FOREIGN KEY (parent_id) REFERENCES MASTER_MENU(menu_id)
);

CREATE TABLE ROLE_MENU (
    role_menu_id SERIAL PRIMARY KEY,
    role_id INT,
    menu_id INT,
    is_active status_enum DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES MASTER_ROLE(role_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES MASTER_MENU(menu_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE USER_SESSIONS (
    session_id SERIAL PRIMARY KEY,
    user_id INT,
    session_token VARCHAR(255) UNIQUE NOT NULL,
    login_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logout_at TIMESTAMP DEFAULT NULL,
    ip_address VARCHAR(50),
    user_agent VARCHAR(255),
    status session_status_enum DEFAULT 'Active',
    FOREIGN KEY (user_id) REFERENCES MASTER_USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Tambahkan data role dulu
INSERT INTO MASTER_ROLE (role_code, role_name, description)
VALUES 
  ('ADMIN', 'Administrator', 'Full access to system'),
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
  (1, 1, 1, 1), -- ADMIN punya akses ke semua menu
  (1, 2, 1, 1),
  (1, 3, 1, 1),
  (2, 1, 1, 1), -- USER hanya akses dashboard
  (2, 2, 1, 1);
