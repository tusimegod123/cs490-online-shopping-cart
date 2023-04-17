package com.cs490.shoppingcart.administrationmodule.payload.response;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
    public class AuthResponse {
        private String token;
        private String error;

        public AuthResponse() {}

        public AuthResponse(String token) {
            this.token = token;
        }

        public AuthResponse(String token, String error) {
            this.token = token;
            this.error = error;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }


