package sk.uniba.fmph.dcs.game_board;

import org.json.JSONArray;
import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.ImmediateEffect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Stack;

public class CivilisationCardDeckFactory {
    private static final File file = new File("resources/cards");

    public static CivilisationCardDeck createCivilisationCardDeck() throws IOException {
        String jsonContent = new String(Files.readAllBytes(file.toPath()));

        JSONObject jsonData = new JSONObject(jsonContent);

        JSONArray cardsArray = jsonData.getJSONArray("buildings");
        Stack<CivilisationCard> cards = new Stack<>();

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            JSONArray immediateEffectsJson = cardJson.getJSONArray("immediateEffects");
            ArrayList<ImmediateEffect> immediateEffects = new ArrayList<>();
            for (int j = 0; j < immediateEffectsJson.length(); j++) {
                immediateEffects.add(immediateEffectsJson.getEnum(ImmediateEffect.class, j));
            }

            JSONArray endOfGameEffectsJson = cardJson.getJSONArray("endOfGameEffects");
            ArrayList<EndOfGameEffect> endOfGameEffects = new ArrayList<>();
            for (int j = 0; j < endOfGameEffectsJson.length(); j++) {
                endOfGameEffects.add(endOfGameEffectsJson.getEnum(EndOfGameEffect.class, j));
            }

            cards.push(new CivilisationCard(immediateEffects.toArray(new ImmediateEffect[0]),
                    endOfGameEffects.toArray(new EndOfGameEffect[0])));
        }

        return new CivilisationCardDeck(cards);
    }

    public static CivilisationCardDeck createCivilisationCardDeckFromJSON(final File file) throws IOException {
        String jsonContent = new String(Files.readAllBytes(file.toPath()));

        JSONObject jsonData = new JSONObject(jsonContent);

        JSONArray cardsArray = jsonData.getJSONArray("buildings");
        Stack<CivilisationCard> cards = new Stack<>();

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            JSONArray immediateEffectsJson = cardJson.getJSONArray("immediateEffects");
            ArrayList<ImmediateEffect> immediateEffects = new ArrayList<>();
            for (int j = 0; j < immediateEffectsJson.length(); j++) {
                immediateEffects.add(immediateEffectsJson.getEnum(ImmediateEffect.class, j));
            }

            JSONArray endOfGameEffectsJson = cardJson.getJSONArray("endOfGameEffects");
            ArrayList<EndOfGameEffect> endOfGameEffects = new ArrayList<>();
            for (int j = 0; j < endOfGameEffectsJson.length(); j++) {
                endOfGameEffects.add(endOfGameEffectsJson.getEnum(EndOfGameEffect.class, j));
            }

            cards.push(new CivilisationCard(immediateEffects.toArray(new ImmediateEffect[0]),
                    endOfGameEffects.toArray(new EndOfGameEffect[0])));
        }

        return new CivilisationCardDeck(cards);
    }

}
