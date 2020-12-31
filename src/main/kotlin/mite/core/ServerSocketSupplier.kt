package mite.core

import java.io.FileInputStream
import java.net.ServerSocket
import java.security.KeyStore
import javax.net.ServerSocketFactory
import javax.net.ssl.*

/**
 * Supplies sockets ready to serve on requested port.
 */
object ServerSocketSupplier {

    fun port(port:Int,secure:Boolean=false) = if (secure) secure(port) else normal(port)

    private fun normal(port:Int = 80) = ServerSocket(port)

    // See https://docs.oracle.com/javase/10/security/sample-code-illustrating-secure-socket-connection-client-and-server.htm
    private fun secure(port:Int = 443, needClientAuth:Boolean = false): ServerSocket {
        val factory = getTlsServerSocketFactory()
        val socket = factory.createServerSocket(port)
        (socket as SSLServerSocket).needClientAuth = needClientAuth
        return socket
    }

    private fun getTlsServerSocketFactory(): ServerSocketFactory {
        return getSSLContext().serverSocketFactory
    }

    private fun getSSLContext(): SSLContext {
        val context = SSLContext.getInstance("TLS")
        context.init(getKeyManagers(), null, null)
        return context
    }

    private fun getKeyManagers(): Array<KeyManager> {
        val kmf: KeyManagerFactory = KeyManagerFactory.getInstance("SunX509")
        val keyStore = KeyStore.getInstance("JKS")
        val passphrase = "passphrase".toCharArray()
        keyStore.load(FileInputStream("testkeys"), passphrase)
        kmf.init(keyStore, passphrase)
        return kmf.keyManagers
    }

}