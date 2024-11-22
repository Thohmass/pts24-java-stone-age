package sk.uniba.fmph.dcs.player_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerToolsTest {
    private PlayerTools playerTools;

    @BeforeEach
    void setUp() {
        playerTools = new PlayerTools();
    }

    @Test
    void testInitialState() {
        assertEquals("All tools: 0 0 0Available tools: 0 0 0", playerTools.state());
    }

    @Test
    void testAddTool() {
        // First tool should increment tools[0]
        playerTools.addTool();
        assertEquals("All tools: 1 0 0Available tools: 1 0 0", playerTools.state());

        // Second tool should increment tools[0] again as it's still the smallest
        playerTools.addTool();
        assertEquals("All tools: 1 1 0Available tools: 1 1 0", playerTools.state());

        // Third tool should increment tools[1] as tools[0] is now larger
        playerTools.addTool();
        playerTools.addTool();
        assertEquals("All tools: 2 1 1Available tools: 2 1 1", playerTools.state());
    }

    @Test
    void testAddSingleUseTool() {
        playerTools.addSingleUseTool(3);
        assertEquals("All tools: 0 0 0 3Available tools: 0 0 0 3", playerTools.state());
    }

    @Test
    void testUseTool() {
        // Add some tools first
        playerTools.addTool(); // adds to tools[0]
        playerTools.addTool(); // adds to tools[1]
        playerTools.addSingleUseTool(3); // adds single-use tool

        // Use permanent tool
        int result = playerTools.useTool(0);
        assertEquals(1, result);
        assertEquals("All tools: 1 1 0 3Available tools: 1 0 3", playerTools.state());

        // Use single-use tool
        result = playerTools.useTool(3);
        assertEquals(3, result);
        // Single-use tool should be removed
        assertEquals("All tools: 1 1 0Available tools: 1 0", playerTools.state());
    }

    @Test
    void testNewTurn() {
        playerTools.addTool();
        playerTools.useTool(0); // Use the first tool
        // Tool should be marked as used
        assertFalse(playerTools.state().contains("Available tools: 1"));

        playerTools.newTurn();
        // After new turn, tool should be available again
        assertTrue(playerTools.state().contains("Available tools: 1"));
    }

    @Test
    void testHasSufficientTools() {
        // Initial state (all tools are 0)
        assertFalse(playerTools.hasSufficientTools(1));

        // Add some tools
        playerTools.addTool(); // adds 1 to tools[0]
        playerTools.addTool(); // adds 1 to tools[1]
        playerTools.addSingleUseTool(3); // adds single-use tool with strength 3

        // Total available strength is 5 (2 from permanent tool + 3 from single-use)
        assertTrue(playerTools.hasSufficientTools(4));

        // Use permanent tool
        playerTools.useTool(0);
        playerTools.useTool(1);
        // Now only 3 strength available (from single-use tool)
        assertFalse(playerTools.hasSufficientTools(4));
        assertTrue(playerTools.hasSufficientTools(2));
    }

    @Test
    void testUsedToolsTracking() {
        playerTools.addTool(); // adds to tools[0]
        playerTools.addTool(); // adds to tools[1]

        // Use first tool
        playerTools.useTool(0);
        String state = playerTools.state();
        // Tool should appear in All tools but not in Available tools
        assertTrue(state.contains("All tools: 1 1 0"));
        assertFalse(state.contains("Available tools: 1 1"));
    }
}
