package memoryheroeses.menglin9.washington.edu.memoryheroes;

import java.util.ArrayList;

/**
 * Created by Menglin on 5/23/15.
 */
public class Category {
    String name;
    ArrayList<FlashCard> flashCard;
    ArrayList<String> choices;

    public Category() {
        flashCard = new ArrayList<FlashCard>();
        choices = new ArrayList<String>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlashCard(ArrayList<FlashCard> flashCard) {
        this.flashCard = flashCard;
    }

    public void setChoices (ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public ArrayList<FlashCard> getFlashCard() {
        return flashCard;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

}
