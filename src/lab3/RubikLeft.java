package lab3;

public class RubikLeft extends Rubik {
    RubikLeft(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik newRubik = super.leftView().right();
        return newRubik.rightView();
    }

    @Override
    public Rubik left() {
        Rubik newRubik = super.leftView().left();
        return newRubik.rightView();
    }

    @Override
    public Rubik half() {
        Rubik newRubik = super.leftView().half();
        return newRubik.rightView();
    }
}
