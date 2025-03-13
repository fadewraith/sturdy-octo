package com.example.ss_2022_c21_e2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class Ss2022C21E1ApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void test1() throws Exception {
    mockMvc.perform(get("/demo")
        .with(jwt())
    // .with(opaqueToken())
    )
        .andExpect(status().isOk());

  }

}
