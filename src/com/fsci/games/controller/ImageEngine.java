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
    private static final String PATH_FOR_BACKGROUND="assets/bg.png";
    private static final String PATH_FOR_GAME_OVER="assets/gameover.png";

    private static  Image bgImage,gameOverWord,backGroundShadow;

    // characters Map save loaded Characters to prevent load it many times
    private static Map<String ,Map<Character.State, Image>> characters;
    static {
        characters=new HashMap<>();

        try {
            bgImage=new Image(PATH_FOR_BACKGROUND);
            gameOverWord=new Image(PATH_FOR_GAME_OVER);
            backGroundShadow=new Image("assets/black-shadow.png");

        }catch (IOException ex){
            System.out.println("Error in load Images :" + ex.getMessage() );
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

        /*
        *   load character state images
        *  convert state name to string and load it
        */
        for(Character.State state :Character.State.values()){
            String imageName=state.toString().toLowerCase() + ".png";

            try {
                Image img=new Image( PATH_FOR_CHARACTER+"/"+characterName+"/"+ imageName);
                img.useSizeRatio(true);
                character.put(state,img);
            } catch (IOException e) {
                // here we handle if the character state not found
                e.printStackTrace();
            }
        }

        // save character images to prevent load it later
        characters.put(characterName,character);

        return character;
    }

    public static Image getBackGroundImage(){
        return bgImage;
    }
    public static Image getGameOverImage(){
        gameOverWord.useSizeRatio(true);
        return gameOverWord;
    }

    public static Image getBackGroundShadow() {
        return backGroundShadow;
    }
}
