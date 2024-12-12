package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public final class BuildingsFactory {

    private static final File FILE = new File("src/main/resources/buildings.json");

    public static ArrayList<Building> createBuildings() throws IOException {
        String jsonContent = new String(Files.readAllBytes(FILE.toPath()));

        JSONObject jsonData = new JSONObject(jsonContent);

        JSONArray buildingsArray = jsonData.getJSONArray("buildings");
        ArrayList<Building> buildings = new ArrayList<>();

        for (int i = 0; i < buildingsArray.length(); i++) {
            JSONObject buildingJson = buildingsArray.getJSONObject(i);

            String buildingType = buildingJson.getString("class");
            switch (buildingType) {
                case "ArbitraryBuilding":
                    buildings.add(new ArbitraryBuilding());
                    break;
                case "VariableBuilding":
                    int numberOfResources = buildingJson.getInt("numberOfResources");
                    int numberOfResourceTypes = buildingJson.getInt("numberOfResourceTypes");
                    buildings.add(new VariableBuilding(numberOfResources, numberOfResourceTypes));
                    break;
                case "SimpleBuilding":
                    JSONArray resourcesJson = buildingJson.getJSONArray("resources");
                    ArrayList<Effect> resources = new ArrayList<>();
                    for (int j = 0; j < resourcesJson.length(); j++) {
                        resources.add(resourcesJson.getEnum(Effect.class, j));
                    }
                    buildings.add(new SimpleBuilding(resources.toArray(new Effect[0])));
                    break;
                default:
                    break;
            }
        }

        return buildings;
    }

    public static ArrayList<Building> createBuildingsFromJSON(final File file) throws IOException {
        String jsonContent = new String(Files.readAllBytes(file.toPath()));

        JSONObject jsonData = new JSONObject(jsonContent);

        JSONArray buildingsArray = jsonData.getJSONArray("buildings");
        ArrayList<Building> buildings = new ArrayList<>();

        for (int i = 0; i < buildingsArray.length(); i++) {
            JSONObject buildingJson = buildingsArray.getJSONObject(i);

            String buildingType = buildingJson.getString("class");
            switch (buildingType) {
                case "ArbitraryBuilding":
                    buildings.add(new ArbitraryBuilding());
                    break;
                case "VariableBuilding":
                    int numberOfResources = buildingJson.getInt("numberOfResources");
                    int numberOfResourceTypes = buildingJson.getInt("numberOfResourceTypes");
                    buildings.add(new VariableBuilding(numberOfResources, numberOfResourceTypes));
                    break;
                case "SimpleBuilding":
                    JSONArray resourcesJson = buildingJson.getJSONArray("resources");
                    ArrayList<Effect> resources = new ArrayList<>();
                    for (int j = 0; j < resourcesJson.length(); j++) {
                        resources.add(resourcesJson.getEnum(Effect.class, j));
                    }
                    buildings.add(new SimpleBuilding(resources.toArray(new Effect[0])));
                    break;
                default:
                    break;
            }
        }

        return buildings;
    }
}
