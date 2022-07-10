package com.deflatedpickle.rawky.server.backend.api.packet

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Server

interface ServerPacket : Packet {
    fun runServer(connection: Connection, server: Server)
}