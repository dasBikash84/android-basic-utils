package com.dasbikash.android_basic_utils.exceptions

class NoInternertConnectionException : RuntimeException {

    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(cause: Throwable) : super(cause) {}
}
