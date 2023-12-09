package com.felipemello.gym.attendance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

  @Autowired
  private Application application;

  @Test
  void contextLoads() {
    assertNotNull(application);
  }

}
