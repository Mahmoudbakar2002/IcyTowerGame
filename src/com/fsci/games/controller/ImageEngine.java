package com.fsci.games.controller;

import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.model.Character;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  Image Engine Class use for loading and read images from internal package
 * */
public class ImageEngine {

    /****************************************
     *  final Variables
     ***************************************/
    private static final String PATH_FOR_CHARACTER="assets/characters/";


    // characters Map save loaded Characters to prevent load it many times
    private static Map<String ,Map<Character.State, Image>> characters;
    static {
        characters=new HashMap<>();
    }




    /**
     *  function to load image for character state
     *      convert state name to string and load it
     *
     * @param character character map to add image in it.
     * @param name character folder name to load image from it
     * @param state state of image to load
     * */
    private static void addCharacterState(Map<Character.State,Image> character,
                                          String name,
                                          Character.State state){

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


    /**
     *  Function to load character images from packages by path character name
     * @param characterName  character folder name to load it
     * */
    public static Map<Character.State, Image> loadCharacterImagesState(String characterName){
        // if character loaded before return it
        if(characters.containsKey(characterName))return characters.get(characterName);

        Map<Character.State,Image> character =new HashMap<>();

        /* load character state images */
        addCharacterState(character,characterName,Character.State.IDLE);
        addCharacterState(character,characterName,Character.State.IDLE1);
        addCharacterState(character,characterName,Character.State.IDLE2);
        addCharacterState(character,characterName,Character.State.IDLE3);
        addCharacterState(character,characterName,Character.State.CHOCK);
        addCharacterState(character,characterName,Character.State.WALK1);
        addCharacterState(character,characterName,Character.State.WALK2);
        addCharacterState(character,characterName,Character.State.WALK3);
        addCharacterState(character,characterName,Character.State.WALK4);
        addCharacterState(character,characterName,Character.State.JUMP);
        addCharacterState(character,characterName,Character.State.JUMP1);
        addCharacterState(character,characterName,Character.State.JUMP2);
        addCharacterState(character,characterName,Character.State.JUMP3);
        return character;
    }
}
