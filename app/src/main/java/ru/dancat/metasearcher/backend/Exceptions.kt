package ru.dancat.metasearcher.backend

class ServerConnectionError(message: String?) : Exception(message)

//TODO: Kotlin reflection library, KClass<*> -> data class
class ServerBadRequest(message: String?, request: Class<*>? = null) : Exception(message)