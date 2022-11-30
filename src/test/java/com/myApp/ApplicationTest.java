package com.myApp;

import com.myApp.junit_exstensions.BeforeAllTestsExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(BeforeAllTestsExtension.class)
public class ApplicationTest {
}
