package com.videoplay.browser.settings

/**
 * Manages search engine settings for the browser.
 */
class SearchEngineSettings {

    /**
     * Available search engines.
     */
    enum class SearchEngine(val displayName: String, val searchUrl: String) {
        GOOGLE("Google", "https://www.google.com/search?q=%s"),
        BING("Bing", "https://www.bing.com/search?q=%s"),
        DUCKDUCKGO("DuckDuckGo", "https://duckduckgo.com/?q=%s"),
        BRAVE("Brave", "https://search.brave.com/search?q=%s"),
        ECOSIA("Ecosia", "https://www.ecosia.org/search?q=%s"),
        CUSTOM("Custom", "")
    }

    private var currentSearchEngine: SearchEngine = SearchEngine.GOOGLE
    private var customSearchUrl: String = ""

    /**
     * Gets the current search engine.
     */
    fun getCurrentSearchEngine(): SearchEngine {
        return currentSearchEngine
    }

    /**
     * Sets the current search engine.
     * @param engine The search engine to set as current.
     */
    fun setCurrentSearchEngine(engine: SearchEngine) {
        currentSearchEngine = engine
    }

    /**
     * Gets the search URL for the current search engine.
     * @param query The search query.
     * @return The full search URL.
     */
    fun getSearchUrl(query: String): String {
        return when (currentSearchEngine) {
            SearchEngine.CUSTOM -> {
                if (customSearchUrl.isNotEmpty()) {
                    customSearchUrl.replace("%s", query)
                } else {
                    SearchEngine.GOOGLE.searchUrl.replace("%s", query)
                }
            }
            else -> currentSearchEngine.searchUrl.replace("%s", query)
        }
    }

    /**
     * Sets a custom search URL.
     * @param url The custom search URL (should contain %s for the query).
     */
    fun setCustomSearchUrl(url: String) {
        customSearchUrl = url
        currentSearchEngine = SearchEngine.CUSTOM
    }

    /**
     * Gets the custom search URL.
     */
    fun getCustomSearchUrl(): String {
        return customSearchUrl
    }

    /**
     * Gets all available search engines.
     */
    fun getAllSearchEngines(): List<SearchEngine> {
        return SearchEngine.values().toList()
    }

    /**
     * Gets the display name of the current search engine.
     */
    fun getCurrentSearchEngineName(): String {
        return when (currentSearchEngine) {
            SearchEngine.CUSTOM -> {
                if (customSearchUrl.isNotEmpty()) {
                    "Custom"
                } else {
                    SearchEngine.GOOGLE.displayName
                }
            }
            else -> currentSearchEngine.displayName
        }
    }

    /**
     * Resets to default search engine (Google).
     */
    fun resetToDefault() {
        currentSearchEngine = SearchEngine.GOOGLE
        customSearchUrl = ""
    }
}
