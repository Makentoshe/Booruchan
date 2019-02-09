package com.makentoshe.controllers

import io.reactivex.subjects.Subject

abstract class ByteArrayRxController<A>(
    observable: Subject<ByteArray>
): SimpleRxController<ByteArray, A>(observable)