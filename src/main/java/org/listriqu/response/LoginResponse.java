package org.listriqu.response;

public class LoginResponse {
    public String status;
    public String message;
    public Data data;

    public static class Data {
        public Integer user_id;
        public String username;
        public String full_name;
        public Integer role_id;
        public String token;
        // Tambahkan menu dsb jika sudah implementasi role-menu

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public Integer getRole_id() {
            return role_id;
        }

        public void setRole_id(Integer role_id) {
            this.role_id = role_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
