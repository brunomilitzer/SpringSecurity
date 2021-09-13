package com.brunomilitzer.springsecurity;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CouponserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCouponWithoutAuth_Forbidden() throws Exception {
        //mockMvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isForbidden());
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isUnauthorized());
    }

    @Test
    //@WithMockUser(roles = { "USER", "ADMIN" })
    @WithUserDetails("brunomilitzer")
    void testGetCouponWithoutAuth_Success() throws Exception {
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isOk())
            .andExpect(content().string("{\"id\":1,\"code\":\"SUPERSALE\",\"discount\":10.000,\"expDate\":\"12/12/21\"}"));
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void testCreateCoupon_WithoutCSRF_Forbidden() throws Exception {
        mockMvc.perform(post("/couponapi/coupons/").content("{\"code\":\"SUPERSALECRSF\",\"discount\":50.000,\"expDate\":\"12/12/21\"}")
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void testCreateCoupon_WithCSRF_Success() throws Exception {
        mockMvc.perform(post("/couponapi/coupons/").content("{\"code\":\"SUPERSALECRSF\",\"discount\":50.000,\"expDate\":\"12/12/21\"}")
            .contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "USER" })
    public void testCreateCoupon_NonAdminUser_Forbidden() throws Exception {
        mockMvc.perform(post("/couponapi/coupons/").content("{\"code\":\"SUPERSALECRSF\",\"discount\":50.000,\"expDate\":\"12/12/21\"}")
            .contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void testCORS() throws Exception {
        mockMvc.perform(options("/couponapi/coupons").header("Access-Control-Request-Method", "POST")
            .header("Origin", "https://www.brunomilitzer.com")).andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"))
            .andExpect(header().string("Access-Control-Allow-Origin", "*"))
            .andExpect(header().exists("Access-Control-Allow-Methods"))
            .andExpect(header().string("Access-Control-Allow-Methods", "POST"));
    }

}
