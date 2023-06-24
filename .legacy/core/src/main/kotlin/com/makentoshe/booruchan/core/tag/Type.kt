package com.makentoshe.booruchan.core.tag

import java.io.Serializable

/**
 * There are five types of tags: artist, character, copyright, general, and meta.
 *
 * Artist tags are red,
 * characters are green,
 * copyrights are purple,
 * general tags are blue or black,
 * and meta tags are orange.
 */
enum class Type: Serializable {

    /**
     * Artist tags identify the creator of a post.
     * This doesn't mean the creator of the original copyrighted artwork.
     *
     * For example, you wouldn't use the Barasui tag on a picture of Matsuoka Miu
     * drawn by Naruko Hanaharu.
     */
    ARTIST,

    /**
     * Meta tags generally describe things beyond the content of the image itself.
     * Examples include "translated", "copyright_request", "duplicate", "image_sample", and "bad_id".
     */
    METADATA,

    /**
     * Copyright tags identify the anime, manga, game, or novel that the post is associated with.
     * More generally, copyright tags identify the source material that the characters are from.
     */
    COPYRIGHT,

    /**
     * Character tags identify the characters in the post.
     * There are several name orderings based on last name and first name
     * and also may contain special expression for describing tag.
     *
     * For example, Danbooru uses LastName_FirstName ordering,
     * so the tag can have hatsune_miku_(cosplay) name.
     */
    CHARACTER,

    /**
     * General tags are used for everything else. General tags objectively describes the contents of the post.
     */
    GENERAL
}

