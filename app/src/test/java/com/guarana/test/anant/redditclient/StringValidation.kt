package com.guarana.test.anant.redditclient

import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.regex.Pattern.matches

/**
 * Created by anant on 2018-11-09.
 */
class StringValidation {

    @Test
    fun usernameValidation(){
        var exampleUsernames = arrayOf( "12121212", "asdasd", "adasd2112", "ASDSAEweweew" , "REWRWEEWW")
        exampleUsernames.forEach {
            assert(it.matches(Regex("[a-zA-Z]+")))
        }
    }
}