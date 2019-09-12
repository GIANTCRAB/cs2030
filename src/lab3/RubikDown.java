package lab3;

public class RubikDown extends Rubik {
    RubikDown(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik newRubik = this.downView().right();
        return newRubik.upView();
    }

    @Override
    public Rubik left() {
        Rubik newRubik = this.downView().left();
        return newRubik.upView();
    }

    @Override
    public Rubik half() {
        Rubik newRubik = this.downView().half();
        return newRubik.upView();
    }
}
