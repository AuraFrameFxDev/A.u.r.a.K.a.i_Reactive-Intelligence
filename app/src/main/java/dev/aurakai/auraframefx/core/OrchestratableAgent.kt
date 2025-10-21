package dev.aurakai.auraframefx.core

import kotlinx.coroutines.CoroutineScope

/**
 * OrchestratableAgent: Interface contract for all managed agents
 *
 * Defines the lifecycle and behavior expectations for agents that can be
 * managed by the GenesisOrchestrator. Each primary agent domain (Aura, Kai,
 * Cascade, OracleDrive) must implement this interface.
 */
interface OrchestratableAgent {

    /**
     * Unique identifier for this agent
     */
    val agentName: String

    /**
     * Initialize the agent with a dedicated coroutine scope.
     * Called once during platform startup, before start() is called.
     *
     * @param scope The CoroutineScope for this agent's lifecycle
     */
    suspend fun initialize(scope: CoroutineScope)

    /**
     * Start agent operations and background tasks.
     * Called after all agents have been initialized.
     */
    suspend fun start()

    /**
     * Pause agent operations, releasing non-critical resources.
     * The agent should be able to resume from this state.
     */
    suspend fun pause()

    /**
     * Resume agent operations from a paused state.
     */
    suspend fun resume()

    /**
     * Gracefully shutdown the agent, releasing all resources.
     * Called during platform termination.
     */
    suspend fun shutdown()
}
