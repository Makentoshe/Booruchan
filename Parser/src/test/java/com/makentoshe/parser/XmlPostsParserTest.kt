package com.makentoshe.parser

import com.makentoshe.booruapi.Posts
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class XmlPostsParserTest {
    private lateinit var parser: Parser<Posts>

    @Before
    fun init() {
        parser = XmlPostsParser()
    }

    @Test
    fun `should parse xml from gelbooru`() {
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
                "<posts count=\"4250254\" offset=\"0\">\n" +
                "<post height=\"1126\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/b7/9f/b79f382b93b8a3290d5d2fec57d26680.jpeg\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/b7/9f/sample_b79f382b93b8a3290d5d2fec57d26680.jpg\" sample_width=\"850\" sample_height=\"798\" preview_url=\"https://simg3.gelbooru.com/thumbnails/b7/9f/thumbnail_b79f382b93b8a3290d5d2fec57d26680.jpg\" rating=\"q\" tags=\"1boy 1girl age_difference breast_sucking breasts cleavage handjob huge_breasts large_breasts nursing_handjob seirinpaul shota smile\" id=\"4527913\" width=\"1200\" change=\"1544970945\" md5=\"b79f382b93b8a3290d5d2fec57d26680\" creator_id=\"336651\" has_children=\"false\" created_at=\"Sun Dec 16 08:35:44 -0600 2018\" status=\"active\" source=\"https://twitter.com/sweetberry334/status/1073848399248539654\" has_notes=\"false\" has_comments=\"false\" preview_width=\"150\" preview_height=\"140\"/>\n" +
                "<post height=\"1200\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/df/f1/dff1938028adc9d8198823cd2b09b840.jpeg\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/images/df/f1/dff1938028adc9d8198823cd2b09b840.jpeg\" sample_width=\"748\" sample_height=\"1200\" preview_url=\"https://simg3.gelbooru.com/thumbnails/df/f1/thumbnail_dff1938028adc9d8198823cd2b09b840.jpg\" rating=\"e\" tags=\"azur_lane breasts condom condom_skirt double_v huge_breasts image_sample kaga_(azur_lane) naughty_face penis pixiv_sample smile used_condom v white_hair\" id=\"4527912\" width=\"748\" change=\"1544970772\" md5=\"dff1938028adc9d8198823cd2b09b840\" creator_id=\"44282\" has_children=\"false\" created_at=\"Sun Dec 16 08:32:52 -0600 2018\" status=\"active\" source=\"https://www.pixiv.net/member_illust.php?mode=medium&illust_id=72129862\" has_notes=\"false\" has_comments=\"false\" preview_width=\"93\" preview_height=\"150\"/>\n" +
                "</posts>"

        val posts = parser.parse(xml)

        assertEquals(2, posts.size)
        assertEquals(4250254, posts.count)
        assertEquals(0, posts.offset)
    }
}