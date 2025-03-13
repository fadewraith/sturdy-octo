package com.example.ss_2022_c21_e1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
                    .with(httpBasic("bill", "12345")))
            .andExpect(status().isOk());

  }

//  @Test
//  void test2() throws Exception {
//    mockMvc.perform(post("/hello")
//                    .with(httpBasic("john", "12345"))
//                    .with(csrf()))
//            .andExpect(status().isOk());
//
//  }

  @Test // used c21e2 as an outh2resource server
  void test2() throws Exception {
    mockMvc.perform(post("/hello")
                    .with(jwt())
//                    .with(opaqueToken())
            )
            .andExpect(status().isOk());

  }

}
