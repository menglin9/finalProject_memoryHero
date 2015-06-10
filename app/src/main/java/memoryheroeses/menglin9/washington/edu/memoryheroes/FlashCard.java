package memoryheroeses.menglin9.washington.edu.memoryheroes;

import java.util.Comparator;

/**
 * Created by Menglin on 5/23/15.
 */
public class FlashCard implements Comparable<FlashCard> {
    public String front;
    public String back;
    public int weight;

    public FlashCard() {
        front = "";
        back = "";
        weight = 0;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public int getWeight() {
        return weight;
    }


    public static class AgeComparator implements Comparator<FlashCard> {
        public int compare(FlashCard p1, FlashCard p2) {
            return p1.getWeight() - p2.getWeight();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FlashCard)) {
            return false;
        }

        FlashCard that = (FlashCard) other;

        // Custom equality check here.
        return this.front.equals(that.front) && this.back.equals(that.back) && this.weight == that.weight;
    }

    public int compareTo(FlashCard p) {
        int result = (int) (this.getWeight() - p.getWeight());
        result = -result;
        return result;
    }
}
