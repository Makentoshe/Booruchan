package com.makentoshe.parser

import com.makentoshe.booruapi.Posts
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JsonPostsParserTest {
    private lateinit var parser: Parser<Posts>

    @Before
    fun init() {
        parser = JsonPostsParser()
    }

    @Test
    fun `should parser posts from gelbooru`() {
        val json =
            "[{\"source\":\"\",\"directory\":\"8e\\/3d\",\"hash\":\"8e3dcbfd2156d2b92870e8daa70ac3a7\",\"height\":450,\"id\":4528378,\"image\":\"8e3dcbfd2156d2b92870e8daa70ac3a7.gif\",\"change\":1544985277,\"owner\":\"shhh~itme\",\"parent_id\":null,\"rating\":\"e\",\"sample\":false,\"sample_height\":0,\"sample_width\":0,\"score\":0,\"tags\":\"acci animal animal_genitalia animated animatic animation balls barbed_penis bestiality big_cat black_and_white boner digital_artwork digital_drawing dominance duo erection fae feline female female_on_feral femdom feral feral_on_human fur gif girl human interspecies larger_male lion loli lying male male\\/female male_penetrating monochrome nude oc on_back on_top original penetration penis pussy riding sex sheath shhhitme simple_background size_difference sketch smaller_female smaller_girl spread_legs spreading vagina vaginal_penetration video white_background young\",\"width\":600,\"file_url\":\"https:\\/\\/simg3.gelbooru.com\\/images\\/8e\\/3d\\/8e3dcbfd2156d2b92870e8daa70ac3a7.gif\",\"created_at\":\"Sun Dec 16 12:34:36 -0600 2018\"}," +
                    "{\"source\":\"\",\"directory\":\"de\\/bb\",\"hash\":\"debbdd43dfc6e30b9a8b43097ae2f84c\",\"height\":1050,\"id\":4528377,\"image\":\"debbdd43dfc6e30b9a8b43097ae2f84c.jpeg\",\"change\":1544985119,\"owner\":\"ynyswydryn\",\"parent_id\":null,\"rating\":\"e\",\"sample\":false,\"sample_height\":0,\"sample_width\":0,\"score\":0,\"tags\":\"1girl aaa_(nisetsuru) areolae arms_behind_back bikini breast_grab breasts clouds cowboy_shot day eyes_closed grabbing grabbing_from_behind groin happy highres horns huge_breasts kobayashi-san_chi_no_maidragon legs long_hair magatsuchi_shouta multicolored_hair navel nipples ocean open_mouth outdoors purple_hair quetzalcoatl_(maidragon) short_hair sky smile standing string_bikini swimsuit thighs topless water\",\"width\":850,\"file_url\":\"https:\\/\\/simg3.gelbooru.com\\/images\\/de\\/bb\\/debbdd43dfc6e30b9a8b43097ae2f84c.jpeg\",\"created_at\":\"Sun Dec 16 12:31:59 -0600 2018\"}]"
        val posts = parser.parse(json)
        assertEquals(2, posts.size)
        assertEquals(4528378, posts[0].id)
        assertEquals(4528377, posts[1].id)
    }
}