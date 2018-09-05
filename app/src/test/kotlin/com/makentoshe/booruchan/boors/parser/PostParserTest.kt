package com.makentoshe.booruchan.boors.parser

import com.makentoshe.booruchan.boors.gelbooru.Gelbooru
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.ByteArrayInputStream
import java.text.ParseException

@RunWith(JUnit4::class)
class PostParserTest {

    private val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?><posts count=\"4125493\" offset=\"0\">\n" +
            "<post height=\"1066\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/f7/7f/f77f25b48a429e80bb3f504af7b5fb16.png\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/f7/7f/sample_f77f25b48a429e80bb3f504af7b5fb16.jpg\" sample_width=\"850\" sample_height=\"567\" preview_url=\"https://simg3.gelbooru.com/thumbnails/f7/7f/thumbnail_f77f25b48a429e80bb3f504af7b5fb16.jpg\" rating=\"e\" tags=\"1girl animal_ears areolae bare_shoulders basket belt black_elbow_gloves black_fingerless_gloves black_gloves breasts bunny_ears carrot easter elbow_gloves fake_animal_ears fingerless_gloves fishnet gloves lips mobilepron navel nipples nude playboy ponytail pubic_hair pussy red_hair tengen_toppa_gurren_lagann thighhighs thighs vibrator white_belt yellow_eyes yoko_littner\" id=\"4393656\" width=\"1599\" change=\"1536165214\" md5=\"f77f25b48a429e80bb3f504af7b5fb16\" creator_id=\"3975\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:33 -0500 2018\" status=\"active\" source=\"\" has_notes=\"false\" has_comments=\"true\" preview_width=\"150\" preview_height=\"100\"/>\n" +
            "<post height=\"900\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/df/5e/df5ed56ddf75f9af7e79db81abbf8d81.jpg\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/images/df/5e/df5ed56ddf75f9af7e79db81abbf8d81.jpg\" sample_width=\"646\" sample_height=\"900\" preview_url=\"https://simg3.gelbooru.com/thumbnails/df/5e/thumbnail_df5ed56ddf75f9af7e79db81abbf8d81.jpg\" rating=\"s\" tags=\" 1girl bench black_dress black_footwear center_frills closed_umbrella dress frilled_dress frills gothic_lolita hot lolita_fashion mattaku_mousuke original shade shoes sitting solo sweat umbrella \" id=\"4393655\" width=\"646\" change=\"1536165127\" md5=\"df5ed56ddf75f9af7e79db81abbf8d81\" creator_id=\"6498\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:05 -0500 2018\" status=\"active\" source=\"http://www.pixiv.net/member_illust.php?mode=medium&illust_id=69835337\" has_notes=\"false\" has_comments=\"false\" preview_width=\"107\" preview_height=\"150\"/>\n" +
            "<post height=\"900\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/06/6a/066a35848c87eda66b7cd34d1bcff85e.jpg\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/images/06/6a/066a35848c87eda66b7cd34d1bcff85e.jpg\" sample_width=\"585\" sample_height=\"900\" preview_url=\"https://simg3.gelbooru.com/thumbnails/06/6a/thumbnail_066a35848c87eda66b7cd34d1bcff85e.jpg\" rating=\"s\" tags=\" 1girl :3 cheerleader commentary_request from_below kneehighs mattaku_mousuke midriff navel original panties pantyshot pleated_skirt pom_poms red_shirt red_skirt shirt skirt smile solo triple_stripe twintails underwear white_legwear \" id=\"4393654\" width=\"585\" change=\"1536165123\" md5=\"066a35848c87eda66b7cd34d1bcff85e\" creator_id=\"6498\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:03 -0500 2018\" status=\"active\" source=\"http://www.pixiv.net/member_illust.php?mode=medium&illust_id=69835337\" has_notes=\"false\" has_comments=\"false\" preview_width=\"97\" preview_height=\"150\"/>\n" +
            "</posts>"

