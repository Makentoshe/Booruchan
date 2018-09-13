package com.makentoshe.booruchan.common.api.parser

import com.makentoshe.booruchan.common.api.parser.AutocompleteSearchParser
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.text.ParseException

@RunWith(JUnit4::class)
class AutocompleteSearchParserTest {

    @Test
    fun `parse Json from autocomplete result`() {
        val json = "[\"hatsune_miku\",\"hatsuyuki_(kantai_collection)\"," +
                "\"hatsune_miku_(append)\",\"hatsune_miku_(cosplay)\"," +
                "\"hatsuzuki_(kantai_collection)\",\"hatsukaze_(kantai_collection)\"," +
                "\"hatsushimo_(kantai_collection)\",\"hatsuharu_(kantai_collection)\"," +
                "\"hatsune_mikuo\",\"hatsuzuki_527\"]"
        val result = AutocompleteSearchParser().parse(json)
        assertEquals(10, result.size)
        assertEquals("hatsune_miku", result[0])
        assertEquals("hatsuyuki_(kantai_collection)", result[1])
        assertEquals("hatsune_miku_(append)", result[2])
        assertEquals("hatsune_miku_(cosplay)", result[3])
        assertEquals("hatsuzuki_(kantai_collection)", result[4])
        assertEquals("hatsukaze_(kantai_collection)", result[5])
        assertEquals("hatsushimo_(kantai_collection)", result[6])
        assertEquals("hatsuharu_(kantai_collection)", result[7])
        assertEquals("hatsune_mikuo", result[8])
        assertEquals("hatsuzuki_527", result[9])
    }

    @Test
    fun `parse Json from autocomplete with Konachan Json-Object wrapper`() {
        val json = "[{\"id\":239,\"name\":\"hatsune_miku\",\"count\":13275,\"type\":4,\"ambiguous\":false}," +
                "{\"id\":59328,\"name\":\"hatsukoi_1/1\",\"count\":110,\"type\":3,\"ambiguous\":false}," +
                "{\"id\":49704,\"name\":\"hatsuyuki_sakura\",\"count\":94,\"type\":3,\"ambiguous\":false}," +
                "{\"id\":15823,\"name\":\"hatsune_mikuo\",\"count\":66,\"type\":4,\"ambiguous\":false}," +
                "{\"id\":95189,\"name\":\"hatsuzuki_(kancolle)\",\"count\":39,\"type\":4,\"ambiguous\":false}," +
                "{\"id\":2665,\"name\":\"hatsuseno_alpha\",\"count\":33,\"type\":4,\"ambiguous\":false}," +
                "{\"id\":77514,\"name\":\"hatsuyuki_(kancolle)\",\"count\":32,\"type\":4,\"ambiguous\":false}," +
                "{\"id\":36184,\"name\":\"hatsukoi_sacrament\",\"count\":31,\"type\":3,\"ambiguous\":false}," +
                "{\"id\":24820,\"name\":\"hatsuko\",\"count\":28,\"type\":1,\"ambiguous\":false}," +
                "{\"id\":81153,\"name\":\"hatsukaze_(kancolle)\",\"count\":26,\"type\":4,\"ambiguous\":false}]"
        val result = AutocompleteSearchParser().parse(json)
        assertEquals(10, result.size)
        assertEquals("hatsune_miku", result[0])
        assertEquals("hatsukoi_1/1", result[1])
        assertEquals("hatsuyuki_sakura", result[2])
        assertEquals("hatsune_mikuo", result[3])
        assertEquals("hatsuzuki_(kancolle)", result[4])
        assertEquals("hatsuseno_alpha", result[5])
        assertEquals("hatsuyuki_(kancolle)", result[6])
        assertEquals("hatsukoi_sacrament", result[7])
        assertEquals("hatsuko", result[8])
        assertEquals("hatsukaze_(kancolle)", result[9])
    }

    @Test
    fun `parse Json from autocomplete with Rule34 Json-Object wrapper`() {
        val json = "[{\"label\":\"hatsune_miku (5857)\",\"value\":\"hatsune_miku\"}," +
                "{\"label\":\"hatsu_inu (84)\",\"value\":\"hatsu_inu\"}," +
                "{\"label\":\"hatsuzuki_(kantai_collection) (72)\",\"value\":\"hatsuzuki_(kantai_collection)\"}," +
                "{\"label\":\"hatsukoi_1\\/1 (67)\",\"value\":\"hatsukoi_1\\/1\"}," +
                "{\"label\":\"hatsune_miku_(cosplay) (38)\",\"value\":\"hatsune_miku_(cosplay)\"}," +
                "{\"label\":\"hatsuinu (37)\",\"value\":\"hatsuinu\"}," +
                "{\"label\":\"hatsukoi_limited (36)\",\"value\":\"hatsukoi_limited\"}," +
                "{\"label\":\"hatsuen_kikan (28)\",\"value\":\"hatsuen_kikan\"}," +
                "{\"label\":\"hatsujou!_mesugaki-kyouwakoku_tennen_nikubenkitachi_no_kuni (27)\",\"value\":\"hatsujou!_mesugaki-kyouwakoku_tennen_nikubenkitachi_no_kuni\"}," +
                "{\"label\":\"hatsuyuki_(kantai_collection) (27)\",\"value\":\"hatsuyuki_(kantai_collection)\"}]"
        val result = AutocompleteSearchParser().parse(json)
        assertEquals(10, result.size)
        assertEquals("hatsune_miku", result[0])
        assertEquals("hatsu_inu", result[1])
        assertEquals("hatsuzuki_(kantai_collection)", result[2])
        assertEquals("hatsukoi_1/1", result[3])
        assertEquals("hatsune_miku_(cosplay)", result[4])
        assertEquals("hatsuinu", result[5])
        assertEquals("hatsukoi_limited", result[6])
        assertEquals("hatsuen_kikan", result[7])
        assertEquals("hatsujou!_mesugaki-kyouwakoku_tennen_nikubenkitachi_no_kuni", result[8])
        assertEquals("hatsuyuki_(kantai_collection)", result[9])
    }

    @Test(expected = ParseException::class)
    fun `parse Json from autocomplete with non parsable string`() {
        val notParsable = "{[Not parsable string]}"
        AutocompleteSearchParser().parse(notParsable)
    }


}