package com.example.ss_2022_c21_e3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class Ss2022C21E3ApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser
  void test1() throws Exception {
    mockMvc.perform(get("/hello"))
        .andExpect(status().isForbidden());

  }

  @Test
  @WithMockUser(authorities = "read")
  void test2() throws Exception {
    mockMvc.perform(get("/hello"))
            .andExpect(status().isOk());
  }

}