    private val json = "[{\"source\":\"http:\\/\\/www.pixiv.net\\/member_illust.php?mode=medium&amp;illust_id=67852797\",\"directory\":\"04\\/b2\",\"hash\":\"04b22d1c3e3e4dd0bdf2fb45a01fc9dd\",\"height\":2824,\"id\":4393717,\"image\":\"04b22d1c3e3e4dd0bdf2fb45a01fc9dd.jpg\",\"change\":1536170470,\"owner\":\"danbooru\",\"parent_id\":null,\"rating\":\"s\",\"sample\":true,\"sample_height\":1200,\"sample_width\":850,\"score\":0,\"tags\":\"3girls absurdres annoyed bb_(fate)_(all) bb_(fate\\/extra_ccc) breasts child comic dual_persona eighth_note eyebrows_visible_through_hair fate\\/extra fate\\/extra_ccc fate\\/grand_order fate_(series) gloves greyscale hair_ribbon happy highres holding_up indoors large_breasts long_hair meltlilith monochrome mother_and_daughter multiple_girls musical_note open_mouth ribbon saberillya2 speech_bubble speed_lines\",\"width\":2000,\"file_url\":\"https:\\/\\/simg3.gelbooru.com\\/images\\/04\\/b2\\/04b22d1c3e3e4dd0bdf2fb45a01fc9dd.jpg\",\"created_at\":\"Wed Sep 05 13:01:10 -0500 2018\"},{\"source\":\"\",\"directory\":\"5e\\/d3\",\"hash\":\"5ed31c006c7dc3af9142eaa531ca87f2\",\"height\":6033,\"id\":4393716,\"image\":\"5ed31c006c7dc3af9142eaa531ca87f2.jpg\",\"change\":1536170467,\"owner\":\"danbooru\",\"parent_id\":null,\"rating\":\"q\",\"sample\":true,\"sample_height\":1236,\"sample_width\":850,\"score\":0,\"tags\":\"1girl absurdres ass barefoot fate\\/grand_order fate_(series) feet hard_translated hard_translated_(non-english) highres japanese_clothes naturalton ninja no_shoes okita_souji_(fate) okita_souji_(fate)_(all) pussy soles toes\",\"width\":4148,\"file_url\":\"https:\\/\\/simg3.gelbooru.com\\/images\\/5e\\/d3\\/5ed31c006c7dc3af9142eaa531ca87f2.jpg\",\"created_at\":\"Wed Sep 05 13:01:02 -0500 2018\"},{\"source\":\"https:\\/\\/twitter.com\\/oraoramotomiki\\/status\\/1014887358640951296\",\"directory\":\"2d\\/03\",\"hash\":\"2d035cff9f77cfb1dae205c4ee6d634c\",\"height\":2048,\"id\":4393715,\"image\":\"2d035cff9f77cfb1dae205c4ee6d634c.jpg\",\"change\":1536170460,\"owner\":\"danbooru\",\"parent_id\":null,\"rating\":\"s\",\"sample\":true,\"sample_height\":1098,\"sample_width\":850,\"score\":0,\"tags\":\"1girl black_hair bodysuit bright_pupils closed_mouth cowboy_shot genderswap genderswap_(mtf) grey_background highres kamen_rider kamen_rider_build kamen_rider_build_(series) looking_at_viewer motomitsu multicolored_hair rabbit+rabbit_form red_eyes red_hair rider-tan simple_background sketch solo two-tone_hair\",\"width\":1585,\"file_url\":\"https:\\/\\/simg3.gelbooru.com\\/images\\/2d\\/03\\/2d035cff9f77cfb1dae205c4ee6d634c.jpg\",\"created_at\":\"Wed Sep 05 13:00:59 -0500 2018\"}]"

    @Test
    fun `parse gelbooru post from xml`() {
        val parser = PostParser(Gelbooru.Post::class.java)
        val posts = parser.parsePosts(ByteArrayInputStream(xml.toByteArray()))
        assertNotNull(posts)
        assertEquals(4125493 ,posts.count)
        assertEquals(0, posts.offset)
        assertEquals(3, posts.count())
    }

    @Test
    fun `parse gelbooru post from json`() {
        val parser = PostParser(Gelbooru.Post::class.java)
        val posts = parser.parsePosts(ByteArrayInputStream(json.toByteArray()))
        assertNotNull(posts)
        assertEquals(0, posts.offset)
        assertEquals(-1, posts.count)
        assertEquals(3, posts.count())
    }

    @Test(expected = ParseException::class)
    fun `parse gelbooru post from unparsable`() {
        val parser = PostParser(Gelbooru.Post::class.java)
        parser.parsePosts(ByteArrayInputStream("sasasasa".toByteArray()))
    }

}