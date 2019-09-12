package lab3;

public class RubikRight extends Rubik {
    RubikRight(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik newRubik = super.rightView().right();
        return newRubik.leftView();
    }

    @Override
    public Rubik left() {
        Rubik newRubik = super.rightView().left();
        return newRubik.leftView();
    }

    @Override
    public Rubik half() {
        Rubik newRubik = super.rightView().half();
        return newRubik.leftView();
    }
}
