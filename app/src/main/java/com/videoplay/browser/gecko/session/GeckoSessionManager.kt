package com.videoplay.browser.gecko.session

import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession

/**
 * Manages GeckoSession instances for browser tabs.
 * Each tab has its own GeckoSession for isolated browsing.
 */
class GeckoSessionManager(private val runtime: GeckoRuntime) {

    private val sessions = mutableListOf<GeckoSession>()

    /**
     * Creates a new GeckoSession and adds it to the list.
     * @return The newly created GeckoSession.
     */
    fun createSession(): GeckoSession {
        val session = GeckoSession().apply {
            open(runtime)
        }
        sessions.add(session)
        return session
    }

    /**
     * Removes a GeckoSession from the list.
     * @param session The GeckoSession to remove.
     */
    fun removeSession(session: GeckoSession) {
        sessions.remove(session)
        session.close()
    }

    /**
     * Removes all GeckoSessions.
     */
    fun clearSessions() {
        sessions.forEach { it.close() }
        sessions.clear()
    }

    /**
     * Returns the list of all GeckoSessions.
     */
    fun getSessions(): List<GeckoSession> {
        return sessions.toList()
    }
}
