package com.fsci.games.controller;

import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.model.Character;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageEngine {
    private static final String PATH_FOR_CHARACTER="assets/characters/";

    private static Map<String ,Map<Character.State, Image>> characters;
    static {
        characters=new HashMap<>();
    }

    private static Map<Character.State,Image> character;
    private static void addCharacterState(Character.State state,String name){
        String imageName=state.toString().toLowerCase() + ".png";

        try {
            Image img=new Image( PATH_FOR_CHARACTER+"/"+name+"/"+ imageName);
            img.useSizeRatio(true);
            character.put(state,img);
        } catch (IOException e) {
            // here we handle if the character state not found
            e.printStackTrace();
        }
    }
    public static Map<Character.State, Image> loadCharacterImagesState(String characterName){
        if(characters.containsKey(characterName))return characters.get(characterName);

        character =new HashMap<>();
        addCharacterState(Character.State.IDLE,characterName);
        addCharacterState(Character.State.IDLE1,characterName);
        addCharacterState(Character.State.IDLE2,characterName);
        addCharacterState(Character.State.IDLE3,characterName);
        addCharacterState(Character.State.CHOCK,characterName);
        addCharacterState(Character.State.WALK1,characterName);
        addCharacterState(Character.State.WALK2,characterName);
        addCharacterState(Character.State.WALK3,characterName);
        addCharacterState(Character.State.WALK4,characterName);
        addCharacterState(Character.State.JUMP,characterName);
        addCharacterState(Character.State.JUMP1,characterName);
        addCharacterState(Character.State.JUMP2,characterName);
        addCharacterState(Character.State.JUMP3,characterName);
        return character;
    }
}
