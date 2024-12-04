package sk.uniba.fmph.dcs.player_board;

import java.util.Arrays;

public final class PlayerTools {

    private int[] tools = { 0, 0, 0 };
    private final int baseToolsNumber = 3;
    private final boolean[] usedTools = { false, false, false };

    public void newTurn() {
        Arrays.fill(usedTools, false);
    }

    public void addTool() {
        if (tools[0] > tools[1]) {
            tools[1]++;
        } else if (tools[1] > tools[2]) {
            tools[2]++;
        } else {
            tools[0]++;
        }
    }

    public void addSingleUseTool(final int strength) {
        tools = Arrays.copyOf(tools, tools.length + 1);
        tools[tools.length - 1] = strength;
    }

    public int useTool(final int index) {
        // get the result
        int result = tools[index];
        // Tag the tool as used
        if (index < baseToolsNumber) {
            usedTools[index] = true;
        } else {
            // Spend the one time tool and remove it from the tools array
            for (int i = index; i < tools.length; i++) {
                if (i + 1 != tools.length) {
                    tools[i] = tools[i + 1];
                }
            }
            tools = Arrays.copyOf(tools, tools.length - 1);
        }

        return result;
    }

    public boolean hasSufficientTools(final int goal) {
        int sum = 0;

        // count the sum of all available tools
        for (int i = 0; i < tools.length; i++) {
            // if the tool is used, do not count it
            if (i < baseToolsNumber && usedTools[i]) {
                continue;
            }
            sum += tools[i];
        }

        return goal < sum;
    }

    public String state() {
        StringBuilder stringBuilder = new StringBuilder();
        // All tools: tools[0] tools[1] tools[2] ...
        stringBuilder.append("All tools:");
        for (int tool : tools) {
            stringBuilder.append(" ").append(tool);
        }
        // Available tools: tools[0] tools[3] ...
        stringBuilder.append("Available tools:");
        for (int i = 0; i < tools.length; i++) {
            if (i < baseToolsNumber && usedTools[i]) {
                continue;
            }
            stringBuilder.append(" ").append(tools[i]);
        }

        return stringBuilder.toString();
    }
}
