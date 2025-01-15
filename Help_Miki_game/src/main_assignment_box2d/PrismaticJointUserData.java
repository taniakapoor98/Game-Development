package main_assignment_box2d;

class PrismaticJointUserData {
    private boolean reverse;

    public PrismaticJointUserData() {
        this.reverse = false;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}