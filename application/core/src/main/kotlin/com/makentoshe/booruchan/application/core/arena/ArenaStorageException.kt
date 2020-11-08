package com.makentoshe.booruchan.application.core.arena

/** Cause in ArenaStorage operations */
class ArenaStorageException(override val message: String, override val cause: Throwable? = null): Exception()