package com.makentoshe.repository

import java.io.Serializable

/**
 * Default image repository which contains byte arrays as an images.
 * The image can be returned by url which is represented by string.
 */
abstract class ImageRepository : Repository<String, ByteArray>, Serializable