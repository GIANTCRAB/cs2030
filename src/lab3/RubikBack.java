package lab3;

public class RubikBack extends Rubik {
    RubikBack(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik newRubik = this.backView().right();
        return newRubik.backView();
    }

    @Override
    public Rubik left() {
        Rubik newRubik = this.backView().left();
        return newRubik.backView();
    }

    @Override
    public Rubik half() {
        Rubik newRubik = this.backView().half();
        return newRubik.backView();
    }
}
