package ru.dancat.metasearcher.backend

class ServerConnectionError(message: String?) : Exception(message)

class ServerBadRequest(message: String?) : Exception(message)