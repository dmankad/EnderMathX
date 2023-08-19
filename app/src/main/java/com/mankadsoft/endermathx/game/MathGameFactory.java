package com.mankadsoft.endermathx.game;

public class MathGameFactory {
    
    public static MathGame GenerateGame(String gameName) {
        if(gameName.equals(SimpleAdditionGame.GAME_NAME)) {
                return new SimpleAdditionGame();
        }
        if(gameName.equals(ComplexAdditionGame.GAME_NAME)) {
            return new ComplexAdditionGame();
        }
        if(gameName.equals(SimpleMultiplication.GAME_NAME)) {
            return new SimpleMultiplication();
        }
        if(gameName.equals(SimpleSubtractionGame.GAME_NAME)) {
            return new SimpleSubtractionGame();
        }
        return null;
    }
    
}
