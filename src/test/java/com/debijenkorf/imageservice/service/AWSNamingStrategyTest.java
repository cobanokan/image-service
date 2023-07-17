package com.debijenkorf.imageservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AWSNamingStrategyTest {

    private AWSNamingStrategy strategy = new AWSNamingStrategy();

    @Test
    void lessThan4chars() {
        Assertions.assertEquals("/original/abc.jpg", strategy.getPathName("original", "abc.jpg"));
    }

    @Test
    void lessThan8chars() {
        Assertions.assertEquals("/thumbnail/abcd/abcde.jpg", strategy.getPathName("thumbnail", "abcde.jpg"));
    }

    @Test
    void moreThan8chars() {
        Assertions.assertEquals("/thumbnail/abcd/efgh/abcdefghij.jpg", strategy.getPathName("thumbnail", "abcdefghij.jpg"));
    }

    @Test
    void withFolder() {
        Assertions.assertEquals("/thumbnail/_som/edir/_somedir_anotherdir_abcdef.jpg", strategy.getPathName("thumbnail", "/somedir/anotherdir/abcdef.jpg"));
    }
}
