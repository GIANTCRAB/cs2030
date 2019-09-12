package lab3;

public class RubikUp extends Rubik {
    RubikUp(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik newRubik = this.upView().right();
        return newRubik.downView();
    }

    @Override
    public Rubik left() {
        Rubik newRubik = this.upView().left();
        return newRubik.downView();
    }

    @Override
    public Rubik half() {
        Rubik newRubik = this.upView().half();
        return newRubik.downView();
    }
}
