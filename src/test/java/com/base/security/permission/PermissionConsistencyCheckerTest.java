package com.base.security.permission;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PermissionConsistencyCheckerTest {

    @Autowired
    private PermissionConsistencyChecker checker;

    @Test
    void allAnnotatedPermissionsExistInDatabase() throws Exception {
        checker.run(null);
    }
}
