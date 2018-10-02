package com.makentoshe.booruchan.common.api.parser

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.io.FileInputStream

@RunWith(JUnit4::class)
class HtmlParserTest {

    @Test
    @Ignore
    fun ` `() {
        val path = "${File("").canonicalPath}\\src\\test\\kotlin\\com\\makentoshe\\booruchan\\common\\api\\gelbooru\\GelbooruCommentsTestSource.html"
        val stream = FileInputStream(path)
//        val postsIdsArr = HtmlParser().parse(stream)
//        assertEquals(3, postsIdsArr.size)
    }

}