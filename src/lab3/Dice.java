package lab3;

import java.util.ArrayList;

public class Dice implements SideViewable {
    private final ArrayList<Character> diceSides;

    Dice() {
        this.diceSides = new ArrayList<>();
        this.diceSides.add('U');
        this.diceSides.add('F');
        this.diceSides.add('R');
        this.diceSides.add('B');
        this.diceSides.add('L');
        this.diceSides.add('D');
    }

    private Dice(ArrayList<Character> diceSides) {
        this.diceSides = Dice.cloneSides(diceSides);
    }

    private ArrayList<Character> getDiceSides() {
        return Dice.cloneSides(this.diceSides);
    }

    public Dice upView() {
        final ArrayList<Character> clonedDiceSides = this.getDiceSides();
        final Character firstPos = clonedDiceSides.get(0);
        final Character secondPos = clonedDiceSides.get(1);
        final Character fourthPos = clonedDiceSides.get(3);
        final Character sixthPos = clonedDiceSides.get(5);

        final ArrayList<Character> newDiceSides = this.getDiceSides();
        newDiceSides.set(0, fourthPos);
        newDiceSides.set(1, firstPos);
        newDiceSides.set(3, sixthPos);
        newDiceSides.set(5, secondPos);

        return new Dice(newDiceSides);
    }

    public Dice frontView() {
        return new Dice(this.getDiceSides());
    }

    public Dice rightView() {
        final ArrayList<Character> clonedDiceSides = this.getDiceSides();
        final Character secondPos = clonedDiceSides.get(1);
        final Character thirdPos = clonedDiceSides.get(2);
        final Character fourthPos = clonedDiceSides.get(3);
        final Character fifthPos = clonedDiceSides.get(4);

        final ArrayList<Character> newDiceSides = this.getDiceSides();
        newDiceSides.set(1, thirdPos);
        newDiceSides.set(2, fourthPos);
        newDiceSides.set(3, fifthPos);
        newDiceSides.set(4, secondPos);

        return new Dice(newDiceSides);
    }

    public Dice backView() {
        return this.rightView().rightView();
    }

    public Dice leftView() {
        return this.rightView().rightView().rightView();
    }

    public Dice downView() {
        return this.upView().upView().upView();
    }

    private static ArrayList<Character> cloneSides(ArrayList<Character> sides) {
        final ArrayList<Character> sideClone = new ArrayList<>(sides.size());
        for (Character side : sides) {
            sideClone.add(new Character(side));
        }
        return sideClone;
    }

    @Override
    public String toString() {
        final ArrayList<Character> clonedDiceSides = this.getDiceSides();

        return String.format("\n%c\n%c%c%c%c\n   %c",
                clonedDiceSides.get(0),
                clonedDiceSides.get(1),
                clonedDiceSides.get(2),
                clonedDiceSides.get(3),
                clonedDiceSides.get(4),
                clonedDiceSides.get(5)
        );
    }
}
